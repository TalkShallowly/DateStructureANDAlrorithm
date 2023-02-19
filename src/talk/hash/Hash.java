package talk.hash;//import javax.xml.bind.DatatypeConverter;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.security.Security;import java.util.Arrays;/** * @author guojunshan * @date 2022/12/25 */public class Hash {    private MessageDigest hash;    public Hash(String algorithm){        try {            hash = MessageDigest.getInstance(algorithm);        } catch (NoSuchAlgorithmException e) {            e.printStackTrace();        }    }    public String hashCode(String input){//        return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();        return Arrays.toString(hash.digest(input.getBytes()));    }    public static void main(String[] args) {        System.out.println("支持的算法 : ");        for (String str : Security.getAlgorithms("MessageDigest")){            System.out.println(str);        }        System.out.println("==========");        String algorithm = "SHA-256";        Hash hash = new Hash(algorithm);        String input1 = "TalkShallowly_1";        String input2 = "TalkShallowly_2";        String input3 = "TalkShallowly_3";        String input4 = "TalkShallowly_4";        String input5 = "TalkShallowly_5";        String input6 = "TalkShallowly_6";        System.out.println(hash.hashCode(input1));        System.out.println(hash.hashCode(input2));        System.out.println(hash.hashCode(input3));        System.out.println(hash.hashCode(input4));        System.out.println(hash.hashCode(input5));        System.out.println(hash.hashCode(input6));    }}