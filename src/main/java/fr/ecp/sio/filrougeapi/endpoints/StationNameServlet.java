package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.StationRepository;
import fr.ecp.sio.filrougeapi.data.DataUtils;
import fr.ecp.sio.filrougeapi.model.Station;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet to handle requests for a single station filtering on its name.
 * This servlet receives requests to URLs /stations/name/{name}.
 */
public class StationNameServlet extends ApiServlet {

    // HttpServlet has protected doXxx() methods that correspond to HTTP verbs (GET, POST...).
    // These methods receive objects to represent both the request (to inspect or read from) and the response (to write to).
    // Default implementation sends a 405 error, so we must override doGet() to support GET requests and NOT call super implementation.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name;
        try {
            // We get the id of the station from the path of the URL of the request, removing the leading "/".
            name = req.getPathInfo().substring(1);

        } catch (Exception e) {
            // Path is not a valid id, this is a "client" error (4XX code).
            resp.sendError(400, "Invalid station name");
            return;
        }

        StationRepository rep = DataUtils.getStationRepository();
        Station station = rep.getStationByName(name);

        if (station == null) {
            // A station was not found with this name, send a 404 error.
            resp.sendError(404, "Station not found");
        }

        // Use our base class to send the station object serialized in JSON.
        sendResponse(station, resp);

    }

}
