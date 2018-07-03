/*
	366. Find Leaves of Binary Tree

	Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.

	Example:
	Given binary tree 
	          1
	         / \
	        2   3
	       / \     
	      4   5    
	Returns [4, 5, 3], [2], [1].

	Explanation:
	1. Removing the leaves [4, 5, 3] would result in this tree:

	          1
	         / 
	        2          
	2. Now removing the leaf [2] would result in this tree:

	          1          
	3. Now removing the leaf [1] would result in the empty tree:

	          []         
	Returns [4, 5, 3], [2], [1].
*/


// Post order,每一个节点从左子节点和右子节点分开走可以得到两个高度，由于成为叶节点的条件是左右子节点都为空，所以我们取左右子节点中较大值加1为当前节点的高度，知道了高度值就可以将节点值加入到结果res中的正确位置了
// 在我们将 叶子节点高度为0，空节点高度为-1
class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList();
        if (root == null)
            return res;
        height(root, res);
        return res;
    }
    
    public int height(TreeNode root, List<List<Integer>> res) {
        if (root == null)
            return -1;
        int level = 1 + Math.max(height(root.left, res), height(root.right, res));
        if (res.size() <= level) {
            res.add(new ArrayList());
        }
        res.get(level).add(root.val);
        return level;
    }
}