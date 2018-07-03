/*
    Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, you only need to return the root node of any one of them.

    Two trees are duplicate if they have the same structure with same node values.

    Example 1:
            1
           / \
          2   3
         /   / \
        4   2   4
           /
          4
    The following are two duplicate subtrees:
          2
         /
        4
    and
        4
    Therefore, you need to return above trees' root in the form of a list.

    Companies: Google

    Related Topics: Tree

    Similar Questions:  Serialize and Deserialize Binary/TreeSerialize and Deserialize BST/Construct String from Binary Tree
 */

// Solution1: O(n^2)
// Time Complexity: O(N^2) where N is the number of nodes in the tree. We visit each node once, but each creation of serial may take O(N) work. Space Complexity: O(N^2)
class Solution {
    HashMap<String, Integer> map;
    List<TreeNode> res;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        map = new HashMap();
        res = new ArrayList();
        collect(root);
        return res;
    }

    private String collect(TreeNode root) {
        if (root == null)
            return "#";
        String serial = root.val + "," + collect(root.left) + "," + collect(root.right);
        map.put(serial, map.getOrDefault(serial, 0) + 1);
        if (map.get(serial) == 2)
            res.add(root);
        return serial;
    }
}

// Solution2: O(n)
class Solution {
    int t = 1;
    HashMap<String, Integer> trees;
    HashMap<Integer, Integer> count;
    List<TreeNode> res;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        trees = new HashMap();
        count = new HashMap();
        res = new ArrayList();
        lookup(root);
        return res;
    }

    private int lookup(TreeNode root) {
        if (root == null)
            return 0;
        String serial = root.val + "," + lookup(root.left) + "," + lookup(root.right);
        /*
            int uid = trees.computeIfAbsent(serial, x-> t++) means something like:
            if serial is not contained in trees, then trees.put(serial, t++) [make the value of this key serial equal to t, and then incremement t]
            and after, uid = trees.get(serial).
         */
        int uid = trees.computeIfAbsent(serial, x -> t++);
        count.put(uid, count.getOrDefault(uid, 0) + 1);
        if (count.get(uid) == 2)
            res.add(root);
        return uid;
    }
}