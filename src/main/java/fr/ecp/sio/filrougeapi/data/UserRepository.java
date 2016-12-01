package fr.ecp.sio.filrougeapi.data;

import java.io.IOException;
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

    //TODO: ajout d'autres méthodes si utilisées dans le endpoint


}
