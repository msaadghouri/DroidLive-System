/**
 * 
 */
package callLogs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arkansas.clientenrollment.beans.CallLogsBean;
import com.arkansas.dao.ConstantUtils;


/**
 * @author msaadghouri
 *
 */
public class CallLogsDialog extends ConstantUtils{

	public ArrayList<CallLogsBean> getCallRecords(String startDate, String endDate, String userRefId)
	{
		ArrayList<CallLogsBean> listLogs = new ArrayList<CallLogsBean>();
		try {
			Connection conn = getConn();

			String queryStr="select ContactName,ContactNumber,CallDuration,CallType,CallDate from arkansas.CallLogsTable where UserRefId='"+userRefId+"' and CallDate between '"+startDate+"' and '"+endDate+"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String cName = rset.getString("ContactName");
				String cNum = rset.getString("ContactNumber");
				int cDur=rset.getInt("CallDuration");
				String cType = rset.getString("CallType");
				Date cDate = rset.getDate("CallDate");
				CallLogsBean log = new CallLogsBean(cName,cNum,cDur,cDate,cType);
				listLogs.add(log);
			}
			rset.close();
			stmt.close();
			conn.close();
			return listLogs;
		} catch (SQLException ex) {
			Logger.getLogger(CallLogsDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public ArrayList<BeanDaysofWeek> getDaysofWeek(ArrayList<CallLogsBean> logList)
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
			for(int i=0;i<logList.size();i++){
				Date cDate=logList.get(i).getCallDate();
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

	public ArrayList<BeanCOI> getMonthlyAnalysis(String sDate, String eDate, String userRefId){
		try{
			ArrayList<BeanCOI> list= new ArrayList<>();
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
				String queryStr="Select coalesce(sum(CallDuration),0), count(ContactNumber) from CallLogsTable where UserRefId='"+userRefId+"' and CallDate between '"+sdform.format(curStartDate)+"' and '"+sdform.format(curEndDate)+"'";
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
						System.out.println("Data Points "+rSet.getObject(1).toString()+" "+rSet.getObject(2).toString());
						BeanCOI dp = new BeanCOI(sdform.format(curStartDate), Integer.parseInt(rSet.getObject(1).toString())/60,Integer.parseInt(rSet.getObject(2).toString()));
						list.add(dp);
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
				Logger.getLogger(CallLogsDialog.class.getName()).log(Level.SEVERE, null, ex);
			}
			return list;	
		}
		catch (Exception e) {
			return null;
		}

	}

	public ArrayList<BeanCOI> getCallofIntD(String startDate, String endDate, String userRefId)
	{
		ArrayList<BeanCOI> listLogs = new ArrayList<>();
		try {
			Connection conn = getConn();

			String queryStr="Select ContactNumber, sum(CallDuration) as totalDur, count(ContactNumber) as frequency from CallLogsTable where UserRefId='"+userRefId+"' and CallDate between '"+startDate+"' and '"+endDate+"'group by ContactNumber order by totalDur desc  limit 10";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String cNum = rset.getString("ContactNumber");
				int cDur=rset.getInt("totalDur");
				int cFreq = rset.getInt("frequency");
				BeanCOI log= new BeanCOI(cDur/60, cFreq, cNum);
				listLogs.add(log);
			}
			rset.close();
			stmt.close();
			conn.close();
			return listLogs;
		} catch (SQLException ex) {
			Logger.getLogger(CallLogsDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public ArrayList<BeanCOI> getCallofIntF(String startDate, String endDate, String userRefId)
	{
		ArrayList<BeanCOI> listLogs = new ArrayList<>();
		try {
			Connection conn = getConn();

			String queryStr="Select ContactNumber, sum(CallDuration) as totalDur, count(ContactNumber) as frequency from CallLogsTable where UserRefId='"+userRefId+"' and CallDate between '"+startDate+"' and '"+endDate+"'group by ContactNumber order by frequency desc  limit 10";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String cNum = rset.getString("ContactNumber");
				int cDur=rset.getInt("totalDur");
				int cFreq = rset.getInt("frequency");
				BeanCOI log= new BeanCOI(cDur/60, cFreq, cNum);
				listLogs.add(log);
			}
			rset.close();
			stmt.close();
			conn.close();
			return listLogs;
		} catch (SQLException ex) {
			Logger.getLogger(CallLogsDialog.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public ArrayList<BeanGraphElements> elementGraph(String startDate, String endDate, String userRefId)
	{
		ArrayList<BeanGraphElements> listNumbers = new ArrayList<>();
		try {
			Connection conn = getConn();

			String queryStr="select ContactNumber,CallType, count(CallType) as Strength from arkansas.CallLogsTable where UserRefId='"+userRefId+"' and CallDate between '"+startDate+"' and '"+endDate+"' and CallType='Incoming' or CallType='Outgoing' group by ContactNumber,CallType";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String cNumber=rset.getString("ContactNumber");
				String cType=rset.getString("CallType");
				int strength=rset.getInt("Strength");
				BeanGraphElements elements= new BeanGraphElements(cNumber, cType, strength);
				listNumbers.add(elements);
			}
			rset.close();
			stmt.close();
			conn.close();
			System.out.println(listNumbers);
			return listNumbers;
		} catch (SQLException ex) {
			Logger.getLogger(CallLogsDialog.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void main(String[] args){
		CallLogsDialog callLogsDialog= new CallLogsDialog();
		ArrayList<BeanGraphElements> aa = callLogsDialog.elementGraph("2016-01-01", "2016-12-31", "C.352991068681083");
		ArrayList<String> nodes=new ArrayList<>();
		ArrayList<String> elements=new ArrayList<>();

		nodes.add("{data:{id:'C.352991068681083'}}");

		for(int i=0;i<aa.size();i++){
			if(aa.get(i).getStrength()>=3){
				String test=null;
				if((aa.get(i).getcType()).equalsIgnoreCase("Outgoing")){
					test="{data:{source:'C.352991068681083',target:'"+aa.get(i).getcNumber()+"',strength:"+aa.get(i).getStrength()+"}}";

				}else if ((aa.get(i).getcType()).equalsIgnoreCase("Incoming")) {
					test="{data:{target:'C.352991068681083',source:'"+aa.get(i).getcNumber()+"',strength:"+aa.get(i).getStrength()+"}}";
				}
				nodes.add("{data:{id:'"+aa.get(i).getcNumber()+"'}}");
				elements.add(test);
			}
		}
		System.out.println(nodes);
		System.out.println(elements);
	}
}