import java.security.InvalidParameterException;
import java.util.Date;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class StacksAndQueues {

    static enum MyAnimal {DOG, CAT}; //QUESTION 3.6

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.1 THREE IN ONE                                              //
    //      Describe how you could use a single array to implement three stacks.                            //
    //                                                                                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class FixedSizeThreeStack {
        private int numberOfStacks = 3;
        private int size;
        private int[] arr;
        private int index1Top;
        private int index2Top;
        private int index3Top;

        /**
         *
         * @param size - The size of each individual array;
         */
        public FixedSizeThreeStack(int size) {
            if (size < 0) throw new InvalidParameterException("size must be at least 1");
            this.size = size;
            this.arr = new int[size*numberOfStacks];
            index1Top = 0;
            index2Top = size;
            index3Top = size*2;
        }

        public boolean isFull(int stack) {
            if (stack < 1 || stack > 3) throw new InvalidParameterException("Parameter must have value 1 || 2 || 3");
            if (stack == 1) {
                return index1Top == size;
            } else if (stack == 2) {
                return index2Top == size*2;
            } else {
                return index3Top == size*3;
            }
        }

        public boolean isEmpty(int stack) {
            if (stack < 1 || stack > 3) throw new InvalidParameterException("Parameter must have value 1 || 2 || 3");
            if (stack == 1) {
                return index1Top == 0;
            } else if (stack == 2) {
                return index2Top == size;
            } else {
                return index3Top == size*2;
            }
        }

        public void push(int stack, int val) throws Exception {
            if (isFull(stack)) throw new Exception("Stack " + stack + " is full");
            if (stack == 1) {
                arr[index1Top++] = val; //set and then increment so that we are ready to add the next element
            } else if (stack == 2) {
                arr[index2Top++] = val;
            } else {
                arr[index3Top++] = val;
            }
        }

        public int pop(int stack) throws Exception {
            if (isEmpty(stack)) throw new Exception("Stack " + stack + " is empty");
            int data;
            if (stack == 1) {
                data = arr[--index1Top]; //pre-decrement because we need to go back to the last element added
            } else if (stack == 2) {
                data = arr[--index2Top];
            } else {
                data = arr[--index3Top];
            }
            return data;
        }

        public int peek(int stack) throws Exception {
            if (isEmpty(stack)) throw new Exception("Stack " + stack + " is empty");
            if (stack == 1) {
                return arr[index1Top-1]; // Need to go back an index to look at the last added value
            } else if (stack == 2) {
                return arr[index2Top-1];
            } else {
                return arr[index3Top-1];
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.2 STACK MINIMUM                                             //
    //      How would you design a stack which, in addition to push and pop, has a function min which       //
    //      returns the minimum element? Push, pop and min should all operate in 0(1) time.                 //
    //                                                                                                      //
    //      Keeping track of the min with every push and pop wouldn't work because as soon as we pop        //
    //      a min value, we would have to recalculate the new min by searching, thus violating our O(1)     //
    //      time limitation. A solution will be for each StackNode to keep track of the the min underneath  //
    //      it. Every time we add, the min for the stack gets updated with the min of the top of the stack  //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class MinStack {
        private StackNode top;
        private int size = 0;
        private int stackMin;

        public MinStack() {}

        private class StackNode {
            int data;
            StackNode next;
            int nodeMin;
            StackNode(int data) {
                this.data = data;
                if (top == null) {
                    stackMin = nodeMin = data;
                } else {
                    next = top;
                    nodeMin = data < next.nodeMin ? data : next.nodeMin;
                    stackMin = stackMin < nodeMin ? stackMin : nodeMin;
                }
            }
        }

        public void push(int data) {
            StackNode newTop = new StackNode(data);
            newTop.next = top;
            top = newTop;
            size++;
        }

        public int pop() throws Exception {
            if (isEmpty()) throw new EmptyStackException();
            stackMin = top.next == null ? -1 : top.next.nodeMin;
            int data = top.data;
            top = top.next;
            size--;

            return data;
        }

        public int peek() throws Exception {
            if (isEmpty()) throw new EmptyStackException();
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public int size() {
            return size;
        }

        public int min () {
            return stackMin;
        }

        public void print() {
            StackNode curr = top;
            System.out.print("Stack: ");
            while (curr != null) {
                System.out.print("" + curr.data + "->");
                curr = curr.next;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.3 STACK OF PLATES                                           //
    //      Imagine a (literal) stack of plates. If the stack gets too high, it might topple.               //
    //      Therefore, in real life, we would likely start a new stack when the previous stack exceeds some //
    //      threshold. Implement a data structure SetOfStacks that mimics this. SetOfStacks should          //
    //      be composed of several stacks and should create a new stack once the previous one exceeds       //
    //      capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single      //
    //      stack (that is, pop() should return the same values as it would if there were just a single     //
    //      stack).                                                                                         //
    //                                                                                                      //
    //      FOLLOW UP                                                                                       //
    //      Implement a function popAt(int index) which performs a pop operation on a specific sub-stack.   //
    //                                                                                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    class SetOfStacks {
        ArrayList<Stack<Integer>> stacks = new ArrayList<>();
        int capacity = 3; // Capacity of each stack before rolling over to a new stack
        int size = 0;

        public SetOfStacks() {
            stacks.add(new Stack<Integer>());
        }

        public boolean isFull(Stack<Integer> stack) {
            if (stack.size() == capacity) {
                return true;
            }
            return false;
        }

        public void push(int data) {
            if (isFull(stacks.get(stacks.size() - 1))) {
                stacks.add(new Stack<Integer>());
            }
            stacks.get(stacks.size() - 1).add(data);
            size++;
        }

        public int pop() {
            if (stacks.get(0).isEmpty()) throw new EmptyStackException();
            if (stacks.get(stacks.size() - 1).isEmpty()) {
                stacks.remove(stacks.size() - 1);
            }
            size--;
            return stacks.get(stacks.size() - 1).pop();
        }

        public int peek() {
            if (stacks.get(0).isEmpty()) throw new EmptyStackException();
            if (stacks.get(stacks.size() - 1).isEmpty()) {
                return stacks.get(stacks.size() - 2).peek();
            }
            return stacks.get(stacks.size() - 1).peek();
        }

        /**
         * Removes an item at the specific index and does so by popping until we reach that index, and then
         * pushing all of the items back onto the multistack.
         * @param index the contiguous index to remove.
         * @return an integer at the specified contiguous index
         */
        public int removeIndex(int index) {
            if (index < 0 || index >= size) throw new InvalidParameterException("Requested size is either to big or less than 0");
            ArrayList<Integer> dataList = new ArrayList<>();
            while (size > index) {
                dataList.add(pop());
            }
            int data = dataList.remove(dataList.size()-1);
            int size = dataList.size();
            for (int i = size-1; i >= 0; i--) {
                push(dataList.remove(i));
            }
            return data;
        }

        /**
         * Pops an item at the specified stack and then re-shifts the numbers at larger stacks to fill in the space
         * at the stack that was popped.
         * @param stack - The first stack is at 0
         * @return an integer at the specified contiguous index
         */
        public int popAt(int stack) {
            if (stack < 0 || stack >= stacks.size()) {
                throw new InvalidParameterException("Requested size is either to big or less than 0. " +
                "Valid parameters are in the range from 0 to " + (stacks.size()-1));
            }
            // If we want to pop at the last available stack, do a regular pop
            if (stack == stacks.size()-1) {
                return pop();
            }
            // Pop at specified stack and shift everything over
            ArrayList<Integer> dataList = new ArrayList<Integer>();
            int topIndexOfSpecifiedStack = capacity * (stack + 1) - 1;
            while (size-1 >= topIndexOfSpecifiedStack) {
                dataList.add(pop());
            }
            int data = dataList.remove(dataList.size()-1);

            // Need to add items back into multistack.
            int numToPush = dataList.size();
            for (int i = numToPush-1; i >= 0; i--) {
                push(dataList.remove(i));
            }
			return data;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.4 QUEUE VIA STACKS                                          //
    //      Implement a MyQueue class which implements a queue using two stacks.                            //
    //                                                                                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    class MyQueue {
        Stack<Integer> newest;
        Stack<Integer> oldest;

        public MyQueue() {
            newest = new Stack<>();
            oldest = new Stack<>();
        }

        int size() {
            return newest.size() + oldest.size();
        }

        void push(int data) {
            newest.push(data);
        }

        int pop() throws Exception {
            if (size() == 0) throw new Exception("Queue is empty");
            shiftOldToNew();
            return oldest.pop();
        }

        private void shiftOldToNew() {
            if (oldest.isEmpty()) {
                while (!newest.empty()) {
                    oldest.push(newest.pop());
                }
            }
        }

        int peek() {
            shiftOldToNew();
            return oldest.peek();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.5 SORT STACK                                                //
    //      Write a program to sort a stack such that the smallest items are on the top. You can use        //
    //      an additional temporary stack, but you may not copy the elements into any other data structure  //
    //      (such as an array). The stack supports the following operations: push, pop, peek, and isEmpty.  //
    //                                                                                                      //
    //      Time Complexity: O(N^2)                                                                         //
    //      Space Complexity: O(N) using another stack that will be filled with N elements                  //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sorts input stack but popping a single element off, and checking that element against elements in another
     * sorted stack. If the temp is smaller than an element in the sorted stack, that element is shifted out of the
     * sorted stack and into the input stack. This process repeats until the temp data can be pushed into the sorted stack
     * There is no need to push the shifted elements back onto the sorted stack because they will each be processed as the temp
     * element.
     *
     * After the input stack is empty, the sorted stack has all our elements in the reverse order we need. Simply push them back onto
     * the input stack to reverse the order.
     */
    public void sort(Stack<Integer> stack) {
        Stack<Integer> sortedStack = new Stack<>();

        // Process off the top one at a time
        while (!stack.isEmpty()) {
            int temp = stack.pop();

            // shift out larger elements from sorted, then push data into correct spot
            while (!sortedStack.isEmpty() && temp <= sortedStack.peek()) {
                stack.push(sortedStack.pop());
            }
            sortedStack.push(temp);
        }

        // Sorted Stack has all elements ordered smallest at bottom, largest at top
        while (!sortedStack.isEmpty()) {
            stack.push(sortedStack.pop());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.6 ANIMAL SHELTER                                            //
    //      An animal shelter, which holds only dogs and cats, operates on a strictly "first in, first out" //
    //      basis. People must adopt either the "oldest" (based on arrival time) of all animals at the      //
    //      shelter, or they can select whether they would prefer a dog or a cat (and will receive the      //
    //      oldest animal of that type). They cannot select which specific animal they would like. Create   //
    //      the data structures to maintain this system and implement operations such as enqueue,           //
    //      dequeueAny, dequeueDog, and dequeueCat. You may use the built-in Linked list data structure.    //
    //                                                                                                      //
    //      Time Complexity: O(?)                                                                           //
    //      Space Complexity: O(?)                                                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    class MyAnimalShelter {

        LinkedList<MyAnimal> dogList;
        LinkedList<MyAnimal> catList;
        LinkedList<MyAnimal> order;

        public MyAnimalShelter() {
            dogList = new LinkedList<MyAnimal>();
            catList = new LinkedList<MyAnimal>();
            order = new LinkedList<MyAnimal>();
        }

        public boolean isEmpty() {
            return order.isEmpty();
        }

        public void enqueue(MyAnimal a) {
            order.add(a);
            if (a == MyAnimal.DOG) {
                dogList.add(a);
            } else {
                catList.add(a);
            }
        }

        public MyAnimal dequeueAny() {
            MyAnimal a = order.removeFirst();
            if (a == MyAnimal.DOG) {
                dogList.removeFirst();
            } else {
                catList.removeFirst();
            }
            return a;
        }

        public MyAnimal dequeueRandom() {
            Random rand = new Random();
            if (rand.nextInt(2) == 0) {
                return dequeueDog();
            } else {
                return dequeueCat();
            }
        }

        public MyAnimal dequeueDog() {
            MyAnimal dog = dogList.removeFirst();
            order.remove(dog);
            return dog;
        }

        public MyAnimal dequeueCat() {
            MyAnimal cat = catList.removeFirst();
            order.remove(cat);
            return cat;
        }
    }

    abstract class Animal {
        private Date admitted;
        protected String name;
        public Animal(String name) {
            this.name = name;
            admitted = new Date();
        }
        boolean isOlderThan(Animal a) {
            return admitted.getTime() <= a.admitted.getTime(); // Older will have a smaller time milliseconds since Jan 1st 1970
        }
    }

    class Dog extends Animal {
        public Dog(String name) {
            super(name);
        }
    }

    class Cat extends Animal {
        public Cat(String name) {
            super(name);
        }
    }


    class AnimalQueueCTCI {
        LinkedList<Dog> dogList;
        LinkedList<Cat> catList;

        public AnimalQueueCTCI() {
            dogList = new LinkedList<Dog>();
            catList = new LinkedList<Cat>();
        }

        public boolean isEmpty() {
            return dogList.isEmpty() && catList.isEmpty();
        }

        public void enqueue(Animal a) {
            if (a instanceof StacksAndQueues.Dog) dogList.addLast((StacksAndQueues.Dog) a);
            else if (a instanceof StacksAndQueues.Cat) catList.addLast((StacksAndQueues.Cat) a);
        }

        public Animal dequeueAny() {
            if (dogList.isEmpty() && !catList.isEmpty()) {
                return dequeueCat();
            } else if (catList.isEmpty() && !dogList.isEmpty()) {
                return dequeueDog();
            } else {
                return dogList.peekFirst().isOlderThan(catList.peekFirst()) ? dequeueDog() : dequeueCat();
            }
        }

        public Dog dequeueDog() {
            return dogList.removeFirst();
        }

        public Cat dequeueCat() {
            return catList.removeFirst();
        }
    }

    public static void main(String[] args) throws Exception {
        StacksAndQueues sq = new StacksAndQueues();
        System.out.println("3.1: Describe how you could use a single array to implement three stacks.\n");
        FixedSizeThreeStack threeStack = sq.new FixedSizeThreeStack(1);
        threeStack.push(1, 1);
        threeStack.push(2, 2);
        threeStack.push(3, 3);

        System.out.println(threeStack.peek(1));
        System.out.println(threeStack.peek(2));
        System.out.println(threeStack.peek(3));

        threeStack.pop(1);
        threeStack.pop(2);
        threeStack.pop(3);
        System.out.println();
        System.out.println("3.2: How would you design a stack which, in addition to push and pop, has a function min which\n" +
        "returns the minimum element? Push, pop and min should all operate in 0(1) time.\n");
        MinStack minStack = sq.new MinStack();
        minStack.push(5);
        minStack.push(2);
        minStack.push(3);
        minStack.push(4);
        minStack.push(1);
        minStack.print();
        System.out.println(" Min: " + minStack.min());
        minStack.pop();
        minStack.print();
        System.out.println(" Min: " + minStack.min());
        minStack.pop();
        minStack.print();
        System.out.println(" Min: " + minStack.min());
        minStack.pop();
        minStack.print();
        System.out.println(" Min: " + minStack.min());
        minStack.pop();
        minStack.print();
        System.out.println(" Min: " + minStack.min());
        minStack.pop();
        minStack.print();
        System.out.println(" Min: " + minStack.min());

        System.out.println();
        System.out.println("3.3: Imagine a (literal) stack of plates. If the stack gets too high, it might topple.\n" +
        "Therefore, in real life, we would likely start a new stack when the previous stack exceeds some\n" +
        "threshold. Implement a data structure SetOfStacks that mimics this. SetOfStacks should\n" +
        "be composed of several stacks and should create a new stack once the previous one exceeds\n" +
        "capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single\n" +
        "stack (that is, pop() should return the same values as it would if there were just a single\n" +
        "stack).\n\nFOLLOW UP\n" +
        "Implement a function popAt(int index) which performs a pop operation on a specific sub-stack.\n");
        SetOfStacks setOfStacks = sq.new SetOfStacks();
        System.out.println("Pushing:");
        for (int i = 0; i < 12; i++) {
            System.out.print(i + " ");
            setOfStacks.push(i);
        }
        System.out.println("\nPopAt:");
        System.out.println(setOfStacks.popAt(0));
        System.out.println("Popping:");
        for (int i = 0; i < 11; i++) {
            System.out.print(setOfStacks.pop() + " ");
        }
        System.out.println();
        System.out.println("3.4: Implement a MyQueue class which implements a queue using two stacks.\n");

        System.out.println();
        System.out.println("3.5: Write a program to sort a stack such that the smallest items are on the top. You can use" +
        "an additional temporary stack, but you may not copy the elements into any other data structure" +
        "(such as an array). The stack supports the following operations: push, pop, peek, and isEmpty.");

        System.out.println();
        System.out.println("3.6: An animal shelter, which holds only dogs and cats, operates on a strictly 'first in, first out'\n" +
        "basis. People must adopt either the 'oldest' (based on arrival time) of all animals at the\n" +
        "shelter, or they can select whether they would prefer a dog or a cat (and will receive the\n" +
        "oldest animal of that type). They cannot select which specific animal they would like. Create\n" +
        "the data structures to maintain this system and implement operations such as enqueue,\n" +
        "dequeueAny, dequeueDog, and dequeueCat. You may use the built-in Linked list data structure.\n");

        AnimalShelter aShelter = sq.new AnimalShelter();
        aShelter.enqueue(Animal.DOG);aShelter.enqueue(Animal.DOG);aShelter.enqueue(Animal.CAT);aShelter.enqueue(Animal.DOG);aShelter.enqueue(Animal.CAT);
        System.out.println(aShelter.dequeueCat());
        while (!aShelter.isEmpty()) {
            System.out.println(aShelter.dequeueAny());
        }
    }
}
