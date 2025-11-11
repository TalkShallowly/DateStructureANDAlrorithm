package talk.other;

/**
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
 * 1）：能装下6个苹果的袋子
 * 2）：能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，要求自己使用的袋子必须数量最少
 * 且每个袋子都必须装满
 * 给定一个正整数N， 返回至少使用多少袋子，如果N无法让使用的每个袋子必须装满，返回-1
 */
public class AppleMinBag {

    public static void main(String[] args) {
        for (int i = 1; i < 100000; i++) {
//            int i1 = minBag(i, 0);
            int i2 = minBag2(i);
            int i3 = minBagAwesome(i);
            if (i2 != i3) {
                System.out.println("oops.....");
            }
//            System.out.println("当前 n 的索引 : " + i + " 返回的结果:  " + i1);
//            System.out.println(i1);
        }
    }

    public static int minBag2 (int apple) {
        if (apple < 0) {
            return -1;
        }
        //一共需要多少个8号袋子
        int bag8 = apple / 8;

        //剩余数量
        int rest = apple - (bag8 * 8);
        while (bag8 >= 0) {
           if (rest % 6 == 0) {
               return bag8 + (rest / 6);
           }else {
               bag8--;
               rest = apple - (bag8 * 8);
           }
        }
        return -1;
    }

//
//    public static int minBag (int n, int cur) {
//        if (n < 6){
//            return -1;
//        }
//        if (n == 6 || n == 8){
//            return cur + 1;
//        }
//        int i = minBag(n - 6, cur + 1);
//        int j = minBag(n - 8, cur + 1);
//        if (i == -1 && j == -1){
//            return -1;
//        }else if (i == -1){
//            return j;
//        }else if (j == -1){
//            return i;
//        }else {
//            return Math.min(i, j);
//        }
//    }

    public static int minBagAwesome (int apple) {
        //奇数返回 -1
        if ((apple & 1) != 0){
            return -1;
        }
        if (apple < 18){
            return apple == 0 ? 0 :
                    apple == 6 || apple == 8 ? 1 :
                            apple == 12 || apple == 14 || apple == 16 ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }
}
