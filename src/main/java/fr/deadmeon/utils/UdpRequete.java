package fr.deadmeon.utils;

import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;

public class UdpRequete implements Runnable {
    private static final int PORT = 2390;
    private static final int TARGET_KEY_LENGTH = 21;
    private static final int TIMEOUT = 500;
    private static final int TIMEOUT_RECEIVE = 10000;
    private static final String key = new SecureRandom().ints(48, 122 + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(TARGET_KEY_LENGTH)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            
    private DatagramSocket socket;
    private InetAddress address;
    private String host;

    private byte[] buf;

    private boolean isReachable = false;

    public UdpRequete(String host) throws SocketException, UnknownHostException {
        this.host = host;
        this.socket = new DatagramSocket();
        this.address = InetAddress.getByName(host);
    }

    public boolean isReachable() {
        return this.isReachable;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }

    public void scan() throws IOException {
        send(key);
        if (receive().equals(key + ":linked")) {
            setReachable(true);
            System.out.println("Linked !");
        }
    }

    public void send(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        socket.send(packet);
    }

    public String receive() throws IOException {
        buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.setSoTimeout(TIMEOUT_RECEIVE);
        socket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }

    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator + 1, lastSeparator + 1);
    }

    public void run() {
        try {
            if (InetAddress.getByName(host).isReachable(TIMEOUT)) {
                System.out.println(host + " is reachable");
                try {
                    scan();
                } catch (Exception s) {
                    System.out.println(host + " timed out");
                } finally {
                    close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        List<UdpRequete> arduinoList = new ArrayList<UdpRequete>();
        List<UdpRequete> scannedNetworks = new ArrayList<UdpRequete>();

        try {
            String currentIP = InetAddress.getLocalHost().toString();
            String subnet = getSubnet(currentIP);

            do {
                scannedNetworks.clear();
                for (int i = 1; i < 254; i++) {
                    String host = subnet + i;
                    scannedNetworks.add(new UdpRequete(host));
                    Thread thread = new Thread(scannedNetworks.get(i - 1));
                    thread.start();
                }
                Thread.sleep(20000);
            } while (!(scannedNetworks.stream().anyMatch(x -> (x.isReachable == true))));
        } catch (Exception e) {
            System.out.println(e);
        }

        scannedNetworks.stream().filter(x -> x.isReachable).forEach(x -> arduinoList.add(x));
        arduinoList.stream().forEach(x -> System.out.println(x.host));
    }
}
