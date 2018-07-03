/*
	Reverse Vowels of a String
	Write a function that takes a string as input and reverse only the vowels of a string.

	Example 1:
	Given s = "hello", return "holle".

	Example 2:
	Given s = "leetcode", return "leotcede".
 */
/*
	key point:
	(1)String vowels = "aeiouAEIOU";
	(2)char to String: Char + "";
	(3)use String.contains(String);
 */

// Solution1: Use String.contains(char + ""), the String.contains is getting char sequence as param, so we have to conver to String type
public class Solution {
    public String reverseVowels(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String vowels = "aeiouAEIOU";
        char[] words = s.toCharArray();
        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            char c1 = words[start];
            char c2 = words[end];
            if (vowels.contains(c1 + "") && vowels.contains(c2 + "")) {
                char temp = c1;
                words[start] = c2;
                words[end] = temp;
                start++;
                end--;
            } else if (vowels.contains(c1 + "")) {
                end--;
            } else if (vowels.contains(c2 + "")) {
                start++;
            } else {
                start++;
                end--;
            }
        }
        return new String(words);
    }
}

// Solution2: Prefer Use statically declared String as the dictionary and use the indexOf function to avoid String comparison
class Solution {
    static final String vowels = "aeiouAEIOU";
    public String reverseVowels(String s) {
        if (s == null || s.length() == 0)
            return "";
        int left = 0;
        int right = s.length() - 1;
        char[] arr = s.toCharArray();
        while (left < right) {
            if (vowels.indexOf(arr[left]) != -1 && vowels.indexOf(arr[right]) != -1) {
                swap(arr, left++, right--);
            } else if (vowels.indexOf(arr[left]) != -1) {
                right--;
            } else if (vowels.indexOf(arr[right]) != -1) {
                left++;
            } else {
                left++;
                right--;
            }
        }
        return new String(arr);
    }

    public void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

// Solution3: Prefer more efficient way
class Solution {
    static final String vowels = "aeiouAEIOU";
    public String reverseVowels(String s) {
        if (s == null || s.length() == 0)
            return "";
        int left = 0;
        int right = s.length() - 1;
        char[] arr = s.toCharArray();
        while (left < right) {
            while (left < right && vowels.indexOf(arr[left]) == -1) {
                left++;
            }
            while (left < right && vowels.indexOf(arr[right]) == -1) {
                right--;
            }
            swap(arr, left++, right--);

        }
        return new String(arr);
    }

    public void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}