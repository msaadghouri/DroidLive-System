/**
 * 
 */
package shortmessage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.arkansas.clientenrollment.beans.SMSBean;
import com.arkansas.dao.ConstantUtils;

import callLogs.BeanDaysofWeek;

/**
 * @author msaadghouri
 *
 */
public class SMSDialog extends ConstantUtils{

	public ArrayList<SMSBean> getSMSRecords(String startDate, String endDate, String userRefId)
	{
		ArrayList<SMSBean> listSMS = new ArrayList<>();
		try {
			Connection conn = getConn();

			String queryStr="SELECT SMSDate,SMSAction,SMSBody FROM arkansas.SMSTable where UserRefId='"+userRefId+"' and SMSDate between '"+startDate+"' and '"+endDate+"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String smsType = rset.getString("SMSAction");
				String smsBody = rset.getString("SMSBody");
				Date smsDate = rset.getDate("SMSDate");
				SMSBean sms = new SMSBean(smsType,smsBody,smsDate);
				listSMS.add(sms);
			}
			rset.close();
			stmt.close();
			conn.close();
			return listSMS;
		} catch (SQLException ex) {
			Logger.getLogger(SMSDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public ArrayList<BeanDaysofWeek> getDaysofWeek(ArrayList<SMSBean> listSMS)
	{
		HashMap<Integer,Integer> daysmap = new HashMap<Integer,Integer>();
		HashMap<Integer, String> days = new HashMap<Integer, String>();
		days.put(1,"Sunday");
		days.put(2, "Monday");
		days.put(3, "Tuesday");
		days.put(4, "Wednesday");
		days.put(5, "Thursday");
		days.put(6, "Friday");
		days.put(7, "Saturday");
		try{
			for(int i=0;i<listSMS.size();i++){
				Date cDate=listSMS.get(i).getSmsDate();
				Calendar calender = Calendar.getInstance();
				calender.setTime(cDate);
				int dow = calender.get(Calendar.DAY_OF_WEEK);                    
				if(daysmap.containsKey(dow))
				{
					daysmap.put(dow,(daysmap.get(dow)+1));
				}
				else
					daysmap.put(dow, 1);
			}

			ArrayList<BeanDaysofWeek> dps= new ArrayList<>();
			Set<Integer> keys = daysmap.keySet();
			for(int key:keys)
			{
				BeanDaysofWeek dp = new BeanDaysofWeek(days.get(key),daysmap.get(key));
				dps.add(dp);
			}
			return dps;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getFrequentText(String startDate, String endDate, String userRefId)
	{
		ArrayList<JSONObject> listSMS = new ArrayList<>();
		try {
			Connection conn = getConn();

			String queryStr="SELECT SMSAddress,count(SMSAddress) as freq FROM arkansas.SMSTable where UserRefId='"+userRefId+"' and SMSDate between '"+startDate+"' and '"+endDate+"' group by SMSAddress desc";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String smsAddress = rset.getString("SMSAddress");
				int freq = rset.getInt("freq");
				JSONObject sms= new JSONObject();
				sms.put("address", smsAddress);
				sms.put("freq", freq);
				listSMS.add(sms);
			}
			rset.close();
			stmt.close();
			conn.close();
			return listSMS;
		} catch (SQLException ex) {
			Logger.getLogger(SMSDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static void main(String[] args) {
		SMSDialog smsDialog= new SMSDialog();
		ArrayList<JSONObject> freqTexter = smsDialog.getFrequentText("2016-01-01", "2016-12-31", "C.352991068681083");
		System.out.println(freqTexter);
	}

	public int wordCount(String smsData){
		String text = smsData.trim();
		if (text.isEmpty())
			return 0;
		return text.split("\\s+").length;
	}

}
