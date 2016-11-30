package fr.ecp.sio.filrougeapi.data;

import fr.ecp.sio.filrougeapi.model.Location;
import fr.ecp.sio.filrougeapi.model.Station;

import java.util.Arrays;
import java.util.List;

/**
 * A DataRepository that returns fake data.
 */
public class DummyDataRepository implements DataRepository {

    @Override
    public Station getStation(long id) {
        if (id == 25) {
            Station station = new Station();
            station.setId(25);
            station.setName("Station de test");
            Location loc = new Location();
            loc.setLatitude(46.0f);
            loc.setLongitude(1.0f);
            station.setLocation(loc);
            return station;
        } else {
            return null;
        }
    }

    @Override
    public List<Station> getStations() {
        return Arrays.asList(getStation(25));
    }

}
