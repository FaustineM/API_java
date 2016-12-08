package fr.ecp.sio.filrougeapi.model;

import java.io.Serializable;

/**
 * A POJO to represent a user.
 */
public class User implements Serializable{

    private String login; // unique login used as the id of the user
    private String password;
    private String token;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
