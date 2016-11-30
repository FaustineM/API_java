package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.DataUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import fr.ecp.sio.filrougeapi.model.User;
import fr.ecp.sio.filrougeapi.data.UserRepository;

/**
 * A servlet to handle requests for a single station.
 * This servlet receives requests to URLs /authentication/{login&password}.
 */
public class TokenRequestServlet extends ApiServlet {

    private String login;
    private String password;
    private Long token;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // We get the login and password of the user from the path of the URL of the request
            // removing the leading "/".
            login = req.getPathInfo().substring(1);
            password = req.getPathInfo().substring(1);
        } catch (NumberFormatException ex) {
            // Path is not a valid id, this is a "client" error (4XX code).
            resp.sendError(400, "Invalid login or password");
            return;
        }

        UserRepository rep = DataUtils.getUserRepository();
        User user = rep.getUser(login, password);

        if (user == null) {
            // A station was not found with this id, send a 404 error.
            resp.sendError(404, "User not found");
        }

        // Use our base class to send the station object serialized in JSON.
        sendResponse(user, resp);

    }




}
