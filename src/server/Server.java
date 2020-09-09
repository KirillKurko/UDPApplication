package server;

import java.net.DatagramSocket;

public class Server {

    private final static int port = 8080;
    private final static int bufferSize = 1024;
    private DatagramSocket datagramSocket = null;

    public Server() {
        try {

        }
        catch (Exception exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    private double calculateValue(float x, float y, float z) {
        double numerator = x + y / (5 + Math.sqrt(x));
        double denominator = Math.abs(y - x) + Math.cbrt(x);
        double temp = numerator / denominator * Math.exp(z + 1);
        return temp + Math.pow(Math.sin(z), 3);
    }


    public static void main(String[] args) {
        Server server = new Server();
    }
}
