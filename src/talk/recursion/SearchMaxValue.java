package talk.recursion;

/**
 * 给定一个数组arr[], 寻找最大值
 * @author talkshallowly
 */
public class SearchMaxValue {

    public static int searchMaxValue(int[] arr,int left,int right){
        if (arr == null && arr.length < 1){
            return 0;
        }
        if (left == right){
            return arr[left];
        }
        //防止数组溢出
        int mid = left + ((right - left) >> 1);
        int leftValue = searchMaxValue(arr,left,mid);
        int rightValue = searchMaxValue(arr,mid + 1,right);
        return Math.max(leftValue,rightValue);
    }

    public static void main(String[] args) {
        int[] arr = {3,5,2,6,4,1};
        System.out.println(searchMaxValue(arr, 0, arr.length - 1));
    }
}
