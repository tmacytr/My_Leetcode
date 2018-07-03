/*
    395. Longest Substring with At Least K Repeating Characters

    Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.

    Example 1:

    Input:
    s = "aaabb", k = 3

    Output:
    3

    The longest substring is "aaa", as 'a' is repeated 3 times.
    Example 2:

    Input:
    s = "ababbc", k = 2

    Output:
    5

    The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */

/*
    这里的sliding window 跟一般的解法不一样，用numUniqueTarget来限制当每个sliding window最多允许numUniqueTarget个不同字符时，可以得到longeset substring是多少。
    我们用一个numNoLessThanK来记录每个window中达到标准的字符数，只有当numNoLessThanK = numUnique = numUniqueTarget 时，才算是找到了一个解
 */
//Solution: sliding window, O(26 * n)
class Solution {
    public int longestSubstring(String s, int k) {
        int res = 0;
        for (int numUniqueTarget = 1; numUniqueTarget <= 26; numUniqueTarget++)
            res = Math.max(res, helper(s, k, numUniqueTarget));
        return res;
    }

    private int helper(String s, int k, int numUniqueTarget) {
        int[] count = new int[128];
        int start = 0, end = 0;
        int numUnique = 0, numNoLessThanK = 0;
        int res = 0;

        while (end < s.length()) {
            if (count[s.charAt(end)]++ == 0)
                numUnique++;
            if (count[s.charAt(end++)] == k)
                numNoLessThanK++;

            while (numUnique > numUniqueTarget) {
                if (count[s.charAt(start)]-- == k)
                    numNoLessThanK--;
                if (count[s.charAt(start++)] == 0)
                    numUnique--;
            }
            // if we found a string where the number of unique chars equals our target
            // and all those chars are repeated at least K times then update max
            if (numUnique == numUniqueTarget && numUnique == numNoLessThanK)
                res = Math.max(res, end - start);
        }
        return res;
    }
}