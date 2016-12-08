package fr.ecp.sio.filrougeapi.endpoints;

import fr.ecp.sio.filrougeapi.data.DataUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import fr.ecp.sio.filrougeapi.model.User;
import fr.ecp.sio.filrougeapi.data.UserRepository;

/**
 * A servlet to handle requests for a token.
 * The client sends their credentials (login and password) to the server.
 * This servlet receives requests to URLs /authentication?LOGIN={login}&PASSWORD={password}.
 */
public class TokenRequestServlet extends ApiServlet {

    private String login;
    private String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // We get the login and password of the user from the path of the URL of the request
            login = req.getParameter("LOGIN");
            password = req.getParameter("PASSWORD");

        } catch (Exception e) {
            // Path is not a valid credential, this is a "client" error (4XX code).
            resp.sendError(400, "Invalid login or password");
            return;
        }

        UserRepository rep = DataUtils.getUserRepository();
        User user = rep.getUser(login, password);

        // Check if the user is valid
        if (user == null) {
            // A user was not found with this credential, send a 404 error.
            resp.sendError(404, "User not found. Invalid login and/or password.");
        }

        // Check if the user does not already have a token
        if (rep.alreadyHaveToken(login)) {
                // Not a valid request, the client already have a token, this is a "client" error (4XX code).
                resp.sendError(400, "You alredady have a token");
                return;
        }

        // Issue a token for the user
        String token = rep.tokenGenerator();

        // Store the generated token along with the user credential
        try {
            rep.updateUser(token, login);
        } catch (SQLException e) {
            resp.sendError(400, "internal sql error");
            return;
        }

        // Use our base class to send the generated token object serialized in JSON.
        sendResponse(token, resp);

    }
}
