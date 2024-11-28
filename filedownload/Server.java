package filedownload;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket accept = serverSocket.accept();
    }
}
