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
            
            
            JSONObject json = new JSONObject(jsonString);
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println(json.get("current_observation"));
            out.close();
            
            
//            String temperature = (String) json.get("temperature_string");
//            String weather = (String) json.get("weather");
//            Float wind_mph = (Float) json.get("wind_mph");
//            String visibility_mi = (String) json.get("visibility_mi");
//            String icon_url = (String) json.get("icon_url");
//            
//            resp.setContentType("text/html");
//            PrintWriter out = resp.getWriter();
//            out.println("<html><header><title>Information</title></header><body>");
//            out.println("<img src='" + icon_url + "' alt='stuff'/>");
//            out.println("Weather: " + weather);
//            out.println("Temp: " + temperature);
//            out.println("Wind Speed: " + wind_mph);
//            out.println("Visibility(miles): " + visibility_mi);
//            out.println("</body></html>");
//            out.close();
            
        } catch (MalformedURLException e) {
            resp.sendError(404);
        } catch (IOException e) {
            resp.sendError(400);
        }
		
	}
	
	
}
