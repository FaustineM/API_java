package fr.ecp.sio.filrougeapi.data;

/**
 * An utility class that gives access to a DataRepository for all the project.
 * This class implements the singleton pattern to always return the same instance for every call to getRepository().
 */
public class DataUtils {

    // Singleton: this static field holds the instance when it has been created.
    private static DataRepository sRepository;

    /*
        Get a DataRepository instance.
        In this method we can substitute the actual implementation with another one if needed.
        This behaviour could also be obtained with Dependency Injection.
     */
    public static DataRepository getRepository() {
        // Singleton: create the instance if needed.
        if (sRepository == null) {
            sRepository = new SQLDataRepository();
        }
        return sRepository;
    }

}
