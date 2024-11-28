package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiverA {
    public static void main(String[] args) throws SocketException {
        //创建一个 DatagramSocket 对象，准备在9999接收数据
        DatagramSocket socket = new DatagramSocket(9999);
        //创建一个DatagramPacket对象，准备接收数据
        //一个数据包最大64k
        byte[] buf = new byte[1024];
        new DatagramPacket(buf,buf.length)



    }
}
