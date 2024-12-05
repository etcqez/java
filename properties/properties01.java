package properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class properties01 {
    public static void main(String[] args) throws IOException {

        //用以前学过的读取
        BufferedReader bufferedReader = new BufferedReader(new FileReader("properties/mysql.properties"));

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split("=");
            System.out.println(split[0] + " 的值是：" + split[1]);
        }

    }
}
