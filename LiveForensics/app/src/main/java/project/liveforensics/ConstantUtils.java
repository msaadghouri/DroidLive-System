package project.liveforensics;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mohammad-Ghouri on 1/22/17.
 */

public class ConstantUtils {

    SharedPreferences preferences;
    public static String smartClientID = ClientEnrollment.clientID;
    static Context context;

    public static String ENROLL_URL = "http://10.0.0.150:8080/Arkansas/rest/arkansas/clientEnroll/";
    public static String DISCOVER_URL = "http://10.0.0.150:8080/Arkansas/rest/arkansas/submitDiscovery/";
    public static final String SHARED_PREF = "ah_firebase";

    public static final String REGISTRATION_COMPLETE = "registrationComplete";
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
