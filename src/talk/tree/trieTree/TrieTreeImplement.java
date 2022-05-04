package talk.tree.trieTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树实现
 * @author talkshallowly
 */
public class TrieTreeImplement {
    public static class Node1{
        private int pass;
        private int end;
        private Node1[] nexts;
        public Node1() {
            pass = 0;
            end = 0;
            //每个节点从a~z共有26条链路
            nexts = new Node1[26];
        }
    }

    public static class Tire1{
        private Node1 root;

        public Tire1(){
            root = new Node1();
        }
        public void insert(String word){
            //如果字符为null。则直接返回
            if (word == null){
                return;
            }
            //第一步： 将字符串转换为字符集
            char[] chars = word.toCharArray();
            //第二部： 记录根节点，
            Node1 node = root;
            //从根结点开始白标记，共有几条来来链路
            node.pass++;
            int index = 0;
            //从左向有右遍历字符集
            for (int i = 0; i < chars.length; i++) {
                //又字符决定需要走那一条链路
                index = chars[i] - 'a';
                //如果走的条链路不存在，则进行创建
                if (node.nexts[index] == null){
                    node.nexts[index] = new Node1();
                }
                //将node指针向下移动
                node = node.nexts[index];
                node.pass++;
            }
            //最后遍历完成后尾部标记
            node.end++;
        }

        public void delete(String word){
            int search = search(word);
//            while (search-- > 0){
            if (search != 0){
                char[] chars = word.toCharArray();
                Node1 node = root;
                //因为已经村存在该链条，所有头节点需要行减去
                node.pass--;
                int index = 0;
                for (int i = 0; i < chars.length; i++) {
                    index = chars[i] - 'a';
                    if (--node.nexts[index].pass == 0) {
                        node.nexts[index] = null;
                        return;
                    }
                    node = node.nexts[index];
                }
                node.end--;
            }
        }

        /**
         * 查找当前节点出现了几次
         * @param word
         * @return
         */
        public int search(String word){
            if (word == null){
                return 0;
            }
            Node1 node = root;
            char[] chars = word.toCharArray();
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (node.nexts[index] == null){
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        public int prefixNumber(String str){
            if (str == null){
                return 0;
            }
            char[] chars = str.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (node.nexts[index] == null){
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    public static class Test{
        private Map<String,Integer> map;
        public Test(){
            map = new HashMap<>();
        }
        public void insert(String word){
            if (map.containsKey(word)){
                map.put(word,map.get(word) + 1);
            }else{
                map.put(word,1);
            }
        }
        public void delete(String word){
            int search = search(word);
//            if (search == 0){
//                return;
//            }else if (search == 1){
//                map.remove(word);
//            }else {
//                map.put(word,map.get(word) - 1);
//            }
            switch (search){
                case 0 -> {
                    return;
                }
                case 1 -> map.remove(word);
                default -> map.put(word,map.get(word) - 1);
            }
        }
        public int search(String word){
            if (map.containsKey(word)){
                return map.get(word);
            }else {
                return 0;
            }
        }

        public int prefixNumber(String str){
            if (str == null){
                return 0;
            }
            int count = 0;
            for (String s : map.keySet()) {
                if (s.startsWith(str)){
                    count++;
                }
            }
            return count;
        }

    }

    public static class Node2{
        private int pass;
        private int end;
        Map<Integer,Node2> map;
        public Node2(){
            pass = 0;
            end = 0;
            map = new HashMap<>();
        }
    }

    public static class Tire2{
        private Node2 root;
        public Tire2(){
            root = new Node2();
        }
        public void insert(String word){
            if (word == null){
                return;
            }
            Node2 node = root;
            node.pass++;
            char[] chars = word.toCharArray();
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (!node.map.containsKey(index)){
                    node.map.put(index,new Node2());
                }
                node = node.map.get(index);
                node.pass++;
            }
            node.end++;
        }

        public int search(String word){
            if (word == null){
                return 0;
            }
            Node2 node = root;
            char[] chars = word.toCharArray();
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (!node.map.containsKey(index)){
                    return 0;
                }
                node = node.map.get(index);
            }
            return node.end;
        }

        public void delete(String word){
            int search = search(word);
//            while (search-- > 0){
            if (search != 0){
                Node2 node = root;
                node.pass--;
                char[] chars = word.toCharArray();
                int index;
                for (int i = 0; i < chars.length; i++) {
                    index = chars[i] - 'a';
                    if (--node.map.get(index).pass == 0){
                        node.map.remove(index);
                        return;
                    }
                    node = node.map.get(index);
                }
                node.end--;
            }
        }

        public int prefixNumber(String str){
            if (str == null){
                return 0;
            }
            Node2 node = root;
            char[] chars = str.toCharArray();
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (!node.map.containsKey(index)){
                   return 0;
                }
                node = node.map.get(index);
            }
            return node.pass;
        }
    }

    /**
     * 生产随机字符串
     * @param strLen
     * @return
     */
    private static String generateRandomString(int strLen){
        int len = (int) (Math.random() * strLen) + 1;
        char[] chars = new char[len];
        for (int i = 0; i < chars.length; i++) {
            int value = (int)(Math.random() * 26);
            chars[i] = (char) ('a' + value);
        }
        return String.valueOf(chars);
    }

    public static String[] generateStrings(int maxLen){
        int len = (int) (Math.random() * maxLen) + 1;
        String[] strings = new String[len];
        for (int i = 0; i < len; i++) {
            strings[i] = generateRandomString(maxLen);
        }
        return strings;
    }

    public static void checkTireTree(String[] str,String prefix){
        Test test = new Test();
        Tire1 tire1 = new Tire1();
        Tire2 tire2 = new Tire2();
        for (int i = 0; i < str.length; i++) {
            test.insert(str[i]);
            tire1.insert(str[i]);
            tire2.insert(str[i]);
        }
        for (int i = 0; i < str.length; i++) {
            double random = Math.random();
             if (random < 0.33){
                test.delete(str[i]);
                tire1.delete(str[i]);
                tire2.delete(str[i]);
            }else if (random < 0.66){
                int search_Test = test.search(str[i]);
                int search_Tire = tire1.search(str[i]);
                 int search_Map = tire2.search(str[i]);
                 if (search_Tire != search_Test || search_Tire != search_Map || search_Test != search_Map){
                    System.out.println("search_Test : " + search_Test + " === search_Tire : " + search_Tire + " === search_map : " + search_Map);
                }
            }else {
                 int search_Test = test.prefixNumber(prefix);
                 int search_Tire = tire1.prefixNumber(prefix);
                 int search_Map = tire2.prefixNumber(prefix);
                 if (search_Tire != search_Test || search_Tire != search_Map || search_Test != search_Map){
                     System.out.println("prefix_Test : " + search_Test + " === prefix_Tire : " + search_Tire + " === prefix_map : " + search_Map);
                 }
             }
        }
    }

    public static void main(String[] args) {
        int maxLen = 26;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            checkTireTree(generateStrings(maxLen),"str");
        }
        System.out.println("测试结束");

    }
}
