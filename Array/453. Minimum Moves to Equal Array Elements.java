/*
    Given a non-empty integer array of size n, find the minimum number of moves required to make all array elements equal, where a move is incrementing n - 1 elements by 1.

    Example:

    Input:
    [1,2,3]

    Output:
    3

    Explanation:
    Only three moves are needed (remember each move increments two elements):

    [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]

    Companies: Indeed, Coursera

    Related Topics: Math

    Similar Questions: Minimum Moves to Equal Array Elements II
 */

//Solution1: 需要换一个角度来看问题，其实给n-1个数字加1，效果等同于给那个未被选中的数字减1, 那么问题也可能转化为，
//           将所有数字都减小到最小值，这样难度就大大降低了，我们只要先找到最小值，然后累加每个数跟最小值之间的差值即可
class Solution {
    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        int res = 0;

        for (int num : nums)
            min = Math.min(num, min);
        for (int num : nums)
            res += num - min;
        return res;
    }
}