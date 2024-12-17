package 算法;

public class 老鼠出迷宫 {
    public static void main(String[] args) {
        //思路
        //1. 先创建迷宫, 用二维数组表示 int[][] map = new int[8][7]
        //2. 先规定map数组的元素值: 0 表示可以走 1 表示障碍物
        int[][] map = new int[8][7];
        //将最上面的一行和最下面的一行, 全部设置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //将最右面的一列和最左面的一列, 全部设置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[3][1] = 1;
        map[3][2] = 1;
        //输出当前地图
        System.out.println("===当前地图情况===");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        //使用findWay 给老鼠找路
        T t1=new T();
        t1.findWay(map,1,1);
        System.out.println("\n===找路的情况如下===");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class T {

    /**
     * 使用递归回溯的思想来解决老鼠出迷宫问题
     *
     * @param map 二维数组, 即表示迷宫
     * @param i   * @param j 老鼠的位置, 初始化的位置为(1,1)
     * @return 如果找到就返回true, 否则返回false
     * 规定: 0 表示可以走 1 表示障碍物 2 表示可以走 3 表示走过, 但是走不通, 是死路
     * 当map[6][5] == 2 就说明找到通路了,就可以结束 否则就继续找
     * 先确定老鼠找路的策略 下-> 右-> 上-> 左
     */
    public boolean findWay(int[][] map, int i, int j) {
        //到出口
        if (map[6][5] == 2) {
            return true;
        } else {
            //假如当前位置可以走
            if (map[i][j] == 0) {
                //假定可以走通
                map[i][j] = 2;
                if (findWay(map, i + 1, j)) {
                    return true;
                } else if (findWay(map, i, j + 1)) {
                    return true;
                } else if (findWay(map, i - 1, j)) {
                    return true;
                } else if (findWay(map, i, j - 1)) {
                    return true;
                } else {
                    map[i][j] = 3;
                    return false;
                }
            } else { //map[i][j] == 1, 2, 3
                return false;
            }
        }
    }
}