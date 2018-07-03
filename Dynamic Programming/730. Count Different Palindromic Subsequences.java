/*
    730. Count Different Palindromic Subsequences

    Given a string S, find the number of different non-empty palindromic subsequences in S, and return that number modulo 10^9 + 7.

    A subsequence of a string S is obtained by deleting 0 or more characters from S.

    A sequence is palindromic if it is equal to the sequence reversed.

    Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i != B_i.

    Example 1:
    Input:
    S = 'bccb'
    Output: 6
    Explanation:
    The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
    Note that 'bcb' is counted only once, even though it occurs twice.
    Example 2:
    Input:
    S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
    Output: 104860361
    Explanation:
    There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 10^9 + 7.
    Note:

    The length of S will be in the range [1, 1000].
    Each character S[i] will be in the set {'a', 'b', 'c', 'd'}.

    Companies: Linkedin

    Related Topics: String, DP

    Similar Questions: Longest Palindromic Subsequence
 */

/*
    https://www.youtube.com/watch?v=UjiFFYU3EKM 非常好的讲解

    dp[i][j]: s.substring(i, j + 1), 这个子字符串中有多少个palindrome subsequence

    1. 当s.charAt(i) == s.charAt(j)时
        有三种情况：
        1.1 i ~ j之间没有和s.charAt(i) 重复的字符
            count("bccb") = 2 * count("cc") + 2 = 2 * 2 + 2 = 6 => "c", "cc", "bcb", "bccb", "b", "bb"
            count("cc") = 2 * count("") + 2 = 2 => "c", "cc"
        1.2 i ~ j之间有一个和s.charAt(i)重复的字符
            count("bcbcb") = 2 * count("cbc") + 1 => 重复的是"b"
        1.3 i ~ j之间有两个或者以上和s.charAt(i) 重复的字符
            count("bbcabb") = 2 * count("bcab") - count("ca") => 重复的是"bcb", "bab"


    2. 当s.charAt(i) ！= s.charAt(j)时: dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
        也就是s.substring(i, j)的subsequence + s.substring(i + 1, j + 1)的subsequence 减去他们重叠的部分
        比如：
            count("abccb") = count("abcc") + count("bccb") - count("bcc") = 4 + 6 - 3 = 7
            count("abcc") = count("abc") + count("bcc") - count("bc")
 */

class Solution {
    public int countPalindromicSubsequences(String S) {
        int len = S.length();
        int[][] dp = new int[len][len];
        char[] chs = S.toCharArray();

        for (int i = 0; i < len; i++) {
            dp[i][i] = 1; // Consider the test case "a", "b", "c"
        }

        for (int distance = 1; distance < len; distance++) {
            for (int i = 0; i < len - distance; i++) {
                int j = i + distance;
                if (chs[i] == chs[j]) {
                    int start = i + 1;
                    int end = j - 1;

                    /* Variable start and end here are used to get rid of the duplicate*/
                    while (start <= end && chs[start] != chs[j])
                        start++;
                    while (start <= end && chs[end] != chs[j])
                        end--;

                    if (start > end) {
                        // case 1.1
                        dp[i][j] = dp[i + 1][j - 1] * 2 + 2;
                    } else if (start == end) {
                        // case 1.2
                        dp[i][j] = dp[i + 1][j - 1] * 2 + 1;
                    } else {
                        // case 1.3
                        dp[i][j] = dp[i + 1][j - 1] * 2 - dp[start + 1][end - 1];
                    }
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
                }
                dp[i][j] = dp[i][j] < 0 ? dp[i][j] + 1000000007 : dp[i][j] % 1000000007;
            }
        }
        return dp[0][len - 1];
    }
}