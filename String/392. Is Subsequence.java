/*
    392. Is Subsequence

    Given a string s and a string t, check if s is subsequence of t.

    You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).

    A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).

    Example 1:
    s = "abc", t = "ahbgdc"

    Return true.

    Example 2:
    s = "axc", t = "ahbgdc"

    Return false.

    Follow up:
    If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?

    Companies: Pinterest

    Related Topics: Binary Search, DP, Greedy

    Similar Questions: Number of Matching Subsequences
 */

//Solution1: two pointer, prefer
class Solution {
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j++)) {
                i++;
            }
            if (i == s.length())
                break;
        }
        return i == s.length();
    }
}


//Solution for follow up: 792.Number of Matching Subsequences
/*
    If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence.
    In this scenario, how would you change your code?

    Answer:
        Binary search:
            record the indexes for each character in t, if s[i] matches t[j], then s[i+1] should match a character in t with index bigger than j.
            This can be reduced to find the first element larger than a value in an sorted array (find upper bound), which can be achieved using binary search.

        Trie:
            For example, if s1 has been matched, s1[last char] matches t[j]. Now, s2 comes, if s1 is a prefix of s2, i.e., s1 == s2.substr[0, i-1], we can start match s2 from s2[i], right?
            So, the idea is to create a Trie for all string that have been matched so far. At a node, we record the position in t which matches this char represented by the node.
            Now, for an incoming string s, we first search the longest prefix in the Trie, find the matching position of the last prefix-char in t, say j. Then, we can start matching the first non-prefix-char of s from j+1.
            Now, if we have done the preprocessing as stated in the binary search approach, we can be even faster.
 */
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        int res = 0;
        List<Node>[] heads = new ArrayList[26];
        for (int i = 0; i < 26; i++)
            heads[i] = new ArrayList();
        for (String word : words)
            heads[word.charAt(0) - 'a'].add(new Node(word, 0));

        for (char c : S.toCharArray()) {
            List<Node> oldBucket = heads[c - 'a'];
            heads[c - 'a'] = new ArrayList();

            for (Node node : oldBucket) {
                node.index++;
                if (node.index == node.word.length()) {
                    res++;
                } else {
                    heads[node.word.charAt(node.index) - 'a'].add(node);
                }
            }
            oldBucket.clear();
        }
        return res;
    }
}

class Node {
    String word;
    int index;
    public Node(String s, int i) {
        word = s;
        index = i;
    }
}