package fr.ecp.sio.filrougeapi.model;

import java.util.List;

/**
 * A POJO to represent the list of all stationsList and their statistics (total number of available bikes and bikes' stands.
 */
public class StationsStatistics {

    private int totalAvailableBikes;
    private int totalAvailableBikeStands;
    private List<Station> stationsList;

    public List<Station> getStationsList() {
        return stationsList;
    }

    public void setStationsList(List<Station> stationsList) {
        this.stationsList = stationsList;
    }

    public int getTotalAvailableBikes() {
        return totalAvailableBikes;
    }

    public void setTotalAvailableBikes(int totalAvailableBikes) {
        this.totalAvailableBikes = totalAvailableBikes;
    }

    public int getTotalAvailableBikeStands() {
        return totalAvailableBikeStands;
    }

    public void setTotalAvailableBikeStands(int totalAvailableBikeStands) {
        this.totalAvailableBikeStands = totalAvailableBikeStands;
    }
}
