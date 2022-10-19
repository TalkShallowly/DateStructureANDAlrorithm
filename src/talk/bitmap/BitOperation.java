package talk.bitmap;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 位运算
 */
public class BitOperation {

    /**
     * 在一个数组中只有一个数出现了奇数次，其他的数出现了偶数次，打印此出现奇数次的数？
     * @param arr
     * @return
     */
    public static int unevenNum(int[] arr){
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }
        return xor;
    }

    /**
     * 生产随机数组
     * @param kinds
     * @param range
     * @param MaxLen
     * @return
     */
    public static int[] randomArray(int kinds,int range,int MaxLen){
        //数组中数据共有几种（最少位2种）
        int kind = (int) (Math.random() * kinds) + 2;
        //随机长度
        int randomLen = (int)(Math.random() * MaxLen) + 1;
        //保证随机长度为 奇数
        int len = randomLen % 2 == 0 ? randomLen + 1 : randomLen;
        int[] nums = new int[len];
        //第一： 向数组中填充奇数个数据
        int uneven = (int)(Math.random() * randomLen);
        int __uneven = uneven % 2 == 0 ? uneven + 1 : uneven;
        int onlyUneven = randomNum(range);
        int index = 0;
        for (int i = 0; i < __uneven; i++) {
            nums[i] = onlyUneven;
            index = i;
        }
        //第二： 向数组中添加 其他数据
        for (int i = 0; i < kind - 1; i++) {
            int even = randomNum(range);
            for (int j = 0; j < len - __uneven; j++) {
                nums[index] = even;
            }
        }
        return  nums;
    }

    /**
     * 返回一个指定范围的随机数
     * @param range
     * @return
     */
    public static int randomNum(int range){
        return Math.random() > 0.5 ? ((int) (Math.random() * range) + 1) : ~((int) (Math.random() * range)) + 1;
    }

    public static int test(int[] arr){
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (hashMap.containsKey(arr[i])){
                hashMap.put(arr[i], hashMap.get(arr[i]) + 1);
            }else {
                hashMap.put(arr[i], 1);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (hashMap.get(arr[i]) % 2 != 0){
                return arr[i];
            }
        }
        return 99999;
    }

    public static void main(String[] args) {
        int kinds = 32;
        int range = 100;
        int maxLen = 200;
        int timeCount = 100000;
        for (int i = 0; i < timeCount; i++) {
            int[] ints = randomArray(kinds, range, maxLen);
            if (unevenNum(ints) != test(ints)){
                System.out.println("出错数据为： " + Arrays.toString(randomArray(kinds,range,maxLen)));
                break;
            }
        }
    }


}
