/*
    522. Longest Uncommon Subsequence II

    Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

    A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

    The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

    Example 1:
    Input: "aba", "cdc", "eae"
    Output: 3
    Note:

    All the given strings' lengths will not exceed 10.
    The length of the given list will be in the range of [2, 50].

    Companies

    Related Topics: String

    Similar Questions: Longest Uncommon Subsequence I
 */

//Solution1: Brute force
class Solution {
    public int findLUSlength(String[] strs) {
        int res = -1;
        int j = 0;
        for (int i = 0; i < strs.length; i++) {
            for (j = 0; j < strs.length; j++) {
                if (i == j)
                    continue;
                if (isSubsequence(strs[i], strs[j])) // check whether strs[i] is subsequence of strs[j]
                    break;
            }
            if (j == strs.length) {
                res = Math.max(res, strs[i].length());
            }
        }
        return res;
    }

    private boolean isSubsequence(String sub, String s) {
        int i = 0, j = 0;
        while (i < sub.length() && j < s.length()) {
            if (sub.charAt(i) == s.charAt(j++))
                i++;
        }
        return i == sub.length();
    }
}

//Solution2: 优化，sort array 第一个找到的就直接返回， prefer
class Solution {
    public int findLUSlength(String[] strs) {
        Arrays.sort(strs, (a, b) -> b.length() - a.length());
        int j = 0;
        for (int i = 0; i < strs.length; i++) {
            for (j = 0; j < strs.length; j++) {
                if (i == j)
                    continue;
                if (isSubsequence(strs[i], strs[j])) // check whether strs[i] is subsequence of strs[j]
                    break;
            }
            if (j == strs.length) {
                return strs[i].length();
            }

        }
        return -1;
    }

    private boolean isSubsequence(String sub, String s) {
        int i = 0, j = 0;
        while (i < sub.length() && j < s.length()) {
            if (sub.charAt(i) == s.charAt(j++))
                i++;
        }
        return i == sub.length();
    }
}

//Solution3: 进一步优化，除了sort array 还要去重，只有非duplicates才可以进入到下一层遍历
class Solution {
    public int findLUSlength(String[] strs) {
        Arrays.sort(strs, (a, b) -> b.length() - a.length());
        Set<String> duplicates = getDuplicates(strs);
        for (int i = 0; i < strs.length; i++) {
            if (!duplicates.contains(strs[i])) {
                if (i == 0)
                    return strs[0].length();
                for (int j = 0; j < i; j++) {
                    if (isSubsequence(strs[i], strs[j]))
                        break;
                    if (j == i - 1)
                        return strs[i].length();
                }
            }
        }
        return -1;
    }

    private boolean isSubsequence(String sub, String s) {
        int i = 0, j = 0;
        while (i < sub.length() && j < s.length()) {
            if (sub.charAt(i) == s.charAt(j++))
                i++;
        }
        return i == sub.length();
    }

    private Set<String> getDuplicates(String[] strs) {
        Set<String> set = new HashSet();
        Set<String> duplicates = new HashSet();
        for (String s : strs) {
            if (!set.add(s))
                duplicates.add(s);
        }
        return duplicates;
    }
}