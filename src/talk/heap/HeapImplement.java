package talk.heap;

import java.util.PriorityQueue;

/**
 * 实现堆结构（大堆积 / 小堆积）
 * @author talkshallowly
 */
public class HeapImplement {

    private int heapSize;

    private int[] heap;

    private int size;

    HeapImplement(){
        heapSize = 0;
        heap = new int[16];
    }

    HeapImplement(int limit){
        heapSize = 0;
        heap = new int[limit];
    }

    public int size(){
        return heapSize;
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int removeHead(){
        if (isEmpty()){
            throw new RuntimeException("堆中无数据");
        }
        int res = heap[0];
        swap(heap,0,--heapSize);
        heapify(heap,0,heapSize);
        return res;
    }

    public void add(int num){
        heap[heapSize] = num;
        heapInsert(heap,heapSize++);
    }

    /**
     *  --》》 从下向上查找调整
     * @param arr
     * @param index
     */
    private void heapInsert(int[] arr,int index){
        //插入一个数时，依次向上寻找父节点是否比自身大
        while (index > 0 && arr[index] > arr[(index - 1) >> 1]){
            swap(arr,index,(index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }
    /**
     *  --- 》》 从上向下对数据进行整理 每次都会只走一条链路，时间复杂度为（logN）
     * @param arr
     * @param index
     * @param heapSize
     */
    private void heapify(int[] arr,int index, int heapSize){
        //计算此节点的左孩子
        int leftIndex = (index << 1) + 1;
        //如果左孩子存在，则一直向下查找
        while (leftIndex < heapSize){
            //判断其父解节点的左右孩子的最大值
            int largest = (leftIndex + 1) < heapSize && arr[leftIndex] < arr[leftIndex + 1] ? leftIndex + 1 : leftIndex;
            if (arr[largest] > arr[index]){
                swap(arr,largest,index);
            }
            index = largest;
            leftIndex = (index << 1) + 1;
        }
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    private void checkHead(int maxLen,int maxValue){
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(((o1, o2) -> o2-o1));
        int length = (int)(Math.random() * maxLen) + 1;
        for (int i = 0; i < length; i++) {
            int value = (int)(Math.random() * maxValue) + 1;
            priorityQueue.add(value);
            add(value);
        }
        for (int i = 0; i < length; i++) {
            int remove = priorityQueue.remove();
            int removeHead = removeHead();
            if (remove != removeHead){
                System.out.println("test() = " + removeHead);
                System.out.println("priorityQueue = " + remove);
            }
        }
    }

    public static void main(String[] args) {
        HeapImplement heapImplement = new HeapImplement(16);
        int maxLen = 16;
        int maxValue = 200;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            heapImplement.checkHead(maxLen,maxValue);
        }
    }
}
