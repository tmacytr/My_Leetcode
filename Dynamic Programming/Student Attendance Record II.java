/*
    Given a positive integer n, return the number of all possible attendance records with length n, which will be regarded as rewardable. The answer may be very large, return it after mod 109 + 7.

    A student attendance record is a string that only contains the following three characters:

    'A' : Absent.
    'L' : Late.
    'P' : Present.
    A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).

    Example 1:
    Input: n = 2
    Output: 8
    Explanation:
    There are 8 records with length 2 will be regarded as rewardable:
    "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
    Only "AA" won't be regarded as rewardable owing to more than one absent times.
    Note: The value of n won't exceed 100,000.
 */

/*
    定义了两个数组P和PorL，其中P[i]表示数组前i个数字中已P结尾的排列个数，PorL[i]表示数组前i个数字中已P或者L结尾的排列个数, 我们先不考虑字符A的情况，而是先把定义的这个数组先求出来。

    1. 求出P和PorL数组(无A）
        由于P字符可以再任意字符后面加上，所以 P[i] = PorL[i-1]；
        而PorL[i]由两部分组成，P[i] + L[i]，其中P[i]已经更新了，L[i]只能当前一个字符是P，或者前一个字符是L且再前一个字符是P的时候加上，即为P[i-1] + P[i-2]，
        所以PorL[i] = P[i] + P[i-1] + P[i-2]

    2. 求出有A出现的情况(insert A到 P和Por共有几种可能)
        那么我们就已经把不包含A的情况求出来了，存在了PorL[n]中，下面就是要求包含一个A的情况，那么我们就得去除一个字符，从而给A留出位置。
        那么就相当于在数组的任意一个位置上加上A，那么数组就被分成左右两个部分了，而这两个部分当然就不能再有A了，实际上所有不包含A的情况都已经在数组PorL中计算过了，
        而分成的子数组的长度又不会大于原数组的长度，所以我们直接在PorL中取值就行了，两个子数组的排列个数相乘，然后再把所有分割的情况累加起来就是最终结果


 */

// Solution: DP,
class Solution {
    private static final int M = (int)(1e9 + 7);
    public int checkRecord(int n) {
        long[] P = new long[n + 1]; // end with P and without A
        long[] PorL = new long[n + 1]; // end with P or L and without A

        PorL[0] = P[0] = P[1] = 1;
        PorL[1] = 2;

        for (int i = 2; i <= n; i++) {
            P[i] = PorL[i - 1];
            PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
        }

        long res = PorL[n];

        for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
            long s = (PorL[i] * PorL[n - i - 1]) % M;
            res = (res + s) % M;
        }
        return (int)res;
    }
}