package 房屋出租.utils;

public class TestUtility {
    //要求输入一个字符串, 长度最大为3
    public static void main(String[] args) {
        String s = Utility.readString(3);
        System.out.println("s="+s);
        //要求输入一个字符串, 长度最大为10, 如果用户直接回车, 就给个默认值
        String s2 = Utility.readString(10, "default");
        System.out.println("s2="+s2);
    }
}
