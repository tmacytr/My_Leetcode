/*
    415. Add Strings
    DescriptionHintsSubmissionsDiscussSolution
    Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

    Note:

    The length of both num1 and num2 is < 5100.
    Both num1 and num2 contains only digits 0-9.
    Both num1 and num2 does not contain any leading zero.
    You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */

// Solution1:
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int carry = 0;
        int m = num1.length();
        int n = num2.length();


        for (int i = 1; i <= Math.max(m, n) || carry != 0; i++) {
            int d1 = i <= m ? num1.charAt(m - i) - '0' : 0;
            int d2 = i <= n ? num2.charAt(n - i) - '0' : 0;
            int sum = d1 + d2 + carry;
            res.insert(0, sum % 10);
            carry = sum / 10;
        }
        return res.toString();
    }
}

// Solution2:
class Solution {
    public String addStrings(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (int i = 0; i < m || i < n; i++) {
            if (i < m)
                sum += num1.charAt(m - i - 1) - '0';
            if (i < n)
                sum += num2.charAt(n - i - 1) - '0';
            sb.insert(0, sum % 10);
            sum /= 10;
        }
        if (sum > 0)
            sb.insert(0, sum);
        return sb.toString();
    }
}