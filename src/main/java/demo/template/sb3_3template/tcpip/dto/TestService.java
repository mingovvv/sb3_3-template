package demo.template.sb3_3template.tcpip.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestService {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {

        // Create Packet
        Packet packet = new Packet(null, null);

        // Send and receive Packet over Socket
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream()) {

            // Serialize and send packet
            byte[] packetBytes = packet.toBytes();
            outputStream.write(packetBytes);
            outputStream.flush();
            System.out.println("Sent Packet: " + packet);

            // Read response
            byte[] responseBytes = new byte[120]; // Header: 20 bytes, Message: 100 bytes
            int bytesRead = inputStream.read(responseBytes);

            if (bytesRead == responseBytes.length) {
                Packet receivedPacket = Packet.parse(responseBytes);
                System.out.println("Received Packet: " + receivedPacket);
            } else {
                System.err.println("Incomplete response received.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        Packet packet = new Packet(null, null);

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            sendPacket(socket, packet);
            Packet response = receivePacket(socket);

            System.out.println("Sent Packet: " + packet);
            System.out.println("Received Packet: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendPacket(Socket socket, Packet packet) throws IOException {
        try (OutputStream outputStream = socket.getOutputStream()) {
            byte[] packetBytes = packet.toBytes();
            outputStream.write(packetBytes);
            outputStream.flush();
        }
    }

    private static Packet receivePacket(Socket socket) throws IOException {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] responseBytes = new byte[120]; // Header: 20 bytes, Message: 100 bytes
            int bytesRead = inputStream.read(responseBytes);

            if (bytesRead == responseBytes.length) {
                return Packet.parse(responseBytes);
            } else {
                throw new IOException("Incomplete response received.");
            }
        }
    }

}
