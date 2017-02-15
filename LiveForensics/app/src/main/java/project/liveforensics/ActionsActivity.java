package project.liveforensics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import project.modelClass.DiscoveryBean;

/**
 * Created by Mohammad-Ghouri on 1/3/17.
 */
public class ActionsActivity extends Activity implements View.OnClickListener {
    private Button discoverButton;
    private DiscoveryBean discoveryBean;
    private ProgressDialog pDialog;
//    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        discoverButton = (Button) findViewById(R.id.discovery);
        discoverButton.setOnClickListener(this);
        discoveryBean = new DiscoveryBean();

//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(ConstantUtils.REGISTRATION_COMPLETE)) {
//                    Toast.makeText(getApplicationContext(), "REGISTERED ON FCM", Toast.LENGTH_SHORT).show();
////                    SharedPreferences pref = getApplicationContext().getSharedPreferences(ConstantUtils.SHARED_PREF, 0);
////                    String regId = pref.getString("regId", null);
////                    Log.e(TAG, "Firebase reg id: " + regId);
//                }
//            }
//        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discovery:
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

                discoveryBean.setUserRefId(cUtils.smartClientID);
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
                new Interrogate().execute(cUtils.smartClientID, discoveryBean.toString());
                break;
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
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ActionsActivity.this);
            pDialog.setMessage("Submitting Discovered Data");
            pDialog.setCancelable(false);
            pDialog.show();
        }

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
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Data Submitted", Toast.LENGTH_SHORT).show();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
