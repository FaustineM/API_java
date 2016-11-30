package fr.ecp.sio.filrougeapi.model;

/**
 * Created by Faustine on 30/11/2016.
 */
public class User {

    private long id;
    private String login;
    private String password;
    private String token;

    /*public User(long id, String login, String password, String token) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.token = token;
    }
*/
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
