/*
    662. Maximum Width of Binary Tree

    Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is the maximum width among all levels. The binary tree has the same structure as a full binary tree, but some nodes are null.

    The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level, where the null nodes between the end-nodes are also counted into the length calculation.

    Example 1:
    Input:

               1
             /   \
            3     2
           / \     \
          5   3     9

    Output: 4
    Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
    Example 2:
    Input:

              1
             /
            3
           / \
          5   3

    Output: 2
    Explanation: The maximum width existing in the third level with the length 2 (5,3).
    Example 3:
    Input:

              1
             / \
            3   2
           /
          5

    Output: 2
    Explanation: The maximum width existing in the second level with the length 2 (3,2).
    Example 4:
    Input:

              1
             / \
            3   2
           /     \
          5       9
         /         \
        6           7
    Output: 8
    Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).


    Note: Answer will in the range of 32-bit signed integer.

    Companies: Amazon

    Related Topics: Tree
 */

//Solution1: BFS, 将root的index设为1，则左孩子为 parent * 2,  右孩子为 parent * 2 + 1, 记录每一层的最大width，也就是 end - start + 1
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new ArrayDeque();
        Map<TreeNode, Integer> map = new HashMap();
        map.put(root, 1);
        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            int end = 0;
            int start = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (i == 0)
                    start = map.get(cur);
                if (i == size - 1)
                    end = map.get(cur);
                if (cur.left != null) {
                    map.put(cur.left, map.get(cur) * 2);
                    queue.offer(cur.left);

                }
                if (cur.right != null) {
                    map.put(cur.right, map.get(cur) * 2 + 1);
                    queue.offer(cur.right);
                }
            }
            res = Math.max(res, end - start + 1);
        }
        return res;
    }
}

//Solution2: DFS
class Solution {
    private int max = 1;
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        List<Integer> startOfLevel = new ArrayList(); //only record the start index of every level
        helper(root, 0, 1, startOfLevel);
        return max;
    }

    public void helper(TreeNode root, int level, int index, List<Integer> list) {
        if (root == null)
            return;
        if (level == startOfLevel.size())
            startOfLevel.add(index);
        max = Math.max(max, index + 1 - startOfLevel.get(level));
        helper(root.left, level + 1, index * 2, startOfLevel);
        helper(root.right, level + 1, index * 2 + 1, startOfLevel);
    }
}