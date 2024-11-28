package filedownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket accept = serverSocket.accept();
        //读取客启端要下载的文件名
        InputStream inputStream = accept.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        String downloadFileName = "";
        while ((len = inputStream.read(bytes)) != -1) {
            downloadFileName += new String(bytes, 0, len);
            System.out.println("客启端希望下载的文件名=" + downloadFileName);
        }
        String 

    }
}
