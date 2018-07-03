/*
    695. Max Area of Island

    Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

    Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

    Example 1:
    [[0,0,1,0,0,0,0,1,0,0,0,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,1,1,0,1,0,0,0,0,0,0,0,0],
     [0,1,0,0,1,1,0,0,1,0,1,0,0],
     [0,1,0,0,1,1,0,0,1,1,1,0,0],
     [0,0,0,0,0,0,0,0,0,0,1,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,0,0,0,0,0,0,1,1,0,0,0,0]]
    Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
    Example 2:
    [[0,0,0,0,0,0,0,0]]
    Given the above grid, return 0.
    Note: The length of each dimension in the given grid does not exceed 50.

    Companies: intuit

    Related Topics: array, dfs

    Similar Questions: Number of Islands, Island Perimeter
 */

//Solution1: dfs
class Solution {
    int res, m, n, localMax;
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        res = 0;
        m = grid.length;
        n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    localMax = 0;
                    helper(grid, i, j, visited);
                }
            }
        }
        return res;
    }

    private void helper(int[][] grid, int i, int j, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] || grid[i][j] != 1)
            return;
        visited[i][j] = true;
        localMax++;
        helper(grid, i + 1, j, visited);
        helper(grid, i - 1, j, visited);
        helper(grid, i, j + 1, visited);
        helper(grid, i, j - 1, visited);
        res = Math.max(res, localMax);
    }
}

//Solution2: not using global constant
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                if(grid[i][j] == 1)
                    max = Math.max(max, dfs(grid, i, j));
        return max;
    }

    public int dfs(int[][] grid, int i, int j){
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
            return 0;
        grid[i][j] = 0;
        return 1 + dfs(grid, i + 1, j) + dfs(grid, i - 1, j) + dfs(grid, i, j - 1) + dfs(grid, i, j + 1);
    }
}