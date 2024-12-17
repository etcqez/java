package 算法;

public class 升序数组插入一个元素依然升序 {
    public static void main(String[] args) {
        int[] arr = {10, 12, 45, 90};
        int insertNum = 23;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            //取比当前位置的值小的地方为索引
            if (insertNum <= arr[i]) {
                index = i;
                break;
            }
        }
        //比所有位置都大就取最后
        if (index == -1) {
            index = arr.length;
        }
        //扩容
        int[] arrNew = new int[arr.length + 1];

        //复制并插入
        for (int i = 0, j = 0; i < arrNew.length; i++) {
            if (i != index) {
                //j指向arr,i指向arrNew
                arrNew[i] = arr[j];
                j++;
            } else {
                arrNew[i] = insertNum;
            }
        }
        arr = arrNew;
        for (int i = 0; i < arrNew.length; i++) {
            System.out.print(arrNew[i] + "\t");
        }
    }
}
