/*
    616. Add Bold Tag in String

    Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.
    Example 1:
    Input:
    s = "abcxyz123"
    dict = ["abc","123"]
    Output:
    "<b>abc</b>xyz<b>123</b>"
    Example 2:
    Input:
    s = "aaabbcc"
    dict = ["aaa","aab","bc"]
    Output:
    "<b>aaabbc</b>c"
    Note:
    The given dict won't contain duplicates, and its length won't exceed 100.
    All the strings in input have length in range [1, 1000].
 */

// Solution: Brute force
// 用一个boolean数组表示字符串的第i个字符是否可以startsWith一个dict里的word，如果可以就意味这个至少可以cover i + word.length的长度
// 随后只要是相邻的字符串都是true 则可以include在同一个bold括号里面
class Solution {
    public String addBoldTag(String s, String[] dict) {
        if (s == null || s.length() == 0)
            return "";
        int end = 0;
        int len = s.length();
        boolean[] isBold = new boolean[len];
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            for (String word : dict) {
                if (s.startsWith(word, i)) {
                    end = Math.max(end, i + word.length());
                }
            }
            isBold[i] = end > i;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (!isBold[i]) {
                sb.append(s.charAt(i));
                continue;
            }
            int j = i;
            while (j < len && isBold[j]) {
                j++;
            }
            sb.append("<b>").append(s.substring(i, j)).append("</b>");
            i = j - 1;
        }
        return sb.toString();
    }
}