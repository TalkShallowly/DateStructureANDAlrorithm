package talk.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 最大线段重合问题
 *      给定 多条线段， 每个线段中给定两个数[start，end]
 *      线段的开始结束位置都为闭区间
 *          规定：
 *              1）：线段的开始/结束位置一定为整数值
 *              2）：线段重合区域长度必须 >= 1
 *              返回线段最多重复区域中，包含了几条线段
 * @author talkshallowly
 */
public class CoverMax {
    /**
     * 方式一： 暴力破解法 (统计两个数之间的 任意一个小数出现的次数，结果为最大的次数)
     * @param lines
     * @return
     */
    public static int maxCover(int[][] lines){
        int minStart = Integer.MAX_VALUE;
        int maxStart = Integer.MIN_VALUE;
        //遍历取出每个字段中的开始位置的最小索引和结束最大索引
        for (int i = 0; i < lines.length; i++) {
            minStart = Math.min(minStart,lines[i][0]);
            maxStart = Math.max(maxStart,lines[i][1]);
        }
        int cover = 0;
        //循环扫描记录从最小值到最大值的整数间隔范围内出现 的小数次数
        for (double j = minStart + 0.5; j < maxStart; j += 1){
            //用于记录小数出现的次数
            int cur = 0;
            //循环记录
            for (int i = 0; i < lines.length; i++){
                if (lines[i][0] < j && lines[i][1] > j){
                    cur++;
                }
            }
            cover = Math.max(cover,cur);
        }
        return cover;
    }

    //将二维数组转为对象进行封装排序
    public static class Line{
        private int start;
        private int end;
        Line(int i, int j){
            this.start = i;
            this.end = j;
        }
    }
    /**
     * 方式二： 小根堆法 : 需要先对数组进行排序，依据开始索引
     * @param arr
     * @return
     */
    public static int maxCoverHeap(int[][] arr){
        Line[] line = new Line[arr.length];
        for (int i = 0; i < arr.length; i++) {
            line[i] = new Line(arr[i][0],arr[i][1]);
        }
        Arrays.sort(line,(o1, o2) -> o1.start - o2.start);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int maxResult = 0;
        for (int i = 0; i < line.length; i++) {
            priorityQueue.add(line[i].end);
            while (!priorityQueue.isEmpty() && priorityQueue.peek() <= line[i].start ){
                priorityQueue.poll();
            }
            maxResult = Math.max(maxResult,priorityQueue.size());
        }
        return maxResult;
    }

    /**
     * 优化小根堆
     * @param arr
     * @return
     */
    public static int maxCoverHeap2(int[][] arr){
        Arrays.sort(arr,(o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int maxResult = 0;
        for (int i = 0; i < arr.length; i++) {
            priorityQueue.add(arr[i][1]);
            while (!priorityQueue.isEmpty() && priorityQueue.peek() <= arr[i][0] ){
                priorityQueue.poll();
            }
            maxResult = Math.max(maxResult,priorityQueue.size());
        }
        return maxResult;
    }

    public static int[][] generateArray(int maxLen, int maxValue){
        int row = (int)(Math.random() * maxLen + 1);
        int[][] arr = new int[row][2];
        for (int i = 0; i < arr.length; i++) {
            int start = 0;
            int end = 0;
            while (start >= end){
                start = (int)(Math.random() * maxValue + 1) - (int)(Math.random() * maxValue);
                end = (int)(Math.random() * maxValue + 1) - (int)(Math.random() * maxValue);
            }
            arr[i][0] = start;
            arr[i][1] = end;
        }
        return arr;
    }

    public static void printArray(int[][] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                System.out.print("[" + i + "][" + j + "]:" + arrays[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 1000;
        int testTime = 50000;
        System.out.println("Start。。。。。。。");
        for (int i = 0; i < testTime; i++) {
            int[][] ints = generateArray(maxLen, maxValue);
            int cover = maxCover(ints);
            int coverHeap = maxCoverHeap2(ints);
            if (cover != coverHeap){
                printArray(ints);
                System.out.println("cover : == " + cover + "    coverHeap : === " + coverHeap);
            }
        }
        System.out.println("End。。。。。。。。");
//        int[][] arr = {{-3,0},{0,2},{0,1},{-2,-1},{-2,2}};
//        System.out.println(maxCover(arr));
//        System.out.println(maxCoverHeap(arr));
    }
}
