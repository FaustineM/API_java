package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.StationRepository;
import fr.ecp.sio.filrougeapi.data.DataUtils;
import fr.ecp.sio.filrougeapi.model.Station;
import fr.ecp.sio.filrougeapi.model.StationsStatistics;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A servlet to handle requests for a list of stations.
 * This servlet receives requests to URLs /stations.
 * The user may use pagination.
 * So this servlet can receive requests to URLs /stations?LIMIT={limit}&OFFSET={offset}.
 */
public class StationsServlet extends ApiServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int limit = 0;
        int offset = 0 ;

        try {
            // We get the pagination of the stations list from the path of the URL of the request.
            limit = Integer.parseInt(req.getParameter("LIMIT"));
            offset = Integer.parseInt(req.getParameter("OFFSET"));
        } catch (NumberFormatException ex) {

            if (limit == 0 && offset == 0) {
                StationRepository rep = DataUtils.getStationRepository();
                StationsStatistics stations = rep.getStations();
                // Use our base class to send the station object serialized in JSON.
                sendResponse(stations, resp);
            } else {
                // Path is not a valid id, this is a "client" error (4XX code).
                resp.sendError(400, "Invalid limit and/or offset.");
                return;
            }
        }

        // If limit and offset parameters has not been defined by the user,
        // simply rturn the list of all stations, without using pagination.

        StationRepository rep = DataUtils.getStationRepository();
        //List<Station> stations = rep.getStationsUsingPag(limit, offset);
        StationsStatistics stations = rep.getStationsUsingPag(limit, offset);
        // Use our base class to send the station object serialized in JSON.
        sendResponse(stations, resp);

    }

}
