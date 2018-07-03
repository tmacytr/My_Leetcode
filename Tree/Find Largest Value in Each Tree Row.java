/*
	515. Find Largest Value in Each Tree Row

	You need to find the largest value in each row of a binary tree.

	Example:
	Input: 

	        1
	       / \
	      3   2
	     / \   \  
	    5   3   9 

	Output: [1, 3, 9]
*/

//Solution1: BFS
class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null)
            return res;
        Queue<TreeNode> queue = new ArrayDeque();
        queue.offer(root);
        
    
        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelMax = Integer.MIN_VALUE;        
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                levelMax = Math.max(cur.val, levelMax);
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
                
                if (i == size - 1)
                    res.add(levelMax);
            }
        }
        return res;
    }
}

//Solution2: DFS
class Solution {
    public List<Integer> largestValues(TreeNode root) {
        if (root == null)
            return new ArrayList();
        List<Integer> res = new ArrayList();
        helper(root, res, 0);
        return res;
    }
    
    public void helper(TreeNode root, List<Integer> res, int depth) {
        if (root == null)
            return;
        if (depth == res.size())
            res.add(root.val);
        else 
            res.set(depth, Math.max(root.val, res.get(depth)));
        helper(root.left, res, depth + 1);
        helper(root.right, res, depth + 1);
    }