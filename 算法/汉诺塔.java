package 算法;

public class 汉诺塔 {

    public static void main(String[] args) {
        Tower tower = new Tower();
        tower.move(2, 'A', 'B', 'C');
    }
}

class Tower {
    public void move(int num, char a, char b, char c) {
        //如果只有一个盘
        if (num == 1) {
            System.out.println(a + "->" + c);

        } else {
            //如果有多个盘, 可以看成两个, 最下面和最上面的所有盘
            //先移动上面所有的盘到b
            move(num - 1, a, c, b);
            //最下面的一个盘
            System.out.println(a + "->" + c);
            //再把b塔所有的盘到c
            move(num - 1, b, a, c);

        }
    }
}