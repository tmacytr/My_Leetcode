/*
	Longest Increasing Path in a Matrix
	Given an integer matrix, find the length of the longest increasing path.

	From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

	Example 1:

	nums = [
	  [9,9,4],
	  [6,6,8],
	  [2,1,1]
	]
	Return 4
	The longest increasing path is [1, 2, 6, 9].

	Example 2:

	nums = [
	  [3,4,5],
	  [3,2,6],
	  [2,2,1]
	]
	Return 4
	The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 */

/*
    Key point: 用一个state数组来存储每一位上的最长路径数，set the larger number to 1， if find smaller near the larger one just add 1  to the smaller one
 */
// Solution1: DFS, time: O(mn), space: O(mn)
public class Solution {
    private static final int[] shift = {0, 1, 0, -1, 0};
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[][] state = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfs(matrix, i, j, state));
            }
        }
        return res;
    }

    //dfs solution1
    public int dfs(int[][] matrix, int x, int y, int[][] state) {
        if (state[x][y] > 0) {
            return state[x][y];
        }
        int max = 0;
        for (int i = 0; i < 4; i++) {
            int nearX = x + shift[i];
            int nearY = y + shift[i + 1];
            if (nearX >= 0 && nearX < matrix.length && nearY >= 0 && nearY < matrix[0].length && matrix[nearX][nearY] > matrix[x][y]) {
                max = Math.max(max, dfs(matrix, nearX, nearY, state));
            }
        }
        state[x][y] = 1 + max;
        return state[x][y];
    }

    //dfs solution2 prefer
    private int dfs(int[][] matrix, int x, int y, int m, int n, int[][] state) {
        if (state[x][y] > 0)
            return state[x][y];
        for (int i = 0; i < 4; i++) {
            int row = x + shift[i];
            int col = y + shift[i + 1];
            if (row < 0 || col < 0 || row > m - 1 || col > n - 1 || matrix[row][col] <= matrix[x][y])
                continue;
            state[x][y] = Math.max(state[x][y], dfs(matrix, row, col, m, n, state));
        }
        return ++state[x][y];
    }
}


// Solution2: BFS + Topological sort,  time: O(mn), space: O(mn)
// 计算每个节点的出度，也就是节点周围有多少点比该节点大。 之后将叶子节点先进队列遍历，一遍更新队列每个点的出度，出度减为0就进队列。看最后一共遍历了多少层就是所求max 长度
class Solution {
    private static final int[] shift = {0, 1, 0, -1, 0};
    private int m, n;
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        m = matrix.length;
        n = matrix[0].length;
        // calculate outdegrees
        int[][] outdegree = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 4; k++) {
                    int x = i + shift[k];
                    int y = j + shift[k + 1];
                    if (x >= 0 && y >= 0 && x < m && y < n && matrix[i][j] < matrix[x][y])
                        outdegree[i][j]++;
                }
            }
        }

        // find leaves who have zero out degree as the initial level
        List<int[]> leaves = new ArrayList();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (outdegree[i][j] == 0)
                    leaves.add(new int[]{i, j});
            }
        }

        // remove leaves level by level in topological order
        int height = 0;
        while (!leaves.isEmpty()) {
            height++;
            List<int[]> newLeaves = new ArrayList();
            for (int[] node : leaves) {
                for (int i = 0; i < 4; i++) {
                    int x = node[0] + shift[i];
                    int y = node[1] + shift[i + 1];
                    if (x < 0 || y < 0 || x >= m || y >= n || (matrix[node[0]][node[1]] <= matrix[x][y])
                        continue;
                    if (--outdegree[x][y] == 0)
                        newLeaves.add(new int[]{x, y});
                }
            }
            leaves = newLeaves;
        }
        return height;
    }
}
