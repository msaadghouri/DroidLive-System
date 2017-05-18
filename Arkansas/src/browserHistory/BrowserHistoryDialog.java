/**
 * 
 */
package browserHistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.arkansas.dao.ConstantUtils;

import callLogs.BeanDaysofWeek;

/**
 * @author msaadghouri
 *
 */
public class BrowserHistoryDialog extends ConstantUtils{
	public ArrayList<BeanDaysofWeek> getDaysofWeek(String startDate, String endDate, String userRefId)
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

		try {
			Connection conn = getConn();

			String queryStr="select SearchedDate from arkansas.BrowserTable where UserRefId='"+userRefId+"' and SearchedDate between '"+startDate+"' and '"+endDate+"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			System.out.println(queryStr);
			while (rset.next())
			{
				String cDate= rset.getString("SearchedDate");
				DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
				Date d= format.parse(cDate);
				System.out.println(d);
				Calendar calender = Calendar.getInstance();
				calender.setTime(d);
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
		} catch (SQLException | ParseException ex) {
			Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getTopKeywords(String startDate,String endDate,String userRefId)
	{
		ArrayList<JSONObject> keywords = new ArrayList<>();
		try {
			Connection conn = getConn();
			String queryStr="select SearchedText,count(SearchedText) as frequency FROM arkansas.BrowserTable where UserRefId='"+userRefId+"' and SearchedDate between '"+startDate+"' and '"+endDate+"' and SearchedText not like '' group by SearchedText";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			System.out.println("Query Eexecuted"+queryStr);
			while (rset.next())
			{
				String term = rset.getString("SearchedText");
				int freq = rset.getInt("frequency");
				JSONObject bsites= new JSONObject();
				bsites.put("term", term);
				bsites.put("freq", freq);
				keywords.add(bsites);
			}
			System.out.println("Query Eexecuted"+keywords);
			rset.close();
			stmt.close();
			conn.close();
			return keywords;
		} catch (SQLException ex) {
			Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static void main(String args[]){
		BrowserHistoryDialog browserHistoryDialog= new BrowserHistoryDialog();
		browserHistoryDialog.getTopKeywords("2016-01-01", "2017-12-31", "C.352991068681083");
	}
}
