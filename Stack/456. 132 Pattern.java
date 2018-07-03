/*
    456. 132 Pattern

    Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in the list.

    Note: n will be less than 15,000.

    Example 1:
    Input: [1, 2, 3, 4]

    Output: False

    Explanation: There is no 132 pattern in the sequence.
    Example 2:
    Input: [3, 1, 4, 2]

    Output: True

    Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
    Example 3:
    Input: [-1, 3, 2, 0]

    Output: True

    Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
 */

//Solution1: brute forece O(n^2)
class Solution {
    public boolean find132pattern(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 1; i++) {
            min  = Math.min(min, nums[i]);
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i] && min < nums[j]) //此时 min肯定不等于nums[i], nums[j]不可能即大于nums[i] 又小于nums[i]
                    return true;
            }
        }
        return false;
    }
}

//Solution2: Stack, O(n), 维护一个num3 也就是第三个数， 必须从后往前遍历
class Solution {
    public boolean find132pattern(int[] nums) {
        int num3 = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < num3)
                return true;
            else {
                while (!stack.isEmpty() && nums[i] > stack.peek()) {
                    num3 = stack.pop();
                }
                stack.push(nums[i]);
            }
        }
        return false;
    }
}