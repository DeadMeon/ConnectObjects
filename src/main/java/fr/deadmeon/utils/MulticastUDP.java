package fr.deadmeon.utils;

import java.io.*;
import java.net.*;

import fr.deadmeon.manager.ArduinoDataRecieveManager;


public class MulticastUDP {
    private static final int PORT = 2390;
    private static final String address = "230.1.1.1"; // "229.97.225.142"
    private static final String separator = ":";
    private MulticastSocket  socket;
    private InetAddress group;

    public MulticastUDP() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(address);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
        socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] buf = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
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

    public static String formatting(String key, String msg) {
        return key + separator + msg;
    }
}
