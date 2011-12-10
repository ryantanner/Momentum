package edu.trinity.webapps.momentum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.*;

import com.google.appengine.api.urlfetch.HTTPResponse;

import sun.net.www.http.HttpClient;

@SuppressWarnings("serial")
public class MomentumServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
        try {
            URL url = new URL("http://api.wunderground.com/api/0f20fef8dd79ad3a/conditions/q/29.464,-98.481.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
        		resp.setContentType("text/plain");
        		resp.getWriter().println(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            resp.sendError(404);
        } catch (IOException e) {
            resp.sendError(400);
        }
		
	}
	
	
}
