import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class birthdayLinkList implements Runnable {

    static List<Integer> giftBag = new ArrayList<>();
    private static AtomicReference<ConcurrentLinkedList> listReference;
    private Random random;

    public birthdayLinkList(ConcurrentLinkedList list) {
        listReference = new AtomicReference<>(list);
        this.random = new Random();
    }


    @Override
    public void run() {
        ConcurrentLinkedList list = listReference.get();

        for (int i = 0; i < giftBag.size(); i++) {
            int task = random.nextInt(3) + 1;
            switch (task) {
                case 1:
                    if (list.add(i))
                        System.out.println("Added: " + i);
                    break;
                case 2:
                    if (!list.remove(i)) {
                        list.add(i);
                         System.out.println("Added (failed to remove): " + i);
                    } else {
                         System.out.println("Deleted: " + i);
                    }
                    break;
                case 3:
                    int randomFind = random.nextInt(10);
                    boolean found = list.contains(randomFind);
                     System.out.println("Found " + randomFind + ": " + found);
                    break;
            }
        }
    }

    public static void setGiftBag(List<Integer> bag) {
        giftBag = bag;
    }

    public static void main(String[] args) {
        // Here we are just generating the gift's tag number
        // and then we shuffle so we can simulate randomly choosing gifts

        // Change this value for testing
        int max = 500000 ;

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        setGiftBag(list);

        ConcurrentLinkedList presentList = new ConcurrentLinkedList();
        Thread[] servants = new Thread[4];

        for (int i = 0; i < 4; i++) {
            servants[i] = new Thread(new birthdayLinkList(presentList));
            servants[i].start();
        }

        try {
            for (Thread servant : servants) {
                servant.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // After all servants have finished their tasks
        System.out.println("All thank you notes have been written.");
    }
}

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        next = null;
    }
}

class ConcurrentLinkedList {

    Node head;

    public synchronized boolean add(int data) {
        if (!contains(data)) { 
            Node newNode = new Node(data);
            if (head == null || head.data >= data) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null && current.next.data < data) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
            return true; 
        }
        return false; 
    }

    public synchronized boolean remove(int data) {
        if (head != null) {
            head = head.next;
            return true;
        }
        return false;
    }

    public synchronized boolean contains(int data) {
        Node current = head;
        while (current != null) {
            if (current.data == data) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
} 

