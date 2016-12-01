package fr.ecp.sio.filrougeapi.data;

import fr.ecp.sio.filrougeapi.model.Location;
import fr.ecp.sio.filrougeapi.model.Station;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A StationRepository that returns fake data.
 */
public class DummyStationRepository implements StationRepository {

    Station station = new Station();
    Location loc = new Location();

    @Override
    public Station getStationByID(long id) {
        if (id == 25) {
            station.setId(25);
            station.setName("Station de test");
            loc.setLatitude(46.0f);
            loc.setLongitude(1.0f);
            station.setLocation(loc);
            return station;
        } else {
            return null;
        }
    }

    @Override
    public Station getStationByName(String name) {
        if  (name.equals("test")) {
            station.setId(25);
            station.setName("Station de test");
            loc.setLatitude(46.0f);
            loc.setLongitude(1.0f);
            station.setLocation(loc);
            return station;
        } else {
            return null;
        }
    }

    @Override
    public List<Station> getStations() throws IOException {
        return null;
    }

    @Override
    public List<Station> getStationsUsingPag(int limit, int offset) throws IOException {
        return null;
    }

}
