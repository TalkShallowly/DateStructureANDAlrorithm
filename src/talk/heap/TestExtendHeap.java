package talk.heap;

import java.util.PriorityQueue;

/**
 * @author talkshallowly
 */
public class TestExtendHeap {
    public static class Person{
        private String name;
        private int age;
        public Person(String name,int age){
            this.age = age;
            this.name = name;
        }
    }
    public static void check(PriorityQueue<Person> priorityQueue, ExtendHeap<Person> extendHeap){

    }

    public static void main(String[] args) {
        int age = 1000;
        int testTime = 200;
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>((o1, o2) -> o1.age - o2.age);
        ExtendHeap<Person> extendHeap = new ExtendHeap<>((o1, o2) -> o1.age - o2.age);
        System.out.println("start......");
        for (int i = 0; i < testTime; i++) {
            int ageValue = (int)(Math.random() * age) + 1;
            Person person = new Person("hello",ageValue);
            priorityQueue.add(person);
            extendHeap.push(person);
        }
        for (int i = 0; i < priorityQueue.size(); i++) {
            if (Math.random() > 0.5){
                int age1 = priorityQueue.poll().age;
                int age2 = extendHeap.pop().age;
                if(age1 != age2){
                    System.out.println("oops .... " + age1 + " ...." + age2 );
                }
            }else {
                int randomIndex = (int)(Math.random() * priorityQueue.size());
                Person person = extendHeap.getIndex(randomIndex);
                priorityQueue.remove(person);
                System.out.println("asd");
                extendHeap.remove(person);
            }
        }
        System.out.println("end.......");

    }
}
