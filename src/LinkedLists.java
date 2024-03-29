import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;

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
            HashSet<Node<T>> hashSet = new HashSet<>();
            Node<T> n = this;

            while (n != null) {
                if (hashSet.contains(n)) {
                    System.out.println();
                    return;
                }
                hashSet.add(n);
                System.out.print(n.data + "->");
                n = n.next;
            }
            System.out.println();
        }
    }

    public Node<Integer> createLinkedList(int size, boolean random) {
        Node<Integer> head = new Node<>(1);
        if (random) {
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                head.appendToTail(rand.nextInt(2));
            }
        } else {
            for (int i = 2; i < size; i++) {
                head.appendToTail(i);
            }
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
    //      Time Complexity w/ Recursion: O(2N) = O(N)                                                      //
    //      Space Complexity w/ Recursion: O(N)                                                             //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Brute Force method
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

    class Bag {
        public int index = 0;
        public int length = 0;
        public boolean isOdd = false;
    }

    public Node<Integer> getKthToLastNodeRecursiveDriver(Node<Integer> head, int k) {
        Bag index = new Bag();
        return getKthToLastNodeRecursive(head, k, index);
    }

    public Node<Integer> getKthToLastNodeRecursive(Node<Integer> node, int k, Bag index) {
        if (node == null) return null;
        Node<Integer> nodeK = getKthToLastNodeRecursive(node.next, k, index);
        if (k == index.index) nodeK = node;

        // Need to increment index every visit on the way back up.
        // If index++ was in an else statement, it would be stuck at the kth node
        // and nodeK will be updated to current node every time after the kth node
        // on the way back up
        index.index++;
        return nodeK;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.3 DELETE MIDDLE NODE                                        //
    //              Implement an algorithm to delete a node in the middle (i.e, any node but the first      //
    //              (and last node, not necessarily the exact middle) of a singly linked list, given        //
    //              only access to that node.                                                               //
    //                                                                                                      //
    //              Example:                                                                                //
    //              Input: The node C from the linked list A->B->C->D->E->F                                 //
    //              Result: Nothing is returned, but the new linked list looks like A->B->D->E->F           //
    //                                                                                                      //
    //      Time Complexity: O(N)                                                                           //
    //      Space Complexity: O(1)                                                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * We will make it appear like the current node is deleted by shifting all of the subsequent values 1 node back.
     * Then we will remove the tail by setting tail-1 node's next value to null
     *
     * @param head - A node in the middle of Linked List. Cannot be the last node.
     */
    public void deleteMiddleNode(Node<Integer> head) {
        Node<Integer> curNode = head;
        while (curNode.next != null) {
            curNode.data = curNode.next.data;
            if (curNode.next.next == null) {
                curNode.next = null; // remove the tail, current node is now the tail
            } else {
                curNode = curNode.next;
            }
        }
    }

    /**
     * We don't need to iterate through every subsequent node, all we need to do is
     * copy the next node's data and then delete it
     *
     * @param head - A node in the middle of Linked List. Cannot be the last node.
     */
    public void deleteMiddleNodeImproved(Node<Integer> head) {
        if (head.next != null) {
            head.data = head.next.data;
            head.next = head.next.next;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.4 PARTITION                                                 //
    //      Write code to partition a linked list around a value x, such that all nodes less                //
    //      than x come before all nodes greater than or equal to x. If x is contained within               //
    //      the list, the values of x only need to be after the elements less than x (see below).           //
    //      The partition element x can appear anywhere in the "right partition"; it does not need          //
    //      to appear between the left and right partitions.                                                //
    //                                                                                                      //
    //      Example:                                                                                        //
    //      Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1  [partition=5]                                          //
    //      Result: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8                                                        //
    //                                                                                                      //
    //      Time Complexity: O(N)                                                                           //
    //      Space Complexity: O(1)                                                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Re-partitions by keeping track of the HEAD and TAIL. Nodes with
     * values smaller than X will be pre-pended and set as HEAD. Nodes with
     * values equal to or larger than X will be appended to the TAIL.
     */
    public Node<Integer> partitionLinkedList(Node<Integer> head, int x) {
        Node<Integer> tail = head;
        Node<Integer> curNode = head;
        Node<Integer> next;

        while (curNode != null) {
            next = curNode.next;
            if (curNode.data < x) {
                curNode.next = head;
                head = curNode;
            } else {
                tail.next = curNode;
                tail = curNode;
            }
            curNode = next;
        }

        tail.next = null;

        /**
         * head has changed, need to return it, if we don't
         * printing Head will only give values that haven't been been reshuffled
         * to in front of original HEAD
         */
        return head;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.5 SUM LISTS                                                 //
    //      You have two numbers represented by a linked list, where each node contains a single digit.     //
    //      The digits are stored in reverse order, such that the 1 's digit is at the head of the list.    //
    //      Write a function that adds the two numbers and returns the sum as a linked list.                //
    //                                                                                                      //
    //      Example:                                                                                        //
    //      Input: (7-> 1 -> 6) + (5 -> 9 -> 2) ...That is, 617 + 295.                                      //
    //      Result: 9 -> 1 -> 2 ...That is, 912.                                                            //
    //                                                                                                      //
    //      Time Complexity: O(2MAX(A, B)) = O(N)                                                           //
    //      Space Complexity: O(N)                                                                          //
    //                                                                                                      //
    //      FOLLOW UP                                                                                       //
    //      Suppose the digits are stored in forward order. Repeat the above problem.                       //
    //                                                                                                      //
    //      EXAMPLE                                                                                         //
    //      Input: (6 -> 1 -> 7) + (2 -> 9 -> 5)  ...That is, 617 + 295                                     //
    //      Output: 9 -> 1 -> 2 ...That is, 912.                                                            //
    //                                                                                                      //                                                                             //
    //      Time Complexity: O(A + B + MAX(A, B)) = O(N)                                                    //
    //      Space Complexity: O(N)                                                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Once a sum is generated, the sum is incrementally processed to remove the single's digit (mod 10)
     * and then updated after stripping off the singles digit (sum = [sum - stripped digit] / 10)
     */
    public Node<Integer> sumReverseOrderLists(Node<Integer> headA, Node<Integer> headB) {
        int sum = sumReverseLists(headA, headB);
        if (sum == 0) {
            return new Node<Integer>(0);
        }

        int digit = sum % 10;
        sum = (sum - digit) / 10;
        Node<Integer> head = new Node<>(digit);
        Node<Integer> curNode = head;

        while (sum > 0) {
            digit = sum % 10;
            sum = (sum - digit) / 10;
            Node<Integer> newNode = new Node<>(digit);
            curNode.next = newNode;
            curNode = newNode;
        }

        return head;
    }

    /**
     * Sums the Lists Digit by Digit, every node traversed increases the multiplier
     * by a factor of 10. Once the shorter list is traversed, the nodes in the
     * remaining list are processed.
     */
    private int sumReverseLists(Node<Integer> headA, Node<Integer> headB) {
        Node<Integer> nodeA = headA;
        Node<Integer> nodeB = headB;
        int sum = 0;
        int multiplier = 1;

        // we can process them together, they both start at the 1's digit
        while (nodeA != null & nodeB != null) {
            sum += (nodeA.data * multiplier) + (nodeB.data * multiplier);
            multiplier *= 10;
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }

        Node<Integer> nodeC = (nodeA == null) ? nodeB : nodeA;
        while (nodeC != null) {
            sum += nodeC.data * multiplier;
            multiplier *= 10;
            nodeC = nodeC.next;
        }
        return sum;
    }

    public Node<Integer> sumLinkedLists(Node<Integer> headA, Node<Integer> headB) {
        int sum = sumLists(headA, headB);
        if (sum == 0) {
            return new Node<Integer>(0);
        }
        int factor10 = (int) ((Math.pow(10, Math.floor(Math.log10(sum)))));
        int digit = sum / factor10;
        sum -= digit * factor10;
        factor10 /= 10;

        Node<Integer> head = new Node<>(digit);
        Node<Integer> curNode = head;

        while (sum > 0) {
            digit = sum / factor10;
            sum -= digit * factor10;
            factor10 /= 10;
            Node<Integer> newNode = new Node<>(digit);
            curNode.next = newNode;
            curNode = newNode;
        }

        return head;
    }

    /**
     * Creates two strings, one out of each list and then sums the strings
     * using parseInt() with a radix of 10
     */
    private int sumLists(Node<Integer> headA, Node<Integer> headB) {
        Node<Integer> nodeA = headA;
        Node<Integer> nodeB = headB;
        String sumA = "";
        String sumB = "";

        // This time we can't process both lists simultaneously.
        // They may start at different powers of 10 (10 vs 1's)
        while (nodeA != null) {
            sumA += nodeA.data;
            nodeA = nodeA.next;
        }

        while (nodeB != null) {
            sumB += nodeB.data;
            nodeB = nodeB.next;
        }

        return Integer.parseInt(sumA, 10) + Integer.parseInt(sumB, 10);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.6 PALINDROME                                                //
    //      Implement a function to check if a linked list is a palindrome.                                 //
    //                                                                                                      //
    //      Time Complexity: O(N)                                                                           //
    //      Space Complexity: O(N)                                                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isPalindromeRecursiveDriver(Node<Integer> head) {
        Stack<Integer> stack = new Stack<>();
        Bag bag = new Bag();
        return isPalindromeRecursive(head, stack, bag);
    }

    /**
     * Recursively navigate to end of list, incrementing index with each recursive call.
     * At the null node, set the length.
     * On the way back, push values onto the stack if we are before the midpoint. And pop values
     * after the midpoint. With Odd numbered lists we don't push or pop at the midpoint.
     */
    private boolean isPalindromeRecursive(Node<Integer> node, Stack<Integer> stack, Bag bag) {
        if (node == null) {
            bag.length = bag.index;
            bag.isOdd = bag.length%2 != 0;
            System.out.println("LENGTH = " + bag.length);
            return true; // is palindrome if length <= 1;
        }
        bag.index++;
        boolean isPalindrome = isPalindromeRecursive(node.next, stack, bag);
        bag.index--;
        if (bag.isOdd && bag.index == bag.length/2) {
            return isPalindrome;
        }
        if (bag.index >= bag.length/2) {
            stack.push(node.data);
        } else {
            if (isPalindrome == false) return isPalindrome;
            isPalindrome = node.data == stack.pop();
        }
        return isPalindrome;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.7 INTERSECTION                                              //
    //      Given two (singly) linked lists, determine if the two lists intersect. Return the               //
    //      inter­secting node. Note that the intersection is defined based on reference, not value.         //
    //      That is, if the kth node of the first linked list is the exact same node (by reference)         //
    //      as the jth node of the second linked list, then they are intersecting.                          //
    //                                                                                                      //
    //      Time Complexity (BruteForce): O(N^2)                                                            //
    //      Space Complexity (BruteForce): O(1)                                                             //
    //                                                                                                      //
    //      Time Complexity (HashTable): O(A+B) = O(N)                                                      //
    //      Space Complexity (HashTable): O(A+B) = O(N)                                                     //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Node<Integer> getIntersectingNode(Node<Integer> head1, Node<Integer> head2) {
        Node<Integer> n1 = head1;

        while (n1 != null) {
            Node<Integer> n2 = head2;
            while (n2 != null) {
                if (n1 == n2) {
                    return n1;
                }
                n2 = n2.next;
            }
            n1 = n1.next;
        }
        return null;
    }

    public Node<Integer> getIntersectingNodeHashTable(Node<Integer> head1, Node<Integer> head2) {
        Hashtable<Node<Integer>, Node<Integer>> hashTable = new Hashtable<>();
        Node<Integer> n1 = head1;
        Node<Integer> n2 = head2;

        while (n1 != null) {
            hashTable.put(n1, n1);
            n1=n1.next;
        }

        while (n2 != null) {
            if (hashTable.containsKey(n2)) {
                return n2;
            }
            n2 = n2.next;
        }

        return null;
    }

    class IntersectingLists<T> {
        Node<T> list1 = null;
        Node<T> list2 = null;
    }

    public IntersectingLists<Integer> createIntersectingLists() {
        Node<Integer> headA = new Node<>(33);
        Node<Integer> nodeA = new Node<Integer>(66);
        Node<Integer> headB = new Node<Integer>(99);
        Node<Integer> sharedNode = new Node<>(69);
        Node<Integer> sharedList = createLinkedList(3, true);
        sharedNode.next = sharedList;
        headA.next = nodeA;
        nodeA.next = sharedNode;
        headB.next = sharedNode;

        IntersectingLists<Integer> lists = new IntersectingLists<>();
        lists.list1 = headA;
        lists.list2 = headB;
        return lists;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        2.8 LOOP DETECTION                                            //
    //      Given a circular linked list, implement an algorithm that returns the node at the               //
    //      beginning of the loop.                                                                          //
    //                                                                                                      //
    //      DEFINITION                                                                                      //
    //      Circular linked list: A (corrupt) linked list in which a node's next pointer points to          //
    //      an earlier node, so as to make a loop in the linked list.                                       //
    //                                                                                                      //
    //      Example:                                                                                        //
    //      Input: A -> B -> C -> D -> E -> C [the same C as earlier]                                       //
    //      Result: C                                                                                       //
    //                                                                                                      //
    //      Time Complexity (BruteForce): O(N^2)                                                            //
    //      Space Complexity (BruteForce): O(1)                                                             //
    //                                                                                                      //
    //      Time Complexity (HashTable): O(A+B) = O(N)                                                      //
    //      Space Complexity (HashTable): O(A+B) = O(N)                                                     //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Iterates through the list and adds each node to a set.
     * If we encounter a node that we've visited before, we have a circular list
     * and we return the node that we've visited before (the beginning of the loop)
     */
    public Node<Integer> getCorruptNodeFromCircularList(Node<Integer> head) {
        Node<Integer> node = head;
        HashSet<Node<Integer>> hashSet = new HashSet<>();

        while (node != null) {
            if (hashSet.contains(node)) {
                node.next = null;
                return node;
            }
            hashSet.add(node);
            node = node.next;
        }

        return null;
    }

    public Node<Integer> createCircularLinkedList(int size) {
        Node<Integer> head = createLinkedList(size, false);
        Node<Integer> node = head;

        while (node.next != null) {
            node = node.next;
        }
        node.next = head;

        return head;
    }
    public static void main(String[] args) throws Exception {
        LinkedLists ll = new LinkedLists();

        System.out.println("2.1 Write code to remove duplicates from an unsorted linked list.\n" +
                           "How do you solve this problem if a temporary buffer is not allowed?");
        Node<Integer> headQ1 = ll.createLinkedList(10, true);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ1.printLinkedList();
        ll.removeDuplicatesNoBuffer(headQ1);
        System.out.println("LINKED LIST DUPLICATES DELETED:");
        headQ1.printLinkedList();
        System.out.print("\n\n");

        System.out.println("2.2 Implement an algorithm to find the kth to last element of a singly linked list:\n" +
                            "FYI: I have defined it so that passing in k=0 means returning the last element");
        Node<Integer> headQ2 = ll.createLinkedList(10, false);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ2.printLinkedList();
        int kthNodeToLast = 5;
        Node<Integer> kToLast = ll.getKthToLastNodeRecursiveDriver(headQ2, kthNodeToLast);
        System.out.println("LINKED LIST Kth Node to Last & K=" + kthNodeToLast);
        kToLast.printLinkedList();
        System.out.print("\n\n");

        System.out.println("2.3 Implement an algorithm to delete a node in the middle (i.e, any node but the first\n" +
                           "(and last node, not necessarily the exact middle) of a singly linked list, given\n" +
                           "only access to that node.\n\n" +
                           "Example:\n" +
                           "Input: The node C from the linked list A->B->C->D->E->F\n" +
                           "Result: Nothing is returned, but the new linked list looks like A->B->D->E->F\n");
        Node<Integer> headQ3 = ll.createLinkedList(6, false);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ3.printLinkedList();
        System.out.println("LINKED LIST WITH DELETED INTERNAL NODE:");
        ll.deleteMiddleNodeImproved(headQ3.next.next);
        headQ3.printLinkedList();
        System.out.print("\n\n");

        System.out.println("2.4 Write code to partition a linked list around a value x, such that all nodes less\n" +
                           "than x come before all nodes greater than or equal to x. If x is contained within\n" +
                           "the list, the values of x only need to be after the elements less than x (see below).\n" +
                           "The partition element x can appear anywhere in the 'right partition'; it does not need\n" +
                           "to appear between the left and right partitions.\n\n" +
                           "Example:\n" +
                           "Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1  [partition=5]\n" +
                           "Result: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8\n");
        Node<Integer> headQ4 = ll.createLinkedList(6, false);
        System.out.println("ORIGINAL LINKED LIST:");
        headQ4.printLinkedList();
        int partition = 3;
        System.out.println("LINKED LIST PARTITIONED AT: " + partition);
        headQ4 = ll.partitionLinkedList(headQ4, partition);
        headQ4.printLinkedList();
        System.out.print("\n\n");

        System.out.println("2.5 You have two numbers represented by a linked list, where each node contains a single digit.\n" +
                           "The digits are stored in reverse order, such that the 1 's digit is at the head of the list.\n" +
                           "Write a function that adds the two numbers and returns the sum as a linked list.");
        Node<Integer> headQ5A = ll.createLinkedList(3, true);
        Node<Integer> headQ5B = ll.createLinkedList(2, true);
        System.out.println("ORIGINAL REVERSE DIGIT LINKED LISTS:");
        headQ5A.printLinkedList();
        headQ5B.printLinkedList();
        System.out.println("REVERSE DIGIT SUM:");
        Node<Integer> headQ5C = ll.sumReverseOrderLists(headQ5A, headQ5B);
        headQ5C.printLinkedList();
        System.out.print("\n");
        System.out.println("2.5 Follow up.\n Suppose the digits are stored in forward order. Repeat the above problem.\n");
        System.out.println("FORWARD DIGIT SUM:");
        headQ5A.printLinkedList();
        headQ5B.printLinkedList();
        Node<Integer> headQ5D = ll.sumLinkedLists(headQ5A, headQ5B);
        headQ5D.printLinkedList();
        System.out.print("\n");

        System.out.println("2.6 Implement a function to check if a linked list is a palindrome.\n");
        Node<Integer> headQ6 = ll.createLinkedList(2, true);
        headQ6.printLinkedList();
        System.out.println("isPalindrome() = " + ll.isPalindromeRecursiveDriver(headQ6));
        System.out.print("\n\n");

        System.out.println("2.7 You have two numbers represented by a linked list, where each node contains a single digit.\n" +
        "The digits are stored in reverse order, such that the 1 's digit is at the head of the list.\n" +
        "Write a function that adds the two numbers and returns the sum as a linked list.\n");
        IntersectingLists<Integer> intersectingLists = ll.createIntersectingLists();
        System.out.println("INTERSECTING LINKED LISTS:");
        intersectingLists.list1.printLinkedList();
        intersectingLists.list2.printLinkedList();
        Node<Integer> intersectingNode = ll.getIntersectingNodeHashTable(intersectingLists.list1, intersectingLists.list2);
        System.out.println("INTERSECTING NODE:");
        intersectingNode.printLinkedList();
        System.out.print("\n\n");

        System.out.println("Given a circular linked list, implement an algorithm that returns the node at the\n" +
        "beginning of the loop.\nDEFINITION\nCircular linked list: A (corrupt) linked list in which a node's next pointer points to\n" +
        "an earlier node, so as to make a loop in the linked list.\n");
        Node<Integer> circularList = ll.createCircularLinkedList(4);
        circularList.printLinkedList();
        Node<Integer> corruptNode = ll.getCorruptNodeFromCircularList(circularList);
        corruptNode.printLinkedList();
    }
}
