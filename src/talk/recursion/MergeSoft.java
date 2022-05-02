package talk.recursion;


import java.util.Arrays;

/**
 * 归并排序
 * @author talkshallowly
 */
public class MergeSoft {

    public static void mergeSoft(int[] arr){
        if (arr == null && arr.length > 2){
            return;
        }
        process(arr,0,arr.length - 1);
    }
    private static void process(int[] arr, int left, int right) {
        //如果左右下标相等，则返回
        if (left == right){
            return;
        }
        int mid = left + ((right - left) >> 1);
        process(arr,left,mid);
        process(arr,mid + 1,right);
        merge(arr,left,mid,right);
    }
    private static void merge(int[] arr, int left, int mid, int right) {
        //借助一个数组，用来存放 L...R 上合并的数据
        int[] help = new int[right - left + 1];
        int i = 0;
        //定一个左指针开始位置
        int leftPointer = left;
        //右指针开始位置
        int rightPointer = mid + 1;
        //此循环解释：左指针依次从左遍历到中间位置，右指针依次从中间位置遍历到最右边位置，在遍历过程中进行依次比较，将小的值依次放入到 help数组中
        while (leftPointer <= mid && rightPointer <= right){
            help[i++] = arr[leftPointer] <= arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //右指针越界： 在上面遍历过程中，右测部分全部遍历完成，左侧若还未遍历完成，则在 help 数组中添加剩余数
        while (leftPointer <= mid){
            help[i++] = arr[leftPointer++];
        }
        //左指针越界：在上面遍历过程中，左测部分全部遍历完成，右侧若还未遍历完成，则在 help 数组中添加剩余数
        while (rightPointer <= right){
            help[i++] = arr[rightPointer++];
        }
        //将 L.....R范围上排序后的数放入到 arr[] 中
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
    }

    /**
     * 使用非递归实现，使用步长 ： 将数组分为固定范围的长度，排好序后在次合并
     */
    public static void mergeSoft02(int[] arr){
        if (arr == null && arr.length > 2){
            return;
        }
        process02(arr,0,arr.length - 1);
    }

    private static void process02(int[] arr, int left, int right) {
        int step = 1;
        while (step <= right){
            //当前开始下标
            int startIndex = 0;
            //因为每一组数的开始都是上一组数据的结束位置 + 1，倘若开始下标超过数据长度，则不需要进行合并，直接作为单独一组
            while (startIndex <= right){
                //如果步长大于 后续 左组数据长度，则可以直接返回， 因为既已无法评凑左组，更无右组，后面无法排序
                if (step > right - startIndex){break;}
                int mid = startIndex + step - 1;
                int endIndex = mid + Math.min(step,right - mid);
                merge(arr,startIndex,mid,endIndex);
                startIndex = endIndex + 1;
            }
            // 防止溢出
            if (step > right / 2) {
                break;
            }
            step <<= 1;
        }
    }

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            ints[i] = Math.random() > 0.5 ? (int) (Math.random() * maxValue) + 1 : -(int) (Math.random() * maxValue);
        }
        return ints;
    }

    public static void checkArray(int[] old, int[] target){
        for (int i = 0; i < old.length; i++) {
            if (old[i] != target[i]){
                System.out.println("error .." + Arrays.toString(target) + "  ==========  " + Arrays.toString(old));
            }
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 1000;
        int testTime = 10000;
        System.out.println("Test.....start");
        for (int i = 0; i < testTime; i++) {
            int[] ints = generateRandomArray(maxLen, maxValue);
            int[] old = Arrays.copyOf(ints, ints.length);
            Arrays.sort(old);
            mergeSoft02(ints);
            checkArray(ints,old);
        }
        System.out.println("Test......end");
    }
}
