package talk.recursion;

/**
 * //荷兰国旗问提（快排核心流程）
 * @author talkshallowly
 */
public class DutchFlag {

    private static void swap(int[] arr,int i ,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void dutchFlag(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr,0,arr.length - 1);
    }

    public static int process(int[] arr,int left, int right){
        if (left > right){
            return -1;
        }
        if (left == right){
            return left;
        }
        int flax = left - 1;
        int index = left;
        while (index <= right){
            if (arr[index] <= arr[right]){
                swap(arr,index,++flax);
            }
            index++;
        }
        swap(arr, ++flax, right);
        return flax;
    }

    public static void main(String[] args) {
        int[] arr = {14,15,13,1,2,3,10};
        process(arr,0,arr.length - 1);
    }

}
