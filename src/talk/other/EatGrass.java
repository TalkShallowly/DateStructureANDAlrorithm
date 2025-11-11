package talk.other;


/**
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里，有一头牛和一只羊，牛先吃，羊后吃，
 * 轮流吃草，不管是牛还是羊，每一轮能吃的草量必须是： 1，4，16，64。。（4的某次方）
 * 谁最先将草吃完，谁获胜
 * 假设牛和羊都绝顶聪明，都想赢， 都会做出最理性的决定，
 * 根据唯一的参数N，返回谁会赢
 *
 */
public class EatGrass {

    public static void main(String[] args) {
        for (int i = 0; i < 80; i++) {
//            System.out.println("当前 " + i +  " 位置" + " , 胜利者 : [" + whoWin(i) + "]");
            if (!whoWin(i).equals(whoWin2(i))){
                System.out.println("oops.......");
            }
        }
    }


    private static String whoWin (int n){
        if (n < 5){
            return n == 0 || n == 2 ? "后手" : "先手";
        }
        int want = 1;
        while (want <= n){
            String whoWin = whoWin(n - want);
            if (whoWin.equals("后手")){
                return "先手";
            }
            if (want > (n / 4)){
                break;
            }
            want *= 4;
        }
        return "后手";
    }

    private static String whoWin2 (int n){
        int res = n % 5;
        return res == 0 || res == 2 ? "后手" : "先手";
    }
}
