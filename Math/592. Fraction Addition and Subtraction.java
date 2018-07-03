/*
    592. Fraction Addition and Subtraction

    Given a string representing an expression of fraction addition and subtraction, you need to return the calculation result in string format. The final result should be irreducible fraction. If your final result is an integer, say 2, you need to change it to the format of fraction that has denominator 1. So in this case, 2 should be converted to 2/1.

    Example 1:
    Input:"-1/2+1/2"
    Output: "0/1"
    Example 2:
    Input:"-1/2+1/2+1/3"
    Output: "1/3"
    Example 3:
    Input:"1/3-1/2"
    Output: "-1/6"
    Example 4:
    Input:"5/3+1/3"
    Output: "2/1"
    Note:
        The input string only contains '0' to '9', '/', '+' and '-'. So does the output.
        Each fraction (input and output) has format ±numerator/denominator. If the first input fraction or the output is positive, then '+' will be omitted.
        The input only contains valid irreducible fractions, where the numerator and denominator of each fraction will always be in the range [1,10]. If the denominator is 1, it means this fraction is actually an integer in a fraction format defined above.
        The number of given fractions will be in the range [1,10].
        The numerator and denominator of the final result are guaranteed to be valid and in the range of 32-bit int.

    Companies: IXL

    Related Topics: Math

    Similar Questions: 1.Solve the Equation
 */

//Solution1: prefer
class Solution {
    public String fractionAddition(String expression) {
        // [ ] means that match any one in the bracket. So [+-] means it can separate a string by + or -. ?= means start from the beginning.
        // If we do not use ?=, for example "3+2+1", the result will just be 1 (ignoring 3+2+). ( ) just means a group.
        String[] tokens = expression.split("(?=[+-])");

        int len = tokens.length;
        int[] numerators = new int[len]; //分子
        int[] denominators = new int[len]; //分母

        for (int i = 0; i < len; i++) {
            numerators[i] = Integer.valueOf(tokens[i].split("/")[0]);
            denominators[i] = Integer.valueOf(tokens[i].split("/")[1]);
        }

        long denominator = 1; // 统计分母之乘
        long numerator = 0; // 统计分子之和，

        for (int i = 0; i < len ; i++)
            denominator *= denominators[i];
        for (int i = 0; i < len ; i++)
            numerator += denominator * numerators[i] / denominators[i];

        long gcd = Math.abs(GCD(denominator, numerator));
        String res = numerator / gcd + "/" + denominator / gcd; // 求出分子分母的最大公约数，并除以这个公约数即为所求的结果分子 分母
        return res;
    }

    private long GCD(long x, long y) {
        if (y == 0)
            return x;
        return GCD(y, x % y);
    }
}

//Solution2: more concise by using Scanner
public String fractionAddition1(String expression) {
    Scanner s = new Scanner(expression).useDelimiter("/|(?=[-+])");
    long num = 0, den = 1;
    while (s.hasNext()) {
        long a = s.nextLong(), b = s.nextLong();
        num = num * b + a * den;
        den *= b;
    }
    long gcd = gcd(num, den);
    return (num / gcd) + "/" + (den / gcd);
}

    private long gcd(long a, long b) {
        if (b == 0)
            return a < 0 ? -a : a;    // always return positive gcd
        return gcd(b, a % b);
    }