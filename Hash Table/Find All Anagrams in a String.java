/*
	Find All Anagrams in a String

	Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.

	Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.

	The order of output does not matter.

	Example 1:

	Input:
	s: "cbaebabacd" p: "abc"

	Output:
	[0, 6]

	Explanation:
	The substring with start index = 0 is "cba", which is an anagram of "abc".
	The substring with start index = 6 is "bac", which is an anagram of "abc".
	Example 2:

	Input:
	s: "abab" p: "ab"

	Output:
	[0, 1, 2]

	Explanation:
	The substring with start index = 0 is "ab", which is an anagram of "ab".
	The substring with start index = 1 is "ba", which is an anagram of "ab".
	The substring with start index = 2 is "ab", which is an anagram of "ab".
*/



// Solution1: prefer O(n)， sliding window
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList();
        if (s == null || s.length() == 0 || p.length() > s.length())
            return res;
        int[] hash = new int[26];
        for (char c : p.toCharArray()) {
            hash[c - 'a']++;
        }
        int count = p.length();
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            if (hash[s.charAt(right) - 'a'] >= 1)
                count--;
            hash[s.charAt(right) - 'a']--;
            right++;
            
            if (count == 0)
                res.add(left);
            
            if (right - left == p.length()) {
                if (hash[s.charAt(left) - 'a'] >= 0) 
                    count++;
                hash[s.charAt(left) - 'a']++;
                left++;
            }
        }
        return res;
    }
}

//Solution2: myself, O(mn)
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList();
        if (s == null || s.length() == 0 || p.length() > s.length())
            return res;
        int[] chars = new int[26];
        for (char c : p.toCharArray()) {
            chars[c - 'a']++;
        }
        
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            chars[s.charAt(i) - 'a']--;
            // 前 p.length() 个 只能-- 不能++
            if (i >= p.length()) {
                chars[s.charAt(start) - 'a']++; 
                start++;
            }

            if (isZero(chars)) {
                res.add(start);
            }
        }
        return res;
        
    }
    
    public boolean isZero(int[] chars) {
        for (int num : chars) {
            if (num != 0)
                return false;
        }
        return true;
    }
}