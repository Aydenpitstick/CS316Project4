import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPEchoServer {
    public static void main(String[] args) throws IOException {
        int serverPort = 3000;
        //Valid Port Numbers1 - 65,636, 1924 - 49151 recommended registration port number
        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        //Socket is required to send & receive packet
        DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);



        while (true){ // Need to force close silver when using while true loop
            serverSocket.receive(clientRequest); //how to receive UDP packet
            InetAddress clientIP = clientRequest.getAddress(); //Extract IP from Client
            int clientPort = clientRequest.getPort();

            byte[] payload = Arrays.copyOf(
                    clientRequest.getData(),
                    clientRequest.getLength()
            );

            String clientMessage = new String(payload);
            System.out.println("Client Message: "+ clientMessage);
            String replyMessage = clientMessage.toUpperCase();
            DatagramPacket replyPacket = new DatagramPacket(
                    replyMessage.getBytes(),
                    replyMessage.getBytes().length,
                    clientIP,
                    clientPort
            );
            serverSocket.send(replyPacket);
        } //end of while loop (Currently each loop only reads 1 request)
    }
}
