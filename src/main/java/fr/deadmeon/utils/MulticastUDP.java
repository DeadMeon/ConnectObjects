package fr.deadmeon.utils;

import java.io.*;
import java.net.*;

public class MulticastUDP {
    private static final int PORT = 2390;
    private static final String address = "229.97.225.142"; // "229.97.225.142"
    private static final String separator = ":";
    private MulticastSocket socket;
    private InetAddress group;

    public MulticastUDP() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(address);
            socket.joinGroup(group);
            socket.setSoTimeout(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String from, String to, String msg) throws IOException {
        String str = to + separator + from + separator + msg;
        DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), group, PORT);
        socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] buf = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public boolean isToMe(String key, String msg) {
        return msg.split(":")[0].equals(key);
    }

    public String getData(String msg) {
        return msg.split(":")[2];
    }

    public void close() {
        try {
            socket.leaveGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
