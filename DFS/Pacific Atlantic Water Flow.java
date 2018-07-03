/*
    417. Pacific Atlantic Water Flow
    DescriptionHintsSubmissionsDiscussSolution
    Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

    Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

    Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

    Note:
    The order of returned grid coordinates does not matter.
    Both m and n are less than 150.
    Example:

    Given the following 5x5 matrix:

      Pacific ~   ~   ~   ~   ~
           ~  1   2   2   3  (5) *
           ~  3   2   3  (4) (4) *
           ~  2   4  (5)  3   1  *
           ~ (6) (7)  1   4   5  *
           ~ (5)  1   1   2   4  *
              *   *   *   *   * Atlantic

    Return:
    如果有点能够到达左上(Pacifi)和右下(atlantic)， 我们就返回所有满足条件的点，不是路径！
    [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 */

// Solution1: DFS prefer, use two boolean[][] visted arry to record whether this point can reach to pac/atl
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    private int m, n;
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList();
        if (matrix == null || matrix.length == 0)
            return res;
        m = matrix.length;
        n = matrix[0].length;

        boolean[][] pacific = new boolean[m][n]; // whethetr this point can reach to pacific;
        boolean[][] atlantic = new boolean[m][n]; // whether this point can reach to atlantic;

        // Horizontal border
        for (int i = 0; i < n; i++) {
            dfs(matrix, 0, i, pacific, Integer.MIN_VALUE);
            dfs(matrix, m - 1, i, atlantic, Integer.MIN_VALUE);
        }

        // Vertical border
        for (int i = 0; i < m; i++) {
            dfs(matrix, i, 0, pacific, Integer.MIN_VALUE);
            dfs(matrix, i, n - 1, atlantic, Integer.MIN_VALUE);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }

    public void dfs(int[][] matrix, int x, int y, boolean[][] visited, int lastValue) {
        if (x < 0 || y < 0 || x >= m || y >= n || visited[x][y] || matrix[x][y] < lastValue)
            return;
        visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int row = x + shift[i];
            int col = y + shift[i + 1];
            dfs(matrix, row, col, visited, matrix[x][y]);
        }
    }
}


// Solution2: BFS
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    private int m, n;
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList();
        if (matrix == null || matrix.length == 0)
            return res;
        m = matrix.length;
        n = matrix[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        Queue<int[]> pQueue = new ArrayDeque();
        Queue<int[]> aQueue = new ArrayDeque();

        // Horizontal border
        for (int i = 0; i < n; i++) {
            pQueue.offer(new int[]{0, i});
            aQueue.offer(new int[]{m - 1, i});
            pacific[0][i] = true;
            atlantic[m - 1][i] = true;
        }

        // Vertical border
        for (int i = 0; i < m; i++) {
            pQueue.offer(new int[]{i, 0});
            aQueue.offer(new int[]{i, n - 1});
            pacific[i][0] = true;
            atlantic[i][n - 1] = true;
        }
        bfs(matrix, pQueue, pacific);
        bfs(matrix, aQueue, atlantic);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }

    public void bfs(int[][] matrix, Queue<int[]> queue, boolean[][] visited) {
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = cur[0] + shift[i];
                int y = cur[1] + shift[i + 1];
                if (x < 0 || y < 0 || x >= m || y >= n || visited[x][y] || matrix[x][y] < matrix[cur[0]][cur[1]])
                    continue;
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
    }
}