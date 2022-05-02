package talk.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 问题：存在一个数据arr[]和一个数组子长度K，其中K为 数据中每个数移动的范围；
 * @author talkshallowly
 */
public class SortArrayDistanceLessK {

    public static void sortArrayDistanceLessK(int[] arr,int K){
        if (K == 0){
            return;
        }
        //默认小根堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < Math.min(arr.length, K); i++) {
            priorityQueue.add(arr[i]);
        }
        int step = 0;
        while (step + K < arr.length){
            priorityQueue.add(arr[step + K]);
            arr[step++] = priorityQueue.poll();
        }
        while (!priorityQueue.isEmpty()){
            arr[step++] = priorityQueue.poll();
        }
    }

    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    /**
     * 从下向上调整堆结构
     * @param arr
     * @param index
     */
    public static void heapInsert(int[] arr,int index){
        while (index > 0 && arr[index] < arr[(index - 1) >> 1]){
            swap(arr,index,(index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }

    /**
     * 从上向下调整堆结构
     * @param arr
     * @param headIndex
     * @param heapSize
     */
    public static void heapify(int[] arr,int headIndex, int heapSize){
        int leftIndex = (headIndex << 1) + 1;
        while (leftIndex < heapSize){
            int smallest = leftIndex + 1 < heapSize && arr[leftIndex] > arr[leftIndex + 1] ? leftIndex + 1 : leftIndex;
            if (arr[headIndex] > arr[smallest]){
                swap(arr,smallest,headIndex);
            }
            headIndex = smallest;
            leftIndex = (headIndex << 1) + 1;
        }
    }

    public static void swap(int[] arr,int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void checkTest(int[] arr1,int[] arr2){
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]){
                throw new RuntimeException("OOPs");
            }
        }
    }

    public static void main(String[] args){
        int maxSize = 100;
        int maxValue = 200;
        int K = 20;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] ints = randomArrayNoMoveMoreK(maxSize, maxValue, K);
            int[] copy = Arrays.copyOf(ints, ints.length);
            checkTest(ints,copy);
        }
        System.out.println("测试结束");
    }
}
