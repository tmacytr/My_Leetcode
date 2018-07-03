/*
    541. Reverse String II

    Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and left the other as original.
    Example:
    Input: s = "abcdefg", k = 2
    Output: "bacdfeg"
    Restrictions:
    The string consists of lower English letters only.
    Length of the given string and k will in the range [1, 10000]

    Companies: Google

    Related Topics: String

    Similar Questions: Reverse String, Reverse Words in a String III
 */

//Solution1: my solution, 计算遍历过的count一旦符合条件就开始revers，比起reset count和start的位置
class Solution {
    public String reverseStr(String s, int k) {
        if (s == null || s.length() == 0 || k <= 1)
            return s;
        int len = s.length();
        int start = 0;
        int count = 0;
        char[] strs = s.toCharArray();
        for (int end = 0; end < len; end++) {
            count++;
            if (count == k) {
                reverse(strs, start, end);
                count = -k;
                start = end + k + 1;
            }
        }
        if (start < len) {
            reverse(strs, start, len - 1);
        }
        return String.valueOf(strs);
    }

    private void reverse(char[] strs, int start, int end) {
        while (start < end) {
            char temp = strs[start];
            strs[start++] = strs[end];
            strs[end--] = temp;
        }
    }
}

//Solution2: more concise, prefer
class Solution {
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();
        for (int i = 0; i < s.length(); i += 2 * k) {
            reverse(ch, i, i + k - 1);
        }
        return String.valueOf(ch);
    }

    private void reverse(char[] ch, int start, int end) {
        end = Math.min(end, ch.length - 1);
        while (start < end) {
            char temp = ch[start];
            ch[start++] = ch[end];
            ch[end--] = temp;
        }
    }
}