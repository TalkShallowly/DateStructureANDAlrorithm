package talk.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author talkshallowly
 */
public class HeapSort {

//    private int heapSize;
//    private int[] arr;
//
//    HeapSort(int length){
//        heapSize = 0;
//        arr = new int[length];
//    }
//
//    public int size(){
//        return heapSize;
//    }
//    public boolean isEmpty(){
//        return heapSize == 0;
//    }
//
//    public void  add(int num){
//       arr[heapSize] = num;
//       heapInsert(arr,heapSize++);
//    }
//
//    public int remove(){
//        if (isEmpty()){ return 0; }
//        int res = arr[0];
//        swap(arr,0,--heapSize);
//        heapify(arr,0,heapSize);
//        return res;
//    }

    public static void headSort(int[] arr){

        //堆插入法排序： 时间复杂度 O（N*LogN）
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr,i);
//        }
        //堆调整法排序： 时间复杂度 O（N）-->> 从下向上调整堆
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr,i, arr.length);
        }
        int length = arr.length;
        for (int j = length - 1; j > 0; j--){
            swap(arr,0,j);
            heapify(arr,0,j);
        }
    }


    private static void heapInsert(int[] arr,int index){
        while (index > 0 && arr[index] > arr[(index - 1) >> 1]){
            swap(arr,index,(index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }

    private static void heapify(int[] arr,int index, int heapSize){
        int leftIndex = (index << 1) + 1;
        while (leftIndex < heapSize){
            int largest = (leftIndex + 1) < heapSize && arr[leftIndex] < arr[leftIndex + 1] ? leftIndex + 1 : leftIndex;
            if (arr[largest] > arr[index]){
                swap(arr,largest,index);
            }
            index = largest;
            leftIndex = (index << 1) + 1;
        }
    }

    private static void swap(int[] arr,int i,int j){
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    public static void checkHeadSort(int[] test,int[] target){
        headSort(test);
        Arrays.sort(target);
        for (int i = 0; i < target.length; i++) {
            if (target[i] != test[i]) {
                System.out.println("test : " + Arrays.toString(test));
                System.out.println("target : " + Arrays.toString(target));
                return;
            }
        }
    }

    public static int[] generateRandomArray(int maxLen,int maxValue){
        int length = (int)(Math.random() * maxLen) + 1;
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = (int)(Math.random() * maxValue) + 1;
        }
        return ints;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int[] ints = generateRandomArray(maxLen, maxValue);
            int[] copy = Arrays.copyOf(ints, ints.length);
            checkHeadSort(ints,copy);
        }
    }
}
