public class switch穿透 {
    public static void main(String[] args) {
        char a='a';
//        // switch后面可以为字符串, 但是csae后面类型必须也为字符串
//        String a="a";
        switch (a){
            // 如果a==常量1, 执行语句块1, 如果没有break, 直接进入到语句块2, 而不会判断常量2
            case 'a':
                System.out.println('a');
            case 'b':
                System.out.println('b');
            case 'c':
                System.out.println('c');
//            case 2.2:   //case只能是常量, 且不能是float, double
//                System.out.println('s');
        }
    }
}
