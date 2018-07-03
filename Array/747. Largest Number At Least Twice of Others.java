/*
    747. Largest Number At Least Twice of Others

    In a given integer array nums, there is always exactly one largest element.

    Find whether the largest element in the array is at least twice as much as every other number in the array.

    If it is, return the index of the largest element, otherwise return -1.

    Example 1:

    Input: nums = [3, 6, 1, 0]
    Output: 1
    Explanation: 6 is the largest integer, and for every other number in the array x,
    6 is more than twice as big as x.  The index of value 6 is 1, so we return 1.


    Example 2:

    Input: nums = [1, 2, 3, 4]
    Output: -1
    Explanation: 4 isn't at least as big as twice the value of 3, so we return -1.


    Note:

    nums will have a length in the range [1, 50].
    Every nums[i] will be an integer in the range [0, 99].

    Companies: Google
    Related Topics: Array
 */

// Prefer One pass, find the secondMax and firstMax, make sure the firstMax is larger than the secondMax * 2
class Solution {
    public int dominantIndex(int[] nums) {
        int firstMax = 0, secondMax = 0, res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > firstMax) {
                secondMax = firstMax;
                firstMax = nums[i];
                res = i;
            } else if (nums[i] > secondMax) {
                secondMax = nums[i];
            }
        }

        return firstMax >= secondMax * 2 ? res : -1;
    }
}

// Two pass my solution
class Solution {
    public int dominantIndex(int[] nums) {
        if (nums.length == 1)
            return 0;
        int maxIndex = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (i == maxIndex)
                continue;
            if (nums[i] * 2 > nums[maxIndex])
                return -1;
        }
        return maxIndex;
    }
}