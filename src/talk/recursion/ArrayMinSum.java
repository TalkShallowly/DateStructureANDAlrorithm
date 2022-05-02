package talk.recursion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 数量之和问题：给定一个数组，计算数组中每个数大于右边数的个数
 *  使用归并算法
 * @author talkshallowly
 */
public class ArrayMinSum {

    public static int mergeSoft(int[] arr){
        if (arr == null && arr.length > 2){
            return 0;
        }
        return process(arr,0,arr.length - 1);
    }
    private static int process(int[] arr, int left, int right) {
        //如果左右下标相等，则返回
        if (left == right){
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        int leftSum = process(arr, left, mid);
        int rightSum = process(arr,mid + 1,right);
        return leftSum + rightSum + merge(arr,left,mid,right);
    }
    private static int merge(int[] arr, int left, int mid, int right) {
        //借助一个数组，用来存放 L...R 上合并的数据
        int[] help = new int[right - left + 1];
        int i = 0;
        int ans = 0;
        //定一个左指针开始位置
        int leftPointer = left;
        //右指针开始位置
        int rightPointer = mid + 1;
        //此循环解释：左指针依次从左遍历到中间位置，右指针依次从中间位置遍历到最右边位置，在遍历过程中进行依次比较，将小的值依次放入到 help数组中
        while (leftPointer <= mid && rightPointer <= right){

            //逆序对
//            ans = arr[leftPointer] > arr[rightPointer] ? ans + mid - leftPointer + 1 : ans ;

            //顺序对：第一种方式：
            ans = arr[leftPointer] < arr[rightPointer] ? ans + (right - rightPointer + 1) : ans;
            //顺序对：第二种方式：需考虑越界情况  若左边数 >= 右边数 时，进行个数统计
//            ans = arr[leftPointer] >= arr[rightPointer] ? ans + leftPointer - left : ans;
            help[i++] = arr[leftPointer] < arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //右指针越界： 在上面遍历过程中，右测部分全部遍历完成，左侧若还未遍历完成，则在 help 数组中添加剩余数
        while (leftPointer <= mid){
            help[i++] = arr[leftPointer++];
        }
        //左指针越界：在上面遍历过程中，左测部分全部遍历完成，右侧若还未遍历完成，则在 help 数组中添加剩余数
        while (rightPointer <= right){
            //若左侧越界，则统计右边所有个数
//            ans = ans + mid - left + 1;
            help[i++] = arr[rightPointer++];
        }
        //将 L.....R范围上排序后的数放入到 arr[] 中
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
        return ans;
    }

    private static int merge_MinSum(int[] arr, int left, int mid, int right) {
        //借助一个数组，用来存放 L...R 上合并的数据
        int[] help = new int[right - left + 1];
        int i = 0;
        int ans = 0;
        //定一个左指针开始位置
        int leftPointer = left;
        //右指针开始位置
        int rightPointer = mid + 1;
        //此循环解释：左指针依次从左遍历到中间位置，右指针依次从中间位置遍历到最右边位置，在遍历过程中进行依次比较，将小的值依次放入到 help数组中
        while (leftPointer <= mid && rightPointer <= right){
            ans = arr[leftPointer] < arr[rightPointer] ? ans + arr[leftPointer] * (right - rightPointer + 1) : ans;

//            ans = arr[leftPointer] >= arr[rightPointer] ? ans + (leftPointer - left + 1) * ans: arr[leftPointer];
            help[i++] = arr[leftPointer] < arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //右指针越界： 在上面遍历过程中，右测部分全部遍历完成，左侧若还未遍历完成，则在 help 数组中添加剩余数
        while (leftPointer <= mid){
            help[i++] = arr[leftPointer++];
        }
        //左指针越界：在上面遍历过程中，左测部分全部遍历完成，右侧若还未遍历完成，则在 help 数组中添加剩余数
        while (rightPointer <= right){
//            ans = ans * (mid - left + 1);
            help[i++] = arr[rightPointer++];
        }
        //将 L.....R范围上排序后的数放入到 arr[] 中
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
        return ans;
    }

    public static int test(int[] arr){
        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j >= 0; j--) {
                count = arr[i] > arr[j] ? count + 1 : count;
            }
        }
        return count;
    }

    public static int test2(int[] arr){
        int sum = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                sum = arr[j] < arr[i] ? sum + arr[j] : sum;
            }
        }
        return sum;
    }

    public static int test3(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                sum = arr[i] > arr[j] ? sum + 1 : sum;
            }
        }
        return sum;
    }

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            ints[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ints;
    }

    public static void main(String[] args) {
        int maxLen = 1000;
        int maxValue = 1000;
        int testTime = 100000;

//        System.out.println("Test.....start");
//        for (int time = testTime; time > 0; time--) {
//            int[] ints = generateRandomArray(maxLen, maxValue);
//            int test = test(ints);
//            System.out.println(Arrays.toString(ints));
//            int i = mergeSoft(ints);
//            System.out.println(Arrays.toString(ints));
//            System.out.println("test : " + test + " ===  I " + i);
//            if (test != i) {
//                System.out.println("test : " + test + " ===  I " + i);
//            }
//        }
//        System.out.println("Test.....end");

        System.out.println("Test.....start");
        for (int time = testTime; time > 0; time--) {
            int[] ints = generateRandomArray(maxLen, maxValue);
//            System.out.println(Arrays.toString(ints));
            int test = test(ints);
            int i = mergeSoft(ints);
//            System.out.println(Arrays.toString(ints));
            if (test != i) {
                System.out.println("test : " + test + " ===  I " + i);
            }
        }
        System.out.println("Test.....end");
    }

}
