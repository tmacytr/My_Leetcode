/*
    411. Minimum Unique Word Abbreviation
    DescriptionHintsSubmissionsDiscussSolution
    A string such as "word" contains the following abbreviations:

    ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
    Given a target string and a set of strings in a dictionary, find an abbreviation of this target string with the smallest possible length such that it does not conflict with abbreviations of the strings in the dictionary.

    Each number or letter in the abbreviation is considered length = 1. For example, the abbreviation "a32bc" has length = 4.

    Note:
    In the case of multiple answers as shown in the second example below, you may return any one of them.
    Assume length of target string = m, and dictionary size = n. You may assume that m ≤ 21, n ≤ 1000, and log2(n) + m ≤ 20.
    Examples:
    "apple", ["blade"] -> "a4" (because "5" or "4e" conflicts with "blade")

    "apple", ["plain", "amber", "blade"] -> "1p3" (other valid answers include "ap3", "a3e", "2p2", "3le", "3l1"
 */

// Solution1: PriorityQueue + DFS(Generalized Abbreviation) + TwoPointer(Valid Word Abbreviation)
class Solution {
    public String minAbbreviation(String target, String[] dictionary) {
        if (target.length() > 0 && (dictionary == null || dictionary.length == 0))
            return target.length() + "";
        PriorityQueue<String> pq = new PriorityQueue<String>((a, b) -> a.length() - b.length());
        // 拿到target的所有abbreviation
        dfs(pq, target, 0, 0, "");
        // 用target的abbreviation和dictionary里所有的word check， 第一个和dict里的word都不相同的abbr 就是最小长度，因为我们这里用了最小堆
        while (!pq.isEmpty()) {
            String s = pq.poll();
            for (int i = 0; i < dictionary.length; i++) {
                if (isValidAbbreviation(dictionary[i], s)) {
                    break;
                }
                if (i == dictionary.length - 1)
                    return s;
            }
        }
        return target.length() + "";
    }

    public void dfs(PriorityQueue<String> pq, String word, int index, int count, String item) {
        if (index == word.length()) {
            if (count > 0)
                item += count;
            pq.offer(item);
            return;
        }
        dfs(pq, word, index + 1, count + 1, item);
        dfs(pq, word, index + 1, 0, item + (count > 0 ? count : "") + word.charAt(index));
    }

    public boolean isValidAbbreviation(String word, String abbr) {
        int i = 0;
        int j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
            } else {
                if (abbr.charAt(j) == '0' || !Character.isDigit(abbr.charAt(j)))
                    return false;
                int start = j;
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j)))
                    j++;
                int len = Integer.valueOf(abbr.substring(start, j));
                i += len;
            }
        }
        return i == word.length() && j == abbr.length();
    }
}