package WebCommunication;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private String SERVER_IP;
    private Integer SERVER_PORT;

    public TCPClient(String ip, Integer port) {
        SERVER_IP = ip;
        SERVER_PORT = port;
    }

    public void sendMessage(String message) throws Exception {
        InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
        Socket socket = new Socket(serverAddress, SERVER_PORT);
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    public String receiveMessage() throws Exception {
        InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
        Socket socket = new Socket(serverAddress, SERVER_PORT);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }
}
