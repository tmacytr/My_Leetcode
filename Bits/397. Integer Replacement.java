/*
    397. Integer Replacement

    Given a positive integer n and you can do operations as follow:

    If n is even, replace n with n/2.
    If n is odd, you can replace n with either n + 1 or n - 1.
    What is the minimum number of replacements needed for n to become 1?

    Example 1:

    Input:
    8

    Output:
    3

    Explanation:
    8 -> 4 -> 2 -> 1
    Example 2:

    Input:
    7

    Output:
    4

    Explanation:
    7 -> 8 -> 4 -> 2 -> 1
    or
    7 -> 6 -> 3 -> 2 -> 1

    Companies: Google, Baidu
    Related Topics: Math, Bit
 */

//Solution1: recursive
class Solution {
    public int integerReplacement(int n) {
        if (n == 1)
            return 0;
        if (n % 2 == 0)
            return 1 + integerReplacement(n / 2);
        long t = n; //防止溢出
        return 2 + Math.min(integerReplacement((int)((t + 1) / 2)), integerReplacement((int)((t - 1) / 2)));
    }
}

/*

    Algorithm: 每遇到奇数时，分别判断n-1还是n+1的尾部零更多，越多的当然步骤越少。需要注意的是，当n = 3时，不满足上述判定条件，需要单独处理。
        1. If n is even, halve it.
        2. If n=3 or n-1 has less 1’s than n+1, decrement n.
        3. Otherwise, increment n.
 */

//Solution2:
class Solution {
    public int integerReplacement(int n) {
        int res = 0;
        while (n != 1) {
            if ((n & 1) == 0) { // n % 2 == 0
                n >>>= 1; // n = n / 2;
            } else if (n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)) {
                --n;
            } else {
                ++n;
            }
            res++;
        }
        return res;
    }
}