/*
	Word Break II 
	Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.

	Return all such possible sentences.

	For example, given
	s = "catsanddog",
	dict = ["cat", "cats", "and", "sand", "dog"].

	A solution is ["cats and dog", "cat sand dog"].
	Tags:DP, Backtracking
*/

//Solution1: Prefer, Recursion with memoization, same logic with word break I, time: O(n^3)
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return helper(s, new HashSet(wordDict), 0, new HashMap<Integer, List<String>>());
    }

    public List<String> helper(String s, Set<String> wordDict, int start, HashMap<Integer, List<String>> map) {
        if (map.containsKey(start))
            return map.get(start);

        List<String> res = new LinkedList();

        if (start == s.length())
            res.add("");

        for (int end = start + 1; end <= s.length(); end++) {
            // 如果s.substring(start, end)在dict里，我们遍历下一个可能的word， 并加入到sublist中返回
            if (wordDict.contains(s.substring(start, end))) {
                List<String> subList = helper(s, wordDict, end, map);
                for (String word : subList) {
                    res.add(s.substring(start, end) + (word.equals("") ? "" : " ") + word);
                }
            }
        }
        map.put(start, res); //memoization
        return res;
    }
}


//Solution2: Brute force + BackTracking + DP to checking whether is wordbreak
public class Solution {
    public List<String> wordBreak(String s, List<String> dict) {
        List<String> res = new ArrayList<String>();
        if (s == null || s.length() == 0 || dict.size() == 0 || dict == null || !isWordBreak(s, dict)) {
            return res;
        }
        helper(s, dict, 0, "", res);
        return res;
    }

    public void helper (String s, List<String> dict, int start, String item, List<String> res) {
    	if (start == s.length()) {
    		res.add(item);
    		return;
    	}
    	StringBuilder sb = new StringBuilder();
    	for (int i = start; i < s.length(); i++) {
    		sb.append(s.charAt(i));
    		if (dict.contains(sb.toString())) {
    			String newItem = item.length() > 0 ? (item + " " + sb.toString()) : sb.toString();
                helper(s, dict, i + 1, newItem, res);
    		}
    	}
    }
    //Word Break I's method, just to check the orginal String whether can be break or not
    public boolean isWordBreak(String s, List<String> dict) {
        if (s == null || s.length() == 0 || dict.size() == 0 || dict == null) {
            return false;
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                }
            }
        }
        return dp[s.length()];
    }
}


//Solution3: 从wordDict开始遍历， 看是否能构建成一个完整的s
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return dfs(s, new HashSet(wordDict), new HashMap<String, LinkedList<String>>());
    }

    private List<String> dfs(String s, Set<String> wordDict, HashMap<String, LinkedList<String>> map) {
        if (map.containsKey(s))
            return map.get(s);

        LinkedList<String> res = new LinkedList();

        if (s.length() == 0) {
            res.add("");
            return res;
        }

        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String> subList = dfs(s.substring(word.length()), wordDict, map);
                for (String sub : subList)
                    res.add(word + (sub.isEmpty() ? "" : " " + sub));
            }
        }
        map.put(s, res);
        return res;
    }
}

//Solution4: 和Solution3思路类似，但是为了防止wordDict有大数据，从s开始遍历
class Solution {
    HashMap<String,List<String>> map = new HashMap<String,List<String>>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<String>();

        if(s == null || s.length() == 0) {
            return res;
        }
        if (map.containsKey(s))
            return map.get(s);

        if (wordDict.contains(s)) {
            res.add(s);
        }

        for (int i = 1; i < s.length(); i++) {
            String sub = s.substring(i);
            if (wordDict.contains(sub)) {
                List<String> subList = wordBreak(s.substring(0, i), wordDict);
                if (!subList.isEmpty()) {
                    for (int j = 0; j < subList.size(); j++) {
                        res.add(subList.get(j) + " " + sub);
                    }
                }
            }
        }

        map.put(s, res);
        return res;
    }
}




import java.io.*;
import java.util.*;

/*
dict = [cats, cat, dog, sand, and]
s = catsanddog

r = [
  cats_and_dog
  cat_sand_dog
]
*/
/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  public static void main(String[] args) {
    String s = "catsanddog";
    List<String> strings = new ArrayList<String>();
    strings.add("cats");
    strings.add("cat");
    strings.add("dog");
    strings.add("sand");
    strings.add("and");

    for (String string : strings) {
      System.out.println(string);
    }
    
    System.out.println(function(s, strings));
  }
  
  public static List<String> function(String s, List<String> dict) {
    return helper(s, new HashSet(dict), 0, new HashMap<Integer, List<String>>());
  }
                  
  public static List<String> helper(String s, Set<String> dict, int start, HashMap<Integer, List<String>> map) {
    if (map.containsKey(start)) {
      return map.get(start);
    }
    
    List<String> res = new LinkedList();
    
    if (start == s.length()) {
      res.add("");
    }
    
    for (int end = start + 1; end <= s.length(); end++) {
      if (dict.contains(s.substring(start, end))) {
        List<String> subList = helper(s, dict, end, map);
        for (String word : subList) {
            res.add(s.substring(start, end) + (word.equals("") ? "" : "_") + word); 
        }
    }
  }
  map.put(start, res);
  return res;
}
}
    
