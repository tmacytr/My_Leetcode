/*
    505. The Maze II

    There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.

    Given the ball's start position, the destination and the maze, find the shortest distance for the ball to stop at the destination. The distance is defined by the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included). If the ball cannot stop at the destination, return -1.

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

    Output: 12
    Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
                 The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.

    Example 2

    Input 1: a maze represented by a 2D array

    0 0 1 0 0
    0 0 0 0 0
    0 0 0 1 0
    1 1 0 1 1
    0 0 0 0 0

    Input 2: start coordinate (rowStart, colStart) = (0, 4)
    Input 3: destination coordinate (rowDest, colDest) = (3, 2)

    Output: -1
    Explanation: There is no way for the ball to stop at the destination.

    Note:
    There is only one ball and one destination in the maze.
    Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
    The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
    The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 */

// Solution1: BFS， 和1的区别在于我们不需要visited boolean数组，而是需要一个dist数组去动态更新start -> destination 之间经过的点的最短距离，最后直接返回dist[destination]。
// 为什么不需要visited boolean数组，因为每次遍历dist的时候会更新长度如果长度大于dist原先值就不会再入列，从而避免了重复遍历一个点
// Time complexity: O(m * n * max(m,n)), m and n refers to the number of rows and columns of the maze.
// Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    private int m, n;
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        m = maze.length;
        n = maze[0].length;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        Queue<int[]> queue = new ArrayDeque();
        queue.offer(start);
        dist[start[0]][start[1]] = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int count = 1;
                int row = cur[0] + shift[i];
                int col = cur[1] + shift[i + 1];

                while (isValid(maze, row, col)) {
                    row += shift[i];
                    col += shift[i + 1];
                    count++;
                }

                row -= shift[i];
                col -= shift[i + 1];
                count--;
                // 如果当前计算的长度比dist里存的大，就不需要进队列了，只存最小的distance
                if (dist[cur[0]][cur[1]] + count < dist[row][col]) {
                    dist[row][col] = dist[cur[0]][cur[1]] + count;
                    queue.offer(new int[]{row, col});
                }
            }
        }
        return dist[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : dist[destination[0]][destination[1]];
    }

    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && y >= 0 && x < m && y < n && maze[x][y] != 1;
    }
}

// Solution2: DFS
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    private int m, n;
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        m = maze.length;
        n = maze[0].length;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[start[0]][start[1]] = 0;
        dfs(maze, start, dist);
        return dist[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : dist[destination[0]][destination[1]];
    }

    private void dfs(int[][] maze, int[] cur, int[][] dist) {
        for (int i = 0; i < 4; i++) {
            int row = cur[0] + shift[i];
            int col = cur[1] + shift[i + 1];
            int count = 1;
            while (isValid(maze, row, col)) {
                row += shift[i];
                col += shift[i + 1];
                count++;
            }

            row -= shift[i];
            col -= shift[i + 1];
            count--;
            if (dist[cur[0]][cur[1]] + count < dist[row][col]) {
                dist[row][col] = dist[cur[0]][cur[1]] + count;
                dfs(maze, new int[]{row, col}, dist);
            }
        }
    }

    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && y >= 0 && x < m && y < n && maze[x][y] != 1;
    }
}

// Solution3: dijkstra最短路径算法
// Time complexity : O(mn*log(mn)
// Complete traversal of maze will be done in the worst case giving a factor of mnmn.
// Further, poll method is a combination of heapifying O(log(n)) and removing the top element(O(1) from the priority queue, and it takes O(n) time for nn elements. In the current case, poll introduces a factor of log(mn)
class Solution {
    private static final int[] shift = {0, -1, 0, 1, 0};
    private int m, n;
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        m = maze.length;
        n = maze[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[start[0]][start[1]] = 0;

        // dijkstra
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        queue.offer(new int[]{start[0], start[1], 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (dist[cur[0]][cur[1]] < cur[2]) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                int row = cur[0] + shift[i];
                int col = cur[1] + shift[i + 1];
                int count = 1;
                while (isValid(maze, row, col)) {
                    row += shift[i];
                    col += shift[i + 1];
                    count++;
                }

                row -= shift[i];
                col -= shift[i + 1];
                count--;
                if (dist[cur[0]][cur[1]] + count < dist[row][col]) {
                    dist[row][col] = dist[cur[0]][cur[1]] + count;
                    queue.offer(new int[]{row, col, dist[row][col]});
                }
            }
        }
        return dist[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : dist[destination[0]][destination[1]];
    }

    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && y >= 0 && x < m && y < n && maze[x][y] != 1;
    }
}