import java.util.HashSet;
import java.util.Hashtable;
import java.util.*;

public class ArraysAndStrings {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        1.1 Is UNIQUE                                                 //
    //             Implement an algorithm to determine if a string has all unique characters.               //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     *  IS THE STRING IN ASCII OR UNICODE?
     *
     */
    public boolean isUnique(String s) {
        Hashtable<Character, Boolean> hashTable = new Hashtable<>();
        boolean isUnique = true;
        for (int i = 0; i < s.length(); i++ ) {
            if (hashTable.get(Character.valueOf(s.charAt(i))) == null ||
                hashTable.get(Character.valueOf(s.charAt(i))) == false) {
                hashTable.put(Character.valueOf(s.charAt(i)), true);
            } else {
                hashTable.put(Character.valueOf(s.charAt(i)), false);
                return false;
            }
        }
        return isUnique;
    }

    public boolean isUniqueSolution(String str) {
        //128 characters in ASCII, if there are more than 128 characters in a string, 1 must be a duplicate
        if (str.length() > 128) return false;

        boolean[] charSet = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int asciiCode = str.charAt(i); //returns ASCII code
            if (charSet[asciiCode]) {
                return false;
            } else {
                charSet[asciiCode] = true;
            }
        }
        return true;
    }

    // TIME COMPLEXITY? O(N)
    // SPACE COMPLEXITY? O(1)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        1.2 CHECK PERMUTATION                                         //
    //   Given two strings, write an algorithm to determine if one string is a permutation of the other.    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 1. ARE THE STRINGS ASCII (128 CHARS) OR UNICODE (U+10FFFF CHARS)? ASCII
     * 2. DO WHITE SPACES COUNT AS A CHARACTER? YES
     */
    public boolean isPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] charArray1 = s1.toCharArray();
        char[] charArray2 = s2.toCharArray();

        Arrays.sort(charArray1);
        Arrays.sort(charArray2);

        for (int i = 0; i < s1.length(); i++) {
            if (charArray1[i] != charArray2[i]) {
                return false;
            }
        }
        return true;
    }

    // TIME COMPLEXITY?
    // SPACE COMPLEXITY?

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        1.3 URLIFY                                                    //
    //   Write a method to replace all spaces in a string with '%20'. You may assume that the string has    //
    //   sufficient space at the end to hold the additional characters, and that you are given the 'true'   //
    //   length of the string. (Note: if implementing in Java, please use a character array so that you)    //
    //   can perform this operation in place)                                                               //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    public void urlify(char[] url) {
        for (int i = 0; i < url.length; i++) {
            if (url[i] == ' ') {
                for (int j = url.length - 1; j > i; j--) {
                    url[j] = url[j-2];
                }
                url[i++] = '%';
                url[i++] = '2';
                url[i++] = '0';
            }
        }
    }

    // TIME COMPLEXITY? O(N)
    // SPACE COMPLEXITY? O(1)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  1.4 PALINDROME PERMUTATION                                          //
    //  Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is   //
    //  a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of       //
    //  letters. The palindrome does not need to be limited to just dictionary words.                       //
    //  Input: 'TACT COA'     Output: 'TACO CAT', 'ATCO CTA', etc.                                          //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 1. ASCII Characters or unicode Characters?
     * 2. Are uppercase letters & their lowercase counterparts considered the same?
     * 3. Do spaces count as a character?
     */
    public boolean isPalindromePermutation(String str) {
        HashSet<Character> charSet = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            char letter = str.charAt(i);
            if (letter == ' ') continue; // If spaces don't count as a character
            if (!charSet.contains(letter)) {
                charSet.add(Character.valueOf(letter));
            } else {
                charSet.remove(Character.valueOf(letter));
            }
        }
        return (charSet.isEmpty() || charSet.size() == 1);
    }

    // TIME COMPLEXITY? O(N)
    // SPACE COMPLEXITY? O(N)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  1.5 ONE WAY                                                         //
    //  There are three types of edits that can be performed on strings: insert a character, remove a       //
    //  character, or replace a character. Given two strings, write a function to check if they are one     //
    //  edit (or zero edits away).                                                                          //
    //   Example:  pale, ple -> true                                                                        //
    //             pales, pale -> true                                                                      //
    //             pale, bale -> true                                                                       //
    //             pale, bake -> false                                                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isOneAway(String str1, String str2) {
        int[] charSet1 = new int[128];
        int[] charSet2 = new int[128];
        for (int i = 0; i < str1.length(); i++) {
            int asciiCode = str1.charAt(i);
            charSet1[asciiCode]++;
        }
        for (int i = 0; i < str2.length(); i++) {
            int asciiCode = str2.charAt(i);
            charSet2[asciiCode]++;
        }
        int differences = 0;
        for (int i = 0; i < charSet1.length; i++) {
            if (charSet1[i] != charSet2[i]) {
                differences++;
            }
        }
        return differences <= 2;
    }

    // Time complexity: O(n)
    // Space complexity: O(n)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  1.6 STRING COMPRESSION                                              //
    //      Implement a method to perform basic string compression using the counts of repeated characters  //
    //      For example, the string aabccccaaa would become a2b1c5a3. If the "compressed" string would not  //
    //      become smaller than the original string, your method should return the original string. You can //
    //      assume the string has only uppercase and lowercase letters (a-z)                                //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String stringCompression(String str) {
        StringBuilder newString = new StringBuilder(str.charAt(0));
        int charCount = 0;
        for (int i = 0; i < str.length(); i++) {
            charCount++;
            if (i == str.length()-1 || str.charAt(i) != str.charAt(i+1)) {
                newString.append(str.charAt(i)).append(charCount);
                charCount = 0;
            }
        }
        return str.length() != newString.length() ? newString.toString() : str;
    }

    // Time Complexity O(n)
    // Space Complexity O(n)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  1.7 ROTATE A MATRIX                                                 //
    //  Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, write a      //
    //  method to rotate the image by 90 degrees. Can you do this in place?                                 //
    //                                                                                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public char[][] rotateMatrix90Degrees(char[][] matrix) {
        int N = matrix.length;
        char[][] rotated = new char[N][N];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                rotated[(N-1) - col][row] = matrix[row][col];
            }
        }
        return rotated;
    }

    public boolean rotateMatrix90DegreesInPlace(char[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
        for (int layer = 0; layer < matrix.length/2; layer++) {
            int first = layer;
            int last = matrix.length-1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;

                // save top into temp
                char temp = matrix[first][i];

                // assign left to top
                matrix[first][i] = matrix[last-offset][first];

                //assign bottom to left
                matrix[last-offset][first] = matrix[last][last-offset];

                //assign right to bottom
                matrix[last][last-offset] = matrix[i][last];

                //assign temp to right
                matrix[i][last] = temp;
            }
        }
        return true;
    }

    public boolean rotateMatrix90DegreesClockWise(char[][] M) {
        if (M.length == 0 || M.length != M[0].length) return false;
        transposeMatrix(M); // flip matrix along diagonal
        reverseRowsInMatrix(M);
        return true;
    }

    public boolean rotateMatrix90DegreesCounterClockWise(char[][] M) {
        if (M.length == 0 || M.length != M[0].length) return false;
        transposeMatrix(M); // flip matrix along diagonal
        reverseColumnsInMatrix(M);
        return true;
    }

    private static void reverseRowsInMatrix(char[][] M) {
        int N = M.length;
        for(int row = 0; row < N; row++) {
            for (int col = 0; col < N/2; col++) {
                char temp = M[row][col];
                M[row][col] = M[row][N-1-col];
                M[row][N-1-col] = temp;
            }
        }
    }

    private static void reverseColumnsInMatrix(char[][] M) {
        int N = M.length;
        for (int col = 0; col < N; col++) {
            for (int row = 0; row < N/2; row++) {
                char temp = M[row][col];
                M[row][col] = M[N-1-row][col];
                M[N-1-row][col] = temp;
            }
        }
    }

    private static void transposeMatrix(char[][] M) {
        for (int row = 0; row < M.length; row++) {
            for (int col = 0; col <= row; col++) {
                char temp = M[row][col];
                M[row][col] = M[col][row];
                M[col][row] = temp;
            }
        }
    }

    private static void printMatrix(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                System.out.print(matrix[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char[][] createSampleMatrix(int size) {
        char[][] matrix = new char[size][size];
        char character = 'A';
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                matrix[row][col] = character++;
            }
        }
        return matrix;
    }

    // Time Complexity = O(N^2) Need to touch each element using CTCI Solution, however using the Transposition & Reverse,
    // we don't actually touch each element twice. I think it is O(N*N/2);
    // Space Complexity = O(1)

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  1.8 ZERO MATRIX                                                     //
    //  Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column are     //
    //  set to 0.                                                                                           //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void zeroMatrix(int[][] M) {
        if (M.length == 0) return;
        boolean[][] foundZero = new boolean[M.length][M[0].length];
        for (int row = 0; row < M.length; row++) {
            for (int col = 0; col < M[M.length-1].length; col++) {
                if (M[row][col] == 0) foundZero[row][col] = true;
            }
        }

        for (int row = 0; row < M.length; row++) {
            for (int col = 0; col < M[M.length-1].length; col++) {
                if (foundZero[row][col]) {
                    setRowAndColToZero(M, row, col);
                }
            }
        }
    }

    private static void setRowAndColToZero(int[][] M, int row, int col) {
        for (int i = 0; i < M.length; i++) {
            M[i][col] = 0;
        }
        for (int j = 0; j < M[M.length-1].length; j++) {
            M[row][j] = 0;
        }
    }

    public void zeroMatrix2(int[][] M) {
        if (M.length == 0) return;
        Set<Integer> rowSet = new HashSet<Integer>();
        Set<Integer> colSet = new HashSet<Integer>();
        for (int row = 0; row < M.length; row++) {
            for (int col = 0; col < M[M.length-1].length; col++) {
                if (M[row][col] == 0) {
                    rowSet.add(row);
                    colSet.add(col);
                }
            }
        }

        setRowAndColToZero(M, rowSet, colSet);
    }

    private static void setRowAndColToZero(int[][] M, Set rowSet, Set colSet) {
        for (Object row : rowSet.toArray()) {
            for (int j = 0; j < M[0].length; j++) {
                M[(int)row][j] = 0;
            }
        }

        for (Object col : colSet.toArray()) {
            for (int i = 0; i < M.length; i++) {
                M[i][(int)col] = 0;
            }
        }
    }

    private static int[][] createRandomZeroMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random rand = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                matrix[row][col] = rand.nextInt(10);
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println();
        ArraysAndStrings arraysAndStrings = new ArraysAndStrings();

        /**
         * 1.1 Implement an algorithm to determine if a string has all unique
         * characters.
         */
        System.out.println("1.1: Implement an algorithm to determine if a string has all unique characters.");
        String uniqueString1_1 = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(arraysAndStrings.isUnique(uniqueString1_1) + ": " + uniqueString1_1);

        System.out.println();
        System.out.println();

        /**
         * 1.2 Given two strings, write an algorithm to determine if one string is a
         * permutation of the other.
         */
        System.out.println( "1.2: Given two strings, write an algorithm to determine if one string is a permutation of the other.");
        String stringA_1_2 = "race car";
        String stringB_1_2 = "race car";
        System.out.println(arraysAndStrings.isPermutation(stringA_1_2, stringB_1_2) + ": '" + stringA_1_2 + "' '"
                + stringB_1_2 + "'");

        System.out.println();
        System.out.println();

        /**
         * Write a method to replace all spaces in a string with '%20'. You may assume
         * that the string has sufficient space at the end to hold the additional
         * characters, and that you are given the 'true' length of the string. (Note: if
         * implementing in Java, please use a character array so that you) can perform
         * this operation in place)
         */
        System.out.println(
                "1.3: Write a method to replace all spaces in a string with '%20'. You may assume that the string has \n"
                        + "sufficient space at the end to hold the additional characters, and that you are given the 'true' \n"
                        + "length of the string. (Note: if implementing in Java, please use a character array so that you) \n"
                        + "can perform this operation in place).");
        String urlString = "www.Does this work?.com    ";
        char[] urlCharArray = urlString.toCharArray();
        arraysAndStrings.urlify(urlCharArray);

        for (int i = 0; i < urlCharArray.length; i++) {
            System.out.print(urlCharArray[i]);
        }

        System.out.println();
        System.out.println();

        /**
         * Given a string, write a function to check if it is a permutation of a
         * palindrome. A palindrome is a word or phrase that is the same forwards and
         * backwards. A permutation is a rearrangement of letters. The palindrome does
         * not need to be limited to just dictionary words. Input: 'TACT COA' Output:
         * 'TACO CAT', 'ATCO CTA', etc.
         */
        System.out.println(
                "1.4: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is \n"
                        + "a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of \n"
                        + "letters. The palindrome does not need to be limited to just dictionary words. \n"
                        + "Input: 'TACT COA'     Output: 'TACO CAT', 'ATCO CTA', etc.");
        String palindromePermutation = "TACO CAT";
        System.out.println(
                arraysAndStrings.isPalindromePermutation(palindromePermutation) + ": " + palindromePermutation);

        System.out.println();
        System.out.println();

        System.out.println(
                "1.5: There are three types of edits that can be performed on strings: insert a character, remove a \n"
                        + "character, or replace a character. Given two strings, write a function to check if they are one\n"
                        + "edit (or zero edits away).\n"
                        + "Example:  pale, ple -> true                                                                   \n"
                        + "          pales, pale -> true                                                                 \n"
                        + "          pale, bale -> true                                                                  \n"
                        + "          pale, bake -> false                                                                 ");
        String oneAwayStr1 = "loupe";
        String oneAwayStr2 = "loup";
        System.out.println(
                arraysAndStrings.isOneAway(oneAwayStr1, oneAwayStr2) + ": " + oneAwayStr1 + " <-> " + oneAwayStr2);

        System.out.println();
        System.out.println();

        System.out.println(
                "1.6: Implement a method to perform basic string compression using the counts of repeated characters \n"
                        + "For example, the string aabccccaaa would become a2b1c5a3. If the 'compressed' string would not \n"
                        + "become smaller than the original string, your method should return the original string. You can\n"
                        + "assume the string has only uppercase and lowercase letters (a-z)");
        String unCompressedString = "aabcccccaaa";
        System.out.println(arraysAndStrings.stringCompression(unCompressedString) + ": " + unCompressedString);

        System.out.println();
        System.out.println();

        System.out.println(
                "1.7: Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, write a\n"
                        + "method to rotate the image by 90 degrees. Can you do this in place?\n Yes following answers are done in place.");
        char[][] rotateMatrixClockWise = createSampleMatrix(5);
        char[][] rotateMatrixCounterClockwise = createSampleMatrix(5);
        System.out.println("ORIGINAL");
        printMatrix(rotateMatrixClockWise);
        System.out.println("CLOCKWISE");
        arraysAndStrings.rotateMatrix90DegreesClockWise(rotateMatrixClockWise);
        printMatrix(rotateMatrixClockWise);

        System.out.println("COUNTER-CLOCKWISE");
        arraysAndStrings.rotateMatrix90DegreesCounterClockWise(rotateMatrixCounterClockwise);
        printMatrix(rotateMatrixCounterClockwise);

        System.out.println();
        System.out.println();

        System.out.println("1.8: Write an algorithm such that if an element in an MxN matrix is 0,\n" +
        "its entire row and column are set to 0.");
        int[][] zeroMatrix = createRandomZeroMatrix(4, 10);
        System.out.println("ORIGINAL");
        printMatrix(zeroMatrix);
        arraysAndStrings.zeroMatrix2(zeroMatrix);
        System.out.println("ZERO-MATRIX");
        printMatrix(zeroMatrix);
        System.out.println();
        System.out.println();

        System.out.println("1.8: Write an algorithm such that if an element in an MxN matrix is 0,\n" +
        "its entire row and column are set to 0.");

        System.out.println();
        System.out.println();
    }
}