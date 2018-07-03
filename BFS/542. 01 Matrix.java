/*
    542. 01 Matrix

    Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

    The distance between two adjacent cells is 1.
    Example 1:
    Input:
    0 0 0
    0 1 0
    0 0 0

    Output:
    0 0 0
    0 1 0
    0 0 0


    Example 2:
    Input:
    0 0 0
    0 1 0
    1 1 1

    Output:
    0 0 0
    0 1 0
    1 2 1

    Note:
    The number of elements of the given matrix will not exceed 10,000.
    There are at least one 0 in the given matrix.
    The cells are adjacent in only four directions: up, down, left and right.

    Companies: Google

    Related Topics: DFS, BFS
 */

//Solution1: BFS, 将0点入队列，并将1点的值设为Integer.MAX_VALUE,
//在遍历队列的坐标点时，一旦发现邻居的值大于本身的值就意味着遇到了1点，更新其距离
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] res = new int[m][n];

        Queue<int[]> queue = new ArrayDeque();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int row = cur[0] + shift[i];
                int col = cur[1] + shift[i + 1];
                if (row < 0 || col < 0 || row >= m || col >= n || matrix[cur[0]][cur[1]] >= matrix[row][col])
                    continue;
                queue.add(new int[]{row, col});
                matrix[row][col] = matrix[cur[0]][cur[1]] + 1;
            }
        }
        return matrix;
    }
}