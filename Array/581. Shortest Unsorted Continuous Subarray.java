/*
    581. Shortest Unsorted Continuous Subarray

    Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order, too.

    You need to find the shortest such subarray and output its length.

    Example 1:
    Input: [2, 6, 4, 8, 10, 9, 15]
    Output: 5
    Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
    Note:
    Then length of the input array is in range [1, 10,000].
    The input array may contain duplicates, so ascending order here means <=.

    Companies: Google, liveramp

    Related Topics: Array
 */

// Solution1: my solution, o(nlogn) time, two pass, o(n) space
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1)
            return 0;
        int[] original = Arrays.copyOf(nums, nums.length);

        Arrays.sort(nums);

        int left = -1;
        int right = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != original[i]) {
                left = i;
                break;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != original[i]) {
                right = i;
                break;
            }
        }
        if (left == -1 && right == -1)
            return 0;
        return right - left + 1;
    }
}

//Solution2: o(n) time, one pass, no space
// 找最右边i一个小于左边max的数的index(end)， 以及找最左边大于右边min的数的index(start)， start ~ end即为我们所求的最短sort 子序列
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int len = nums.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int start = -1, end = -1;

        for (int i = 0; i < len; i++) {
            max = Math.max(max, nums[i]);
            min = math.min(min, nums[len - i - 1]);

            if (nums[i] < max)
                end = i;
            if (nums[len - i - 1] > min)
                start = len - i - 1;
        }

        return start == -1 ? 0 :end - start + 1; //the entire array is already sorted if start == -1
    }
}