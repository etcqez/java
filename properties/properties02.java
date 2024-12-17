package properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class properties02 {
    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        //加载指定配置文件
        properties.load(new FileReader("properties/mysql.properties"));
        //把k-v显示到控制台
        properties.list(System.out);
        //根据key 获取对应的值
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        System.out.println("用户名=" + user);
        System.out.println("密码是=" + pwd);


    }
}
