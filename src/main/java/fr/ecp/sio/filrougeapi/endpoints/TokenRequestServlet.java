package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.DataUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import fr.ecp.sio.filrougeapi.model.User;
import fr.ecp.sio.filrougeapi.data.UserRepository;

/**
 * A servlet to handle requests for a token.
 * The client sends their credentials (login and password) to the server.
 * This servlet receives requests to URLs /authentication/{login&password}.
 */
public class TokenRequestServlet extends ApiServlet {

    private String login;
    private String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // We get the login and password of the user from the path of the URL of the request
            //TODO: get the login & password from the url
            login = req.getPathInfo().substring(1);
            password = req.getPathInfo().substring(1);

            //TODO: specified the exception type
        } catch (Exception ex) {
            // Path is not a valid credential, this is a "client" error (4XX code).
            resp.sendError(400, "Invalid login or password");
            return;
        }

        UserRepository rep = DataUtils.getUserRepository();
        User user = rep.getUser(login, password);

        if (user == null) {
            // A user was not found with this credential, send a 404 error.
            resp.sendError(404, "User not found. Invalid login and/or password.");
        }

        // Use our base class to send the generated token object serialized in JSON.
        //TODO: don't send back a user but the generated token
        sendResponse(user, resp);

    }




}
