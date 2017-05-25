package project.liveforensics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import static project.liveforensics.ConstantUtils.PERMISSIONS_REQUIRED;
import static project.liveforensics.ConstantUtils.PERMISSION_CALLBACK_CONSTANT;
import static project.liveforensics.ConstantUtils.REQUEST_PERMISSION_SETTING;

public class ClientEnrollment extends Activity {
    private Button clientEnroll, checkPermissions;
    private ProgressDialog pDialog;
    private MyUserBean initialUser;
    private SessionManager session;
    private LinearLayout registerLayout;
    private RelativeLayout constantLay;
    public static String clientID;
    private EditText userName, fullName;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences permissionStatus,buttonStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_enrollment);
        initGUI();
        registerLayout.setVisibility(View.GONE);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
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

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        buttonStatus = getSharedPreferences("buttonStatus", MODE_PRIVATE);

        if(buttonStatus.getBoolean("buttonStatus",false)){
            clientEnroll.setEnabled(true);
        }

        checkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[0]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[1]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[2]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[3]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[4]) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[5]) != PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[0])
                            || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[1])
                            || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[2])
                            || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[3])
                            || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[4])
                            || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[5])) {
                        //Show Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClientEnrollment.this);
                        builder.setTitle("Alert!");
                        builder.setMessage("Accept all permissions to proceed.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(ClientEnrollment.this, PERMISSIONS_REQUIRED, PERMISSION_CALLBACK_CONSTANT);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else if (permissionStatus.getBoolean(PERMISSIONS_REQUIRED[0], false)) {
                        //Previously Permission Request was cancelled with 'Dont Ask Again',
                        // Redirect to Settings after showing Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClientEnrollment.this);
                        builder.setTitle("Alert!");
                        builder.setMessage("Accept all permissions to proceed.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                Toast.makeText(getBaseContext(), "Go to Permissions", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        //just request the permission
                        ActivityCompat.requestPermissions(ClientEnrollment.this, PERMISSIONS_REQUIRED, PERMISSION_CALLBACK_CONSTANT);
                    }


                    SharedPreferences.Editor editor = permissionStatus.edit();
                    editor.putBoolean(PERMISSIONS_REQUIRED[0], true);
                    editor.commit();
                } else {
                    //You already have the permission, just go ahead.
                    proceedAfterPermission();
                }
            }
        });
        
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
        checkPermissions= (Button) findViewById(R.id.checkPermissions);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ClientEnrollment.this, PERMISSIONS_REQUIRED[5])) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientEnrollment.this);
                builder.setTitle("Alert!");
                builder.setMessage("Accept all permissions to proceed.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(ClientEnrollment.this, PERMISSIONS_REQUIRED, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permissions", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[0]) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[1]) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[2]) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[3]) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[4]) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ClientEnrollment.this, PERMISSIONS_REQUIRED[5]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            } else {
                Toast.makeText(getBaseContext(), "Accept all permissions", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void proceedAfterPermission() {

        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = buttonStatus.edit();
        editor.putBoolean("buttonStatus", true);
        editor.commit();
        clientEnroll.setEnabled(true);
    }
}
