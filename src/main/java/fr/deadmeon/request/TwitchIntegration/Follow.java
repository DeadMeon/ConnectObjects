package fr.deadmeon.request.TwitchIntegration;

import fr.deadmeon.pattern.Observable;

import java.util.Objects;

public class Follow implements Observable {
    public static final String OBJECT_TYPE = "TwitchFollow";
    public static final String MESSAGE_TO_SEND = "newTwitchFollow";

    private String from_id;
    private String from_login;
    private String from_name;
    private String to_id;
    private String to_login;
    private String to_name;
    private String followed_at;


    @Override
    public String getObjectType() { return OBJECT_TYPE; }

    @Override
    public String getMessageToSend() {
        return MESSAGE_TO_SEND;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_login() {
        return from_login;
    }

    public void setFrom_login(String from_login) {
        this.from_login = from_login;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getTo_login() {
        return to_login;
    }

    public void setTo_login(String to_login) {
        this.to_login = to_login;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getFollowed_at() {
        return followed_at;
    }

    public void setFollowed_at(String followed_at) {
        this.followed_at = followed_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(from_id, follow.from_id) && Objects.equals(from_login, follow.from_login) && Objects.equals(from_name, follow.from_name) && Objects.equals(to_id, follow.to_id) && Objects.equals(to_login, follow.to_login) && Objects.equals(to_name, follow.to_name) && Objects.equals(followed_at, follow.followed_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from_id, from_login, from_name, to_id, to_login, to_name, followed_at);
    }



    @Override
    public String toString() {
        return "Follow {" +
                "\n\tfrom_id='" + from_id + '\'' +
                "\n\tfrom_login='" + from_login + '\'' +
                "\n\tfrom_name='" + from_name + '\'' +
                "\n\tto_id='" + to_id + '\'' +
                "\n\tto_login='" + to_login + '\'' +
                "\n\tto_name='" + to_name + '\'' +
                "\n\tfollowed_at='" + followed_at + '\'' +
                "\n}";
    }
}
