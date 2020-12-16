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

    public static void main(String[] args) {
        LinkedLists ll = new LinkedLists();
        Node<Integer> head = ll.createLinkedList(10);
        head.printLinkedList();
        ll.removeDuplicatesNoBuffer(head);
        head.printLinkedList();
    }


}
