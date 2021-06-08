package fr.deadmeon.data;

import fr.deadmeon.request.TwitchIntegration.Follow;
import fr.deadmeon.request.TwitchIntegration.Subscription;

public class TwitchIntegrationSave {
    private String token;
    private Subscription subscription;
    private Follow follow;

    public TwitchIntegrationSave() {
    }

    public TwitchIntegrationSave(String token, Subscription subscription, Follow follow) {
        this.token = token;
        this.subscription = subscription;
        this.follow = follow;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
