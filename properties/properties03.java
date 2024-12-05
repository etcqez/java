package properties;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class properties03 {
    public static void main(String[] args) throws IOException {

        //创建配置文件
        Properties properties = new Properties();

        //中文保存的是unicode
        properties.setProperty("charset", "utf8");
        properties.setProperty("user", "汤姆");
        properties.setProperty("pwd", "abc111");

        //存储到文件中
        properties.store(new FileOutputStream("properties/mysql2.properties"),null);
        System.out.println("保存配置文件成功");

    }
}
