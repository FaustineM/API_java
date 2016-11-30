package fr.ecp.sio.filrougeapi.data;

import java.io.IOException;
import fr.ecp.sio.filrougeapi.model.User;

/**
 * An interface for a "Repository" component that will expose methods to interact with the data layer of our project.
 * All servlets will get an instance of the Repository (with DataUtils).
 */
public interface UserRepository {


    User getUser(String login, String password) throws IOException;


}
