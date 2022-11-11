package talk.monotonous_stack;import java.util.Stack;/** * 给定一个数组 arr[] , 返回所有子数组最小值的累加和 * * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。 * * 由于答案可能很大，因此 返回答案模 10^9 + 7 。 * * * 输入：arr = [3,1,2,4] * 输出：17 * 解释： * 子数组为 [3]，[1]，[2]，[4]，[3,1]，[1,2]，[2,4]，[3,1,2]，[1,2,4]，[3,1,2,4]。 * 最小值为 3，1，2，4，1，1，2，1，1，1，和为 17。 * * 测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/ */public class SumOfSubarrayMinimums {    public static long sumSubarrayMins(int[] arr){        if (arr == null || arr.length == 0){            return 0;        }        long res = 0;        Stack<Integer> stack = new Stack<>();        for (int i = 0; i < arr.length; i++) {            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]){                Integer pop = stack.pop();                int right = stack.isEmpty() ? -1 : stack.peek();                res += (long) f((i - right - 1),(i - pop - 1),(pop - right - 1)) * arr[pop];            }            stack.push(i);        }        while (!stack.isEmpty()){            Integer pop = stack.pop();            int right = stack.isEmpty() ? -1 : stack.peek();            res += (long) f((arr.length - right - 1),(arr.length - pop - 1),(pop - right - 1)) * arr[pop];        }        return res;    }    public static int sumSubarrayMins2(int[] arr){        if (arr == null || arr.length == 0){            return 0;        }        long res = 0;        int[] stack = new int[arr.length];        int size = 0;        for (int i = 0; i < arr.length; i++) {            while (size != 0 && arr[stack[size - 1]] >= arr[i]){                int pop = stack[--size];                int right = size == 0 ? -1 : stack[size - 1];                res += (long) f((i - right - 1), (i - pop - 1), (pop - right - 1)) * arr[pop];            }            stack[size++] = i;        }        while (size != 0){            int pop = stack[--size];            int right = size == 0 ? -1 : stack[size - 1];            res += (long) f((arr.length - right - 1),(arr.length - pop - 1),(pop - right - 1)) * arr[pop];        }        return (int)res % 1000000007;    }    /**     * 累加和     *      比如:     *         6,5,3,1,2,4,7 其中的 1 计算方式     *         包含种类数 = [6,5,3,1,2,4,7] - [6,5,3] - [2,4,7]     * @param num 总长度     * @param before 前     * @param after 后     * @return 包含 n 的种类数     */    private static int f(int num, int before, int after){        return  ((num * (num + 1)) >> 1) - ((before * (before + 1)) >> 1) - ((after * (after + 1)) >> 1);    }    public static void main(String[] args) {        int[] arr = {11,81,94,43,3};        System.out.println(sumSubarrayMins2(arr));    }}