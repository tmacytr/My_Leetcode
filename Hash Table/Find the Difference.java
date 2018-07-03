/*
    389. Find the Difference

    Given two strings s and t which consist of only lowercase letters.

    String t is generated by random shuffling string s and then add one more letter at a random position.

    Find the letter that was added in t.

    Example:

    Input:
    s = "abcd"
    t = "abcde"

    Output:
    e

    Explanation:
    'e' is the letter that was added.
 */

// Solution1:
class Solution {
    public char findTheDifference(String s, String t) {
        int[] dict = new int[26];

        for (char c : s.toCharArray()) {
            dict[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            dict[c - 'a']--;
        }

        char res = 'a';
        for (int i = 0; i < dict.length; i++) {
            if (dict[i] < 0)
                res += i;
        }
        return res;
    }
}

// Solution2: bits
class Solution {
    public char findTheDifference(String s, String t) {
        int i = 0;
        int j = 0;
        int res = 0;
        while (i < t.length()) {
            if (i < s.length())
                res ^= s.charAt(i) - 'a';
            if (i < t.length())
                res ^= t.charAt(i) - 'a';
            i++;
        }
        return (char) (res + 'a');
    }
}