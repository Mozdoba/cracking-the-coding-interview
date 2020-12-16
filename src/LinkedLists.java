import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class LinkedLists {

    class Node<T> {
        Node<T> next = null;
        T data;

        public Node (T data) {
            this.data = data;
        }

        public void appendToTail(T data) {
            Node<T> tail = new Node<T>(data);
            Node<T> n = this;
            while (n.next != null) {
                n = n.next;
            }
            n.next = tail;
        }

        public void printLinkedList() {
            Node<T> n = this;

            while (n != null) {
                System.out.print(n.data + "->");
                n = n.next;
            }
            System.out.println();
        }
    }

    public Node<Integer> createLinkedList(int size) {
        Random rand = new Random();
        Node<Integer> head = new Node<>(rand.nextInt(4));
        for (int i = 0; i < size; i++) {
            head.appendToTail(rand.nextInt(4));
        }
        return head;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.1 REMOVE DUPLICATES                                         //
    //      Write code to remove duplicates from an unsorted linked list. How do you solve this problem     //
    //      if a temporary buffer is not allowed?                                                           //
    //                                                                                                      //
    //      Time Complexity w/ Buffer: O(N)                                                                 //
    //      Space Complexity w/ Buffer: O(N)                                                                //
    //                                                                                                      //
    //      Time Complexity without Buffer: O(N)                                                            //
    //      Space Complexity without Buffer: O(N)                                                           //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void removeDuplicates(Node<Integer> head) {
        HashSet<Integer> set = new HashSet<>();
        set.add(head.data);
        Node<Integer> n = head;
        outermost:
        while(n.next != null) {
            while(set.contains(n.next.data)) {
                if (n.next.next == null) {
                    n.next = null;
                    break outermost;
                } else {
                    n.next = n.next.next;
                }
            }
            set.add(n.next.data);
            n = n.next;
        }
    }

    public void removeDuplicates2(Node<Integer> head) {
        HashSet<Integer> set = new HashSet<>();
        set.add(head.data);
        Node<Integer> prev = head;
        Node<Integer> curr = head.next;
        while (curr != null) {
            if (set.contains(curr.data)) {
                prev.next = curr.next;
            } else {
                set.add(curr.data);
                prev = curr;
            }
            curr = curr.next;
        }
    }

    public void removeDuplicatesNoBuffer(Node<Integer> head) {
        Node<Integer> current = head;
        Node<Integer> prevRunner = head;
        Node<Integer> runner = head.next;
        while (current != null) {
            while (runner != null) {
                if (current.data == runner.data) {
                    prevRunner.next = runner.next;
                } else {
                    prevRunner = prevRunner.next;
                }
                runner = runner.next;
            }
            current = current.next;
            prevRunner = current;

            if (current != null) {
                runner = current.next;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.2 RETURN Kth TO LAST                                        //
    //              Implement an algorithm to find the kth to last element of a singly linked list          //
    //              FYI: I have defined it so that passing in k=0 returns the last element                  //
    //                                                                                                      //
    //      Time Complexity without Recursion: O(N)                                                         //
    //      Space Complexity without Recursion: O(1)                                                        //
    //                                                                                                      //
    //      Time Complexity w/ Recursion: O(?)                                                              //
    //      Space Complexity w/ Recursion: O(?)                                                             //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Node<Integer> getKthToLastNode(Node<Integer> head, int k) throws Exception {
        if (k < 0) throw new Exception("Invalid Input: Below Zero");
        Node<Integer> current = head;
        int length = 0;
        while (current != null) {
            length++;
            current = current.next;
        }
        if (k >= length) throw new Exception("Invalid Input: Index Out of Bounds");
        current = head;
        for (int i = 0; i < length-1-k; i++) {
            current = current.next;
        }
        return current;
    }

    public static void main(String[] args) throws Exception {
        LinkedLists ll = new LinkedLists();

        System.out.println("Write code to remove duplicates from an unsorted linked list.\n" +
                           "How do you solve this problem if a temporary buffer is not allowed?");
        Node<Integer> headQ1 = ll.createLinkedList(10);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ1.printLinkedList();
        ll.removeDuplicatesNoBuffer(headQ1);
        System.out.println("LINKED LIST DUPLICATES DELETED:");
        headQ1.printLinkedList();
        System.out.print("\n\n");

        System.out.println("Implement an algorithm to find the kth to last element of a singly linked list:\n" +
                            "FYI: I have defined it so that passing in k=0 means returning the last element");
        Node<Integer> headQ2 = ll.createLinkedList(10);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ2.printLinkedList();
        int kthNodeToLast = 10;
        Node<Integer> kToLast = ll.getKthToLastNode(headQ2, kthNodeToLast);
        System.out.println("LINKED LIST Kth Node to Last & K=" + kthNodeToLast);
        kToLast.printLinkedList();
        System.out.print("\n\n");
    }


}
