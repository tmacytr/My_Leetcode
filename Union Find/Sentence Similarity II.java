/*
	737. Sentence Similarity II

	Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

	For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].

	Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.

	Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

	Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

	Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

	Note:

	The length of words1 and words2 will not exceed 1000.
	The length of pairs will not exceed 2000.
	The length of each pairs[i] will be 2.
	The length of each words[i] and pairs[i][j] will be in the range [1, 20].
*/

// Solution1: Union Find
// 1.将pairs里的所有单词union 2.再union find 两个数组里的单词是否有相同的parent
class Solution {
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length)
            return false;
        Map<String, String> map = new HashMap();
        for (String[] pair : pairs) {
            String parent1 = find(map, pair[0]);
            String parent2 = find(map, pair[1]);
            
            if (!parent1.equals(parent2)) {
                map.put(parent1, parent2);
            }
        }
        int len = words1.length;
        for (int i = 0; i < len; i++) {
            String word1 = words1[i];
            String word2 = words2[i];
            
            if (word1.equals(word2))
                continue;
            
            if (!find(map, word1).equals(find(map, word2)))
                return false;
        }
        return true;
    }
    
    public String find(Map<String, String> map, String s) {
        if (!map.containsKey(s))
            map.put(s, s);
        return s.equals(map.get(s)) ? s : find(map, map.get(s));
    }
}

// Solution2: DFS
class Solution {
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length)
            return false;
        
        HashMap<String, HashSet<String>> map = new HashMap();
        
        for (String[] pair: pairs) {
            String word1 = pair[0];
            String word2 = pair[1];
            
            if (map.get(word1) == null)
                map.put(word1, new HashSet());
            if (map.get(word2) == null)
                map.put(word2, new HashSet());
            
            map.get(word1).add(word2);
            map.get(word2).add(word1);
        }
        
        int len = words1.length;
        Set<String> visited = new HashSet();
        for (int i = 0; i < len; i++) {
            String word1 = words1[i];
            String word2 = words2[i];
            
            if (word1.equals(word2))
                continue;
            if (!map.containsKey(word1))
                return false;
            if (!dfs(map, word1, word2, new HashSet()))
                return false;
        }
        return true;
    }
    
    public boolean dfs(HashMap<String, HashSet<String>> map, String source, String target, Set<String> visited) {
        if (map.get(source).contains(target))
            return true;
        visited.add(source);
        for (String next : map.get(source)) {
            if (!visited.contains(next) && dfs(map, next, target, visited)) {
                return true;
            }
        }
        return false;
    }
}