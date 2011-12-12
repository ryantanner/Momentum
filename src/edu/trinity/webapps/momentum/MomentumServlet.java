package edu.trinity.webapps.momentum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.http.*;

import com.google.appengine.api.urlfetch.HTTPResponse;
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
		
		String longlat = req.getParameter("location");
		String urlString = "http://api.wunderground.com/api/0f20fef8dd79ad3a/conditions/q/" + longlat + ".json";
		
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

            
            JSONObject momentumJson = new JSONObject();
            momentumJson.put("icon_url", icon_url);
            momentumJson.put("location", location);
            momentumJson.put("weather", weather);
            momentumJson.put("temperature", temperature);
            momentumJson.put("wind_mph", wind_mph);
            momentumJson.put("visibility_mi", visibility_mi);
            
            resp.setContentType("application/JSON");
            PrintWriter out = resp.getWriter();
            out.print(momentumJson);
            out.close();
            
        } catch (MalformedURLException e) {
        	//TO-DO: Add intelligible error messaging for these catches.
        	resp.sendError(404);
        } catch (IOException e) {
            resp.sendError(400);
        }
		
	}
	
	
}
