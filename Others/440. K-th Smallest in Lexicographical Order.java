/*
    440. K-th Smallest in Lexicographical Order
    Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.

    Note: 1 ≤ k ≤ n ≤ 109.

    Example:

    Input:
    n: 13   k: 2

    Output:
    10

    Explanation:
    The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.

    Company: Hulu
*/
/*

    总体思路如下：
        有一个当前数字 i ，需要计算的是 i 到 i + 1 按照 lexicographical 排序的差，也就是在 lexicographical 排序中 i + 1 比 i 大多少，我们叫这个值为 step 。
        case 1： step <= k，那样当前数字就变成 i + 1 ，然后 k 减去 step 作为新的 k 。
        case 2： step > k，那样就只将当前数字加上 lexicographical 排序中的 1 ，又因为 step 这时候必然是大于 1 ，所以不可能是 [11, 12, 13, …] 这种情况，只能是 [1, 10, 100, .., 2, 20, ..] 这种，所以 i 就变成 i * 10 ，k 自减 1 。
        重复上述步骤直到 k 变成 0 ，此时当前的数字就是第 k 个。

        怎么计算 i 到 i + 1 的 step 呢？我们将这个问题概括成，计算 n1 到 n2 的 step 。假设当前数字为 curr ，另 n1 = curr， n2 = curr + 1 ，如果把这串数字看成树，那 n1 始终是 n2 的右边那个节点，是不是想到了层级遍历？比如要计算 1 到 2 的步数，在第 1 层，这俩差 1 ；但 1 下面还有 10 ~ 19 ，19 右边的数是 20 ，所以 2 - 1 （lexicographical） = （2 - 1） + （20 - 10） = 11
        case 1：n2 ≤ n + 1 ，说明 n1 所在层的最右节点存在，step 叠加自然顺序 n2 - n1
        case 2：n2 > n + 1 ，说明 n2 在 n1 到 n 之间，step 叠加自然顺序 n - n1
        然后进入下一层，n1 = n1 10 ， n2 = n2 10 ，重复上述步骤直到 n1 > n


 */

//时间复杂度是 O(log10n)
class Solution {
    public int findKthNumber(int n, int k) {
        int cur = 1;
        k = k - 1;
        while (k > 0) {
            int steps = helper(n, cur, cur + 1);
            if (steps <= k) {
                cur += 1;
                k -= steps;
            } else {
                cur *= 10;
                k -= 1;
            }
        }
        return cur;
    }

    // 计算两个数字之间的 lexicographical 差
    private int helper(int n, long n1, long n2) {
        int steps = 0;
        while (n1 <= n) {
            // 判断当前层有多少个数字，假如是满的，那么就是n2-n1，如果不是满的，那么就是n+1-n1。
            if (n2 > n + 1) {
                steps += n + 1 - n1;
            } else {
                steps += n2 - n1;
            }
            // 到下一层（n1 和 n2 始终同一层）
            n1 *= 10;
            n2 *= 10;
        }
        return steps;
    }
}