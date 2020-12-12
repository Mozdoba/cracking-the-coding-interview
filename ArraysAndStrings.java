import java.util.HashSet;
import java.util.Hashtable;
import java.lang.reflect.Array;
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
            if (letter == ' ') continue; //If spaces don't count as a character
            if (!charSet.contains(letter)) {
                charSet.add(Character.valueOf(letter));
            } else {
                charSet.remove(Character.valueOf(letter));
            }
        }
        return (charSet.isEmpty() || charSet.size() == 1);
    }

    // TIME COMPLEXITY? O(N)
    // SPACE COMPLEXITY? O(1)

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

    public static void main(String[] args) {
        System.out.println();
        ArraysAndStrings arraysAndStrings = new ArraysAndStrings();

        /**
         * 1.1 Implement an algorithm to determine if a string has all unique characters.
         */
        System.out.println("1.1: Implement an algorithm to determine if a string has all unique characters.");
        String uniqueString1_1 = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(arraysAndStrings.isUnique(uniqueString1_1) + ": " + uniqueString1_1);

        System.out.println();
        System.out.println();

        /**
         * 1.2 Given two strings, write an algorithm to determine if one string is a permutation of the other.
         */
        System.out.println("1.2: Given two strings, write an algorithm to determine if one string is a permutation of the other.");
        String stringA_1_2 = "race car";
        String stringB_1_2 = "race car";
        System.out.println(arraysAndStrings.isPermutation(stringA_1_2, stringB_1_2) + ": '" + stringA_1_2 + "' '" + stringB_1_2 + "'");

        System.out.println();
        System.out.println();

        /**
         * Write a method to replace all spaces in a string with '%20'. You may assume that the string has
         * sufficient space at the end to hold the additional characters, and that you are given the 'true'
         * length of the string. (Note: if implementing in Java, please use a character array so that you)
         * can perform this operation in place)
         */
        System.out.println("1.3: Write a method to replace all spaces in a string with '%20'. You may assume that the string has \n" +
                            "sufficient space at the end to hold the additional characters, and that you are given the 'true' \n" +
                            "length of the string. (Note: if implementing in Java, please use a character array so that you) \n" +
                            "can perform this operation in place).");
        String urlString = "www.Does this work?.com    ";
        char[] urlCharArray = urlString.toCharArray();
        arraysAndStrings.urlify(urlCharArray);

        for(int i = 0; i < urlCharArray.length; i++) {
            System.out.print(urlCharArray[i]);
        }

        System.out.println();
        System.out.println();

        /**
         * Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is
         * a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of
         * letters. The palindrome does not need to be limited to just dictionary words.
         * Input: 'TACT COA'     Output: 'TACO CAT', 'ATCO CTA', etc.
         */
        System.out.println("1.4: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is \n" +
                                "a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of \n" +
                                "letters. The palindrome does not need to be limited to just dictionary words. \n" +
                                "Input: 'TACT COA'     Output: 'TACO CAT', 'ATCO CTA', etc.");
        String palindromePermutation = "TACO CAT";
        System.out.println(arraysAndStrings.isPalindromePermutation(palindromePermutation) + ": " + palindromePermutation);

        System.out.println();
        System.out.println();

        System.out.println("1.5: There are three types of edits that can be performed on strings: insert a character, remove a \n" +
        "character, or replace a character. Given two strings, write a function to check if they are one\n" +
        "edit (or zero edits away).\n" +
        "Example:  pale, ple -> true                                                                   \n" +
        "          pales, pale -> true                                                                 \n" +
        "          pale, bale -> true                                                                  \n" +
        "          pale, bake -> false                                                                 ");
        String oneAwayStr1 = "loupe";
        String oneAwayStr2 = "loup";
        System.out.println(arraysAndStrings.isOneAway(oneAwayStr1, oneAwayStr2) + ": " + oneAwayStr1 + " <-> " + oneAwayStr2);

        System.out.println();
        System.out.println();
    }
}