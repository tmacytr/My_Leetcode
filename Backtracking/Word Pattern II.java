/*
	Word Pattern II
	Word Pattern II My Submissions Question Solution 
	Total Accepted: 39 Total Submissions: 136 Difficulty: Hard
	Given a pattern and a string str, find if str follows the same pattern.

	Here follow means a full match, such that there is a bijection between a letter in pattern and a substring in str.

	Examples:
	pattern = "abab", str = "redblueredblue" should return true.
	pattern = "aaaa", str = "asdasdasdasd" should return true.
	pattern = "aabb", str = "xyzabcxzyabc" should return false.
	Notes:
	You may assume both pattern and str contains only lowercase letters.

	Hide Tags:Backtracking
*/
/*
	https://leetcode.com/discuss/63252/share-my-java-backtracking-solution
*/

/*
	We can solve this problem using backtracking, we just have to keep trying to use a character in the pattern to match different length of substrings in the input string,
	keep trying till we go through the input string and the pattern.

	For example, input string is "redblueredblue", and the pattern is "abab", first let's use 'a' to match "r", 'b' to match "e", 
	then we find that 'a' does not match "d", so we do backtracking, use 'b' to match "ed", so on and so forth ...

	When we do the recursion, if the pattern character exists in the hash map already, we just have to see if we can use it to match the same length of the string. 
    For example, let's say we have the following map:
	'a': "red"
	'b': "blue"

	now when we see 'a' again, we know that it should match "red", the length is 3, then let's see if str[i ... i+3] matches 'a', 
	where i is the current index of the input string. Thanks to StefanPochmann's suggestion, in Java we can elegantly use str.startsWith(s, i) to do the check.

	Also thanks to i-tikhonov's suggestion, we can use a hash set to avoid duplicate matches, if character a matches string "red", 
	then character b cannot be used to match "red". In my opinion though, we can say apple (pattern 'a') is "fruit", orange (pattern 'o') is "fruit", 
	so they can match the same string, anyhow, I guess it really depends on how the problem states.
	
	The following code should pass OJ now, if we don't need to worry about the duplicate matches, 
	just remove the code that associates with the hash set.
*/

//Solution1: backtracking + hashtabl + with comment
class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        return isMatch(str, 0, pattern, 0, map, set);
    }
    
    public boolean isMatch(String str, int i, String pat, int j, Map<Character, String> map, Set<String> set) {
        if (i == str.length() && j == pat.length()) {
            return true;
        }
        if (i == str.length() || j == pat.length()) {
            return false;
        }
        // get current pattern character
        char c = pat.charAt(j);
        // if the pattern character exists
        if (map.containsKey(c)) {
            String s = map.get(c);
             // then check if we can use it to match str[i...i+s.length()]
            if (!str.startsWith(s, i)) {
                return false;
            }
            // if it can match, great, continue to match the rest
            return isMatch(str, i + s.length(), pat, j + 1, map, set);
        }
        // pattern character does not exist in the map
        for (int k = i; k < str.length(); k++) {
            String p = str.substring(i, k + 1);
            if (set.contains(p)) {
                continue;
            }
            // create or update it
            map.put(c, p);
            set.add(p);
            // continue to match the rest
            if (isMatch(str, k + 1, pat, j + 1, map, set)) {
                return true;
            }
            // backtracking
            set.remove(map.get(c));
            map.remove(c);
        }
        // we've tried our best but still no luck
        return false;
    }
}

//Soltuion2: with comment, concise, prefer
//Time complexity: the problem is more like slicing the string into m pieces. How many slicing ways? C(n^m).
// For each slice, it takes O(n) to validate.
// So the total complexity is O(n * C(n^m))
class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        if (pattern.length() > str.length())
            return false;
        return helper(str, 0, pattern, 0, new HashMap(), new HashSet());
    }

    private boolean helper(String str, int i, String pattern, int j, Map<Character, String> map, Set<String> visited) {
        if (i == str.length() && j == pattern.length())
            return true;
        if (i == str.length() || j == pattern.length())
            return false;
        char c = pattern.charAt(j);
        if (map.containsKey(c)) {
            String s = map.get(c);
            if (!str.startsWith(s, i)) {
                return false;
            }
            return helper(str, i + s.length(), pattern, j + 1, map, visited);
        }

        for (int k = i; k < str.length(); k++) {
            String nextStr = str.substring(i, k + 1);
            if (visited.contains(nextStr))
                continue;
            map.put(c, nextStr);
            visited.add(nextStr);

            if (helper(str, k + 1, pattern, j + 1, map, visited))
                return true;

            visited.remove(nextStr);
            map.remove(c);
        }
        return false;
    }
}
