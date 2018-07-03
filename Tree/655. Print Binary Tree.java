/*
    655. Print Binary Tree

    Print a binary tree in an m*n 2D string array following these rules:

    The row number m should be equal to the height of the given binary tree.
    The column number n should always be an odd number.
    The root node's value (in string format) should be put in the exactly middle of the first row it can be put. The column and the row where the root node belongs will separate the rest space into two parts (left-bottom part and right-bottom part). You should print the left subtree in the left-bottom part and print the right subtree in the right-bottom part. The left-bottom part and the right-bottom part should have the same size. Even if one subtree is none while the other is not, you don't need to print anything for the none subtree but still need to leave the space as large as that for the other subtree. However, if two subtrees are none, then you don't need to leave space for both of them.
    Each unused space should contain an empty string "".
    Print the subtrees following the same rules.
    Example 1:
    Input:
         1
        /
       2
    Output:
    [["", "1", ""],
     ["2", "", ""]]
    Example 2:
    Input:
         1
        / \
       2   3
        \
         4
    Output:
    [["", "", "", "1", "", "", ""],
     ["", "2", "", "", "", "3", ""],
     ["", "", "4", "", "", "", ""]]
    Example 3:
    Input:
          1
         / \
        2   5
       /
      3
     /
    4
    Output:

    [["",  "",  "", "",  "", "", "", "1", "",  "",  "",  "",  "", "", ""]
     ["",  "",  "", "2", "", "", "", "",  "",  "",  "",  "5", "", "", ""]
     ["",  "3", "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]
     ["4", "",  "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]]
    Note: The height of binary tree is in the range of [1, 10].

    Companies: Poynt

    Related Topics: Tree
 */

/*
    计算树的高度并构建默认的print string list是关键
 */

//Solution1: Itertaive
class Solution {
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> res = new ArrayList();
        if (root == null)
            return res;
        int rows = getHeight(root);
        int cols =  (int)Math.pow(2, rows) - 1;

        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList();
            for (int j = 0; j < cols; j++) {
                row.add("");
            }
            res.add(row);
        }

        Queue<TreeNode> queue = new LinkedList(); //不能用 ArrayDeque
        Queue<int[]> indexQueue = new LinkedList();
        queue.offer(root);
        indexQueue.offer(new int[]{0, cols - 1}); //太精妙了，每个node的位置，就是(left + right) / 2
        int row = -1;

        while (!queue.isEmpty()) {
            row++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                int[] index = indexQueue.poll();

                int left = index[0];
                int right = index[1];
                int mid = left + (right - left) / 2;
                res.get(row).set(mid, String.valueOf(cur.val));

                if (cur.left != null) {
                    queue.offer(cur.left);
                    indexQueue.offer(new int[] {left, mid - 1});
                }

                if (cur.right != null) {
                    queue.offer(cur.right);
                    indexQueue.offer(new int[] {mid + 1, right});
                }
            }
        }

        return res;
    }

    private int getHeight(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}

//Solution2: recursive
class Solution {
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> res = new ArrayList();
        if (root == null)
            return res;
        int rows = getHeight(root);
        int cols = (int)Math.pow(2, rows) - 1;
        List<String> rowItem = new ArrayList();
        for (int i = 0; i < cols; i++)
            rowItem.add("");
        for (int i = 0; i < rows; i++)
            res.add(new ArrayList(rowItem));
        dfs(root, res, 0, rows, 0, cols - 1);
        return res;
    }

    private void dfs(TreeNode root, List<List<String>> res, int curRow, int totalRow, int left, int right) {
        if (root == null || curRow == totalRow)
            return;
        res.get(curRow).set((left + right) / 2, String.valueOf(root.val));
        dfs(root.left, res, curRow + 1, totalRow, left, ((left + right) / 2) - 1);
        dfs(root.right, res, curRow + 1, totalRow, ((left + right) / 2) + 1, right);
    }

    private int getHeight(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}