/*
	Remove Duplicate Letters
	Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.

	Example:
	Given "bcabc"
	Return "abc"

	Given "cbacdcbc"
	Return "acdb"

	Tags: Greedy
*/
/*
	Given the string s, the greedy choice (i.e., the leftmost letter in the answer) is the smallest s[i], 
    s.t. the suffix s[i .. ] contains all the unique letters. 
	(Note that, when there are more than one smallest s[i]'s, we choose the leftmost one. 
    Why? Simply consider the example: "abcacb".)

	After determining the greedy choice s[i], we get a new string s' from s by

    removing all letters to the left of s[i],
	removing all s[i]'s from s.
	We then recursively solve the problem w.r.t. s'.

	The runtime is O(26 * n) = O(n).
*/

/*
    核心思想：
            对所有的char统计出现的次数，从左边开始遍历对遍历到的字符count--，pos记录排序最小的字符出现的位置如果相同取最左边的，
             一旦某个字符的count == 0， 则break出来将pos位置的字符加入res，并将所有的s.charAt(pos)replace成“”，并从pos + 1 开始截取字符进行下一次遍历
*/
//Solution1:
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int minPos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(minPos)) //为什么要有这一句？因为结果必须是要返回的所有可能字符串中 排序最小的
                minPos = i;
            if (--count[s.charAt(i) - 'a'] == 0)
                break;
        }

        if (s.length() == 0)
            return "";

        char minChar = s.charAt(minPos);
        return minChar + removeDuplicateLetters(s.substring(minPos + 1).replaceAll(minChar + "", ""));
    }
}

//Solution2: iterative
class Solution {
    public String removeDuplicateLetters(String s) {
        char[] letters = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap();

        for (int i = 0; i < letters.length; i++) {
            map.put(letters[i], i);
        }

        char[] res = new char[map.size()];

        int start = 0;
        for (int i = 0; i < res.length; i++) {
            int end = findMinPos(map);
            char min = 'z' + 1;
            for (int j = start; j <= end; j++) {
                if (map.containsKey(letters[j]) && letters[j] < min) {
                    min = letters[j];
                    start = j + 1;
                }
            }
            res[i] = min;
            map.remove(min);
        }
        return String.valueOf(res);
    }

    private int findMinPos(HashMap<Character, Integer> map) {
        int pos = Integer.MAX_VALUE;
        for (int lastPos : map.values())
            pos = Math.min(pos, lastPos);
        return pos;
    }
}