package fr.ecp.sio.filrougeapi.data;

import fr.ecp.sio.filrougeapi.model.Location;
import fr.ecp.sio.filrougeapi.model.Station;
import fr.ecp.sio.filrougeapi.model.StationsStatistics;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A StationRepository backed by an SQL database.
 * This repository depends on the MySQL Connector dependency to connect to the SQL server.
 */
public class SQLStationRepository implements StationRepository {

    // The URL of the SQL server to connect to, including the host (localhost), the port, the database name (velib) and some options.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/velib?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Credentials to connect to the SQL server. It is a good practice to have credentials dedicated to the API for access.
    //TODO: We should not use root!
    private static final String USER = "root";
    private static final String PASSWORD = "promo2016";



    private Connection openConnection() throws SQLException {
        try {
            // This weird call is apparently useless but it ensures the SQL driver is registered.
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // This exception should never occur, wrap it as a SQLException an rethrow to avoid dealing with ClassNotFoundException.
            throw new SQLException(e);
        }
    }

    // A private method to create a station object from the current row of a ResultSet.
    private Station createStation(ResultSet rs) throws SQLException {
        Station s = new Station();
        s.setId(rs.getLong("id"));
        Location l = new Location();
        l.setLatitude(rs.getFloat("latitude"));
        l.setLongitude(rs.getFloat("longitude"));
        l.setAltitude(rs.getFloat("altitude"));
        s.setLocation(l);
        s.setName(rs.getString("name"));
        s.setAvailableBikes(rs.getInt("availableBikes"));
        s.setAvailableBikeStands(rs.getInt("availableBikeStands"));
        return s;
    }

    private StationsStatistics createStationsStat(int totalAvailableBikes, int totalAvailableBikeStands, List<Station> list) throws SQLException {
        StationsStatistics s = new StationsStatistics();
        s.setTotalAvailableBikes(totalAvailableBikes);
        s.setTotalAvailableBikeStands(totalAvailableBikeStands);
        s.setStationsList(list);
        return s;
    }

    @Override
    public Station getStationByID(long id) throws IOException {
        // This syntax is a "try-with-resources".
        // We can instantiate in the brackets classes that implements AutoClosable (= have a close() method).
        // These 'resources' will have their close() method called at the end of the block, even in case of error.
        try (
            // Open a connection to the database.
            Connection c = openConnection();
            // Prepare a statement, with '?' placeholders.
            // Prepared statements are efficiently cached and allow a safe handling of placeholders.
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM stations WHERE id = ?");
        ) {
            // Provide values for placeholders (indexes start at 1 is SQL!)
            stmt.setLong(1, id);
            // The statement is ready, we can execute it and get the result.
            try (ResultSet rs = stmt.executeQuery()) {
                // ResultSet works like an iterator: we call next() to position to the next line, then read with other methods.
                if (!rs.next()) return null;
                return createStation(rs);
            }
        } catch (SQLException e) {
            // The StationRepository interface only allows exceptions of type IOException to be thrown, so we encapsulate the SQLException.
            throw new IOException("Database error", e);
        }
    }

    @Override
    public Station getStationByName(String name) throws IOException {
        // This syntax is a "try-with-resources".
        // We can instantiate in the brackets classes that implements AutoClosable (= have a close() method).
        // These 'resources' will have their close() method called at the end of the block, even in case of error.
        try (
                // Open a connection to the database.
                Connection c = openConnection();
                // Prepare a statement, with '?' placeholders.
                // Prepared statements are efficiently cached and allow a safe handling of placeholders.
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM stations WHERE name LIKE ?");
        ) {
            // Provide values for placeholders (indexes start at 1 is SQL!)
            // The '%' enables to select all station with name containing the string sent in the HTTP request.:
            stmt.setNString(1, '%' + name + '%');
            // The statement is ready, we can execute it and get the result.
            try (ResultSet rs = stmt.executeQuery()) {
                // ResultSet works like an iterator: we call next() to position to the next line, then read with other methods.
                if (!rs.next()) return null;
                return createStation(rs);
            }
        } catch (SQLException e) {
            // The StationRepository interface only allows exceptions of type IOException to be thrown, so we encapsulate the SQLException.
            throw new IOException("Database error", e);
        }
    }

    @Override
    public StationsStatistics getStations() throws IOException {
        // Statistics : total number of available bikes and bike stands, calculated while returning the stationd list.
        int totalAvailableBikes = 0;
        int totalAvailableBikeStands = 0;
        try (
            Connection c = openConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM stations");
            ResultSet rs = stmt.executeQuery();
        ) {
            List<Station> list = new ArrayList<>();
            // Iterate over all the lines of the result.

            while (rs.next()) {
                list.add(createStation(rs));
                totalAvailableBikes += rs.getInt("availableBikes");
                totalAvailableBikeStands += rs.getInt("availableBikeStands");
            }
            return createStationsStat(totalAvailableBikes, totalAvailableBikeStands, list);

        } catch (SQLException e) {
            throw new IOException("Database error", e);
        }
    }

    @Override
    public StationsStatistics getStationsUsingPag(int limit, int offset) throws IOException {
        // Statistics : total number of available bikes and bike stands, calculated while returning the stationd list.
        int totalAvailableBikes = 0;
        int totalAvailableBikeStands = 0;
        try (
                Connection c = openConnection();
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM stations ORDER BY id LIMIT ? OFFSET ?");
        )
         {
             // set the preparedstatement parameters
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            List<Station> list = new ArrayList<>();
            // Iterate over all the lines of the result.
            while (rs.next()) {
                list.add(createStation(rs));
                totalAvailableBikes += rs.getInt("availableBikes");
                totalAvailableBikeStands += rs.getInt("availableBikeStands");
            }

             return createStationsStat(totalAvailableBikes, totalAvailableBikeStands, list);

        }  catch (SQLException e) {
            throw new IOException("Database error", e);
        }

    }

}
