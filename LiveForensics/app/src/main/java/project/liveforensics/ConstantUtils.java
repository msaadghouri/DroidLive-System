package project.liveforensics;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Telephony;

/**
 * Created by Mohammad-Ghouri on 1/22/17.
 */

public class ConstantUtils {

    SharedPreferences preferences;
    public static String smartClientID = ClientEnrollment.clientID;
    static Context context;

    private static final String SERVER_URL = "http://10.0.0.150:8080/Arkansas/rest/arkansas/";
    //144.167.241.32
    public static final String ENROLL_URL = SERVER_URL + "clientEnroll/";
    public static final String DISCOVER_URL = SERVER_URL + "submitDiscovery/";
    public static final String HISTORY_URL = SERVER_URL + "submitHistory/";
    public static final String CALLLOGS_URL = SERVER_URL + "submitCallLogs/";
    public static final String CONTACTS_URL = SERVER_URL + "submitContacts/";
    public static final String SMS_URL = SERVER_URL + "submitSMS/";

    public static final String SHARED_PREF = "ah_firebase";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static final Uri BOOKMARKS_URI = Uri.parse("content://com.android.chrome.browser/history");
    public static final Uri SMS_URI_T14 = Uri.parse("content://sms");

    public static final int PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static final String[] PERMISSIONS_REQUIRED= new String[]{
            android.Manifest.permission.READ_CALL_LOG, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.GET_ACCOUNTS
    };

    public static final String[] HISTORY_PROJECTION = new String[]{"url", "date",};
    public static final String[] CONTACTS_PROJECTION = {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER};
    public static final String[] CALL_PROJECTION = {
            CallLog.Calls._ID,
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION,
            CallLog.Calls.DATE,
            CallLog.Calls.TYPE,
            CallLog.Calls.COUNTRY_ISO
    };
    public static final String[] SMS_PROJECTION_T19 = {
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE,
            Telephony.Sms.BODY
    };
    public static final String[] SMS_PROJECTION_T14 = {
            "_id",
            "address",
            "date",
            "type",
            "body"
    };


    public ConstantUtils(Context context) {
        preferences = context.getSharedPreferences(SessionManager.PREF_NAME,
                context.MODE_PRIVATE);
        smartClientID = preferences.getString(SessionManager.smartClientID, "");
        this.context = context;
    }

    public static boolean isNull(String data) {

        boolean isnull = false;
        if (data != null) {
            if (!data.equals("") && !data.equals("null") && data != null
                    && !data.equals("-1")) {
                isnull = true;
            }
        } else {
            isnull = false;
        }
        return isnull;
    }
}
