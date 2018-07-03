/*
	Same Tree 
	Given two binary trees, write a function to check if they are equal or not.

	Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
*/

public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null  || q == null) {
            return false;
        }
        
        if (p.val == q.val) {
            boolean leftTree = isSameTree(p.left, q.left);
            boolean rightTree = isSameTree(p.right, q.right);
            return leftTree && rightTree;
        } else {
            return false;
        }
    }
}