/*
	Shortest Word Distance III
	This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.

	Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

	word1 and word2 may be the same and they represent two individual words in the list.

	For example,
	Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

	Given word1 = “makes”, word2 = “coding”, return 1.
	Given word1 = "makes", word2 = "makes", return 3.
*/

//Solution1 by Myself
class Solution {
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int index1 = -1;
        int index2 = -1;
        int minLen = words.length;
        boolean same = word1.equals(word2);
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (same) {
                if (word.equals(word1)) {
                    if (index1 != -1) {
                        minLen = Math.min(minLen, i - index1);
                    } 
                    index1 = i;
                }
            } else {
                if (word.equals(word1)) {
                    index1 = i;
                } else if (word.equals(word2)) {
                    index2 = i;
                }

                if (index1 != -1 && index2 != -1)
                    minLen = Math.min(minLen, Math.abs(index1 - index2));
            } 
        }
        return minLen;
    }
}

//Solution2: concise, still use two index to record
class Solution {
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int minLen = words.length;
        int index1 = minLen;
        int index2 = -minLen;
        boolean isSame = word1.equals(word2);
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.equals(word1))
                index1 = i;
            if (word.equals(word2)) {
                if (isSame)
                    index1 = index2;
                index2 = i;
            }
            minLen = Math.min(minLen, Math.abs(index1 - index2));
        }
        return minLen;
    }
}
