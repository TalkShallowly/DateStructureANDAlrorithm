package talk.dynamic;

import java.util.Random;

/**
 * int[]d, d[i] ： i 号怪兽的能力
 * int[]p, p[i] :  i 号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你的当前的能力，小于i号怪兽的能力你必须付出p[i] 的钱，贿赂这个怪兽
 * 然后怪兽就会加入你，他的能力就会累加到你的能力上；如果你当前的能力，大于等于
 * i 号怪兽的能力，你可以选择直接通过，并且你的能力并不会下降，你也可以选择贿赂这个怪兽，
 * 然后怪兽直接加入你，他的能力直接累加到你的能力上
 * 返回通过所有的怪兽，需要花费最少的钱
 *
 *
 * 1): 如果 d[i] 的数范围较大, p[i]的数范围较小, 使用 以 p[i] 为动态规划列
 * 2): 如果 p[i] 的数范围较大, d[i]的数范围较小, 使用 以 d[i] 为动态规划列
 * 3): 两个范围都是比较大,则使用暴力递归方式
 *
 *
 */
public class MoneyProblem {

    public static int[] generateNumbers(int n, int range) {
        int[] numbers = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            numbers[i] = random.nextInt(range) + 1;
        }
        return numbers;
    }

    public static void main(String[] args) {
        for (int k = 0; k < 1000; k++) {
            int maxBlow = 10000;
            int maxMoney = 100;
            int[] b = generateNumbers(10, maxBlow);
            int[] p = generateNumbers(10, maxMoney);
            int i = minMoney(b, p);
            int i1 = process_dp(b, p);
            int i2 = minMoney2(b, p);
            int i3 = process2_dp(b, p);
            if (i1 != i2 && i != i1 && i != i3) {
                System.out.println("oops.....");
            }
        }
    }


    //方法一:
    private static int minMoney (int[] b, int[] p) {
        return process(b, p, 0, 0);
    }

    //从 0~index 和每一步的 能力值为 条件,逐步尝试
    private static int process (int[]b , int[] p, int index, int ability) {
        if (index == b.length) {
            return 0;
        }
        //如果能力值小于当前怪兽数值, 只能选择
        if (ability < b[index]){
            return p[index] + process(b, p, index + 1, ability + b[index]);
        }
        else {
            return Math.min(
                    process(b, p, index + 1, ability),
                    p[index] + process(b, p, index + 1, ability + b[index]));
        }
    }

    //方式一: 动态规划
    private static int process_dp (int[] b, int[] p) {
        //总能力值
        int sumAbility = 0;
        for (int k : b) {
            sumAbility += k;
        }
        int[][] dp = new int[b.length + 1][sumAbility + 1];
        for (int i = b.length - 1; i >= 0; i--) {
            for (int j = sumAbility - b[i]; j >= 0; j--) {
                if (j < b[i]){
                    dp[i][j] = p[i] + dp[i + 1][j + b[i]];
                }else {
                    dp[i][j] = Math.min(
                            dp[i + 1][j],
                            p[i] + dp[i + 1][j + b[i]]);
                }
            }
        }
        return dp[0][0];
    }

    private static int process_dp2 (int[] b, int[] p) {
        //总能力值
        int sumAbility = 0;
        for (int k : b) {
            sumAbility += k;
        }
        int[][] dp = new int[b.length + 1][sumAbility + 1];
        for (int i = b.length - 1; i >= 0; i--) {
            for (int j = 0; j <= sumAbility; j++) {
                // 如果这种情况发生，那么这个hp必然是递归过程中不会出现的状态
                // 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
                if (j + b[i] > sumAbility) {
                    continue;
                }
                //如果能力值小于当前怪兽数值, 只能选择
                if (j < b[i]){
                    dp[i][j] = p[i] + dp[i + 1][j + b[i]];
                }else {
                    dp[i][j] = Math.min(
                            dp[i + 1][j],
                            p[i] + dp[i + 1][j + b[i]]);
                }
            }
        }
        return dp[0][0];
    }


    //方式二
    private static int minMoney2 (int[] b, int[] p) {
        int sum = 0;
        for (int j : p) {
            sum += j;
        }

        for (int i = 0; i < sum; i++) {
            int ability = process2(b, p, b.length - 1, i);
            if (ability != -1) {
                return i;
            }
        }
        return sum;
    }



    // 从0....index号怪兽，花的钱，必须严格==money
    // 如果通过不了，返回-1
    // 如果可以通过，返回能通过情况下的最大能力值
    private static int process2 (int[] b, int[] p, int index, int money) {
        if (money < 0){
            return -1;
        }
        if (index < 0){
            return money == 0 ? 0 : -1;
        }
        //使用当前能力值
        int useAbility = process2(b, p, index - 1, money - p[index]);
        int p1 = -1;
        if (useAbility != -1) {
            p1 = b[index] + useAbility;
        }
        //不使用当前能量值
        int noUseAbility = process2(b, p, index - 1, money);
        int p2 = -1;
        if (noUseAbility != -1 && noUseAbility >= b[index]) {
            p2 = noUseAbility;
        }
        return Math.max(p1, p2);
    }

    private static int process2_dp (int[] b, int[] p) {
        int sumMoney = 0;
        for (int value : p) {
            sumMoney += value;
        }
        // dp[i][j]含义：
        // 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
        int[][] dp = new int[b.length][sumMoney + 1];
        //初始化
        for (int j = 0; j < b.length; j++) {
            for (int k = 0; k <= sumMoney; k++) {
                dp[j][k] = -1;
            }
        }
        // 经过0～i的怪兽，花钱数一定为p[0]，达到武力值d[0]的地步。其他第0行的状态一律是无效的
        dp[0][p[0]] = b[0];

        for (int i = 1; i < p.length; i++) {
            for (int j = 0; j <= sumMoney; j++) {
                // 可能性一，为当前怪兽花钱
                // 存在条件：
                // j - p[i]要不越界，并且在钱数为j - p[i]时，要能通过0～i-1的怪兽，并且钱数组合是有效的。
                if (j >= p[i] && dp[i - 1][j - p[i]] != -1){
                    dp[i][j] = dp[i - 1][j - p[i]] + b[i];
                }

                // 可能性二，不为当前怪兽花钱
                // 存在条件：
                // 0~i-1怪兽在花钱为j的情况下，能保证通过当前i位置的怪兽
                if (dp[i - 1][j] >= b[i]){
                    dp[i][j] = Math.max(dp[i][j],dp[i - 1][j]);
                }
            }
        }

        // dp表最后一行上，dp[N-1][j]代表：
        // 能经过0～N-1的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 那么最后一行上，最左侧的不为-1的列数(j)，就是答案
        for (int i = 0; i <= sumMoney; i++) {
            if (dp[b.length - 1][i] != -1) {
                return i;
            }
        }
        return sumMoney;
    }
}
