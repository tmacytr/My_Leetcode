/*
    368. Largest Divisible Subset

    Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.

    If there are multiple solutions, return any subset is fine.

    Example 1:

    nums: [1,2,3]

    Result: [1,2] (of course, [1,3] will also be ok)
    Example 2:

    nums: [1,2,4,8]

    Result: [1,2,4,8]

    Companies: Google

    Related Topics: DP
 */

/*
    较小数对较大数取余一定不为0，那么问题就变成了看较大数能不能整除这个较小数。

    如果数组是无序的，处理起来就比较麻烦，所以我们首先可以先给数组排序，这样我们每次就只要看后面的数字能否整除前面的数字。

    定义一个动态数组dp，其中dp[i]表示到数字nums[i]位置最大可整除的子集合的长度，还需要一个一维数组pre，来保存上一个能整除的数字的index，我们可以从后往前遍历数组，对于某个数字再遍历到末尾，
    在这个过程中，如果nums[j]能整除nums[i], 且dp[i] < dp[j] + 1的话，更新dp[i]和pre[i]，如果dp[i]大于max了，还要更新max和max index，最后循环结束后，我们来填res数字，根据pre数组来找到每一个数字
 */

//
class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> res = new ArrayList();
        int m = nums.length;
        int[] dp = new int[m];
        int[] pre = new int[m];
        Arrays.sort(nums);
        int max = 0;
        int maxIndex = -1;

        for (int i = 0; i < m; i++) {
            dp[i] = 1;
            pre[i] = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    if (1 + dp[j] > dp[i]) {
                        dp[i] = 1 + dp[j];
                        pre[i] = j;
                    }
                }
            }
            if (dp[i] > max) {
                max = dp[i];
                maxIndex = i;
            }
        }

        while (maxIndex != -1) {
            res.add(nums[maxIndex]);
            maxIndex = pre[maxIndex];
        }
        return res;
    }
}