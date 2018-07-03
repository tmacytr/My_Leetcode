/*
    788. Rotated Digits

    X is a good number if after rotating each digit individually by 180 degrees, we get a valid number that is different from X.  Each digit must be rotated - we cannot choose to leave it alone.

    A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate to each other; 6 and 9 rotate to each other, and the rest of the numbers do not rotate to any other number and become invalid.

    Now given a positive number N, how many numbers X from 1 to N are good?

    Example:
    Input: 10
    Output: 4
    Explanation:
    There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
    Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
    Note:

    N  will be in range [1, 10000].

    Companies: Google

    Related Topics: String, DP
 */


/*
    题目有歧义，只要翻转后还能成为valid的数 并且和之前不同就行，并不需要在 [1, N]这个范围里面
 */

//Solution1: brute force, O(nlogn)
class Solution {
    public int rotatedDigits(int N) {
        int res = 0;
        for (int i = 1; i <= N; i++) {
            if (isValid(i))
                res++;
        }
        return res;
    }

    private boolean isValid(int num) {
        boolean validFound = false;
        while (num > 0) {
            switch (num % 10) {
                case 2:
                case 5:
                case 6:
                case 9:
                    validFound = true;
                    break;
                case 3:
                case 4:
                case 7:
                    return false;
            }
            num = num / 10;
        }
        return validFound;
    }
}
/*
    Using a int[] for dp.
    dp[i] = 0, invalid number
    dp[i] = 1, valid and same number
    dp[i] = 2, valid and different number
 */
//Solution2: DP O(logn)
class Solution {
    public int rotatedDigits(int N) {
        int[] dp = new int[N + 1];
        int res = 0;
        for (int i = 0; i <= N; i++) {
            if (i < 10) {
                if (i == 0 || i == 1 || i == 8) {
                    dp[i] = 1;
                } else if (i == 2 || i == 5 || i == 6 || i == 9) {
                    dp[i] = 2;
                    res++;
                }
            } else {
                int a = dp[i / 10];
                int b = dp[i % 10];
                if (a == 1 && b == 1)
                    dp[i] = 1;
                else if (a >= 1 && b >= 1) { // a 和 b都必须valid 并且至少有一个是different number
                    dp[i] = 2;
                    res++;
                }
            }
        }
        return res;
    }
}