/*
    407. Trapping Rain Water II
    DescriptionHintsSubmissionsDiscussSolution
    Given an m x n matrix of positive integers representing the height of each unit cell in a 2D elevation map, compute the volume of water it is able to trap after raining.

    Note:
    Both m and n are less than 110. The height of each unit cell is greater than 0 and is less than 20,000.

    Example:

    Given the following 3x6 height map:
    [
      [1,4,3,1,3,2],
      [3,2,1,3,2,4],
      [2,3,3,2,3,1]
    ]

    Return 4.
 */


// Solution: BFS， 和 Trapping Rain Water I没关系，反倒是有点像417. Pacific Atlantic Water Flow
// 首先将四个边界入队列，从边界开始做BFS, 因为边界的点肯定不能装水，需要从外围向内包围查找，记录最小的柱高，也就是木桶原理，最矮的柱子决定了灌水的高度。
/*
    从最外围一圈向内部遍历，记录包围“墙”的最小柱高，可以利用min-heap（PriorityQueue）
    记录遍历过的点visited[][]
    对于min-heap的堆顶元素，假设高度h，查找其周围4个方向上未曾访问过的点
    如果比h高，则说明不能装水，但是提高了“围墙”最低高度，因此将其加入min-heap中，设置元素被访问
    如果比h矮，则说明可以向其中灌水，且灌水高度就是h - h'，其中h'是当前访问的柱子高度，同样的，要将其加入min heap中，（且该元素高度记为灌水后的高度，也就是h，可以设想为一个虚拟的水位高度），设置元素被访问
 */
class Solution {
    private static final int[] shift = {0, 1, 0, -1, 0};
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length <= 2 || heightMap[0].length <= 2)
            return 0;
        int m = heightMap.length;
        int n = heightMap[0].length;
        int res = 0;
        boolean[][] visited = new boolean[m][n];

        // use minHeap, sicne we want to pull the minimum cell every time.
        PriorityQueue<Cell> minHeap = new PriorityQueue<Cell>(
                (a, b) -> a.height - b.height
        );

        // From Horizontal
        for (int i = 0; i < n; i++) {
            minHeap.offer(new Cell(0, i, heightMap[0][i]));
            minHeap.offer(new Cell(m - 1, i, heightMap[m - 1][i]));
            visited[0][i] = true;
            visited[m - 1][i] = true;
        }

        // From Vertical
        for (int i = 0; i < m; i++) {
            minHeap.offer(new Cell(i, 0, heightMap[i][0]));
            minHeap.offer(new Cell(i, n - 1, heightMap[i][n - 1]));
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        while (!minHeap.isEmpty()) {
            Cell cell = minHeap.poll();
            for (int i = 0; i < 4; i++) {
                int row = cell.x + shift[i];
                int col = cell.y + shift[i + 1];
                if (row < 0 || col < 0 || row >= m || col >= n || visited[row][col])
                    continue;
                visited[row][col] = true;
                int curHeight = heightMap[row][col];
                res += Math.max(0, cell.height - curHeight);
                minHeap.offer(new Cell(row, col, Math.max(curHeight, cell.height))); //如果该cell可以灌水，则高度上升为cell.height,否则依然为当前高度
            }
        }
        return res;
    }

    class Cell {
        int x;
        int y;
        int height;
        public Cell(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }
    }
}