/*
    740. Delete and Earn

    Given an array nums of integers, you can perform operations on the array.

    In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.

    You start with 0 points. Return the maximum number of points you can earn by applying such operations.

    Example 1:
    Input: nums = [3, 4, 2]
    Output: 6
    Explanation:
    Delete 4 to earn 4 points, consequently 3 is also deleted.
    Then, delete 2 to earn 2 points. 6 total points are earned.
    Example 2:
    Input: nums = [2, 2, 3, 3, 3, 4]
    Output: 9
    Explanation:
    Delete 3 to earn 3 points, deleting both 2's and the 4.
    Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
    9 total points are earned.
    Note:

    The length of nums is at most 20000.
    Each element nums[i] is an integer in the range [1, 10000].

    Companies: Akuna Capital

    Related Topics: DP

    Similar Questions: House Robber
 */

/*
    通过观察，只要选取了nums[n] 则nums[n - 1], nums[n + 1]都不能选取，因为我们可以转化为先求每个num出现的count，以此作为遍历的数组,比较count[n]， 和count[n - 1] + count[n + 1]大小
    也就转换成了 house robber问题
 */

//Solution1: DP
class Solution {
    public int deleteAndEarn(int[] nums) {
        int[] count = new int[10001];
        for (int num : nums)
            count[num]++;

        int[] dp = new int[10001];
        dp[1] = count[1];

        for (int i = 2; i <= 10000; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + i * count[i]);
        }
        return dp[10000];
    }
}

//Solution2: DP 这里的values[i] 等于上面的  count[i] * i
class Solution {
    public int deleteAndEarn(int[] nums) {
        int[] values = new int[10001];
        for (int num : nums)
            values[num] += num;

        int take = 0, skip = 0;
        for (int value : values) {
            int temp = Math.max(skip + value, take);
            skip = take;
            take = temp;
        }
        return take;
    }
}