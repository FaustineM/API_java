package fr.ecp.sio.filrougeapi.model;

/**
 * Created by Faustine on 30/11/2016.
 */
public class User {

    private String login;
    private String password;
    private long token;

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

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }
}
