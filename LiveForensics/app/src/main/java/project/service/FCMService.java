package project.service;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import project.liveforensics.ConstantUtils;
import project.liveforensics.R;
import project.modelClass.DiscoveryBean;


public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.getSimpleName();
    private DiscoveryBean discoveryBean;

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
//            JSONObject data = json.getJSONObject("data");

            String userRefId = json.getString("userRefId");
            String flowName = json.getString("flowName");
            String flowDate=json.getString("flowDate");
            int transactionID = json.getInt("transactionID");

            if(flowName.equalsIgnoreCase("Discover")){
                discoveryBean = new DiscoveryBean();
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

                discoveryBean.setUserRefId(userRefId);
                discoveryBean.setSystem("Android");
                discoveryBean.setNode(manufacturer + " " + myDeviceModel);
                discoveryBean.setRelease(fName);
                discoveryBean.setVersion(sysVersion);
                discoveryBean.setMachine(arch);
                discoveryBean.setKernel(kernel);
                discoveryBean.setFQDN(manufacturer + " " + myDeviceModel);
                discoveryBean.setInstallDate(dateText);
                discoveryBean.setClientName(appName);
                discoveryBean.setClientVersion(version);
                discoveryBean.setBuildTime(installedSQlDate);
                discoveryBean.setClientDescription(appName + " " + user + " " + arch);
                discoveryBean.setMacAddress(macAddress);
                discoveryBean.setIpv4(ipv4);
                discoveryBean.setIpv6(ipv6);
                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                discoveryBean.setCreatedDate(sqlDate);
                discoveryBean.setTransactionId(transactionID);
                new Interrogate().execute(cUtils.smartClientID, discoveryBean.toString());
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
    private String longToDateString(long time) {
        Date date1 = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(date1);
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

                URL url = new URL(ConstantUtils.DISCOVER_URL + ID);
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
}
