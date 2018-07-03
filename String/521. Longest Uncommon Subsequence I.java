/*
    521. Longest Uncommon Subsequence I

    Given a group of two strings, you need to find the longest uncommon subsequence of this group of two strings. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

    A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

    The input will be two strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

    Example 1:
    Input: "aba", "cdc"
    Output: 3
    Explanation: The longest uncommon subsequence is "aba" (or "cdc"),
    because "aba" is a subsequence of "aba",
    but not a subsequence of any other strings in the group of two strings.
    Note:

    Both strings' lengths will not exceed 100.
    Only letters from a ~ z will appear in input strings.

    Companies: Google

    Related Topics: String

    Similar Questions
        Longest Uncommon Subsequence II
 */

/*
    1. First considered the special case, two strings are not of same length, it is clearly the longest one is the answer.
    2. Then for two strings with same length, if anyone of them has a char which the other string does not have, clearly the whole string’s length is the answer.
    3. Then for two strings with same charset, one string is just a combination of chars of another string, I thought about the ordering of chars matters here.
       If they are not equal, we can quickly decide, which also covers case 2
 */
class Solution {
    public int findLUSlength(String a, String b) {
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }
}