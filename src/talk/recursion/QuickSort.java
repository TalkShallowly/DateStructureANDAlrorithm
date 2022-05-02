package talk.recursion;

import java.util.Arrays;

/**
 * @author talkshallowly
 */
public class QuickSort {

    public static void quickSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process03(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int left, int right) {
        if (left >= right){
            return;
        }
        int partition = partition(arr, left, right);
        process(arr,left,partition - 1);
        process(arr,partition + 1,right);
    }

    private static int partition(int[] arr,int startIndex, int endIndex){
        if (startIndex == endIndex){
            return startIndex;
        }
        int index = startIndex;
        int lessEqual = startIndex;
        while(index < endIndex){
            if (arr[index] <= arr[endIndex]){
                swap(arr,index,lessEqual++);
            }
            index++;
        }
        swap(arr,index,lessEqual);
        return lessEqual;
    }

    private static void process02(int[] arr, int left, int right) {
        if (left >= right){
            return;
        }
        int[] partition = partition02(arr, left, right);
        process02(arr,left,partition[0] - 1);
        process02(arr,partition[1] + 1,right);
    }

    private static int[] partition02(int[] arr,int startIndex, int endIndex){
        if (startIndex == endIndex){
            return new int[]{startIndex,endIndex};
        }
        int index = startIndex;
        //左边界
        int leftPointer = startIndex - 1;
        //右边界
        int rightPointer = endIndex;
        //(当前位置不能和左边界相遇)
        while(index < rightPointer){
            if (arr[index] <= arr[endIndex]){
                swap(arr,index++,++leftPointer);
            } else {
                swap(arr,index,--rightPointer);
            }
        }
        swap(arr,rightPointer,endIndex);
        return new int[]{leftPointer + 1,rightPointer};
    }

    private static void process03(int[] arr, int left, int right) {
        if (left >= right){
            return;
        }
        int[] partition = partition03(arr, left, right);
        process03(arr,left,partition[0] - 1);
        process03(arr,partition[1] + 1,right);
    }

    private static int[] partition03(int[] arr,int startIndex, int endIndex){
        if (startIndex == endIndex){
            return new int[]{startIndex,endIndex};
        }
        int index = startIndex;
        //左边界
        int leftPointer = startIndex - 1;
        //右边界
        int rightPointer = endIndex;
        //如果数组本来有序，经过其排序过程为 O(N^2)，使用过随机性，将已排序基数设置为概率事件， 统计可得事件复杂度为 O(NlogN)
        int randomIndex = (int)(Math.random() * (endIndex - startIndex - 1));
//        System.out.println("randomIndex = " + randomIndex);
        //将其随机数与最后一个数交换
        swap(arr,startIndex + randomIndex,endIndex);
        //(当前位置不能和左边界相遇)
        while(index < rightPointer){
            if (arr[index] <= arr[endIndex]){
                swap(arr,index++,++leftPointer);
            } else {
                swap(arr,index,--rightPointer);
            }
        }
        swap(arr,rightPointer,endIndex);
        return new int[]{leftPointer + 1,rightPointer};
    }

    private static void swap(int[] arr, int i, int j){
        if (arr[i] != arr[j]){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
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
    public static void checkArraySort(int[] target,int[] testArray){
        for (int i = 0; i < testArray.length; i++) {
            if (target[i] != testArray[i]){
                System.out.println("TargetArr == " + Arrays.toString(target));
                System.out.println("testArray == " + Arrays.toString(testArray));
                System.out.println("=======================");
            }
        }
    }

    public static void main(String[] args) {
        int maxLen = 1000;
        int maxValue = 1000;
        int testTime = 10000;
        System.out.println("test....start");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen,maxValue);
            int[] copy = Arrays.copyOf(arr, arr.length);
//            System.out.println("********" + Arrays.toString(copy));
            quickSort(copy);
            Arrays.sort(arr);
            checkArraySort(arr,copy);
        }
        System.out.println("test.....end");
////
//        int[] arr = {5, -7, -9, 0, 5, -4};
//        process03(arr,0,arr.length - 1);
//        System.out.println(Arrays.toString(arr));
    }
}
