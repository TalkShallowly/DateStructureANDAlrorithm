package talk;


/**
 * 最大子序列问题
 * @author talkshallowly
 */
public class Code02_MaxSubSum {

    public static int maxSubSum01(int[] arr){
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int curSum = 0;
                for (int k = i; k<= j; k++){
                    curSum += arr[k];
                    if (curSum > maxSum){
                        maxSum = curSum;
                    }
                }
            }
        }
        return maxSum;
    }

//    public static int maxSubSum03(int[] arr){
//        int maxSum = 0;
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = i; j < arr.length; j++) {
//
//            }
//        }
//    }

    public static int maxSubSum(int[] arr){
        int maxSum = 0, curSum = 0;
        for (int i = 0; i < arr.length; i++) {
            curSum += arr[i];
            if (curSum > maxSum) {
                maxSum = curSum;
            } else if (curSum < 0) {
                curSum = 0;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = {-1,3,10,-3,-2,12,-1};
        System.out.println(maxSubSum01(arr));
    }
}
