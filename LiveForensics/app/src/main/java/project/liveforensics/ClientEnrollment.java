package project.liveforensics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import project.modelClass.MyUserBean;

public class ClientEnrollment extends Activity {
    private Button clientEnroll;
    private ProgressDialog pDialog;
    private MyUserBean initialUser;
    private SessionManager session;
    private LinearLayout registerLayout;
    private RelativeLayout constantLay;
    public static String clientID;
    private EditText userName, fullName;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_enrollment);
        initGUI();
        registerLayout.setVisibility(View.GONE);

        session = new SessionManager(getApplicationContext());
        boolean flag = session.checkEnroll();
        if (flag) {
            constantLay.setVisibility(View.VISIBLE);
//            startActivity(new Intent(ClientEnrollment.this, ActionsActivity.class));
//            finish();
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(ConstantUtils.REGISTRATION_COMPLETE)) {
                    Toast.makeText(getApplicationContext(), "REGISTERED ON FCM", Toast.LENGTH_SHORT).show();
                    registerLayout.setVisibility(View.VISIBLE);

                }
            }
        };

        initialUser = new MyUserBean();
        clientEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                clientID = "C." + telephonyManager.getDeviceId();
                String appDirectory = getApplicationInfo().dataDir;
                java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                SharedPreferences pref = getApplicationContext().getSharedPreferences(ConstantUtils.SHARED_PREF, 0);
                String regId = pref.getString("regId", null);
                initialUser.setUserRefId(clientID);
                initialUser.setUserName(userName.getText().toString());
                initialUser.setFullName(fullName.getText().toString());
                initialUser.setFirstLogOn(sqlDate);
                initialUser.setLastLogOn(sqlDate);
                initialUser.setHomeDir(appDirectory);
                initialUser.setFirebaseRegID(regId);
                new Enroll().execute(initialUser.toString());
            }
        });
    }

    private void initGUI() {
        clientEnroll = (Button) findViewById(R.id.clientEnrollment);
        userName = (EditText) findViewById(R.id.userName);
        fullName = (EditText) findViewById(R.id.fullName);
        registerLayout = (LinearLayout) findViewById(R.id.registerLayout);
        constantLay=(RelativeLayout) findViewById(R.id.constantLay);
    }


    private class Enroll extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ClientEnrollment.this);
            pDialog.setMessage("Enrolling Client");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonData = strings[0];
            try {

                URL url = new URL(ConstantUtils.ENROLL_URL + clientID);
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
//                super.onPostExecute(result);
                pDialog.dismiss();
                session.createSession();
//                startActivity(new Intent(ClientEnrollment.this,
//                        ActionsActivity.class));
//                finish();
                registerLayout.setVisibility(View.GONE);
                constantLay.setVisibility(View.VISIBLE);
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(ConstantUtils.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
