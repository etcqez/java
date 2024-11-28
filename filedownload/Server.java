package filedownload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
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
        String resFileName = ""; //返回的文件名
        if ("cat.jpg".equals(downloadFileName)) {
            resFileName = "cat.jpg";
        } else {
            resFileName = "dog.jpg";
        }

        //创建一个输入流，读取文件
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(resFileName));

        //使用工具类，读取一个文件到字符数组
        byte[] bytes1 = StreamUtils.streamToByteArray(bufferedInputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(accept.getOutputStream());
        bufferedOutputStream.write(bytes1);
        accept.shutdownOutput();

        //关闭相关流
        bufferedOutputStream.close();
        bufferedInputStream.close();
        inputStream.close();

        accept.close();
        serverSocket.close();

        System.out.println("服");

    }
}
