/*
    611. Valid Triangle Number

    Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
    Example 1:
    Input: [2,2,3,4]
    Output: 3
    Explanation:
    Valid combinations are:
    2,3,4 (using the first 2)
    2,3,4 (using the second 2)
    2,2,3
    Note:
    The length of the given array won't exceed 1000.
    The integers in the given array are in the range of [0, 1000].

    Companies: Expedia
    Related Topics: Array
    Similar Questions: 3Sum Smaller
 */

//Solution1: O(n^2) Assume a is the longest edge, b and c are shorter ones, to form a triangle, they need to satisfy len(b) + len(c) > len(a).
class Solution {
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3)
            return 0;
        Arrays.sort(nums);
        int res = 0;
        for (int right = 2; right < nums.length; right++) {
            int left = 0;
            int mid = right - 1;
            while (left < mid) {
                if (nums[left] + nums[mid] > nums[right]) {
                    res += mid - left;
                    mid--;
                } else {
                    left++;
                }
            }
        }
        return res;
    }
}