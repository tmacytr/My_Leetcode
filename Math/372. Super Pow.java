/*
    372. Super Pow

    Your task is to calculate ab mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.

    Example1:

    a = 2
    b = [3]

    Result: 8
    Example2:

    a = 2
    b = [1,0]

    Result: 1024

    Related Topics: Math

    Similar Questions: Pow(x, n)
 */
/*
    这道题和之前那道Pow(x, n)的解法很类似，我们都得对半缩小，不同的是后面都要加上对1337取余。

    由于给定的指数b是一个一维数组的表示方法，我们要是折半缩小处理起来肯定十分不方便，所以我们采用按位来处理，

    比如2^23 = (2^2)^10 * 2^3, 所以我们可以从b的最高位开始，算出个结果存入res，然后到下一位是，res的十次方再乘以a的该位次方再对1337取余
 */
class Solution {
    public int superPow(int a, int[] b) {
        int res = 1;
        for (int i : b)
            res = pow(res, 10) * pow(a, i) % 1337;
        return res;
    }

    private int pow(int x, int y) {
        if (y == 0)
            return 1;
        if (y == 1)
            return x % 1337;
        return pow(x % 1337, y / 2) * pow(x % 1337, y - y / 2) % 1337;
    }
}