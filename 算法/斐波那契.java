package 算法;

public class 斐波那契 {
    public int fibonacci(int n) {
        if (n >= 1) {
            if (n == 1 || n == 2) {
                return 1;
            } else {
                return fibonacci(n - 1) + fibonacci(n - 2);
            }
        }
        else {
            System.out.println("要求输入n>=1的数");
            return -1;
        }
    }

    public static void main(String[] args) {
        斐波那契 t = new 斐波那契();
        int result = t.fibonacci(7);
        System.out.println(result);
    }
}
