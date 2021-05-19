package fr.deadmeon.request.TwitchIntegration;

import java.util.Arrays;

public class BroadcasterSubscriptions {
    private Subscription[] data;

    public Subscription[] getData() {
        return data;
    }

    public void setData(Subscription[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BroadcasterSubscriptions {" +
                "\n\tdata=" + Arrays.toString(data) +
                "\n}";
    }
}
