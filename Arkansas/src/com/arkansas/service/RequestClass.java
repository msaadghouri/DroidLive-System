/**
 * 
 */
package com.arkansas.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.arkansas.dao.EnrollmentDAOImpl;

/**
 * @author msaadghouri
 *
 */
public class RequestClass {
	public static String FIREBASE_SERVER_KEY = "AAAAwJPZdJk:APA91bFodWExOdhQrFq7rqcx1TO"
			+ "scgDk1NGNx-R2MMFd_VsxfscHER5SpBhshH7noTKdCXWXFAIVheJ2_CFP53dkQCggsFhi"
			+ "-jZMNiR8y0K_rM_HQzuNkLRn7JPpIm_yodUFFPXZBd2n";
	@SuppressWarnings("unchecked")
	public String sendRequest(String userRefId,String firebaseRegID, String flowName, Date flowDate, int transactionID){

		try{
			JSONObject obj = new JSONObject();
			obj.put("to",firebaseRegID );

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String text = df.format(flowDate);
			System.out.println("The date is: " + text);

			JSONObject data = new JSONObject();
			data.put("userRefId", userRefId);
			data.put("flowName", flowName);
			data.put("flowDate", text);
			data.put("transactionID", transactionID);

			obj.put("data", data);

			System.out.println(obj);
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" +FIREBASE_SERVER_KEY);
			conn.setRequestProperty("Content-Type",
					"application/json");

			conn.setDoInput(true);
			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(obj.toJSONString());
			System.out.println("done");
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
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(sb.toString());
				if(json.get("success").toString().equalsIgnoreCase("1")){
					EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
					daoImpl.submitRequest(userRefId, firebaseRegID, flowName, flowDate, transactionID, "PENDING");
				}
				return "Success";
			}
			return "Error";
		}catch(Exception e){
			e.printStackTrace();
			return "Exception";
		}
	}
}
