/*
    724. Find Pivot Index

    Given an array of integers nums, write a method that returns the "pivot" index of this array.

    We define the pivot index as the index where the sum of the numbers to the left of the index is equal to the sum of the numbers to the right of the index.

    If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most pivot index.

    Example 1:
    Input:
    nums = [1, 7, 3, 6, 5, 6]
    Output: 3
    Explanation:
    The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of numbers to the right of index 3.
    Also, 3 is the first index where this occurs.
    Example 2:
    Input:
    nums = [1, 2, 3]
    Output: -1
    Explanation:
    There is no index that satisfies the conditions in the problem statement.
    Note:

    The length of nums will be in the range [0, 10000].
    Each element nums[i] will be an integer in the range [-1000, 1000].

    Companies: Coupang, radius

    Related Topics: Array, Hashtable

    Similar Questions: Subarray Sum Equals K
 */

// Solution1: use extra hashmap
class Solution {
    public int pivotIndex(int[] nums) {
        if (nums == null || nums.length == 0)
            return -1;
        HashMap<Integer, Integer> map = new HashMap();
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            map.put(i, sum);
        }
        for (int i = 0; i < n; i++) {
            if (map.get(i) - nums[i] == map.get(n - 1) - map.get(i))
                return i;
        }
        return -1;
    }
}

//Solution2: not using hashmap
class Solution {
    public int pivotIndex(int[] nums) {
        if (nums == null || nums.length == 0)
            return -1;
        int sum = 0;
        int n = nums.length;
        for (int num : nums)
            sum += num;
        int preSum = 0;
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            if (preSum - nums[i] == sum - preSum)
                return i;
        }
        return -1;
    }
}