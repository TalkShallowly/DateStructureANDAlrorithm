package talk.bitmap;

import java.util.*;

/**
 * @author talkshallowly
 * 问题：在一个数组中，有一个数出现了K次，其他数出现了M次，打印出现了K次的数（M>0,M>K）
 */
public class Code_04_BitOperation_02 {

    public static int findNum(int[] arr,int m){
        if (arr == null || arr.length < 2){
            return 0;
        }
        //第一步： 使用32位的数组用来记录数的二进制位次数 （如果定义31位，负数会越界）
        int[] ints = new int[32];
        int ans = 0;
        //第二步： 将每一个数的的二进制位保存在申请的数组中
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                ints[j] = (arr[i] & (1 << j)) == 0 ? ints[j] : ints[j] + 1;
            }
        }
        //第三步：查询每一次的出现K次的数的二进制位 进行 或运算
        for (int j = 0; j < ints.length; j++) {
            if (ints[j] % m != 0){
                ans |= (1 << j);
            }
        }
        return ans;
    }

    public static int[] randomArray(int k,int m,int kinds,int scope){
        List<Integer> list = new ArrayList<>();
        int i = 0;
        int k_Count = 0;
        int m_Count = 0;
        while (true){
            k_Count = generateNum(k,true);
            m_Count = generateNum(m,true);
            if (m_Count > k_Count && m_Count != 0){
                break;
            }
        }
        list.add(m_Count);
        do {
            int k_Num = 0;
            if (i == 0){
                k_Num = generateNum(scope,false);
                for (int j = 0; j < k_Count; j++) {
                    list.add(k_Num);
                }
            }else {
                int m_Num = generateNum(scope,false);
                if (k_Num == m_Num){
                    m_Num += 1;
                }
                for (int j = 0; j < m_Count; j++) {
                    list.add(m_Num);
                }
            }
            i++;
        }while (i >= 1 && i < kinds);
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }
    public static int generateNum(int scope,boolean swatch){
        if(swatch){
            return (int)(Math.random() * scope) + 1;
        }else {
            return Math.random() > 0.5 ? (int)(Math.random() * scope) + 1 : -(int)(Math.random() * scope);
        }
    }

    public static int test(int[] arr,int m){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 1; i < arr.length; i++) {
            if (map.containsKey(arr[i])){
                map.put(arr[i],map.get(arr[i]) + 1);
            } else {
                map.put(arr[i],1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() % m != 0){
                return entry.getKey();
            }
        }
        return 0;
    }

    public static void main(String[] args) {
//        int[] arr = {3,3,3,3,3,3,4,4,4,4,4,4,5,5,5};
//        for (int i = 0; i < 10000; i++) {
//            int[] arr = randomArray(500, 1000, 100, 500);
//            if (findNum(arr, arr[0]) != test(arr, arr[0])) {
//                System.out.println("数组： " + Arrays.toString(arr));
//                System.out.println("实际： " + findNum(arr, arr[0]));
//                System.out.println("测试： " + test(arr, arr[0]));
//                break;
//            }
//        }
        print32Bit(5);
    }

    public static void print32Bit(int num){
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
    }
}
