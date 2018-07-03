/*
    792. Number of Matching Subsequences

    Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.

    Example :
    Input:
    S = "abcde"
    words = ["a", "bb", "acd", "ace"]
    Output: 3
    Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
    Note:

    All words in words and S will only consists of lowercase letters.
    The length of S will be in the range of [1, 50000].
    The length of words will be in the range of [1, 5000].
    The length of words[i] will be in the range of [1, 50].

    Companies: Google

    Related Topics: Array

    Similar Questions: Is Subsequence
 */

// Solution1: HashMap key: words里的word， value: 相同word的count， 遍历map.keySet() 如果符合subsequence，直接加count
// O(words.length * S.length + ∑words[i].length)
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        int res = 0;
        HashMap<String, Integer> map = new HashMap();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        for (String word : map.keySet()) {
            if (isSubseq(S, word)) {
                res += map.get(word);
            }

        }
        return res;
    }

    private boolean isSubseq(String s, String sub) {
        int i = 0, j = 0;
        while (i < sub.length() && j < s.length()) {
            if (sub.charAt(i) == s.charAt(j++)) {
                i++;
            }
            if (i == sub.length())
                break;
        }
        return i == sub.length();
    }
}

/*
    Let’s go through the given example:

    S = "abcde"
    words = ["a", "bb", "acd", "ace"]
    I store that "a", "acd" and "ace" are waiting for an 'a' and "bb" is waiting for a 'b' (using parentheses to show how far I am in each word):
    'a':  ["(a)", "(a)cd", "(a)ce"]
    'b':  ["(b)b"]

    Then I go through S. First I see 'a', so I take the list of words waiting for 'a' and store them as waiting under their next letter:
    'b':  ["(b)b"]
    'c':  ["a(c)d", "a(c)e"]
    None: ["a"]

    You see "a" is already waiting for nothing anymore, while "acd" and "ace" are now waiting for 'c'. Next I see 'b' and update accordingly:
    'b':  ["b(b)"]
    'c':  ["a(c)d", "a(c)e"]
    None: ["a"]

    Then 'c':
    'b':  ["b(b)"]
    'd':  ["ac(d)"]
    'e':  ["ac(e)"]
    None: ["a"]

    Then 'd':
    'b':  ["b(b)"]
    'e':  ["ac(e)"]
    None: ["a", "acd"]

    Then 'e':
    'b':  ["b(b)"]
    None: ["a", "acd", "ace"]
    And now I just return how many words aren’t waiting for anything anymore.
 */
//Solution2:  O(S.length + ∑words[i].length)
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