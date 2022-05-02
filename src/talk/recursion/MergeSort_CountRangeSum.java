package talk.recursion;

import java.util.Arrays;

/**
 *  给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 * @author talkshallowly
 */
public class MergeSort_CountRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        return process(arrIndexSum(nums),lower,upper,0,nums.length - 1);
    }

    /**
     * 计算数组累加和,通过累加和，进行归并统计查询
     * @param arr
     * @return
     */
    private static long[] arrIndexSum(int[] arr){
        if (arr == null && arr.length < 1){
            return null;
        }
        long[] indexSumArr = new long[arr.length];
        indexSumArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            indexSumArr[i] = indexSumArr[i - 1] + arr[i];
        }
        return indexSumArr;
    }

    private static int process(long[] indexSum, int lower, int upper, int leftIndex, int rightIndex){
        //第一步：如果累加和中有数据在 【lower，upper】范围上，则表示为 0——N 范围的数组累加和
        if (leftIndex == rightIndex){
            return indexSum[leftIndex] >= lower && indexSum[leftIndex] <= upper ? 1 : 0;
        }
        int mid = leftIndex + ((rightIndex - leftIndex) >> 1);
        int leftSum = process(indexSum, lower, upper, leftIndex, mid);
        int rightSum = process(indexSum, lower, upper, mid + 1, rightIndex);
        return leftSum + rightSum + merge(indexSum,lower,upper,leftIndex,mid,rightIndex);

    }

    private static int merge(long[] indexSum, int lower, int upper, int leftIndex, int mid, int rightIndex) {
        //此处使用滑动窗口 --》左右两边指针不回退
        int windowsLeft = leftIndex;
        int windowRight = leftIndex;
        int res = 0;
        /**
         * 第二步：此时从右组的第一个开始寻找，因为在上面已经有统计 0～N 之间累加和处于范围上的统计，此处处理 i～N 直接的累加和
         *         原理：0～N - 0～i == i～N  ，所以此处判断i ～N的数据时，可根据提供的范围，通过对右组数据的遍历，依次比较是否存在左组数据中处于
         *         右组指定的范围内， 则可以计算 i～N之间的统计
         */
        for (int i = mid + 1; i <= rightIndex; i++) {
            //先查询左组数据中 小于右组的最大范围数量
            while (windowRight <= mid && indexSum[windowRight] <= (indexSum[i] - lower)){
                windowRight++;
            }
            //查询左组数据组，小于右组的最小范围的数据
            while (windowsLeft <= mid && indexSum[windowsLeft] < (indexSum[i] - upper)){
                windowsLeft++;
            }
            //计算结果
            res += windowRight - windowsLeft;
        }

        long[] help = new long[rightIndex - leftIndex + 1];
        int leftPointer = leftIndex;
        int rightPointer = mid + 1;
        int flag = 0;
        while (leftPointer <= mid && rightPointer <= rightIndex){
            help[flag++] = indexSum[leftPointer] < indexSum[rightPointer] ? indexSum[leftPointer++] : indexSum[rightPointer++];
        }
        //右指针越界
        while (leftPointer <= mid){
            help[flag++] = indexSum[leftPointer++];
        }
        //左指针越界
        while (rightPointer <= rightIndex){
           help[flag++] = indexSum[rightPointer++];
        }
        //对元数组对应范围替换对应的数据
        for (int i = 0; i < help.length; i++) {
            indexSum[leftIndex + i] = help[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {-2147483647,0,-2147483647,2147483647};
        int i = countRangeSum(arr, -584, 3864);
        System.out.println(i);
    }
}