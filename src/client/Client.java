package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private final static int port = 8080;
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private final static int bufferSize = 1024;
    private byte[] buffer;

    public Client() {
        try {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName("localhost");
            buffer = new byte[bufferSize];

            double[] variables = getVariablesFromInput();
            buffer = createMessage(variables).getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
            datagramSocket.send(datagramPacket);

            datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            String message = new String(datagramPacket.getData()).trim();
            System.out.println("Result: " + message);
            datagramSocket.close();
        }
        catch (Exception exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }


    private String createMessage(double[] variables) {
        return variables[0] + " " + variables[1] + " " + variables[2];
    }

    private double[] getVariablesFromInput() {
        double[] variables = new double[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter x: ");
        variables[0] = scanner.nextDouble();
        System.out.println("Enter y: ");
        variables[1] = scanner.nextDouble();
        System.out.println("Enter z: ");
        variables[2] = scanner.nextDouble();
        return variables;
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
