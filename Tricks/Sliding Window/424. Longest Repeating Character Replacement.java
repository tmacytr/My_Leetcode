/*
    424. Longest Repeating Character Replacement

    Given a string that consists of only uppercase English letters, you can replace any letter in the string with another letter at most k times. Find the length of a longest substring containing all repeating letters you can get after performing the above operations.

    Note:
    Both the string's length and k will not exceed 104.

    Example 1:

    Input:
    s = "ABAB", k = 2

    Output:
    4

    Explanation:
    Replace the two 'A's with two 'B's or vice versa.
    Example 2:

    Input:
    s = "AABABBA", k = 1

    Output:
    4

    Explanation:
    Replace the one 'A' in the middle with 'B' and form "AABBBBA".
    The substring "BBBB" has the longest repeating letters, which is 4.

    Companies: Pocket Gems

    Similar Questions: Longest Substring with At Most K Distinct Characters
 */

/*
    这道题跟之前那道Longest Substring with At Most K Distinct Characters很像，都需要用滑动窗口Sliding Window来解。

    我们首先来想，如果没有k的限制，让我们求把字符串变成只有一个字符重复的字符串需要的最小置换次数，那么就是字符串的总长度减去出现次数最多的字符的个数。

    如果加上k的限制，我们其实就是求满足(子字符串的长度减去出现次数最多的字符个数)<=k的最大子字符串长度即可，

    搞清了这一点，我们也就应该知道怎么用滑动窗口来解了吧我们用一个变量start记录滑动窗口左边界，初始化为0，然后我们遍历字符串，每次累加出现字符的个数，

    然后更新出现最多字符的个数，然后我们判断当前滑动窗口是否满足之前说的那个条件，如果不满足，我们就把滑动窗口左边界向右移动一个，并注意去掉的字符要在counts里减一，直到满足条件
 */

//Solution1:
class Solution {
    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLen = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) { // 也可以用if
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }
}