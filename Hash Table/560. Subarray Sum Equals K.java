/*
    560. Subarray Sum Equals K

    Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

    Example 1:
    Input:nums = [1,1,1], k = 2
    Output: 2
    Note:
    The length of the array is in range [1, 20,000].
    The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

    Companies: Google

    Related Topics: Array, HashTable

    Similar Questions:
        1.Two Sum
        2. Continuous Subarray Sum
        3. Subarray Product Less Than K
        4. Find Pivot Index
 */

//Solution1: brute force
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return 0;
        int[] preSum = new int[nums.length];
        int res = 0;
        preSum[0] = nums[0];
        for (int i = 0; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            if (preSum[i] == k)
                res++;
            for (int j = i + 1; j < nums.length; j++) {
                if (preSum[j] - preSum[i] == k)
                    res++;
            }
        }
        return res;
    }
}

//Solution2: use map instead of
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return 0;
        int res = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k)
                res++;
            if (map.containsKey(sum - k)) {
                res += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }

    public int subArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int res = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) {
                res++;
            }
            if (map.containsKey(sum - k)) {
                res += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
        
    }
}