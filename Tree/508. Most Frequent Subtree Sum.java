/*
    508. Most Frequent Subtree Sum

    Given the root of a tree, you are asked to find the most frequent subtree sum. The subtree sum of a node is defined as the sum of all the node values formed by the subtree rooted at that node (including the node itself). So what is the most frequent subtree sum value? If there is a tie, return all the values with the highest frequency in any order.

    Examples 1
    Input:

      5
     /  \
    2   -3
    return [2, -3, 4], since all the values happen only once, return all of them in any order.
    Examples 2
    Input:

      5
     /  \
    2   -5
    return [2], since 2 happens twice, however -5 only occur once.
    Note: You may assume the sum of values in any subtree is in the range of 32-bit signed integer.

    Companies: Amazon

    Related Topics: Hash Table, Tree

    Similar Questions: Subtree of Another Tree
 */

//一个节点的子树和是指其所有子节点以及子节点的子节点的值之和, 要比较所有节点的子树和，那就要求每遇到一个节点，都要以这个节点为根节点，计算其子树和
class Solution {
    Map<Integer, Integer> map;
    int maxCount = 0;
    public int[] findFrequentTreeSum(TreeNode root) {
        map = new HashMap();
        maxCount = 0;
        helper(root);
        List<Integer> res = new ArrayList();
        for (int key : map.keySet()) {
            if (map.get(key) == maxCount)
                res.add(key);
        }
        int[] ans = new int[res.size()];
        for (int i = 0; i < ans.length; i++)
            ans[i] = res.get(i);
        return ans;
    }

    private int helper(TreeNode root) {
        if (root == null)
            return 0;
        int left = helper(root.left);
        int right = helper(root.right);
        int sum = left + right + root.val;
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        maxCount = Math.max(map.get(sum), maxCount);
        return sum;
    }
}