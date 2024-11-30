package qqclient.view;

import qqclient.utility.Utility;

//客启端的菜单界面
public class QQView {
    private boolean loop = true;
    private String key = "";
    //显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==============欢迎登录网络通信系统==============");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");

            key = Utility.readString(1);
            
        }

    }
}
