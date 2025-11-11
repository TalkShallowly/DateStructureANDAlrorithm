package talk.dynamic;

import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * 给定一个非负数组arr，和一个正数M
 * 返回所有arr中所有子数组累加和%m之后的最大值
 */
public class SubsquenceMaxModM {

    public static int maxM (int[] arr, int m){
        //使用set存放所有子数组累加和
        HashSet<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        int max = 0;
        for (Integer num : set) {
            max = Math.max(max, num % m);
        }
        return max;
    }

    private static void process (int[] arr, int index, int sum, HashSet<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        }else {
            process(arr,index + 1, sum, set);
            process(arr,index + 1, sum + arr[index], set);
        }
    }

    //dp[i][j] : i 为数组索引, j为数组累加和总数
    //从 0~i 过程中是否可以存在 累加和为 j
    //适用于 : arr的累加和不大
    public static int maxM2 (int[] arr, int m) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        boolean[][] dp = new boolean[arr.length][sum + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[arr.length - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    //dp[i][j] : i 数组长度, j为值 M
    //表示从 0 ~i 所有累加和中是否可以计算出 j
    // 适用于数组长度不大,值很大的情况
    public static int maxM3 (int[] arr, int m) {
        boolean[][] dp = new boolean[arr.length][m];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = true;
        }

        dp[0][arr[0] % m] = true;

        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < m; j++) {
                //不需要i位置的数
                dp[i][j] = dp[i - 1][j];
                //当前数据 % m 的值
                int cur = arr[i] % m;
                if (cur <= j) {
                    //小于 : 如果之前的数据有 值为 j - cur 则可以使用当前值
                    dp[i][j] |= dp[i - 1][j - cur];
                }else {
                    //大于 : 上一个数据是否是 (之前的数据值 + 当前的值) % m
//                    取余操作时对边界的处理
                    dp[i][j] |= dp[i - 1][m + j - cur];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j < m; j++) {
            if (dp[arr.length - 1][j]) {
                ans = j;
            }
        }
        return ans;
    }

    //分治法 : 样本量很大, 并且所有的容易整合的逻辑
    //如果数组累加和很大, M也很大, 则使用分治法
    public static int maxM4 (int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] & m;
        }
        TreeSet<Integer> set1 = new TreeSet<>();
        int mid = (arr.length - 1) >> 2;
        process4(arr,m,0,0,mid,set1);
        TreeSet<Integer> set2 = new TreeSet<>();
        process4(arr,m,mid + 1,0,arr.length - 1,set2);
        int ans = 0;
        for (Integer leftMod : set1) {
            //数据来源: 左侧 / 右侧 / 左 + 右
            //如果左侧的数据可以在右侧找到 <= (m - 1 - leftMod) 的数,那么就是m最大的
            ans = Math.max(ans, leftMod + set2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    private static void process4(int[] arr, int m, int index, int sum, int end, TreeSet<Integer> set) {
        if (index == end + 1) {
            set.add(sum % m);
        }else {
            process4(arr, m, index + 1, sum, end, set);
            process4(arr, m , index + 1, sum + arr[index], end, set);
        }
    }


    
    static final Random random = new Random();
    public static int[] generateRandomArr(int maxLen, int maxVal) {
        int len = random.nextInt(maxLen + 1);
        System.out.println("len = " + len);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = random.nextInt(maxVal);
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 1000;
        
        int maxVal = 100;

        int m = 100;

        for (int i = 0; i < 10; i++) {
            int[] arr = generateRandomArr(maxLen, maxVal);
            int i1 = measureExecutionTime(() -> maxM(arr, m));
            int i2 = measureExecutionTime(() -> maxM2(arr, m));
            int i3 = measureExecutionTime(() -> maxM3(arr, m));
            int i4 = measureExecutionTime(() -> maxM4(arr, m));
            if ( i2 != i3) {
                System.out.println("Oops......");
            }
            System.out.println("===========");
        }
    }

    public static <R> R measureExecutionTime(Supplier<R> supplier) {
        long startTime = System.nanoTime();
        R result = supplier.get();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // 将纳秒转化为毫秒
        System.out.println("Method execution time: " + duration + " milliseconds");
        return result;
    }
}
