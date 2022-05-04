package talk.sort;

import java.util.Arrays;

/**
 * 基数排序
 */
public class RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null && arr.length < 2) {
            return;
        }
        //定义 0~9 的基数
        final int radix = 10;
        int i,j;
        //使用一个帮助数组
        int[] help = new int[arr.length];
        //获取数组最大值的位数
        int maxBits = maxBits(arr);
        //数组最大值有几位，遍历几次
        for (int d = 1; d <= maxBits; d++) {
            //定义一个 基数数组
            int[] count = new int[radix];
            //此处循环 获取数组中每个数的基数，在基数数组中进行对应的统计
            for (i = 0; i < arr.length; i++){
               j = getDigit(arr[i],d);
               count[j]++;
            }
            /*
                此处循环： 统计当前基数位之前的数据个数
                    例如：count[0] 当前位（d位）是0的数据有多少个
                         count[1] 当前位（d位）是0~1的数据有多少个
                         count[2] 当前位（d位）是0~2的数据有多少个
                 count[0]上面已经有统计，此处循环9次
             */
            for (i = 1; i < radix; i++){
                count[i] = count[i] + count[i - 1];
            }
            //将原数组中的数据依次放入辅助数组中
            for (i = arr.length - 1; i >= 0; i--){
                j = getDigit(arr[i],d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }

            for (i = 0; i < arr.length; i++){
                arr[i] = help[i];
            }

        }
    }

    /**
     * 获取 0~9 中对应的基数
     * @param x
     * @param d
     * @return
     */
    private static int getDigit(int x, int d){
        return (x / (int)(Math.pow(10,d - 1))) % 10;
    }

    /**
     * 获取数据最大值的位数
     * @return
     */
    private static int maxBits(int[] arr){
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(i,max);
        }
        int res = 0;
        while (max != 0){
            res++;
            max /= 10;
        }
        return res;
    }


    public static void main(String[] args) {
        int[] arr = {432,12,34,3,60,3000};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
