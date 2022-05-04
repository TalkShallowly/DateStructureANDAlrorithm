package talk.sort;


import java.util.Arrays;

/**
 * 计数排序
 */
public class CountSort {

    public static void countSort(int[] arr){
        if (arr == null && arr.length < 2){
            return;
        }
        //第一步：寻找数组中最大的数据
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(i,max);
        }
        //第二部：创建一个以最大数据为边界的桶
        int[] bucket = new int[max + 1];
        //第三步：循环遍历数组，将数据中的数据按照大小依次放入该桶中
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int i = 0;
        //第四步：遍历桶中的数据
        for (int j = 0; j < bucket.length; j++){
            //如果桶中有数据，则按照顺序放入原来数组中
            while (bucket[j]-- > 0) arr[i++] = j;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3,2,1,4,5,2,1,3};
        countSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
