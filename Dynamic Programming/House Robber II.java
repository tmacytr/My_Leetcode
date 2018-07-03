/*
	House Robber II 
	Note: This is an extension of House Robber.

	After robbing those houses on that street, the thief has found himself a new place for his thievery so that he will not get too much attention. This time, all houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, the security system for these houses remain the same as for those in the previous street.

	Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

	Credits:
	Special thanks to @Freezen for adding this problem and creating all test cases
*/

//Solution1: Space O(1) prefer, use the House Robber as max function.
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];
        return Math.max(rob(nums, 0, nums.length - 1), rob(nums, 1, nums.length));
    }
    
    public int rob(int[] nums, int start, int end) {
        int robbedPre = 0;
        int notRobbedPre = 0;
        
        for (int i = start; i < end; i++) {
            int curRobbed = notRobbedPre + nums[i];
            
            int curNotRobbed = Math.max(notRobbedPre, robbedPre);
            notRobbedPre = curNotRobbed;
            robbedPre = curRobbed;
        }
        return Math.max(notRobbedPre, robbedPre);
    }
}


//Solution2: space O(n) myself

class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];
        int[] dp = new int[nums.length + 1];
        
        dp[0] = 0;
        dp[1] = nums[0];
        
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        
        int res = dp[nums.length - 1];
        
        dp[1] = 0;

        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return Math.max(res, dp[nums.length]);
        
    }
}