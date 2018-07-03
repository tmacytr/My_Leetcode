/*
    472. Concatenated Words

    Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
    A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.

    Example:
    Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

    Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

    Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
    "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
    Note:
    The number of elements of the given array will not exceed 10,000
    The length sum of elements in the given array will not exceed 600,000.
    All the input string will only include lower case letters.
    The returned elements order does not matter.

    Related Topics: DP, DFS, Trie

    Similar Questions: Word Break II
 */

//Solution1: HashTable + DP, 用word break的方法判断word是否能被分隔
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList();
        Set<String> preWords = new HashSet();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        for (int i = 0; i < words.length; i++) {
            if (canForm(words[i], preWords))
                res.add(words[i]);
            preWords.add(words[i]);
        }
        return res;
    }

    private boolean canForm(String word, Set<String> dict) {
        if (dict.isEmpty())
            return false;
        boolean[] dp = new boolean[word.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 0; j < i; j++) {
                if(dp[j] && dict.contains(word.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[word.length()];
    }
}

//Solution2: DFS
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> list = new ArrayList();
        Set<String> set = new HashSet(Arrays.asList(words));

        for(String word : words) {
            set.remove(word);
            if(dfs(word, set, ""))
                list.add(word);
            set.add(word);
        }
        return list;
    }

    private boolean dfs(String word, Set<String> set, String previous) {
        if(!previous.equals(""))
            set.add(previous);
        if(set.contains(word))
            return true;

        for(int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0,i);
            if(set.contains(prefix) && dfs(word.substring(i,word.length()), set, previous+prefix)) {
                return true;
            }
        }
        return false;
    }
}