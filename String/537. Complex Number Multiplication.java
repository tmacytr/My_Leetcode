/*
    537. Complex Number Multiplication

    Given two strings representing two complex numbers.

    You need to return a string representing their multiplication. Note i2 = -1 according to the definition.

    Example 1:
    Input: "1+1i", "1+1i"
    Output: "0+2i"
    Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
    Example 2:
    Input: "1+-1i", "1+-1i"
    Output: "0+-2i"
    Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.
    Note:

    The input strings will not have extra blank.
    The input strings will be given in the form of a+bi, where the integer a and b will both belong to the range of [-100, 100]. And the output should be also in this form.

    Companies: Amazon

    Related Topics: Math, String
 */

/*
    (a + bi) * (c + di) = ac + (ad + bc)i + bd i^2
 */
class Solution {
    public String complexNumberMultiply(String a, String b) {
        int sign1 = a.indexOf("+");
        int sign2 = b.indexOf("+");

        int a1 = Integer.valueOf(a.substring(0, sign1));
        int a2 = Integer.valueOf(a.substring(sign1 + 1, a.indexOf("i")));

        int b1 = Integer.valueOf(b.substring(0, sign2));
        int b2 = Integer.valueOf(b.substring(sign2 + 1, b.indexOf("i")));

        return a1 * b1 + a2 * b2 * (-1) + "+" + (a1 * b2 + a2 * b1) + "i";
    }
}