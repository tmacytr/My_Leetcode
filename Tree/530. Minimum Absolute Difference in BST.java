/*
    530. Minimum Absolute Difference in BST

    Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

    Example:

    Input:

       1
        \
         3
        /
       2

    Output:
    1

    Explanation:
    The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
    Note: There are at least two nodes in this BST.

    Companies: Google

    Related Topics: Binary Search Tree

    Similar Questions: K-diff Pairs in an Array
 */

/*
    The most common idea is to first inOrder traverse the tree and compare the delta between each of the adjacent values.
    It’s guaranteed to have the correct answer because it is a BST thus inOrder traversal values are sorted.

    Time complexity O(N), space complexity O(1).
 */

// Solution1: inorder traverse, prefer
class Solution {
    int res = Integer.MAX_VALUE;
    TreeNode pre;
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return res;
    }

    private void inorder(TreeNode root) {
        if (root == null)
            return;
        inorder(root.left);
        if (pre != null)
            res = Math.min(res, root.val - pre.val);
        pre = root;
        inorder(root.right);
    }
}

/*
    What if it is not a BST? (Follow up of the problem):
        The idea is to put values in a TreeSet and then every time we can use O(lgN) time to lookup for the nearest values.
 */
// Solution2: Pre-Order traverse, O(NlgN) time
class Solution {
    int res = Integer.MAX_VALUE;
    TreeSet<Integer> set = new TreeSet();
    public int getMinimumDifference(TreeNode root) {
        if (root == null)
            return res;
        if (!set.isEmpty()) {
            if (set.floor(root.val) != null)
                res = Math.min(res, root.val - set.floor(root.val));
            if (set.ceiling(root.val) != null)
                res = Math.min(res, set.ceiling(root.val) - root.val);
        }

        set.add(root.val);
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        return res;
    }
}

//Solution3: Inorder, iterative
public int getMinimumDifference(TreeNode root) {
    int min = Integer.MAX_VALUE;
    Stack<TreeNode> stack = new Stack<>();
    TreeNode cur = root, pre = null;
    while (cur != null || !stack.empty()) {
        if (cur != null) {
            stack.push(cur);
            cur = cur.left;
        } else {
            cur = stack.pop();
            if (pre != null)
                min = Math.min(min, cur.val - pre.val);
            pre = cur;
            cur = cur.right;
        }
    }
    return min;
}

//Solution4: Morris traversal
public static int getMinimumDifference(TreeNode root) {
    int min = Integer.MAX_VALUE;
    int pre = Integer.MAX_VALUE;
    if(root == null) return min;
    TreeNode cur1 = root;
    TreeNode cur2 = null;
    while (cur1 != null) {
        cur2 = cur1.left;
        if (cur2 != null) {
            while (cur2.right != null && cur2.right != cur1) {
                cur2 = cur2.right;
            }
            if (cur2.right == null) {
                cur2.right = cur1;
                cur1 = cur1.left;
                continue;
            } else {
                cur2.right = null;
            }
        }
        min = Math.min(min, Math.abs(cur1.val - pre));
        pre = cur1.val;
        cur1 = cur1.right;
    }
    return min;
}