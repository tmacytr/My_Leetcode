/*
    687. Longest Univalue Path

    Given a binary tree, find the length of the longest path where each node in the path has the same value. This path may or may not pass through the root.

    Note: The length of path between two nodes is represented by the number of edges between them.

    Example 1:

    Input:

                  5
                 / \
                4   5
               / \   \
              1   1   5
    Output: 2
    Example 2:

    Input:

                  1
                 / \
                4   5
               / \   \
              4   4   5
    Output:

    2
    Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
 */

// 求最大路径， 两个节点的路径长度=两个节点之间的边数，这里用 post order遍历
class Solution {
    int res = 0;
    public int longestUnivaluePath(TreeNode root) {
        if (root == null)
            return 0;
        helper(root);
        return res;
    }

    public int helper(TreeNode root) {
        if (root == null)
            return 0;
        int left = helper(root.left);
        int right = helper(root.right);
        int leftMax = 0;
        int rightMax = 0;
        if (root.left != null && root.val == root.left.val)
            leftMax = left + 1;
        if (root.right != null && root.val == root.right.val)
            rightMax = right + 1;
        res = Math.max(res, leftMax + rightMax);
        return Math.max(leftMax, rightMax);
    }
}