package project.liveforensics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "LiveForensics";
    private static final String IS_ENROLLED = "IsEnrolled";
    public static final String smartClientID = "smartClientID";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession() {
        editor.putBoolean(IS_ENROLLED, true);
        editor.putString(smartClientID, ClientEnrollment.clientID);
        editor.commit();
    }

    public boolean checkEnroll() {
        boolean checkLoginFlag = false;
        if (!this.isEnrolled()) {
            checkLoginFlag = false;
        } else {
            checkLoginFlag = true;
        }
        return checkLoginFlag;
    }

    public boolean isEnrolled() {
        return pref.getBoolean(IS_ENROLLED, false);

    }
}
