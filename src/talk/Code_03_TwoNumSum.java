package talk;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 */
public class Code_03_TwoNumSum {

    public static int[] two_NumSum(int[] nums, int target){
        int startIndex = 0;
        int endIndex = 0;
        int curSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target){
                startIndex = endIndex = i;
                break;
            }
            curSum += nums[i];
            if (curSum == target){
                endIndex = i;
                break;
            }else if ( curSum > target){
                curSum = curSum - nums[startIndex];
                startIndex += 1;
                endIndex = i;
            }else {
                continue;
            }
        }
       return new int[]{startIndex,endIndex};
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3,3,7,11};
        int[] ints = two_NumSum(arr, 11);
        System.out.println(Arrays.toString(ints));

    }

}
