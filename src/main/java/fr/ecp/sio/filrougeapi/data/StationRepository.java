package fr.ecp.sio.filrougeapi.data;

import fr.ecp.sio.filrougeapi.model.Station;

import java.io.IOException;
import java.util.List;

/**
 * An interface for a "Station Repository" component that will expose methods to interact with the data layer of our project.
 * All servlets will get an instance of the Repository (with DataUtils).
 */
public interface StationRepository {

    /*
        Get a station by id.
        Returns null if the station is not found.
     */
    Station getStationByID(long id) throws IOException;

    /*
    Get a station by name.
    Returns null if the station is not found.
    */
    Station getStationByName(String name) throws IOException;

    /*
        Get a list of all stations.
     */
    List<Station> getStations() throws IOException;

    /*
    Get a list of all stations.
    Using perpage parameters to filter the list (pagination).
    */
    List<Station> getStationsUsingPag(int limit, int offset) throws IOException;

}
