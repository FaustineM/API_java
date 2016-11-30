package fr.ecp.sio.filrougeapi.data;

import fr.ecp.sio.filrougeapi.model.User;
import java.io.IOException;
import java.sql.*;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * A UserRepository backed by an SQL database.
 * This repository depends on the MySQL Connector dependency to connect to the SQL server.
 */
public class SQLUserRepository implements UserRepository {

    // The URL of the SQL server to connect to, including the host (localhost), the port, the database name (velib) and some options.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/velib?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Credentials to connect to the SQL server. It is a good practice to have credentials dedicated to the API for access.
    //TODO: We should not use root!
    private static final String USER = "root";
    private static final String PASSWORD = "promo2016";

    private SecureRandom random = new SecureRandom();

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
    private User createUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setLogin("login");
        u.setPassword("password");
        String newToken = new tokenGenerator();
        u.setToken(newToken);

        updateUser(u.getToken(), u.getId());

        return u;
    }

/*
    private void authenticate(String login, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    }
*/
    // Issue a token
    // Return the issued token
    protected String tokenGenerator() {
        return new BigInteger(130, random).toString(32);
    }

    // The user is updated : adding the generated token
    private void updateUser(String token, long id) throws SQLException {
        // Open a connection to the database.
        Connection c = openConnection();
        // Prepare a statement, with '?' placeholders.
        // Prepared statements are efficiently cached and allow a safe handling of placeholders.
        PreparedStatement stmt = c.prepareStatement("UPDATE Users SET token = ? WHERE id = ?");

        // set the preparedstatement parameters
        stmt.setString(1,token);
        stmt.setLong(2, id);

        // call executeUpdate to execute our sql update statement
        stmt.executeUpdate();
        stmt.close();
    }


    private boolean isValidUser(String login, String password) throws SQLException {
        try (
                // Open a connection to the database.
                Connection c = openConnection();
                // Prepare a statement, with '?' placeholders.
                // Prepared statements are efficiently cached and allow a safe handling of placeholders.
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
        ) {


            // set the preparedstatement parameters
            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                // ResultSet works like an iterator: we call next() to position to the next line, then read with other methods.
                if (!rs.next()) return false;
                return true;
            }
        } catch (SQLException e) {
            // The StationRepository interface only allows exceptions of type IOException to be thrown, so we encapsulate the SQLException.
            throw new IOException("Database error", e);
        }
    }

    public static boolean isValidToken(String token) {
        try (
                // Open a connection to the database.
                Connection c = openConnection();
                // Prepare a statement, with '?' placeholders.
                // Prepared statements are efficiently cached and allow a safe handling of placeholders.
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE token = ?");
        ) {
            // Provide values for placeholders (indexes start at 1 is SQL!)
            stmt.setLong(1, token);
            // The statement is ready, we can execute it and get the result.
            try (ResultSet rs = stmt.executeQuery()) {
                // ResultSet works like an iterator: we call next() to position to the next line, then read with other methods.
                if (!rs.next()) return false;
                return true;
            }
        } catch (SQLException e) {
            // The StationRepository interface only allows exceptions of type IOException to be thrown, so we encapsulate the SQLException.
            throw new IOException("Database error", e);
        }
    }

    @Override
    public User getUser(String login, String password) throws IOException{

        try (
                // Open a connection to the database.
                Connection c = openConnection();
                // Prepare a statement, with '?' placeholders.
                // Prepared statements are efficiently cached and allow a safe handling of placeholders.
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE login LIKE ?");
        ) {
            // Provide values for placeholders (indexes start at 1 is SQL!)
            // The '%' enables to select all station with name containing the string sent in the HTTP request.:
            stmt.setNString(1, login);
            // The statement is ready, we can execute it and get the result.
            try (ResultSet rs = stmt.executeQuery()) {
                // ResultSet works like an iterator: we call next() to position to the next line, then read with other methods.


                User u = new createUser(rs);
                if (!rs.next()) return null;

                /*if (.... != password) return null;*/

                return u;
            }




        } catch (SQLException e) {
            // The StationRepository interface only allows exceptions of type IOException to be thrown, so we encapsulate the SQLException.
            throw new IOException("Database error", e);
        }

    }


}
