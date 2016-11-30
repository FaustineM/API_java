package fr.ecp.sio.filrougeapi.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A base class to provide some shared methods to our endpoint servlets.
 */
public class ApiServlet extends HttpServlet {

    /*
        A method to serialize an object (data) as JSON and send it in the HTTP response.
        This architecture allows a common configuration for JSON serialization.
     */
    protected void sendResponse(Object data, HttpServletResponse resp) throws IOException {
        // Optional: the GsonBuilder instance could be configured here for all the API (e.g. date format).
        Gson gson = new GsonBuilder().create();
        // The HttpServletResponse exposes a getWriter() method to write to the output stream.
        gson.toJson(data, resp.getWriter());
    }

}
