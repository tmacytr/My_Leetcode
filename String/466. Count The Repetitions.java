/*
    466. Count The Repetitions

    Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] ="abcabcabc".

    On the other hand, we define that string s1 can be obtained from string s2 if we can remove some characters from s2 such that it becomes s1. For example, “abc” can be obtained from “abdbec” based on our definition, but it can not be obtained from “acbbe”.

    You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 0 ≤ n1 ≤ 106 and 1 ≤ n2 ≤ 106. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. Find the maximum integer M such that [S2,M] can be obtained from S1.

    Example:

    Input:
    s1="acb", n1=4
    s2="ab", n2=2

    Return:
    2

    Tags: DP
 */

//Solution1: Brute force， 给定俩字符s1和s2, 分别能重复n1和n2次， 问 s2 * n2 能在  s1 * n1中出现几次， 我们可以转化为求s2在s1 * n1中出现几次， 等到count之后再 count / n2即为所求
class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int index = 0;
        int repeatCount = 0;
        int m = s1.length(), n = s2.length();
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < m; j++) {
                if (s1.charAt(j) == s2.charAt(index)) {
                    index++;
                }
                if (index == n) {
                    index = 0;
                    ++repeatCount;
                }
            }
        }
        return repeatCount / n2;
    }
}

// https://leetcode.com/problems/count-the-repetitions/discuss/95398/C++-solution-inspired-by-@70664914-with-organized-explanation
//Solution2:
class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int len1 = s1.length(), len2 = s2.length();
        int[] repeatCount = new int[len2 + 1];
        int[] nextIndex = new int[len2 + 1];

        int j = 0, count = 0;

        for (int k = 1; k <= n1; k++) {
            for (int i = 0; i < len1; i++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j++;
                    if (j == len2) {
                        j = 0;
                        count++;
                    }
                }
            }
            repeatCount[k] = count; // record the k-th repeatCount and nextIndex
            nextIndex[k] = j;

            for (int start = 0; start < k; start++) {
                if (nextIndex[start] == j) { // see if you have met this nextIndex before
                    int prefixCount = repeatCount[start]; // prefixCount is the start-th repeatCount
                    // (repeatCount[k] - repeatCount[start]) is the repeatCount of one occurrance of the pattern
                    // There are (n1 - start) / (k - start) occurrances of the pattern
                    // So (repeatCount[k] - repeatCount[start]) * (n1 - start) / (k - start) is the repeatCount of the repetitive part
                    int patternCount = (n1 - start) / (k - start) * (repeatCount[k] - repeatCount[start]);
                    // The suffix contains the incomplete repetitive remnant (if any)
                    // Its length is (n1 - start) % (k - start)
                    // So the suffix repeatCount should be repeatCount[start + (n1 - start) % (k - start)] - repeatCount[start]
                    int suffixCount = repeatCount[start + (n1 - start) % (k - start)] - repeatCount[start];
                    return (prefixCount + patternCount + suffixCount) / n2;
                }
            }
        }
        return repeatCount[n1] / n2;
    }
}