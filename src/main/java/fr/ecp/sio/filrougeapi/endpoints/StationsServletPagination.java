package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.StationRepository;
import fr.ecp.sio.filrougeapi.data.DataUtils;
import fr.ecp.sio.filrougeapi.model.Station;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A servlet to handle requests for a list of stations using pagination.
 * This servlet receives requests to URLs /stations?LIMIT={limit}&OFFSET={offset}.
 */
public class StationsServletPagination extends ApiServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int limit;
        int offset;

        try {
            // We get the pagination of the stations list from the path of the URL of the request.
            //TODO: Define how to get the limit & offset from the url
            limit = Integer.parseInt(req.getPathInfo().substring(1));
            offset = Integer.parseInt(req.getPathInfo().substring(1));
        } catch (NumberFormatException ex) {
            // Path is not a valid id, this is a "client" error (4XX code).
            resp.sendError(400, "Invalid limit and / or offset.");
            return;
        }

        StationRepository rep = DataUtils.getStationRepository();
        List<Station> stations = rep.getStationsUsingPag(limit, offset);

        // Use our base class to send the station object serialized in JSON.
        sendResponse(stations, resp);

    }

}