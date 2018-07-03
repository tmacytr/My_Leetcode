/*
    409. Longest Palindrome

    Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

    This is case sensitive, for example "Aa" is not considered a palindrome here.

    Note:
    Assume the length of given string will not exceed 1,010.

    Example:

    Input:
    "abccccdd"

    Output:
    7

    Explanation:
    One longest palindrome that can be built is "dccaccd", whose length is 7.
 */

//Solution1: Two Pass, no using hashset, 所有的偶数character字符都可以使用，奇数字符可以使用n - 1, 并且还可以再加上一个unique的字符
class Solution {
    public int longestPalindrome(String s) {
        int[] letters = new int[128];
        for (char c : s.toCharArray()) {
            letters[c]++;
        }
        int res = 0;

        for (int num : letters) {
            if (num % 2 == 0) {
                res += num;
            } else {
                res += num - 1;
                if (res % 2 == 0 && num % 2 == 1) // key point: this would onely enter once
                    res++;
            }
        }

        return res;
    }
}

//Solution2: One Pass, HashSet, 计算odd的数有多少， 直接用 len - odd即为所求数
class Solution {
    public int longestPalindrome(String s) {
        Set<Character> set = new HashSet();

        for (char c : s.toCharArray()) {
            if (!set.add(c))
                set.remove(c);
        }
        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
}