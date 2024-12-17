import java.io.*;
import java.util.Vector;

public class Recorder {

    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义IO对象，准备写文件到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String reccordFile = "/Users/f/IdeaProjects/tankgame/src/MyRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static String getReccordFile() {
        return reccordFile;
    }

    //读取recordFile，恢复相关信息
    //该方法在继续上局的时候调用
    public static Vector<Node> getNodesAndTankRec() {

        try {
            br = new BufferedReader(new FileReader(reccordFile));
                allEnemyTankNum = Integer.parseInt(br.readLine());
                //循环读取文件，生成 node 集合
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] xyd = line.split(" ");
                    Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                            Integer.parseInt(xyd[2]));
                    nodes.add(node);
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return nodes;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //增加一个方法，当游戏退出时，我们将 allEnemyTankNum 保存到 MyRecord.txt
    //对keepRecord 进行升级，保存敌人坦克的坐标和方向
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(reccordFile));
            bw.write(allEnemyTankNum + "\n");

            //遍历敌人坦克的Vector，然后根据情况保存即可
            //OOP，定义一个属性，然后通过setXxx得到敌人坦克的Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入到文件
                    bw.write(record + "\n");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁一个敌人坦克，就应当 allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
