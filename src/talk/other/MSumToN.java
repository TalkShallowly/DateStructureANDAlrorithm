package talk.other;

/**
 * 定义一个数，可以表示成若干（数量>1） 连续正数和的数
 * 比如： 5 = 2 + 3
 * 12 = 3 + 4 + 5
 * 1 不是这样的数，因为需要数量大于1个。并且连续正数和
 * 2也不是 2 = 1 + 1 ，非连续正数和
 * 给定一个正数N。返回是不是可以表示为连续若干连续正数和的数
 */
public class MSumToN {


    public static void main(String[] args) {

        for (int i = 1; i < 10000; i++) {
            if (findContinuousPositiveSum(i) != findContinuousPositiveSum2(i)) {
                System.out.println("oops.......");
            }
//            System.out.println("当前 索引: " + i + "  是否连续正数和 : " + continuousPositiveSum);
        }
    }

    private static boolean findContinuousPositiveSum(int n){

        if (n <= 2){
            return false;
        }
        int flag = (n & 1) == 0 ? n / 2 : n / 2 + 1;
        for (int i = 1; i <= flag; i++) {
            int sum = i;
            for (int j = i + 1; j <= flag; j++) {
//                System.out.println("--- sum = " + sum + " j = " + j);
                if (sum + j == n){
                    return true;
                }else{
                    sum += j;
                }
            }
        }
        return false;
    }

    //最后规律. 2的次方数据全部为false
    private static boolean findContinuousPositiveSum2(int n){
//        return num == (num & (~num + 1));
//        return num == (num & (-num));
        return n <= 0 || (n & (n - 1)) != 0;
    }

}
