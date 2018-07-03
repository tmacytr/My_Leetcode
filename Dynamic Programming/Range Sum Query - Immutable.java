/*
	Range Sum Query - Immutable
	Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

	Example:
	Given nums = [-2, 0, 3, -5, 2, -1]

	sumRange(0, 2) -> 1
	sumRange(2, 5) -> -1
	sumRange(0, 5) -> -3
	Note:
	You may assume that the array does not change.
	There are many calls to sumRange function.
	Tags:Dynamic Programming
*/

// 累加和数组 求sum(i, j) -> sum[j] - sum[i - 1]
class NumArray {
    int[] sum;
    public NumArray(int[] nums) {
        sum = new int[nums.length];
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            num += nums[i];
            sum[i] = num;
        }
    }
    
    public int sumRange(int i, int j) {
        return i - 1 >= 0 ? sum[j] - sum[i - 1] : sum[j];
    }
}