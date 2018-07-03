/*
	340. Longest Substring with At Most K Distinct Characters

	Given a string, find the length of the longest substring T that contains at most k distinct characters.

	For example, Given s = “eceba” and k = 2,

	T is "ece" which its length is 3.
*/


// Solution1: 
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0)
            return 0;
        HashMap<Character, Integer> map = new HashMap();
            
        int left = 0;
        int res = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (map.size() > k) {
                char leftChar = s.charAt(left);
                if (map.get(leftChar) != null) {
                    map.put(leftChar, map.get(leftChar) - 1);
                    if (map.get(leftChar) == 0)
                        map.remove(leftChar);
                }
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }
}

// Follow Up: If the string is given as a steam. In this situation, we can’t maintain a “left pointer” as the classical O(n) hashmap solution. In a stream you cannot do s.charAt(left)
// 类似LRU的做法，每次将之前的char key 删除，更新最新的位置，这里的LinkedHashMap的value是该字符出现的最后位置
// LinkedHashMap 遍历时， 数据按照插入顺序排列，最旧的最先拿到
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        int left = 0;
        int right = 0;
        int res = 0;

        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);
            if (map.containsKey(c) || map.size() < k) {
                map.remove(c);
                map.put(c, right);
                res = Math.max(res, right++ - left + 1);
            } else {
                Character leftChar = map.keySet().iterator().next();
                left = map.get(leftChar);
                map.remove(leftChar);
                left++;
            }
        }
        return res;
    }
}