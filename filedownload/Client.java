package filedownload;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入下载的文件名");
        String downloadFileName = scanner.next();
    }
}
