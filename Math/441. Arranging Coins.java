/*
    441. Arranging Coins

    You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k coins.

    Given n, find the total number of full staircase rows that can be formed.

    n is a non-negative integer and fits within the range of a 32-bit signed integer.

    Example 1:

    n = 5

    The coins can form the following rows:
    ¤
    ¤ ¤
    ¤ ¤

    Because the 3rd row is incomplete, we return 2.
    Example 2:

    n = 8

    The coins can form the following rows:
    ¤
    ¤ ¤
    ¤ ¤ ¤
    ¤ ¤

    Because the 4th row is incomplete, we return 3.

    Companies: GoDaddy

    Related Topics: Math, Binary Search
 */


//Solution1: brute force， my solution time: O(logn)
class Solution {
    public int arrangeCoins(int n) {
        int count = 0;
        int i = 1;
        while (n > 0) {
            n -= i;
            i++;
            if (n < 0)
                break;
            count++;
        }
        return count;
    }
}

/*
    利用了等差数列的性质，我们建立等式, n = (1 + x) * x / 2, 我们用一元二次方程的求根公式可以得到 x = (-1 + sqrt(8 * n + 1)) / 2, 然后取整后就是能填满的行数
 */
//Solution2: O(1)
public class Solution {
    public int arrangeCoins(int n) {
        return (int)((-1 + Math.sqrt(1 + 8 * (long)n)) / 2);
    }
}