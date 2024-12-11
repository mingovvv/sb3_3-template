package demo.template.sb3_3template.tcpip.dto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public record Packet(

        AuthHeader authHeader,
        AuthBody authBody

) {

    public static Packet parse(byte[] data) {
        byte[] headerBytes = new byte[12];
        byte[] messageBytes = new byte[4];

        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.get(headerBytes);
        buffer.get(messageBytes);

        AuthHeader header = AuthHeader.parse(headerBytes);
        AuthBody message = AuthBody.parse(messageBytes);

        return new Packet(header, message);
    }

    public byte[] toBytes() {
        byte[] headerBytes = authHeader.toBytes();
        byte[] messageBytes = authBody.toBytes();

        ByteBuffer buffer = ByteBuffer.allocate(headerBytes.length + messageBytes.length);
        buffer.put(headerBytes);
        buffer.put(messageBytes);

        return buffer.array();
    }

    public record AuthHeader(
            String message1,
            String message2,
            String message3
    ) {

        public AuthHeader(String message1, String message2, String message3) {
            this.message1 = String.format("%-4s", message1);
            this.message2 = String.format("%-4s", message2);
            this.message3 = String.format("%-4s", message3);
        }

        public byte[] toBytes() {
            return (message1 + message2 + message3).getBytes(StandardCharsets.UTF_8);
        }

        public static AuthHeader parse(byte[] data) {
            String headerString = new String(data, StandardCharsets.UTF_8);
            String message1 = headerString.substring(0, 4).trim();
            String message2 = headerString.substring(4, 8).trim();
            String message3 = headerString.substring(8, 12).trim();
            return new AuthHeader(message1, message2, message3);
        }

    }

    public record AuthBody(
            String body
    ) {

        public AuthBody(String body) {
            this.body = String.format("%-4s", body);
        }

        public byte[] toBytes() {
            return (body).getBytes(StandardCharsets.UTF_8);
        }

        public static AuthBody parse(byte[] data) {
            String headerString = new String(data, StandardCharsets.UTF_8);
            String body = headerString.substring(0,4).trim();
            return new AuthBody(body);
        }
    }


}
