import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPFileUploadClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        //创建读取磁盘文件的输入流
        String filePath = "cat.jpg";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));

        //bytes 就是fielPath对应的字节数组
        byte[] bytes = StreamUtils.streamToByteArray(bis);

        //通过socket获取到输出流，将bytes发送给服务端
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        bos.flush();
        bis.close();
        //设置写入数据的结束标记
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        //使用StreamUtils的方法，直接将inputStream读取到的内容转成字符串
        String s = StreamUtils.streamToString(inputStream);
        System.out.println(s);

        inputStream.close();
        bos.close();
        socket.close();


    }

}