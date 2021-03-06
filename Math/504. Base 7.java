/*
    504. Base 7

    Given an integer, return its base 7 string representation.

    Example 1:
    Input: 100
    Output: "202"
    Example 2:
    Input: -7
    Output: "-10"
    Note: The input will be in range of [-1e7, 1e7].
 */

//Solution1: recursion
class Solution {
    public String convertToBase7(int num) {
        if (num < 0)
            return '-' + convertToBase7(-num);
        if (num < 7)
            return num + "";
        return convertToBase7(num / 7) + num % 7;
    }
}

//Solution2: iterative, prefer
class Solution {
    public String convertToBase7(int num) {
        if (num == 0)
            return "0";
        StringBuilder sb = new StringBuilder();
        boolean isNegative = num < 0;
        while (num != 0) {
            sb.append(Math.abs(num % 7));
            num /= 7;
        }
        String res = sb.reverse().toString();
        return isNegative ? "-" + res : res;
    }
}