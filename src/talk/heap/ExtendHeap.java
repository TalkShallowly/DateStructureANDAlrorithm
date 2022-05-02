package talk.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 扩展堆结构，使得堆删除指定数据 已经入堆的元素，
 * 如果参与排序的指标发生变化，PriorityQueue无法做到时间复杂度为 O（logN）的调整，都是O(N)的调整
 * 系统提供的堆只能弹出堆顶元素，没有实现自由删除任何一个堆中的元素。换言之，无法在时间复杂度为O(logN)内完成，一定会高于O(logN)
 * 根本原因：无反相索引表的维护
 *  1: 维护一个反向索引表
 *  2：建立一个比较器
 *  3：各种结构相互配合
 * @author talkshallowly
 */
public class ExtendHeap<T> {
    private ArrayList<T> heap;
    private HashMap<T,Integer> indexMap;
    private int heapSize;
    private Comparator<T> comparator;
    public ExtendHeap(Comparator<T> comp){
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        this.comparator = comp;
    }
    public int size(){
        return heapSize;
    }
    public boolean isEmpty(){
        return heapSize == 0;
    }
    public boolean contains(T value){
        return indexMap.containsKey(value);
    }
    public T getIndex(int index){
        return heap.get(index);
    }
    public T peek(){
        return heap.get(0);
    }
    public void push(T value){
        heap.add(value);
        indexMap.put(value,heapSize);
        heapInsert(heapSize++);
    }

    public T pop(){
        T result = heap.get(0);
        swap(0,heapSize - 1);
        indexMap.remove(result);
        heap.remove(--heapSize);
        heapify(0);
        return result;
    }

    /**
     * 删除指定元素
     * @param obj
     */
    public void remove(T obj){
        //获取最后一个元素
        T res = heap.get(heapSize - 1);
        //获取删除元素在索引表中的下标
        int index = indexMap.get(obj);
        //删除指定元素的索引表，以及数组最后一个元素
        indexMap.remove(obj);
        heap.remove(--heapSize);
        //如果删除的元素和最后一个元素不想等时，将数组中的删除的元素被最后一个元素替换，并将索引表中的数据索引进行更新
        if (obj != res){
            heap.set(index,res);
            indexMap.put(res,index);
            //因为无法确定最后元素的位置，需要动态调整
            resign(res);
        }
    }

    /**
     * 动态调整，以下两个调整方法只能走一个
     * @param obj
     */
    private void resign(T obj){
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    /**
     * 从下向上进行调整(小根堆)
     * @param index
     */
    private void heapInsert(int index){
        while (index > 0 && comparator.compare(heap.get(index),heap.get((index - 1) >> 1)) < 0){
            swap(index,(index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }

    /**
     * 从上向下调整
     */
    private void  heapify(int index){
        int leftIndex = (index << 1) + 1;
        while (leftIndex < heapSize){
            int smallest = leftIndex + 1 < heapSize && comparator.compare(heap.get(leftIndex),heap.get(leftIndex + 1)) > 0 ? leftIndex + 1 : leftIndex;
            if (comparator.compare(heap.get(index),heap.get(smallest)) > 0){
                swap(index,smallest);
            }
            index = smallest;
            leftIndex = (index << 1) + 1;
        }
    }

    /**
     * 交换方法（需要在同步表中也进行交换）
     * @param i
     * @param j
     */
    private void swap(int i, int j){
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i,o2);
        heap.set(j,o1);
        indexMap.put(o1,j);
        indexMap.put(o2,i);
    }
}
