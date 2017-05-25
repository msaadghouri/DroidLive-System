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
			while (rset.next())
			{
				String cDate= rset.getString("SearchedDate");
				DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
				Date d= format.parse(cDate);
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
	public ArrayList<JSONObject> getMonthlyAnalysis(String sDate, String eDate, String userRefId){
		try{
			ArrayList<JSONObject> list= new ArrayList<>();
			int calendarFiledIDX;
			SimpleDateFormat sdform = new SimpleDateFormat();
			sdform.applyPattern("yyyy-MM-dd");

			java.util.Date startDate=sdform.parse(sDate);
			java.util.Date endDate=sdform.parse(eDate);
			java.util.Date curStartDate = startDate;
			java.util.Date curEndDate;
			boolean isStop = false;
			java.util.Calendar clndr = java.util.Calendar.getInstance();
			clndr.setTime(startDate);

			calendarFiledIDX = java.util.Calendar.MONTH;
			curEndDate = getLastDayOfMonth(clndr.get(java.util.Calendar.YEAR), clndr.get(java.util.Calendar.MONTH) + 1);
			clndr.setTime(curEndDate);
			clndr.add(java.util.Calendar.DATE, 1);

			if (curEndDate.compareTo(endDate) > 0)
			{
				curEndDate = endDate;
				isStop = true;
			}
			boolean tempFlag = false;
			Connection conn = getConn();
			do
			{
				if(tempFlag == true)
				{
					isStop = true;
				}
				String queryStr="Select count(URLName) from arkansas.BrowserTable where UserRefId='"+userRefId+"' and SearchedDate between '"+sdform.format(curStartDate)+"' and '"+sdform.format(curEndDate)+"'";
				ResultSet rSet= null;
				try
				{
					Statement stmt = conn.createStatement();
					stmt=conn.createStatement();
					rSet=stmt.executeQuery(queryStr);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				try
				{
					if(rSet.next())
					{   
						JSONObject object= new JSONObject();
						object.put("startDate", sdform.format(curStartDate));
						object.put("frequency", Integer.parseInt(rSet.getObject(1).toString()));
						list.add(object);
					}

				}catch(SQLException e)
				{
					e.printStackTrace();
				}
				curStartDate = clndr.getTime();
				clndr.add(calendarFiledIDX, 1);
				clndr.add(java.util.Calendar.DATE, -1);
				curEndDate = clndr.getTime();
				clndr.add(java.util.Calendar.DATE, 1);
				if (curEndDate.compareTo(endDate) > 0) {
					curEndDate = endDate;
					tempFlag=true;              //added another flag because the iteration was ending one month early.
				}
			}while(!isStop);
			try {
				conn.close();
			} catch (SQLException ex) {
				Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			}
			return list;	
		}
		catch (Exception e) {
			return null;
		}

	}
	public java.util.Date getLastDayOfMonth(int year, int month)
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.YEAR, year);
		cal.set(java.util.Calendar.MONTH, month - 1);
		cal.set(java.util.Calendar.DATE, 1);
		cal.add(java.util.Calendar.MONTH, 1);
		cal.add(java.util.Calendar.DATE, -1);
		return cal.getTime();
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
			while (rset.next())
			{
				String term = rset.getString("SearchedText");
				int freq = rset.getInt("frequency");
				JSONObject bsites= new JSONObject();
				bsites.put("term", term);
				bsites.put("freq", freq);
				keywords.add(bsites);
			}
			rset.close();
			stmt.close();
			conn.close();
			return keywords;
		} catch (SQLException ex) {
			Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getTopDomain(String startDate,String endDate,String userRefId)
	{
		ArrayList<JSONObject> topDomains = new ArrayList<>();
		try {
			Connection conn = getConn();
			String queryStr="select DomainName,count(DomainName) as frequency FROM arkansas.BrowserTable where UserRefId='"+userRefId+"' and SearchedDate between '"+startDate+"' and '"+endDate+"' group by DomainName order by frequency desc";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String domainName = rset.getString("DomainName");
				int frequency = rset.getInt("frequency");
				JSONObject tdJSON= new JSONObject();
				tdJSON.put("domainName", domainName);
				tdJSON.put("frequency", frequency);
				topDomains.add(tdJSON);
			}
			rset.close();
			stmt.close();
			conn.close();
			return topDomains;
		} catch (SQLException ex) {
			Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getRecentVisits(String startDate,String endDate,String userRefId)
	{
		ArrayList<JSONObject> recentURLs = new ArrayList<>();
		try {
			Connection conn = getConn();
			String queryStr="select URLName, SearchedDate FROM arkansas.BrowserTable where UserRefId='"+userRefId+"' and SearchedDate between '"+startDate+"' and '"+endDate+"' order by SearchedDate desc";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String urlName = rset.getString("URLName");
				String sDate = rset.getString("SearchedDate");
				JSONObject tdJSON= new JSONObject();
				tdJSON.put("urlName", urlName);
				tdJSON.put("sDate", sDate);
				recentURLs.add(tdJSON);
			}
			rset.close();
			stmt.close();
			conn.close();
			return recentURLs;
		} catch (SQLException ex) {
			Logger.getLogger(BrowserHistoryDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static void main(String args[]){
		BrowserHistoryDialog browserHistoryDialog= new BrowserHistoryDialog();
		browserHistoryDialog.getMonthlyAnalysis("2016-01-01", "2017-12-31", "C.352991068681083");
	}
}
