/*
    408. Valid Word Abbreviation

    Given a non-empty string s and an abbreviation abbr, return whether the string matches with the given abbreviation.

    A string such as "word" contains only the following valid abbreviations:

    ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
    Notice that only the above abbreviations are valid abbreviations of the string "word". Any other string is not a valid abbreviation of "word".

    Note:
    Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.

    Example 1:
    Given s = "internationalization", abbr = "i12iz4n":

    Return true.
    Example 2:
    Given s = "apple", abbr = "a2e":

    Return false.
 */

// Solution1: 双指针i -> word, j -> abbr, 同时遍历word和abbr, 判断最后是否一起遍历到尾部，注意edge case, not leading zero
// (1) abc  a0c false
// (2) abc  a2c false
// (3) a    1   true
// (4) abcd a2e false
class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        if ((word == null || word.length() == 0) && abbr.length() != 0)
            return false;
        int i = 0;
        int j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
            } else {
                // if the first character is not digit or is zero return false
                if (abbr.charAt(j) == '0' || !Character.isDigit(abbr.charAt(j)))  {
                    return false;
                }
                int start = j; // 开始算有多少个数字
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j)))
                    j++;
                int len = Integer.valueOf(abbr.substring(start, j));
                i += len;
            }
        }

        return i == word.length() && j == abbr.length();
    }
}