/*
    516. Longest Palindromic Subsequence

    Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

    Example 1:
    Input:

    "bbbab"
    Output:
    4
    One possible longest palindromic subsequence is "bbbb".
    Example 2:
    Input:

    "cbbd"
    Output:
    2
    One possible longest palindromic subsequence is "bb".

    Companies: Amazon

    Related Topics: Uber

    Similar Questions:
        1. Longest Palindromic Substring
        2. Palindromic Substrings
        3. Count Different Palindromic Subsequences
 */

/*
    dp[i][j]: the longest palindromic subsequence's length of substring(i, j+1) // here, the 'end' is exclusive in String.substring(int start, int end);
    State transition:
        1. Initialization: dp[i][i] = 1
        2. dp[i][j] = dp[i+1][j-1] + 2 if s.charAt(i) == s.charAt(j)
        3. otherwise, dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])

 */
//Solution1: O(n * n) space
class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }
}

//Solution2: O(n) space
class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[] dp = new int[n];
        int pre = 0, cur = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            pre = 0;
            for (int j = i + 1; j < n; j++) {
                cur = dp[j];
                if (s.charAt(i) == s.charAt(j)) {
                    dp[j] = pre + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                pre = cur;
            }
        }
        return dp[n - 1];
    }
}