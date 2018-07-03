/*
    645. Set Mismatch

    The set S originally contains numbers from 1 to n. But unfortunately, due to the data error, one of the numbers in the set got duplicated to another number in the set, which results in repetition of one number and loss of another number.

    Given an array nums representing the data status of this set after the error. Your task is to firstly find the number occurs twice and then find the number that is missing. Return them in the form of an array.

    Example 1:
    Input: nums = [1,2,2,4]
    Output: [2,3]
    Note:
    The given array size will in the range [2, 10000].
    The given array's numbers won't have any order.

    Companies: Amazon

    Related Topics: HashTable, Math

    Similar Questions: Find the Duplicate Number
 */

//Solution1: HashSet
class Solution {
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i]))
                res[0] = nums[i];
        }

        for (int i = 1; i <= nums.length; i++) {
            if (!set.contains(i))
                res[1] = i;
        }
        return res;
    }
}

//Solution2:
class Solution {
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        for (int num : nums) {
            if (nums[Math.abs(num) - 1] < 0)
                res[0] = Math.abs(num);
            else
                nums[Math.abs(num) - 1] *= -1;
        }

        for (int i = 0; i < nums.length; i++)
            if (nums[i] > 0)
                res[1] = i + 1;
        return res;
    }
}