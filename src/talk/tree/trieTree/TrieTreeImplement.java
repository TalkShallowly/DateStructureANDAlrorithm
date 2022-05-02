package talk.tree.trieTree;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 前缀树实现
 * @author talkshallowly
 */
public class TrieTreeImplement {
    public static void main(String[] args) {

        MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
        System.out.println(mc.toString());
        BigDecimal linkWeight = new BigDecimal(30);
        BigDecimal divide = linkWeight.divide(BigDecimal.valueOf(100), mc);
        System.out.println(divide);
        BigDecimal linkScore = new BigDecimal(20);
        System.out.println(linkScore.multiply(divide));
    }
}
