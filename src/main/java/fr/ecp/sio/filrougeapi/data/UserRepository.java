package fr.ecp.sio.filrougeapi.data;

import java.io.IOException;
import java.sql.SQLException;

import fr.ecp.sio.filrougeapi.model.User;

/**
 * An interface for a "User Repository" component that will expose methods to interact with the data layer of our project.
 * All servlets will get an instance of the Repository (with DataUtils).
 */
public interface UserRepository {

    /*
    Get a user by credential.
    Returns null if the user is not found.
    */
    User getUser(String login, String password) throws IOException;

    // Issue a token and returned it
    String tokenGenerator();

    // Update a user : adding the generated token
    void updateUser(String token, String login) throws SQLException;

    // Ensure that the token given by the user is valid
    boolean isValidToken(String token) throws IOException;

    // Check is the user who request a token, does not already have once
    boolean alreadyHaveToken(String login) throws IOException;

}
