import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //说明，当子弹击中敌人坦克，就加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    Vector<Node> nodes = new Vector<>();
    int enemyTankSize = 6;

    //定义三张炸弹图片，用于显示爆炸效果
    BufferedImage image1 = null;
    BufferedImage image2 = null;
    BufferedImage image3 = null;

    public MyPanel(String key) {
        File file = new File(Recorder.getReccordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndTankRec();
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        //将MyPanel对象的 enemyTanks 设置给Record的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(700, 100);
        hero.setSpeed(5);

        switch (key) {
            //1: 重新开始    2: 继续上局
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (1 + i), 100);
                    //将enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //敌方坦克初始为下方向
                    enemyTank.setDirect(3);
                    //启动敌人坦克线程，让它动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //启动shot对象
                    new Thread(shot).start();
                    enemyTank.shots.add(shot);
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":

                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //敌方坦克初始为下方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程，让它动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //启动shot对象
                    new Thread(shot).start();
                    enemyTank.shots.add(shot);
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }

        //初始化图片对象
        try {
            image1 = ImageIO.read(new File("src/bomb_1.png"));
            image2 = ImageIO.read(new File("src/bomb_2.png"));
            image3 = ImageIO.read(new File("src/bomb_3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        new PlayAudio("src/111.wav").start();
//        String filePath = "src/111.wav"; // 替换为你的 WAV 文件路径
//        WavPlayer player = new WavPlayer(filePath);
//        // 开始播放
//        player.startPlaying();
//        System.out.println("Playing audio...");


    }

    //编写方法，显示我方击毁敌方坦克信息
    public void showInfo(Graphics g) {

        //画出玩家的总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累积击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色
        showInfo(g);

        if (hero != null && hero.isLive) {
            //画出坦克
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //画出hero射击的子弹
//        if (hero.shot != null && hero.shot.isLive == true) {
//            g.draw3DRect(hero.shot.getX(), hero.shot.getY(), 1, 1, false);
//        }
        //将hero的子弹集合 shots ,遍历取出绘制
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                //画出hero的所有子弹
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else { //如果该shot对象已经无效，就从shots集合中拿掉，多线程一定要销毁不需要的线程
                hero.shots.remove(shot);
            }
        }

        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为0，就从bombs的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * @param x      坦克的左上角x坐标
     * @param y
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
//        根据不同类型坦克，设置不同颜色
        switch (type) {
            case 0: //我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1: //敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false); //画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false); //画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20); //画出坦克盖子
                g.drawLine(x + 20, y + 30, x + 20, y); //画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false); //画出坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false); //画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20); //画出坦克盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20); //画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false); //画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false); //画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20); //画出坦克盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60); //画出炮筒
                break;
            case 3: //表示向右
                g.fill3DRect(x, y, 60, 10, false); //画出坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false); //画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20); //画出坦克盖子
                g.drawLine(x + 30, y + 20, x, y + 20); //画出炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    //如果我们的坦克可以发射多个子弹
    //在判断我方子弹是否击中敌人坦克时，就需要把我们的子弹集合中
    //所有的子弹，都取出和敌人的所有坦克，进行判断
    public void hitEnemyTank() {
        if (hero.shot != null && hero.shot.isLive) {

            //遍历敌人所有的坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void hitHero() {
        //遍历坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历坦克的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }


            }


        }
    }

    //编写方法，判断我方的子弹是否击中敌方坦克
    public void hitTank(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    //以防止爆炸过的坦克再爆炸
                    enemyTanks.remove(enemyTank);
                    //当击毁一个敌人坦克 allEnemyTankNum++
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
            case 1:
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    //以防止爆炸过的坦克再爆炸
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wdsa键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断hero的子弹是否消亡
            //发射一颗子弹
//            if (hero.shot == null || !hero.shot.isLive) {
//                hero.shotEnemyTank();
//            }

            //以射多颗子弹
            hero.shotEnemyTank();
        }

        //让面板重绘
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100ms重绘区域

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //判断是否击中了敌人坦克
            hitEnemyTank();

            //判断敌人坦克是否击中了我们
            hitHero();

            this.repaint();

        }

    }
}
