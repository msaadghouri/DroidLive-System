/**
 * 
 */
package com.arkansas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author msaadghouri
 *
 */
public class ConstantUtils {
	protected Connection getConn()
	{
		String url=null;
		try {
			String dbuser = "root";
			String dbpassword = "root";
			String dburl = "jdbc:mysql://localhost:3306/";
			String dataBaseName = "arkansas";
			url=dburl+dataBaseName+"?user="+dbuser+"&password="+dbpassword;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url);
			return conn;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IllegalAccessException ex) {
			ex.printStackTrace();	
		}catch (InstantiationException ex) {
			ex.printStackTrace();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<String> returnDates(String datePicked){
		List<String> listDates = new ArrayList<>();
		try{
			String arr[] = datePicked.split("-", 2);
			String sdate1 = arr[0];   
			String sdate2 = arr[1];
			sdate2=sdate2.trim();
			int mm1=Month.valueOf(sdate1.substring(0, sdate1.indexOf(' ')).toUpperCase()).getValue();
			int mm2=Month.valueOf(sdate2.substring(0, sdate2.indexOf(' ')).toUpperCase()).getValue();

			String tempdate[] = sdate1.split(" ", 3);
			String dd=tempdate[1];
			String year=tempdate[2];
			String d1 = year+"-"+mm1+"-"+dd;
			d1=d1.replace(" ", "");

			String tempdate2[] = sdate2.split(" ", 3);
			dd=tempdate2[1];
			year=tempdate2[2];
			String d2 = year+"-"+mm2+"-"+dd;
			d2=d2.replace(" ", "");
			listDates.add(d1);
			listDates.add(d2);
			return listDates;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
