/*
    474. Ones and Zeroes

    In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.

    For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with strings consisting of only 0s and 1s.

    Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can be used at most once.

    Note:
    The given numbers of 0s and 1s will both not exceed 100
    The size of given string array won't exceed 600.
    Example 1:
    Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
    Output: 4

    Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
    Example 2:
    Input: Array = {"10", "0", "1"}, m = 1, n = 1
    Output: 2

    Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 */

/*
    define dp[i][j] stands for max number of str can we pick from strs with limitation of i "0"s and j "1"s.

    For each str, assume it has a "0"s and b "1"s, we update the dp array iteratively and set dp[i][j] = Math.max(dp[i][j], dp[i - a][j - b] + 1)

    why we iterate backward ?
    To better understand, DP[][] can be rewrite as DP[][][], e.g. DP[0][i][j] = 0 means when the string array is empty, we can not form any string.

    Then we put the first element of strs in our pool, and we can write DP[1][i][j] = Math.max(DP[0][i][j], 1+DP[0][i-c[0]][j-c[1]]); (for each i and j).

    Actually, we find that each time we add one more string k, DP[k][i][j] = Math.max(DP[k-1][i][j], 1+DP[k-1][i-c[0]][j-c[1]]).

    Since each i and j will not be influenced by higher i,j, and DP[k-1] will not be used in the future, we can iterate backward and update DP[i][j] in place.

    In the end, we return DP[m][n] which is actually DP[strs.length][m][n].
 */

// dp[i][j]表示有i个0和j个1时能组成的最多字符串的个数, 对于当前遍历到的字符串，我们统计出其中0和1的个数为zeros: count[0]和ones: count[1]，
// 然后dp[i - zeros][j - ones]表示当前的i和j减去zeros和ones之前能拼成字符串的个数，那么加上当前的zeros和ones就是当前dp[i][j]可以达到的个数，我们跟其原有数值对比取较大值即可
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int[] count = count(str); // count str里 0和1的数量
            for (int i = m; i >= count[0]; i--) {
                for (int j = n; j >= count[1]; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - count[0]][j - count[1]]  + 1);
                }
            }
        }
        return dp[m][n];
    }

    private int[] count(String s) {
        int[] res = new int[2];
        for (char c : s.toCharArray()) {
            res[c - '0']++;
        }
        return res;
    }
}