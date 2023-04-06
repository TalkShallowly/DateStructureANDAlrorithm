package talk.recursion;

import java.util.Arrays;

/**
 * //荷兰国旗问提（快排核心流程）
 * @author talkshallowly
 */
public class DutchFlag {

    private static void swap(int[] arr,int i ,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void dutchFlag(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr,0,arr.length - 1);
    }

    public static int process(int[] arr,int left, int right){
        if (left > right){
            return -1;
        }
        if (left == right){
            return left;
        }
        //定义一个左边界
        int flax = left - 1;
        int index = left;
        while (index <= right){
            //设置最后一个位置的数为 基数
            if (arr[index] <= arr[right]){
                //如果小于,则将当前位置的数据 归放到边界中
                swap(arr,index,++flax);
            }
            index++;
        }
        //最后交换边界后面一个数据的位置和基数的位置
        swap(arr, ++flax, right);
        return flax;
    }

    public static void main(String[] args) {
        int[] arr = {14,15,13,1,2,3,10};
        process(arr,0,arr.length - 1);
    }

}
