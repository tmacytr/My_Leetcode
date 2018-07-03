/*
    484. Find Permutation
    By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a decreasing relationship between two numbers, 'I' represents an increasing relationship between two numbers. And our secret signature was constructed by a special integer array, which contains uniquely all the different number from 1 to n (n is the length of the secret signature plus 1). For example, the secret signature "DI" can be constructed by array [2,1,3] or [3,1,2], but won't be constructed by array [3,2,4] or [2,1,3,4], which are both illegal constructing special string that can't represent the "DI" secret signature.

    On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2, ... n] could refer to the given secret signature in the input.

    Example 1:
    Input: "I"
    Output: [1,2]
    Explanation: [1,2] is the only legal initial spectial string can construct secret signature "I", where the number 1 and 2 construct an increasing relationship.
    Example 2:
    Input: "DI"
    Output: [2,1,3]
    Explanation: Both [2,1,3] and [3,1,2] can construct the secret signature "DI",
    but since we want to find the one with the smallest lexicographical permutation, you need to output [2,1,3]
    Note:

    The input string will only contain the character 'D' and 'I'.
    The length of input string is a positive integer and will not exceed 10,000
 */

// Solution: reverse subarray, 思路，如果从index i开始遇到连续的n个D就要reverse (i ~ i + n)之间的数字。遇到I不用做任何事
class Solution {
    public int[] findPermutation(String s) {
        int len = s.length();
        int[] res = new int[len + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        int count = 0;
        for (int i = 0; i <= len; i++) {
            if (i < len && s.charAt(i) == 'D') {
                count++;
            } else if (count > 0) {
                reverse(res, i - count, i);
                count = 0;
            }
        }

        return res;
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }
}

// Solution2: without reverse, greedy
class Solution {
    public int[] findPermutation(String s) {
        final int[] res = IntStream.rangeClosed(1, s.length() + 1).toArray();
        for (int i = 0, len = s.length(); i < len;) {
            int j = i;
            while (j < len && s.charAt(j) == 'D') {
                j++;
            }
            for (int k = j - i; k >= 0; k--, j--) {
                res[i++] = j + 1;
            }
        }
        return res;
    }
}