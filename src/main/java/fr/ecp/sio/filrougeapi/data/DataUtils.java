package fr.ecp.sio.filrougeapi.data;

/**
 * An utility class that gives access to a StationRepository for all the project.
 * This class implements the singleton pattern to always return the same instance for every call to getStationRepository().
 */
public class DataUtils {

    // Singleton: this static field holds the instance when it has been created.
    private static StationRepository sRepository;

    private static UserRepository uRepository;

    /*
        Get a StationRepository instance.
        In this method we can substitute the actual implementation with another one if needed.
        This behaviour could also be obtained with Dependency Injection.
     */
    public static StationRepository getStationRepository() {
        // Singleton: create the instance if needed.
        if (sRepository == null) {
            sRepository = new SQLStationRepository();
        }
        return sRepository;
    }

    /*
    Get a UserRepository instance.
    In this method we can substitute the actual implementation with another one if needed.
    This behaviour could also be obtained with Dependency Injection.
 */
    public static UserRepository getUserRepository() {
        // Singleton: create the instance if needed.
        if (uRepository == null) {
            uRepository = new SQLUserRepository();
        }
        return uRepository;
    }

}
