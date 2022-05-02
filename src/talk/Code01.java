package talk;

/**
 * 位运算
 */
public class Code01 {
    public static void main(String[] args) {
        int num = 4;
        print(num);
    }

    /**
        打印32位整形
     */
    public static void print(int  num){
        for(int i = 31; i >= 0; i--){
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
    }
}
