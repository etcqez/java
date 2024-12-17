package 房屋出租.service;

import 房屋出租.domain.House;

public class HouseService {
    private House[] houses;
    private int houseNums = 1; //记录当前有多少个房屋信息
    private int idCounter = 1; //记录id自增长

    public HouseService(int size) {
        //当创建HouseService对象, 指定数组大小
        houses = new House[size];
        //为了测试, 初始化一个对象
        houses[0] = new House(1, "jack", "112", "海演区", 2000, "未出租");
    }

    public House findById(int findId) {
        for (int i = 0; i < houseNums; i++) {
            if (findId == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;

    }

    public boolean del(int delId) {
        int index = -1;
        for (int i = 0; i < houseNums; i++) {
            if (delId == houses[i].getId()) {
                index = i;
            }
        }
        //说明delId在数组中不存在
        if (index == -1) {
            return false;
        }
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
//        houses[houseNums-1] = null;
//        houseNums--;
        houses[--houseNums] = null;
        return true;

    }

    public boolean add(House newHouse) {
        //判断是否可以继续添加(我们暂时不考虑数组扩容的问题)
        if (houseNums == houses.length) {
            System.out.println("数组已满, 不能再添加了");
            return false;
        }
        houses[houseNums++] = newHouse;
        newHouse.setId(++idCounter);
        return true;
    }

    public House[] list() {
        return houses;
    }
}
