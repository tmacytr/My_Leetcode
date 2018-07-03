/*
    749. Contain Virus

    A virus is spreading rapidly, and your task is to quarantine the infected area by installing walls.

    The world is modeled as a 2-D array of cells, where 0 represents uninfected cells, and 1 represents cells contaminated with the virus. A wall (and only one wall) can be installed between any two 4-directionally adjacent cells, on the shared boundary.

    Every night, the virus spreads to all neighboring cells in all four directions unless blocked by a wall. Resources are limited. Each day, you can install walls around only one region -- the affected area (continuous block of infected cells) that threatens the most uninfected cells the following night. There will never be a tie.

    Can you save the day? If so, what is the number of walls required? If not, and the world becomes fully infected, return the number of walls used.

    Example 1:
    Input: grid =
    [[0,1,0,0,0,0,0,1],
     [0,1,0,0,0,0,0,1],
     [0,0,0,0,0,0,0,1],
     [0,0,0,0,0,0,0,0]]
    Output: 10
    Explanation:
    There are 2 contaminated regions.
    On the first day, add 5 walls to quarantine the viral region on the left. The board after the virus spreads is:

    [[0,1,0,0,0,0,1,1],
     [0,1,0,0,0,0,1,1],
     [0,0,0,0,0,0,1,1],
     [0,0,0,0,0,0,0,1]]

    On the second day, add 5 walls to quarantine the viral region on the right. The virus is fully contained.
    Example 2:
    Input: grid =
    [[1,1,1],
     [1,0,1],
     [1,1,1]]
    Output: 4
    Explanation: Even though there is only one cell saved, there are 4 walls built.
    Notice that walls are only built on the shared boundary of two different cells.
    Example 3:
    Input: grid =
    [[1,1,1,0,0,0,0,0,0],
     [1,0,1,0,1,1,1,1,1],
     [1,1,1,0,0,0,0,0,0]]
    Output: 13
    Explanation: The region on the left only builds two new walls.
    Note:
    The number of rows and columns of grid will each be in the range [1, 50].
    Each grid[i][j] will be either 0 or 1.
    Throughout the described process, there is always a contiguous viral region that will infect strictly more uncontaminated squares in the next round.
 */

/*
    1. Find all viral regions (connected components), additionally for each region keeping track of the frontier (neighboring uncontaminated cells), and the perimeter of the region.
    2. Disinfect the most viral region, adding it's perimeter to the answer.
    3. Spread the virus in the remaining regions outward by 1 square.
 */
class Solution {
    Set<Integer> visited;
    List<Set<Integer>> regions;
    List<Set<Integer>> frontiers;
    List<Integer> perimeters;
    int[][] grid;
    int m, n;
    int[] shift = {0, -1, 0, 1, 0};
    public int containVirus(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        int res = 0;

        while (true) {
            // 对于每一个 connected component, 重新new以下的
            regions = new ArrayList();
            frontiers = new ArrayList();
            perimeters = new ArrayList();
            visited = new HashSet();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1 && !visited.contains(i * n + j)) {
                        regions.add(new HashSet());
                        frontiers.add(new HashSet());
                        perimeters.add(0);
                        dfs(i, j);
                    }
                }
            }

            if (regions.isEmpty())
                break;

            int maxIndex = 0;
            for (int i = 0; i < frontiers.size(); i++) {
                if (frontiers.get(maxIndex).size() < frontiers.get(i).size())
                    maxIndex = i;
            }

            // 累加每一个connect component的邻接节点
            res += perimeters.get(maxIndex);

            for (int i = 0; i < regions.size(); i++) {
                // 如果是含有最多邻接点的region，直接标位-1， 表示先处理
                if (i == maxIndex) {
                    for (int point : regions.get(i)) {
                        grid[point / n][point % n] = -1;
                    }
                } else {
                    // 否则将其他的region邻接的点感染 + 1
                    for (int point : regions.get(i)) {
                        int x = point / n;
                        int y = point % n;
                        for (int j = 0; j < 4; j++) {
                            int row = x + shift[j];
                            int col = y + shift[j + 1];
                            if (row >= 0 && row < m && col >= 0 && col < n && grid[row][col] == 0) {
                                grid[row][col] = 1;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private void dfs(int x, int y) {
        int point = x * n + y;
        if (!visited.contains(point)) {
            visited.add(point);
            int N = regions.size();
            regions.get(N - 1).add(point);
            for (int i = 0; i < 4; i++) {
                int row = x + shift[i];
                int col = y + shift[i + 1];
                int nextPoint = row * n + col;
                if (row >= 0 && row < m && col >= 0 && col < n) {
                    if (grid[row][col] == 1) {
                        dfs(row, col);
                    } else if (grid[row][col] == 0) {
                        frontiers.get(N - 1).add(nextPoint);
                        perimeters.set(N - 1, perimeters.get(N - 1) + 1);
                    }
                }
            }
        }
    }
}