/*
    524. Longest Word in Dictionary through Deleting

    Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some characters of the given string. If there are more than one possible results, return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.

    Example 1:
    Input:
    s = "abpcplea", d = ["ale","apple","monkey","plea"]

    Output:
    "apple"
    Example 2:
    Input:
    s = "abpcplea", d = ["a","b","c"]

    Output:
    "a"
    Note:
    All the strings in the input will only contain lower-case letters.
    The size of the dictionary won't exceed 1,000.
    The length of all the strings in the input won't exceed 1,000.
 */

// 思路: 从dict里开始遍历，如果word的character全部在s里面出现 则满足条件再check是否最长deleted字符

// Solution1：
// Time complexity : O(n*x). One iteration over all strings is required. Here n refers to the number of strings in list dd and x refers to average string length.
class Solution {
    public String findLongestWord(String s, List<String> d) {
        String res = "";
        for (String word : d) {
            int i = 0;
            // check whether is word isSubsequence of s
            for (char c : s.toCharArray()) {
                if (i < word.length() && c == word.charAt(i))
                    i++;
            }
            // check whether is max result
            if (i == word.length() && word.length() >= res.length()) {
                if (word.length() > res.length() || word.compareTo(res) < 0)
                    res = word;
            }
        }
        return res;
    }
}