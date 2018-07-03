/*
	416. Partition Equal Subset Sum

	Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

	Note:
	Each of the array element will not exceed 100.
	The array size will not exceed 200.
	Example 1:

	Input: [1, 5, 11, 5]

	Output: true

	Explanation: The array can be partitioned as [1, 5, 5] and [11].
	Example 2:

	Input: [1, 2, 3, 5]

	Output: false

	Explanation: The array cannot be partitioned into equal sum subsets.
*/

// Solution1: space: O(mn), dp[i][j]: whether we can use first i numbers to sum to j, dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]],
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (nums.length == 1 || sum % 2 != 0)
            return false;
        int target = sum / 2;
        int m = nums.length;
        boolean[][] dp = new boolean[m + 1][target + 1];
        
        dp[0][0] = true;
        
        for (int i = 1; i < m + 1; i++)
            dp[i][0] = true;
        
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j <= target; j++) {
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[m][target];
    }
}

// Solution2: space: O(m)
class Solution {
    public boolean canPartition(int[] nums) {
        if (nums.length == 1)
            return false;
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (sum % 2 != 0)
            return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
        	// Why do we let i go from the right instead of left?
        	// The reason is that we have to get dp[i] from its previous loop dp[i-1]
            for (int j = target; j >= nums[i]; j--)
                dp[j] = dp[j] || dp[j - nums[i]];
        }
        return dp[target];
    }
}