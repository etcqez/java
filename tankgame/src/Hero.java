import java.util.Vector;

public class Hero extends Tank {

    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        //每调用一次该方法就产生一个Shot对象，并且运行run方法，和方最多发射5
        if (shots.size() == 5) {
            return;
        }
        //确定第一颗子弹的位置
        if (getDirect() == 0) {
            shot = new Shot(getX() + 20, getY(), 0);
        } else if (getDirect() == 1) {
            shot = new Shot(getX() + 60, getY() + 20, 1);
        } else if (getDirect() == 2) {
            shot = new Shot(getX() + 20, getY() + 60, 2);
        } else if (getDirect() == 3) {
            shot = new Shot(getX(), getY() + 20, 3);
        }

        shots.add(shot);
        //启动shot线程 运行run方法
        new Thread(shot).start();
    }

}
