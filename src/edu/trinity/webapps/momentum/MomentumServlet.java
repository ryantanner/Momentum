package edu.trinity.webapps.momentum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

import sun.net.www.http.HttpClient;

@SuppressWarnings("serial")
public class MomentumServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try {
			doGetOrPost(req, resp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try {
			doGetOrPost(req, resp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
		
		boolean test = false;
		Enumeration paramNames = req.getParameterNames();
		List <String> longLatList = new ArrayList<String>();
		while(paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			if(paramName.compareTo("test") == 0){
				test = true;
			}
			longLatList.add(req.getParameter(paramName));
		}
		
		if(!test){
			JSONObject rootMarkersJson = new JSONObject();
			JSONArray longLatJsonArray = new JSONArray();
			
			for(int i = 0; i < longLatList.size(); i++){
			String urlString = "http://api.wunderground.com/api/0f20fef8dd79ad3a/conditions/q/" + longLatList.get(i) + ".json";
		        try {
		            URL url = new URL(urlString);
		            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		            String line;
		            String jsonString = "";
		            
		            while ((line = reader.readLine()) != null) {
		        		jsonString = jsonString + line;
		            }
		            reader.close();
		            
		            JSONObject rootJson = new JSONObject(jsonString);
		            JSONObject observationJson = rootJson.getJSONObject("current_observation");
		            JSONObject locationJson = observationJson.getJSONObject("display_location");
		            
		            JSONObject singleJson = new JSONObject();
		            singleJson.put("icon_url", observationJson.get("icon_url"));
		            singleJson.put("location", locationJson.get("full"));
		            singleJson.put("weather", observationJson.get("weather"));
		            singleJson.put("temperature", observationJson.get("temperature_string"));
		            singleJson.put("wind_mph", observationJson.get("wind_mph"));
		            singleJson.put("visibility_mi", observationJson.get("visibility_mi"));
		            singleJson.put("latitude", locationJson.get("latitude"));
		            singleJson.put("longitude", locationJson.get("longitude"));
		            
		            longLatJsonArray.put(singleJson);
		            
		        } catch (MalformedURLException e) {
		        	//TO-DO: Add intelligible error messaging for these catches.
		        	resp.sendError(404);
		        } catch (IOException e) {
		            resp.sendError(400);
		        }
			
			}
			
			rootMarkersJson.put("markers", longLatJsonArray);
			
	        resp.setContentType("application/JSON");
	        PrintWriter out = resp.getWriter();
	        out.print(rootMarkersJson);
	        out.close();
		}
		else{
			//This test block exists in order to limit the amount of API hits so we don't use up our key
			
			String testJsonString = "{\"markers\":[{\"visibility_mi\":\"7.0\",\"location\":\"Katy, TX\",\"wind_mph\":0,\"icon_url\":\"http://icons-ak.wxug.com/i/c/k/nt_cloudy.gif\",\"weather\":\"Overcast\",\"longitude\":\"-95.851220\",\"latitude\":\"29.777110\",\"temperature\":\"56.7 F (13.7 C)\"},{\"visibility_mi\":\"10.0\",\"location\":\"Glidden, TX\",\"wind_mph\":2.7,\"icon_url\":\"http://icons-ak.wxug.com/i/c/k/nt_cloudy.gif\",\"weather\":\"Overcast\",\"longitude\":\"-96.517830\",\"latitude\":\"29.702690\",\"temperature\":\"57.4 F (14.1 C)\"},{\"visibility_mi\":\"10.0\",\"location\":\"Flatonia, TX\",\"wind_mph\":3,\"icon_url\":\"http://icons-ak.wxug.com/i/c/k/nt_cloudy.gif\",\"weather\":\"Overcast\",\"longitude\":\"-97.120750\",\"latitude\":\"29.696280\",\"temperature\":\"59.0 F (15.0 C)\"},{\"visibility_mi\":\"10.0\",\"location\":\"McQueeney, TX\",\"wind_mph\":11.5,\"icon_url\":\"http://icons-ak.wxug.com/i/c/k/nt_cloudy.gif\",\"weather\":\"Overcast\",\"longitude\":\"-98.022160\",\"latitude\":\"29.567290\",\"temperature\":\"62.9 F (17.2 C)\"},{\"visibility_mi\":\"9.0\",\"location\":\"San Antonio, TX\",\"wind_mph\":5,\"icon_url\":\"http://icons-ak.wxug.com/i/c/k/nt_rain.gif\",\"weather\":\"light rain\",\"longitude\":\"-98.493630\",\"latitude\":\"29.424130\",\"temperature\":\"62.6 F (17.0 C)\"}]}";
			JSONObject testObject = new JSONObject(testJsonString);
			resp.setContentType("application/JSON");
	        PrintWriter out = resp.getWriter();
	        out.print(testObject);
	        out.close();
		}
	}
	
	
}
