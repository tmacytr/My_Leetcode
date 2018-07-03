/*
    664. Strange Printer

    There is a strange printer with the following two special requirements:

    The printer can only print a sequence of the same character each time.
    At each turn, the printer can print new characters starting from and ending at any places, and will cover the original existing characters.
    Given a string consists of lower English letters only, your job is to count the minimum number of turns the printer needed in order to print it.

    Example 1:
    Input: "aaabbb"
    Output: 2
    Explanation: Print "aaa" first and then print "bbb".
    Example 2:
    Input: "aba"
    Output: 2
    Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.
    Hint: Length of the given string will not exceed 100.

    Companies: NetEase

    Related Topics: DP, DFS

    Similar Questions: Remove Boxes
 */

/*
    dp[i][j] represents the number we need to print the substring [i,j]
    Boundary condition:
    dp[i][i] = 1
    dp[i][i-1] = 0

    t(i, j) = 0, i > j => empty string
    t(i, j) = t(i, j - 1) + 1
    t(i, j) = min({t(i, k) + t(l + 1, j - 1)}, i <= k < j && S[j] == S[k]
}
 */
//Solution1: time O(n^3)
class Solution {
    public int strangePrinter(String s) {
        int n = s.length();
        if (n == 0)
            return 0;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++)
            dp[i][i] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                dp[j][j + i] = i + 1;
                for (int k = j + 1; k <= j + i; k++) {
                    int temp = dp[j][k - 1] + dp[k][j + i];
                    if (s.charAt(k - 1) == s.charAt(j + i))
                        temp--;
                    dp[j][j + i] = Math.min(dp[j][j + i], temp);
                }
            }
        }
        return dp[0][n - 1];
    }
}

//Solution2: Memorization recursion, same as 546. remove box
class Solution {
    public int strangePrinter(String s) {
        int n = s.length();
        int[][][] dp = new int[n][n][n];
        return dfs(s, 0, n - 1, 0, dp);
    }

    private int dfs(String s, int l, int r, int k, int[][][] dp) {
        if (l > r) return 0;
        if (dp[l][r][k] != 0)
            return dp[l][r][k];
        while (r > l && s.charAt(l) == s.charAt(l + 1)) {
            l++;
            k++;
        }
        dp[l][r][k] = dfs(s, l, r - 1, 0, dp) + 1;
        for (int i = l; i <= r - 1; i++) {
            if (s.charAt(i) == s.charAt(r)) {
                dp[l][r][k] = Math.min(dp[l][r][k], dfs(s, l, i, k + 1, dp) + dfs(s, i + 1, r - 1, 0, dp));
            }
        }
        return dp[l][r][k];
    }
}
