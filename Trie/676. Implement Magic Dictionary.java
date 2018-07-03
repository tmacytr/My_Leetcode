/*
    676. Implement Magic Dictionary

    Implement a magic directory with buildDict, and search methods.

    For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary.

    For the method search, you'll be given a word, and judge whether if you modify exactly one character into another character in this word, the modified word is in the dictionary you just built.

    Example 1:
    Input: buildDict(["hello", "leetcode"]), Output: Null
    Input: search("hello"), Output: False
    Input: search("hhllo"), Output: True
    Input: search("hell"), Output: False
    Input: search("leetcoded"), Output: False

    Note:
    You may assume that all the inputs are consist of lowercase letters a-z.
    For contest purpose, the test data is rather small by now. You could think about highly efficient algorithm after the contest.
    Please remember to RESET your class variables declared in class MagicDictionary, as static/class variables are persisted across multiple test cases. Please see here for more details.

    Companies: Google
    Similar Questions: Implement Trie (Prefix Tree), Longest Word in Dictionary
*/

//Solution1: Brute force, map里的key是dict里的word的长度， value是这个长度的字符的集合
//Time Complexity: buildDict: O(S), search: O(NK). Where N is the number of words in our magic dictionary, S is the total number of letters in it, and K is the length of the search word.
class MagicDictionary {
    HashMap<Integer, HashSet<String>> map;
    /** Initialize your data structure here. */
    public MagicDictionary() {
        map = new HashMap();
    }

    /** Build a dictionary through a list of words */
    public void buildDict(String[] dict) {
        if (dict == null || dict.length == 0)
            return;
        for (String word : dict) {
            int len = word.length();
            map.putIfAbsent(len, new HashSet());
            map.get(len).add(word);
        }
    }

    /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
    public boolean search(String word) {
        if (map.get(word.length()) == null)
            return false;

        for (String s : map.get(word.length())) {
            if (isModified(s, word))
                return true;
        }
        return false;
    }

    public boolean isModified(String s1, String s2) {
        if (s1.equals(s2) || s1.length() != s2.length())
            return false;
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++count == 2)
                return false;
        }
        return true;
    }
}


//Solution2: Generalized Neighbors
// Time Complexity:  O(∑ Wi^2) to build and O(K^2) to search. Wi is the length of words[i], K is the length of our search word.
// Space Complexity: O(∑ Wi^2) the space used by count. We also use O(K^2) space when generating neighbors to search.
class MagicDictionary {
    HashSet<String> words;
    HashMap<String, Integer> count;

    public MagicDictionary() {
        words = new HashSet();
        count = new HashMap();
    }

    public void buildDict(String[] dict) {
        for (String word : dict) {
            words.add(word);
            for (String nei : generateNeighbor(word)) {
                count.put(nei, count.getOrDefault(nei, 0) + 1);
            }
        }
    }

    public boolean search(String word) {
        for (String nei : generateNeighbor(word)) {
            int count = count.getOrDefault(nei, 0);
            if (count > 1 || (count == 1 && !words.contains(word))) //如果count = 1， 可能会出现word已经是dict里的其中一员的edge case
                return true;
        }
        return false;
    }

    public List<String> generateNeighbor(String word) {
        List<String> res = new ArrayList();
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char temp = charArray[i];
            charArray[i] = '*';
            String nei = new String(charArray);
            charArray[i] = temp;
            res.add(nei);
        }
        return res;
    }
}