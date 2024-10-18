public class 前后加加 {
    /**
     * int i = i++
     *
     * @param args
     */
    public static void main(String[] args) {
        int i = 1;
        // 行为temp=i; i=i+1; i=temp
        // 总结i=temp
        i = i++;
        System.out.println("int i = 1; i = i++的值: " + i);
        reverse();
    }

    static void reverse() {
        // 行为i=i+1; temp=i; i=temp
        // 总结i=i+i
        int i = 1;
        i = ++i;
        System.out.println("int i =1; i = ++i的值: " + i);
    }
}
