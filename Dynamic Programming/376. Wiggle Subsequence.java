/*
    376. Wiggle Subsequence

    A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with fewer than two elements is trivially a wiggle sequence.

    For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two differences are positive and the second because its last difference is zero.

    Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence, leaving the remaining elements in their original order.

    Examples:
    Input: [1,7,4,9,2,5]
    Output: 6
    The entire sequence is a wiggle sequence.

    Input: [1,17,5,10,13,15,10,5,16,8]
    Output: 7
    There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].

    Input: [1,2,3,4,5,6,7,8,9]
    Output: 2
    Follow up:
    Can you do it in O(n) time?
 */

/*
    For every position in the array, there are only three possible statuses for it.

    up position, it means nums[i] > nums[i-1]
    down position, it means nums[i] < nums[i-1]
    equals to position, nums[i] == nums[i-1]
    So we can use two arrays up[] and down[] to record the max wiggle sequence length so far at index i.
    If nums[i] > nums[i-1], that means it wiggles up. the element before it must be a down position. so up[i] = down[i-1] + 1; down[i] keeps the same with before.
    If nums[i] < nums[i-1], that means it wiggles down. the element before it must be a up position. so down[i] = up[i-1] + 1; up[i] keeps the same with before.
    If nums[i] == nums[i-1], that means it will not change anything becasue it didn't wiggle at all. so both down[i] and up[i] keep the same.
 */

//Solution1: DP, O(n) time and space
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int n = nums.length;
        int[] up = new int[n];
        int[] down = new int[n];
        up[0] = down[0] = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = down[i - 1] + 1;
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                up[i] = up[i - 1];
                down[i] = up[i - 1] + 1;
            } else {
                down[i] = down[i - 1];
                up[i] = up[i - 1];
            }
        }
        return Math.max(up[n - 1], down[n - 1]);
    }
}

//Solution2: DP, O(1) space
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int up = 1;
        int down = 1;

        for (int i = 1; i < nums.length;; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            } else if (nums[i] < nums[i - 1]) {
                down = up + 1;
            }
        }
        return Math.max(up, down);
    }
}