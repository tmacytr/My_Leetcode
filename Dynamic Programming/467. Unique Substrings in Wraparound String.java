/*
    467. Unique Substrings in Wraparound String

    Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s will look like this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".

    Now we have another string p. Your job is to find out how many unique non-empty substrings of p are present in s. In particular, your input is the string p and you need to output the number of different non-empty substrings of p in the string s.

    Note: p consists of only lowercase English letters and the size of p might be over 10000.

    Example 1:
    Input: "a"
    Output: 1

    Explanation: Only the substring "a" of string "a" is in the string s.
    Example 2:
    Input: "cac"
    Output: 2
    Explanation: There are two substrings "a", "c" of string "cac" in the string s.
    Example 3:
    Input: "zab"
    Output: 6
    Explanation: There are six substrings "z", "a", "b", "za", "ab", "zab" of string "zab" in the string s.

    Companies: MAQ Software

    Related Topics: DP
 */

/*
    举例子abcd这个字符串，以d结尾的子字符串有abcd, bcd, cd, d，那么我们可以发现bcd或者cd这些以d结尾的字符串的子字符串都包含在abcd中，

    那么我们知道以某个字符结束的最大字符串包含其他以该字符结束的字符串的所有子字符串，

    那么题目就可以转换为分别求出以每个字符(a-z)为结束字符的最长连续字符串就行了，我们用一个数组count记录下来，最后在求出数组count的所有数字之和就是我们要的结果
 */

//Solution: caculate the max length of unique substrings in p ends with 'a', 'b', ..., 'z'"
class Solution {
    public int findSubstringInWraproundString(String p) {
        int[] count = new int[26];
        int maxLenCur = 0;
        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
                maxLenCur++;
            } else {
                maxLenCur = 1;
            }
            int index = p.charAt(i) - 'a';
            count[index] = Math.max(count[index], maxLenCur);
        }

        int res = 0;
        for (int i = 0; i < 26; i++)
            res += count[i];
        return res;
    }
}