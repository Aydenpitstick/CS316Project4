import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class ClientNIST {
    public static void main(String[] args) throws IOException {
        if (args.length != 2){
            System.out.println("Need 2 Arguments: ServerIP & ServerPort");
            return;
        }
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);
        DatagramPacket requestPacket = new DatagramPacket(
                input.getBytes(),
                input.getBytes().length,
                serverIP,
                serverPort
        );
        DatagramSocket clientSocket = new DatagramSocket();
        // OS assigns the port number for client
        clientSocket.send(requestPacket);

        DatagramPacket serverReply = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(serverReply);

        byte[] payload = Arrays.copyOf(
                serverReply.getData(),
                serverReply.getLength()
        );

        String replyMessage = new String(payload);
        System.out.println("Server Reply" + replyMessage);
        clientSocket.close();
    }
}
