package fr.deadmeon.request.TwitchIntegration;

import java.util.Arrays;

public class BroadcasterFollows {
    private int total;
    private Follow[] data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Follow[] getData() {
        return data;
    }

    public void setData(Follow[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BroadcasterFollows {" +
                "\n\ttotal=" + total +
                "\n\tdata=" + Arrays.toString(data) +
                "\n}";
    }
}
