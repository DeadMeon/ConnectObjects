package fr.deadmeon.request.TwitchIntegration;

import java.util.Arrays;

public class Validate {
    private String client_id;
    private String login;
    private String[] scopes;
    private String user_id;
    private int expires_in; // en seconde

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "Validate {" +
                "\n\tclient_id='" + client_id + '\'' +
                "\n\tlogin='" + login + '\'' +
                "\n\tscopes=" + Arrays.toString(scopes) +
                "\n\tuser_id='" + user_id + '\'' +
                "\n\texpires_in=" + expires_in +
                "\n}";
    }
}
