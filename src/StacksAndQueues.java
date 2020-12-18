import java.security.InvalidParameterException;
import java.util.EmptyStackException;

public class StacksAndQueues {

    public class MyStack<T> {
        private MyStackNode<T> top;
        private int size = 0;

        public MyStack() {}

        private class MyStackNode<T> {
            T data;
            MyStackNode<T> next;
            MyStackNode(T data) {
                this.data = data;
                this.next = null;
            }
        }

        public void push(T data) {
            MyStackNode<T> newTop = new MyStackNode<>(data);
            newTop.next = top;
            top = newTop;
            size++;
        }

        public T pop() throws Exception {
            if (isEmpty()) throw new EmptyStackException();
            T data = top.data;
            top = top.next;
            size--;

            return data;
        }

        public T peek() throws Exception {
            if (isEmpty()) throw new EmptyStackException();
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public int size() {
            return size;
        }
    }
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
                return arr[index1Top];
            } else if (stack == 2) {
                return arr[index2Top];
            } else {
                return arr[index3Top];
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        3.2 STACK MINIMUM                                             //
    //      How would you design a stack which, in addition to push and pop, has a function min which       //
    //      returns the minimum element? Push, pop and min should all operate in 0(1) time.                 //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        FixedSizeThreeStack threeStack = sq.new FixedSizeThreeStack(3);
        threeStack.push(1, 1);
        threeStack.push(1, 2);
        threeStack.push(1, 3);
        threeStack.push(2, 4);
        threeStack.push(2, 5);
        threeStack.push(2, 6);
        threeStack.push(3, 7);
        threeStack.push(3, 8);
        threeStack.push(3, 9);

        threeStack.pop(1);
        threeStack.pop(1);
        threeStack.pop(1);
        threeStack.pop(2);
        threeStack.pop(2);
        threeStack.pop(2);
        threeStack.pop(3);
        threeStack.pop(3);
        threeStack.pop(3);
    }
}
