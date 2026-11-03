import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class ClientNIST {
    public static void main(String[] args) throws IOException {

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);
        byte[] empty = new byte[0];
        DatagramPacket requestPacket = new DatagramPacket(
                empty,
                0,
                serverIP,
                serverPort
        );
        DatagramSocket clientSocket = new DatagramSocket();
        // OS assigns the port number for client
        clientSocket.send(requestPacket);

        byte[] buf = new byte[4];
        DatagramPacket serverReply = new DatagramPacket(buf, buf.length);
        clientSocket.receive(serverReply);

        byte[] payload = Arrays.copyOfRange(
                serverReply.getData(),
                serverReply.getOffset(),
                serverReply.getOffset() + 4
        );

        int value = ByteBuffer.wrap(payload).getInt();
        long seconds = Integer.toUnsignedLong(value);

        ZonedDateTime epoch = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime eastern = epoch.plusSeconds(seconds).withZoneSameInstant((ZoneId.of("America/Indiana/Indianapolis")));

        System.out.println(seconds);
        System.out.println(eastern);

        clientSocket.close();
    }
}
