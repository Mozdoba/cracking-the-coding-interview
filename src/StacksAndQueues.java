import java.security.InvalidParameterException;
import java.util.EmptyStackException;

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
                    top = (StackNode) this;
                    stackMin = nodeMin = data;
                } else {
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
            stackMin = top.next.nodeMin;
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

    public static void main(String[] args) throws Exception {
        StacksAndQueues sq = new StacksAndQueues();
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
    }
}
