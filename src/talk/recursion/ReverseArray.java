package talk.recursion;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * 给定一个数组nums，如果i < j且nums[i] > 2*nums[j]我们就将(i, j)称作一个重要翻转对。
 *
 * 你需要返回给定数组中的重要翻转对的数量。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-pairs
 * @author talkshallowly
 */
public class ReverseArray {

    public static int reversePairs(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }
        return process(arr,0,arr.length - 1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right){
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        int leftCount = process(arr, left, mid);
        int rightCount = process(arr, mid + 1, right);
        return leftCount + rightCount + merge(arr,left,mid,right);
    }
    private static int merge(int[] arr, int left, int mid, int right) {
        int windowsLeft = left;
        int windowRight = mid + 1;
        int res = 0;
        while (windowsLeft <= mid && windowRight <= right){
            int helpFlag = (arr[windowsLeft] & 1) == 0 ? arr[windowsLeft] >> 1 : (arr[windowsLeft] >> 1) + 1;
            if (helpFlag > arr[windowRight]){
                res += mid - windowsLeft + 1;
                windowRight++;
            }else {
                windowsLeft++;
            }
        }
        int[] help = new int[right - left + 1];
        int leftPointer = left;
        int rightPointer = mid + 1;
        int flag = 0;
        while (leftPointer <= mid && rightPointer <= right){
            help[flag++] = arr[leftPointer] < arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //右指针越界
        while(leftPointer <= mid){
            help[flag++] = arr[leftPointer++];
        }
        //左指针越界
        while (rightPointer <= right){
            help[flag++] = arr[rightPointer++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};
        int[] arr = {1,3,2,3,1};
        int i = reversePairs(arr1);
        System.out.println("i = " + i);
    }
}
