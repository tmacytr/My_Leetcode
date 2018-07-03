/*
    648. Replace Words

    In English, we have a concept called root, which can be followed by some other words to form another longer word - let's call this word successor. For example, the root an, followed by other, which can form another word another.

    Now, given a dictionary consisting of many roots and a sentence. You need to replace all the successor in the sentence with the root forming it. If a successor has many roots can form it, replace it with the root with the shortest length.

    You need to output the sentence after the replacement.

    Example 1:
    Input: dict = ["cat", "bat", "rat"]
    sentence = "the cattle was rattled by the battery"
    Output: "the cat was rat by the bat"
    Note:
    The input will only have lower-case letters.
    1 <= dict words number <= 1000
    1 <= sentence words number <= 1000
    1 <= root length <= 100
    1 <= sentence words length <= 1000

    Companies: Uber

    Related Topics: Hashtable, Trie

    Similar Questions: Implement Trie (Prefix Tree)
 */

//Solution1: hashset, O(n * m)
class Solution {
    public String replaceWords(List<String> dict, String sentence) {
        String[] words = sentence.split(" ");
        Set<String> set = new HashSet();
        set.addAll(dict);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            for (int i = 1; i <= word.length(); i++) {
                String root = word.substring(0, i);
                if (set.contains(root)) {
                    sb.append(root).append(" ");
                    break;
                }
                if (i == word.length()) {
                    sb.append(word).append(" ");
                }
            }
        }
        return sb.toString().trim();
    }
}

//Solution2: my trie solution
class Solution {
    TrieNode root;
    public String replaceWords(List<String> dict, String sentence) {
        String[] words = sentence.split(" ");
        root = new TrieNode();
        for (String word : dict) {
            insert(word);
        }

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            String prefix = startsWith(word);
            if (!prefix.isEmpty()) {
                sb.append(prefix).append(" ");
            } else {
                sb.append(word).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private String startsWith(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            if (cur.children[c - 'a'] == null) {
                return "";
            } else if (!cur.children[c - 'a'].word.isEmpty()) {
                return cur.children[c - 'a'].word;
            } else {
                cur = cur.children[c - 'a'];
            }
        }
        return "";
    }

    private void insert(String s) {
        TrieNode cur = root;
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a'];
            sb.append(c);
        }
        cur.word = sb.toString();
    }
}

class TrieNode {
    TrieNode[] children;
    String word;
    public TrieNode() {
        children = new TrieNode[26];
        word = "";
    }
}