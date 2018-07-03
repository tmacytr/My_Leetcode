/*
	425. Word Squares

	Given a set of words (without duplicates), find all word squares you can build from them.

	A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 ≤ k < max(numRows, numColumns).

	For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.

	b a l l
	a r e a
	l e a d
	l a d y
	Note:
	There are at least 1 and at most 1000 words.
	All words will have the exact same length.
	Word length is at least 1 and at most 5.
	Each word contains only lowercase English alphabet a-z.
	Example 1:

	Input:
	["area","lead","wall","lady","ball"]

	Output:
	[
	  [ "wall",
	    "area",
	    "lead",
	    "lady"
	  ],
	  [ "ball",
	    "area",
	    "lead",
	    "lady"
	  ]
	]

	Explanation:
	The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
	Example 2:

	Input:
	["abat","baba","atan","atal"]

	Output:
	[
	  [ "baba",
	    "abat",
	    "baba",
	    "atan"
	  ],
	  [ "baba",
	    "abat",
	    "baba",
	    "atal"
	  ]
	]

	Explanation:
	The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
*/


// Explain: https://leetcode.com/problems/word-squares/discuss/91333/Explained.-My-Java-solution-using-Trie-126ms-1616
// Solution: 
class Solution {
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> res = new ArrayList();
        if (words == null || words.length == 0)
            return res;
        int len = words[0].length();
        Trie trie = new Trie(words);
        
        List<String> item = new ArrayList();
        for (String word : words) {
            item.add(word);
            search(len, trie, res, item);
            item.remove(item.size() - 1);
        }
        return res;
    }
    
    private void search(int len, Trie trie, List<List<String>> res, List<String> item) {
        if (item.size() == len) {
            res.add(new ArrayList(item));
            return;
        }
        int index = item.size();
        StringBuilder sb = new StringBuilder();
        for (String s : item)
            sb.append(s.charAt(index));
        List<String> words = trie.findByPrefix(sb.toString());
        for (String word : words) {
            item.add(word);
            search(len, trie, res, item);
            item.remove(item.size() - 1);
        }
    }
    
    class TrieNode {
    	 // The only difference between the trie here and the normal trie is that:
    	 // we hold one more list of all the words which have the prefix (from the root char to the current node char
        List<String> startsWith;
        TrieNode[] children;
        
        TrieNode() {
            startsWith = new ArrayList();
            children = new TrieNode[26];
        }
    }
    
    class Trie {
        TrieNode root;
        public Trie (String[] words) {
            root = new TrieNode();
            for (String word : words) {
                TrieNode cur = root;
                for (char c : word.toCharArray()) {
                    int index = c - 'a';
                    if (cur.children[index] == null)
                        cur.children[index] = new TrieNode();
                    cur.children[index].startsWith.add(word);
                    cur = cur.children[index];
                }
            }
        }
        
        List<String> findByPrefix(String prefix) {
            List<String> res = new ArrayList();
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                int index = c - 'a';
                if (cur.children[index] == null)
                    return res;
                cur = cur.children[index];
            }
            res.addAll(cur.startsWith);
            return res;
        }
    }
}