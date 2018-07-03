/*
    796. Rotate String

    We are given two strings, A and B.

    A shift on A consists of taking string A and moving the leftmost character to the rightmost position. For example, if A = 'abcde', then it will be 'bcdea' after one shift on A. Return True if and only if A can become B after some number of shifts on A.

    Example 1:
    Input: A = 'abcde', B = 'cdeab'
    Output: true

    Example 2:
    Input: A = 'abcde', B = 'abced'
    Output: false
    Note:

    A and B will have length at most 100.
 */

//Solution1: brute force O(n^2) time
class Solution {
    public boolean rotateString(String A, String B) {
        if (A.isEmpty() && B.isEmpty())
            return true;
        if (A.isEmpty() || B.isEmpty() || A.length() != B.length())
            return false;
        int n = A.length();

        for (int shift = 0; shift < n; shift++) {
            for (int i = 0; i < n; i++) {
                if (A.charAt(i) != B.charAt((shift + i) % n))
                    break;
                if (i == n - 1)
                    return true;
            }
        }
        return false;
    }
}

//Solution2: string contains check, O(n^2) time
class Solution {
    public boolean rotateString(String A, String B) {
        return A.length() == B.length() && (A + A).contains(B);
    }
}

//Solution3: KMP O(n)
class Solution {
    public boolean rotateString(String A, String B) {
        if (A.length() != B.length()) return false;
        int[] prefix = buildPrefix(A);
        int i = 0, j = 0;
        while (i < A.length()) {
            while (j < B.length() && A.charAt(i % A.length()) == B.charAt(j)) {
                i++;
                j++;
            }
            if (j == B.length()) return true;
            if (j == 0) i++;
            else j = prefix[j-1];
        }
        return false;
    }

    private int[] buildPrefix(String s) {
        int[] prefix = new int[s.length()];
        int j = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = prefix[j-1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }
}