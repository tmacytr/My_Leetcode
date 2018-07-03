    /*
	Binary Tree Longest Consecutive Sequence
	Given a binary tree, find the length of the longest consecutive sequence path.

	The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).

	For example,
	   1
	    \
	     3
	    / \
	   2   4
	        \
	         5
	Longest consecutive sequence path is 3-4-5, so return 3.
	   2
	    \
	     3
	    /
	   2
	  /
	 1
	Longest consecutive sequence path is 2-3,not3-2-1, so return 2.

	Companies: Google
	Related Topics: Tree
	Similar Questions: Longest Consecutive Sequence, Binary Tree Longest Consecutive Sequence II
*/

// My solution, prefer
class Solution {
    int res = 1;
    public int longestConsecutive(TreeNode root) {
        if (root == null)
            return 0;
        helper(root, null, 1);
        return res;
    }
    
    public void helper(TreeNode cur, TreeNode parent, int len) {
        if (cur == null)
            return;
        if (parent != null) {
            len = parent.val + 1 == cur.val ? len + 1 : 1;
            res = Math.max(res, len);
        }
        helper(cur.left, cur, len);
        helper(cur.right, cur, len);  
    }
}

//Concise
public class Solution {
    int max;
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        max = 0;
        dfs(root, root, 0);
        return max;
    }
    public void dfs(TreeNode cur, TreeNode pre, int count) {
        if (cur == null) {
            return;
        }
        count = pre.val + 1 == cur.val ? count + 1 : 1;
        max = Math.max(count, max);
        dfs(cur.left, cur, count);
        dfs(cur.right, cur, count);
    }
}