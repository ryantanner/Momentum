package edu.trinity.webapps.momentum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
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
		
		//?location=[(xx.xxx, xx.xxx), (yy.yyy, yy.yyy)]
		//?loc1=xx.xxx,xx.xxx
		
		String longlat1 = req.getParameter("loc1");
		String longlat2 = req.getParameter("loc2");
		String longlat3 = req.getParameter("loc3");
		String longlat4 = req.getParameter("loc4");
		String longlat5 = req.getParameter("loc5");
		String longlat6 = req.getParameter("loc6");
		String longlat7 = req.getParameter("loc7");
		String longlat8 = req.getParameter("loc8");
		
		List<String> longLatList = Arrays.asList(longlat1, longlat2, longlat3, longlat4, longlat5, longlat6, longlat7, longlat8);
		
//		{"markers":[{"lat":57.7973333,"lng":12.0502107,"title":"Angered"},{"lat":57.6969943,"lng":11.9865,"title":"Gothenburg"}]}
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
	            
	            String location = (String) locationJson.get("full");
	            String icon_url = (String) observationJson.get("icon_url");
	            String weather = (String) observationJson.get("weather");
	            String temperature = (String) observationJson.get("temperature_string");
	            Double wind_mph = (Double) observationJson.get("wind_mph");
	            String visibility_mi = (String) observationJson.get("visibility_mi");
	
	            
	            JSONObject singleJson = new JSONObject();
	            singleJson.put("icon_url", icon_url);
	            singleJson.put("location", location);
	            singleJson.put("weather", weather);
	            singleJson.put("temperature", temperature);
	            singleJson.put("wind_mph", wind_mph);
	            singleJson.put("visibility_mi", visibility_mi);
	            
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
	
	
}
