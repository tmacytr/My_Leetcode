/*
	Word Pattern
	Given a pattern and a string str, find if str follows the same pattern.

	Examples:
	pattern = "abba", str = "dog cat cat dog" should return true.
	pattern = "abba", str = "dog cat cat fish" should return false.
	pattern = "aaaa", str = "dog cat cat dog" should return false.
	pattern = "abba", str = "dog dog dog dog" should return false.
	Notes:
	patterncontains only lowercase alphabetical letters, and str contains words separated by a single space. Each word in str contains only lowercase alphabetical letters.
	Both pattern and str do not have leading or trailing spaces.
	Each letter in pattern must map to a word with length that is at least 1
*/


//Solution1: use containsValue function which is O(n)
class Solution {

    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }
        HashMap<Character, String> map = new HashMap<>();
        for (int i = 0;i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];
            if (!map.containsKey(c)) {
                if (map.containsValue(word)) {
                    return false;
                }
                map.put(c, word); 
            } else {
                if (!map.get(c).equals(word)) {
                    return false;
                }
            } 
        }
        return true;
    }
}

//Solution2: use extra space for storing visited string
class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        if (pattern.length() != words.length)
            return false;
        Map<Character, String> map = new HashMap();
        Set<String> visited = new HashSet();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!map.containsKey(c)) {
                if (!visited.add(words[i])) {
                    return false;
                }
                map.put(c, words[i]);
            } else if (!map.get(c).equals(words[i])) {
                return false;
            }
        }
        return true;
    }
}
