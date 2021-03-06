/*
    413. Arithmetic Slices

    A sequence of number is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.

    For example, these are arithmetic sequence:

    1, 3, 5, 7, 9
    7, 7, 7, 7
    3, -1, -5, -9
    The following sequence is not arithmetic.

    1, 1, 2, 5, 7

    A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such that 0 <= P < Q < N.

    A slice (P, Q) of array A is called arithmetic if the sequence:
    A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.

    The function should return the number of arithmetic slices in the array A.


    Example:

    A = [1, 2, 3, 4]

    return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
 */

/*
    dp[i]表示，到i位置为止的算数切片的个数，那么我们从第三个数字开始遍历，如果当前数字和之前两个数字构成算数切片，

    那么我们更新dp[i]为dp[i-1]+1，然后res累加上dp[i]的值
 */
//Solution1: O(n) space
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        int[] dp = new int[A.length];
        int res = 0;
        for (int i = 2; i < A.length; i++) {
            if ((A[i] - A[i - 1]) == (A[i - 1] - A[i - 2])) {
                dp[i] = dp[i - 1] + 1;
            }
            res += dp[i];
        }
        return res;
    }
}

//Solution2: O(1) space
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0, cur = 0;
        for (int i = 2; i < A.length; i++) {
            if ((A[i] - A[i - 1]) == (A[i - 1] - A[i - 2])) {
                cur = cur + 1;
            } else {
                cur = 0;
            }
            res += cur;
        }
        return res;
    }
}