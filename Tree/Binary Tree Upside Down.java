/*
	Binary Tree Upside Down
	Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.

	For example:
	Given a binary tree {1,2,3,4,5},
	    1
	   / \
	  2   3
	 / \
	4   5
	return the root of the binary tree [4,5,2,#,#,3,1].
	   4
	  / \
	 5   2
	    / \
	   3   1  
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

//对于一个根节点来说，我们的目标是将其左子节点变为根节点，右子节点变为左子节点，原根节点变为右子节点，那么我们首先判断这个根节点是否存在，且其有没有左子节点，如果不满足这两个条件的话，直接返回即可，不需要翻转操作。
public class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null)
            return root;
        // inorder traverse
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        
        root.left.left = root.right;
        root.left.right = root;
        
        root.left = null;
        root.right = null;
        return newRoot;
    }
}

//iterative
public class Solution {
    public TreeNode UpsideDownBinaryTree(TreeNode root) {
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode next = null;
        TreeNode temp = null;

        while (cur != null) {
            next = cur.left;
            cur.left = temp;
            temp = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }
}