import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.time.ZoneId;

public class ServerNIST {
    public static final int PORT = 37;

    public static void main(String[] args) throws Exception {

        DatagramSocket serverSocket = new DatagramSocket(PORT);


        while (true){ // Need to force close silver when using while true loop
            DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);

            serverSocket.receive(clientRequest); //how to receive UDP packet

            InetAddress clientIP = clientRequest.getAddress(); //Extract IP from Client

            int clientPort = clientRequest.getPort();

            byte[] timeBytes = getCurrentTimeBytes();

            DatagramPacket replyPacket = new DatagramPacket(
                    timeBytes,
                    timeBytes.length,
                    clientIP,
                    clientPort
            );
            serverSocket.send(replyPacket);

        /*
         * TODO:
         *  - Create a DatagramSocket to listen on the specified port
         *  - Wait for a request from the client (ignore the packet content)
         *  - Call the provided method to generate the 4-byte array
         *  - Send the reply with the 4-byte array as application data
         *    back to the client
         */
        }
    }

    /**
     * Returns the current time as a 4-byte array (which encodes the number of
     * seconds elapsed since 1900-01-01 UTC), following the NIST time protocol.
     *
     * Do not modify this method.
     */
    public static byte[] getCurrentTimeBytes() {
        // Current time in UTC
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        // NIST epoch: 1900-01-01 00:00:00 UTC
        ZonedDateTime epoch = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

        // Compute the number of seconds between epoch and now
        long secondsSince1900 = now.toEpochSecond() - epoch.toEpochSecond();

        // Convert the number of seconds to a 4-byte array
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt((int) secondsSince1900);
        return buffer.array();
    }
}

