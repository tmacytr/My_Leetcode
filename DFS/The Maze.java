/*
    490. The Maze

    There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.

    Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination.

    The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.

    Example 1

    Input 1: a maze represented by a 2D array

    0 0 1 0 0
    0 0 0 0 0
    0 0 0 1 0
    1 1 0 1 1
    0 0 0 0 0

    Input 2: start coordinate (rowStart, colStart) = (0, 4)
    Input 3: destination coordinate (rowDest, colDest) = (4, 4)

    Output: true
    Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.

    Example 2

    Input 1: a maze represented by a 2D array

    0 0 1 0 0
    0 0 0 0 0
    0 0 0 1 0
    1 1 0 1 1
    0 0 0 0 0

    Input 2: start coordinate (rowStart, colStart) = (0, 4)
    Input 3: destination coordinate (rowDest, colDest) = (3, 2)

    Output: false
    Explanation: There is no way for the ball to stop at the destination.

    Note:
    There is only one ball and one destination in the maze.
    Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
    The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
    The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 */

/*
    这里的关键在于如何处理球的滚动，一直滚到停止在有效的位置，我们再进行下一轮的dfs, 注意滚的过程我们不check 是否visited， 只有对于停下来的position才check是否visited过
 */

// Solution1: DFS
class Solution {
    private static final int[] shift = {0, 1, 0, -1, 0};
    int m, n;
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        m = maze.length;
        n = maze[0].length;
        boolean[][] visited = new boolean[m][n];

        return dfs(maze, start, destination, visited);
    }

    private boolean dfs(int[][] maze, int[] start, int[] end, boolean[][] visited) {
        if (visited[start[0]][start[1]])
            return false;

        if (start[0] == end[0] && start[1] == end[1])
            return true;

        visited[start[0]][start[1]] = true;

        for (int i = 0; i < 4; i++) {
            int row = start[0] + shift[i];
            int col = start[1] + shift[i + 1];

            while (isValid(row, col, maze)) {
                row += shift[i];
                col += shift[i + 1];
            }

            row -= shift[i]; // 因为此时已经是invalid的坐标了，我们需要roll back
            col -= shift[i + 1];

            if (dfs(maze, new int[]{row, col}, end, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int[][] maze) {
        return row >= 0 && col >= 0 && row < m && col < n && maze[row][col] != 1;
    }
}


// Solution2: BFS, prefer
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        int m = maze.length;
        int n = maze[0].length;

        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new ArrayDeque();
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            visited[point[0]][point[1]] = true;
            for (int i = 0; i < 4; i++) {
                int row = point[0] + shift[i];
                int col = point[1] + shift[i + 1];
                while (isValid(row, col, maze)) {
                    row += shift[i];
                    col += shift[i + 1];
                }
                row -= shift[i];
                col -= shift[i + 1];
                if (visited[row][col])
                    continue;
                if (row == destination[0] && col == destination[1]) {
                    return true;
                }
                queue.offer(new int[]{row, col});
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int[][] maze) {
        return row >= 0 && col >= 0 && row < m && col < n && maze[row][col] != 1;
    }
}