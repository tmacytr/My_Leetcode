/*
    414. Third Maximum Number

    Given a non-empty array of integers, return the third maximum number in this array. If it does not exist, return the maximum number. The time complexity must be in O(n).

    Example 1:
    Input: [3, 2, 1]

    Output: 1

    Explanation: The third maximum is 1.
    Example 2:
    Input: [1, 2]

    Output: 2

    Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
    Example 3:
    Input: [2, 2, 3, 1]

    Output: 1

    Explanation: Note that the third maximum here means the third maximum distinct number.
    Both numbers with value 2 are both considered as second maximum.

    Companies: Amazon

    Related Topics: Array

    Similar Questions: Kth Largest Element in an Array
 */

//Solution1: myself
class Solution {
    public int thirdMax(int[] nums) {
        Integer max1 = nums[0], max2 = null, max3 = null;
        for (Integer num : nums) {
            if (num.equals(max1) || num.equals(max2) || num.equals(max3))
                continue;
            if (max2 == null) {
                max2 = Math.min(max1, num);
                max1 = Math.max(max1, num);
            } else {
                int temp1 = max1;
                int temp2 = max2;
                max1 = Math.max(max1, num);
                max2 = Math.max(max2, Math.min(temp1, num));
                max3 = max3 == null ? Math.min(temp2, num) : Math.max(max3, Math.min(temp2, num));
            }
        }
        return max3 != null ? max3 : max1;
    }
}

//Solution2: more concise
class Solution {
    public int thirdMax(int[] nums) {
        Integer max1 = nums[0], max2 = null, max3 = null;
        for (Integer num : nums) {
            if (num.equals(max1) || num.equals(max2) || num.equals(max3))
                continue;
            if (max1 == null || num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (max2 == null || num > max2) {
                max3 = max2;
                max2 = num;
            } else if (max3 == null || num > max3) {
                max3 = num;
            }
        }
        return max3 != null ? max3 : max1;
    }
}