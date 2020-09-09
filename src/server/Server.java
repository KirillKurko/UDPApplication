package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Server {

    private final static int port = 8080;
    private final static int bufferSize = 1024;
    private byte[] buffer = null;
    private DatagramSocket datagramSocket = null;

    public Server() {
        try {
            datagramSocket = new DatagramSocket(port);
            buffer = new byte[bufferSize];
            System.out.println("Server started on " + datagramSocket.getLocalAddress() + " : " + datagramSocket.getLocalPort());

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);

            InetAddress inetAddress = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            String message = new String(datagramPacket.getData()).trim();
            double[] variables = parseMessage(message);
            double value = calculateValue(variables[0], variables[1], variables[2]);

            ByteBuffer.wrap(buffer).putDouble(value);
            datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
            datagramSocket.send(datagramPacket);
            datagramSocket.close();

        }
        catch (Exception exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    private double calculateValue(double x, double y, double z) {
        double numerator = x + y / (5 + Math.sqrt(x));
        double denominator = Math.abs(y - x) + Math.cbrt(x);
        double temp = numerator / denominator * Math.exp(z + 1);
        return temp + Math.pow(Math.sin(z), 3);
    }

    private double[] parseMessage(String message) {
        double[] variables = new double[3];
        String[] strings = message.split(" ");
        for (int i = 0; i < strings.length; ++i) {
            variables[i] = Double.parseDouble(strings[i]);
        }
        return variables;
    }


    public static void main(String[] args) {
        Server server = new Server();
    }
}
