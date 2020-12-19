import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class StacksAndQueues {

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
        int capacity = 3;
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
         *
         * @param index the contiguous index to pop at.
         * @return an integer at the specified contiguous index
         */
        public int popAt(int index) {
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
        System.out.println("\nPopping at index 1:");
        System.out.println(setOfStacks.popAt(1));
        System.out.println("Popping:");
        for (int i = 0; i < 11; i++) {
            System.out.print(setOfStacks.pop() + " ");
        }
        System.out.println();
    }
}
