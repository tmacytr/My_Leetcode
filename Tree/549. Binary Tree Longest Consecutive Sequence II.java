/*
    549. Binary Tree Longest Consecutive Sequence II

    Given a binary tree, you need to find the length of Longest Consecutive Path in Binary Tree.

    Especially, this path can be either increasing or decreasing. For example, [1,2,3,4] and [4,3,2,1] are both considered valid, but the path [1,2,4,3] is not valid. On the other hand, the path can be in the child-Parent-child order, where not necessarily be parent-child order.

    Example 1:
    Input:
            1
           / \
          2   3
    Output: 2
    Explanation: The longest consecutive path is [1, 2] or [2, 1].
    Example 2:
    Input:
            2
           / \
          1   3
    Output: 3
    Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].
    Note: All the values of tree nodes are in the range of [-1e7, 1e7].

    Companies: Google

    Related Topics: Tree

    Similar Questions: Binary Tree Longest Consecutive Sequence
 */

//Solution1: using a
class Solution {
    int res = 0;
    public int longestConsecutive(TreeNode root) {
        helper(root);
        return res;
    }

    public int[] helper(TreeNode root) {
        if (root == null)
            return new int[]{0, 0};
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        int inc = 1, desc = 1;
        if (root.left != null) {
            if (root.val - root.left.val == 1)
                desc = left[1] + 1;
            else if (root.val - root.left.val == -1)
                inc = left[0] + 1;
        }

        if (root.right != null) {
            if (root.val - root.right.val == 1) {
                desc = Math.max(desc, right[1] + 1);
            } else if (root.val - root.right.val == -1)
                inc = Math.max(inc, right[0] + 1);
        }
        res = Math.max(res, inc + desc - 1);
        return new int[]{inc, desc};
    }
}