package fr.deadmeon.request.TwitchIntegration;

import fr.deadmeon.pattern.Observable;
import java.util.Objects;

public class Subscription implements Observable {
    public static final String OBJECT_TYPE = "TwitchSub";
    public static final String MESSAGE_TO_SEND = "newTwitchSub";

    private String broadcaster_id;
    private String broadcaster_login;
    private String broadcaster_name;
    private String gifter_id;
    private String gifter_login;
    private String gifter_name;
    private boolean is_gift;
    private String tier;
    private String plan_name;
    private String user_id;
    private String user_name;
    private String user_login;

    @Override
    public String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    public String getMessageToSend() {
        return MESSAGE_TO_SEND;
    }

    public String getBroadcaster_id() {
        return broadcaster_id;
    }

    public void setBroadcaster_id(String broadcaster_id) {
        this.broadcaster_id = broadcaster_id;
    }

    public String getBroadcaster_login() {
        return broadcaster_login;
    }

    public void setBroadcaster_login(String broadcaster_login) {
        this.broadcaster_login = broadcaster_login;
    }

    public String getBroadcaster_name() {
        return broadcaster_name;
    }

    public void setBroadcaster_name(String broadcaster_name) {
        this.broadcaster_name = broadcaster_name;
    }

    public String getGifter_id() {
        return gifter_id;
    }

    public void setGifter_id(String gifter_id) {
        this.gifter_id = gifter_id;
    }

    public String getGifter_login() {
        return gifter_login;
    }

    public void setGifter_login(String gifter_login) {
        this.gifter_login = gifter_login;
    }

    public String getGifter_name() {
        return gifter_name;
    }

    public void setGifter_name(String gifter_name) {
        this.gifter_name = gifter_name;
    }

    public boolean isIs_gift() {
        return is_gift;
    }

    public void setIs_gift(boolean is_gift) {
        this.is_gift = is_gift;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return is_gift == that.is_gift && Objects.equals(broadcaster_id, that.broadcaster_id) && Objects.equals(broadcaster_login, that.broadcaster_login) && Objects.equals(broadcaster_name, that.broadcaster_name) && Objects.equals(gifter_id, that.gifter_id) && Objects.equals(gifter_login, that.gifter_login) && Objects.equals(gifter_name, that.gifter_name) && Objects.equals(tier, that.tier) && Objects.equals(plan_name, that.plan_name) && Objects.equals(user_id, that.user_id) && Objects.equals(user_name, that.user_name) && Objects.equals(user_login, that.user_login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broadcaster_id, broadcaster_login, broadcaster_name, gifter_id, gifter_login, gifter_name, is_gift, tier, plan_name, user_id, user_name, user_login);
    }

    @Override
    public String toString() {
        return "Subscription {" +
                "\n\tbroadcaster_id='" + broadcaster_id + '\'' +
                "\n\tbroadcaster_login='" + broadcaster_login + '\'' +
                "\n\tbroadcaster_name='" + broadcaster_name + '\'' +
                "\n\tgifter_id='" + gifter_id + '\'' +
                "\n\tgifter_login='" + gifter_login + '\'' +
                "\n\tgifter_name='" + gifter_name + '\'' +
                "\n\tis_gift=" + is_gift +
                "\n\ttier='" + tier + '\'' +
                "\n\tplan_name='" + plan_name + '\'' +
                "\n\tuser_id='" + user_id + '\'' +
                "\n\tuser_name='" + user_name + '\'' +
                "\n\tuser_login='" + user_login + '\'' +
                "\n}";
    }
}
