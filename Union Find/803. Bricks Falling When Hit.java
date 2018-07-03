/*
    803. Bricks Falling When Hit

    We have a grid of 1s and 0s; the 1s in a cell represent bricks.  A brick will not drop if and only if it is directly connected to the top of the grid, or at least one of its (4-way) adjacent bricks will not drop.

    We will do some erasures sequentially. Each time we want to do the erasure at the location (i, j), the brick (if it exists) on that location will disappear, and then some other bricks may drop because of that erasure.

    Return an array representing the number of bricks that will drop after each erasure in sequence.

    Example 1:
    Input:
    grid = [[1,0,0,0],[1,1,1,0]]
    hits = [[1,0]]
    Output: [2]
    Explanation:
    If we erase the brick at (1, 0), the brick at (1, 1) and (1, 2) will drop. So we should return 2.
    Example 2:
    Input:
    grid = [[1,0,0,0],[1,1,0,0]]
    hits = [[1,1],[1,0]]
    Output: [0,0]
    Explanation:
    When we erase the brick at (1, 0), the brick at (1, 1) has already disappeared due to the last move. So each erasure will cause no bricks dropping.  Note that the erased brick (1, 0) will not be counted as a dropped brick.


    Note:

    The number of rows and columns in the grid will be in the range [1, 200].
    The number of erasures will not exceed the area of the grid.
    It is guaranteed that each erasure will be different from any other erasure, and located inside the grid.
    An erasure may refer to a location with no brick - if it does, no bricks drop.

    Companies: Google
    Tags: Union Find
 */

/*
    题意： 图上有有一堆砖块(1)和empty点(0), 砖块只要在第一行就不会掉下来，或者是和不会掉下来的砖块连接(4个方向都行）。第二个参数hits是给你一系列的坐标点，表示要抹去这个坐标点，问每次抹去一个点后会有几个砖块掉下来，返回掉下来的砖块数量的序列

    算法思想：https://leetcode.com/problems/bricks-falling-when-hit/discuss/119829/Python-Solution-by-reversely-adding-hits-bricks-back 有图更清楚
        1. For each hit (i, j), if grid[i][j]==0, set grid[i][j]=-1 otherwise set grid[i][j]=0.
           Since a hit may happen at an empty position, we need to seperate emptys from bricks.
        2. For i in [0, n], do dfs at grid[i][0] and mark no-dropping bricks.
           Here we get the grid after all hits.
        3. Then for each hit (i,j) (reversely), first we check grid[i][j]==-1, if yes, it’s empty, skip this hit.
           Then we check whether it’s connected to any no-dropping bricks or it’s at the top, if not, it can’t add any no-dropping bricks, skip this hit.
           Otherwise we do dfs at grid[i][j], mark new added no-dropping bricks and record amount of them.
        4. Return the amounts of new added no-dropping bricks at each hits.

        主要思路就是将hits数组倒过来加进去，看加上之后会有多少砖块和top联通，就是所求的drop数。需要事先preprocess grid，将0点标-1， 1点标0
*/
//Solution1: DFS
class Solution {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length;
        int n = grid[0].length;
        int k = hits.length;

        //Mark whether there is a brick at the each hit
        for (int[] h : hits) {
            if (grid[h[0]][h[1]] == 1)
                grid[h[0]][h[1]] = 0;
            else
                grid[h[0]][h[1]] = -1;
        }

        //Mark all top bricks as value 2 after all hits, no counting
        for (int i = 0; i < n; i++)
            dfs(0, i, grid);

        //Reversely add the block of each hit back and get count of saved bricks
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            int[] h = hits[i];
            if (grid[h[0]][h[1]] == -1)
                continue;
            grid[h[0]][h[1]] = 1;
            if (!isConnectedToTop(h[0], h[1], grid))
                continue;
            res[i] = dfs(h[0], h[1], grid) - 1;
        }
        return res;
    }

    //Connect unconnected bricks and mark visited bricks as value 2
    //Return how many extra bricks will be saved bacause of adding (i, j) back, including (i, j) itself
    private int dfs(int i, int j, int[][] grid) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
            return 0;
        int res = 1;
        grid[i][j] = 2;
        res += dfs(i-1, j, grid);
        res += dfs(i+1, j, grid);
        res += dfs(i, j-1, grid);
        res += dfs(i, j+1, grid);
        return res;
    }

    //Check whether (i, j) is connected to top
    private boolean isConnectedToTop(int i, int j, int[][] grid) {
        if ((i - 1 >= 0 && grid[i - 1][j] == 2)
                || (i + 1 < grid.length && grid[i + 1][j] == 2)
                || (j - 1 >= 0 && grid[i][j - 1] == 2)
                || (j + 1 < grid[0].length && grid[i][j + 1] == 2)
                || i == 0)
            return true;
        return false;
    }
}

//Solution2: Union Find
class Solution {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        int R = grid.length, C = grid[0].length;
        int[] dr = {1, 0, -1, 0};
        int[] dc = {0, 1, 0, -1};

        int[][] A = new int[R][C];
        for (int r = 0; r < R; ++r)
            A[r] = grid[r].clone();
        for (int[] hit: hits)
            A[hit[0]][hit[1]] = 0;

        DSU dsu = new DSU(R*C + 1);
        for (int r = 0; r < R; ++r) {
            for (int c = 0; c < C; ++c) {
                if (A[r][c] == 1) {
                    int i = r * C + c;
                    if (r == 0)
                        dsu.union(i, R*C);
                    if (r > 0 && A[r-1][c] == 1)
                        dsu.union(i, (r-1) *C + c);
                    if (c > 0 && A[r][c-1] == 1)
                        dsu.union(i, r * C + c-1);
                }
            }
        }
        int t = hits.length;
        int[] ans = new int[t--];

        while (t >= 0) {
            int r = hits[t][0];
            int c = hits[t][1];
            int preRoof = dsu.top();
            if (grid[r][c] == 0) {
                t--;
            } else {
                int i = r * C + c;
                for (int k = 0; k < 4; ++k) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];
                    if (0 <= nr && nr < R && 0 <= nc && nc < C && A[nr][nc] == 1)
                        dsu.union(i, nr * C + nc);
                }
                if (r == 0)
                    dsu.union(i, R*C);
                A[r][c] = 1;
                ans[t--] = Math.max(0, dsu.top() - preRoof - 1);
            }
        }

        return ans;
    }
}

class DSU {
    int[] parent;
    int[] rank;
    int[] sz;

    public DSU(int N) {
        parent = new int[N];
        for (int i = 0; i < N; ++i)
            parent[i] = i;
        rank = new int[N];
        sz = new int[N];
        Arrays.fill(sz, 1);
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        int xr = find(x), yr = find(y);
        if (xr == yr) return;

        if (rank[xr] < rank[yr]) {
            int tmp = yr;
            yr = xr;
            xr = tmp;
        }
        if (rank[xr] == rank[yr])
            rank[xr]++;

        parent[yr] = xr;
        sz[xr] += sz[yr];
    }

    public int size(int x) {
        return sz[find(x)];
    }

    public int top() {
        return size(sz.length - 1) - 1;
    }
}