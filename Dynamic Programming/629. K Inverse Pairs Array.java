/*
    629. K Inverse Pairs Array

    Given two integers n and k, find how many different arrays consist of numbers from 1 to n such that there are exactly k inverse pairs.

    We define an inverse pair as following: For ith and jth element in the array, if i < j and a[i] > a[j] then it's an inverse pair; Otherwise, it's not.

    Since the answer may be very large, the answer should be modulo 109 + 7.

    Example 1:
    Input: n = 3, k = 0
    Output: 1
    Explanation:
    Only the array [1,2,3] which consists of numbers from 1 to 3 has exactly 0 inverse pair.
    Example 2:
    Input: n = 3, k = 1
    Output: 2
    Explanation:
    The array [1,3,2] and [2,1,3] have exactly 1 inverse pair.
    Note:
    The integer n is in the range [1, 1000] and k is in the range [0, 1000].

    Companies: Works Applications

    Related Topics: DP
 */

/*
    这道题给了我们1到n总共n个数字，让我们任意排列数组的顺序，使其刚好存在k个翻转对，所谓的翻转对，就是位置在前面的数字值大，

    而且题目中表明了结果会很大很大，要我们对一个很大的数字取余。

    我们需要一个二维的DP数组，其中dp[i][j]表示1到i的数字中有j个翻转对的排列总数，那么我们要求的就是dp[n][k]了，即1到n的数字中有k个翻转对的排列总数。

    现在难点就是要求递推公式了。我们想如果我们已经知道dp[n][k]了，怎么求dp[n+1][k]，先来看dp[n+1][k]的含义，是1到n+1点数字中有k个翻转对的个数，

    那么实际上在1到n的数字中的某个位置加上了n+1这个数，为了简单起见，我们先让n=4，那么实际上相当于要在某个位置加上5，那么加5的位置就有如下几种情况：

    xxxx5

    xxx5x

    xx5xx

    x5xxx

    5xxxx

    这里xxxx表示1到4的任意排列，那么第一种情况xxxx5不会增加任何新的翻转对，因为xxxx中没有比5大的数字，

    而 xxx5x会新增加1个翻转对，xx5xx，x5xxx，5xxxx分别会增加2，3，4个翻转对。那么xxxx5就相当于dp[n][k]，即dp[4][k]，

    那么依次往前类推，就是dp[n][k-1], dp[n][k-2]...dp[n][k-n]，这样我们就可以得出dp[n+1][k]的求法了:

    dp[n+1][k] = dp[n][k] + dp[n][k-1] + ... + dp[n][k - n]

    那么dp[n][k]的求法也就一目了然了:

    dp[n][k] = dp[n - 1][k] + dp[n - 1][k-1] + ... + dp[n - 1][k - n + 1]

    我们可以对上面的解法进行时间上的优化，还是来看我们的递推公式:

    dp[n][k] = dp[n - 1][k] + dp[n - 1][k-1] + ... + dp[n - 1][k - n + 1]

    我们可以用k+1代替k，得到：

    dp[n][k+1] = dp[n - 1][k+1] + dp[n - 1][k] + ... + dp[n - 1][k + 1 - n + 1]

    用第二个等式减去第一个等式可以得到：

    dp[n][k+1] = dp[n][k] + dp[n - 1][k+1] - dp[n - 1][k - n + 1]

    将k+1换回成k，可以得到：

    dp[n][k] = dp[n][k-1] + dp[n - 1][k] - dp[n - 1][k - n]

    我们可以发现当k>=n的时候，最后一项的数组坐标才能为非负数，从而最后一项才有值，

    所以我们再更新的时候只需要判断一下k和n的关系，如果k>=n的话，就要减去最后一项，这种递推式算起来更高效，减少了一个循环，
 */
class Solution {
    public int kInversePairs(int n, int k) {
        int mod = 1000000007;
        if (k > n * (n - 1) / 2 || k < 0)
            return 0;
        if (k == 0 || k == n * (n - 1) / 2)
            return 1;
        long[][] dp = new long[n + 1][k + 1];
        dp[2][0] = 1;
        dp[2][1] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1 ; j <= Math.min(k, i * (i - 1) / 2); j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                if (j >= i)
                    dp[i][j] -= dp[i - 1][j - i];
                dp[i][j] = (dp[i][j] + mod) % mod;
            }
        }
        return (int)dp[n][k];
    }
}