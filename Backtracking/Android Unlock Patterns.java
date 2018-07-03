/*
    351. Android Unlock Patterns

    Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the total number of unlock patterns of the Android lock screen, which consist of minimum of m keys and maximum n keys.

    Rules for a valid pattern:
    Each pattern must connect at least m keys and at most n keys.
    All the keys must be distinct.
    If the line connecting two consecutive keys in the pattern passes through any other keys, the other keys must have previously selected in the pattern. No jumps through non selected key is allowed.
    The order of keys used matters.

    Explanation:
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
    Invalid move: 4 - 1 - 3 - 6
    Line 1 - 3 passes through key 2 which had not been selected in the pattern.

    Invalid move: 4 - 1 - 9 - 2
    Line 1 - 9 passes through key 5 which had not been selected in the pattern.

    Valid move: 2 - 4 - 1 - 3 - 6
    Line 1 - 3 is valid because it passes through key 2, which had been selected in the pattern

    Valid move: 6 - 5 - 4 - 1 - 9 - 2
    Line 1 - 9 is valid because it passes through key 5, which had been selected in the pattern.

    Example:
    Given m = 1, n = 1, return 9.
 */

// Time complexity : O(n!), where n is maximum pattern length
class Solution {
    public int numberOfPatterns(int m, int n) {
        int[][] skip = new int[10][10];
        // if skip[i][j] is not zero, skip[i][j] means from i to j, we have cross skip[i][j]
        // why we set up skip array? we only can visited 2 number which is adjacent or skip number is already visited
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[3][9] = skip[9][3] = 6;
        skip[7][9] = skip[9][7] = 8;
        skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
        boolean[] visited = new boolean[10];
        int res = 0;
        // make sure from m to n
        for (int i = m; i <= n; i++) {
            res += dfs(visited, skip, 1, i - 1) * 4; // 1, 3, 7, 9 have same cases
            res += dfs(visited, skip, 2, i - 1) * 4; // 2, 4, 6, 8 have same cases
            res += dfs(visited, skip, 5, i - 1); // case 5
        }
        return res;
    }

    public int dfs(boolean[] visited, int[][] skip, int cur, int remain) {
        if (remain < 0)
            return 0;
        if (remain == 0)
            return 1;
        visited[cur] = true;
        int res = 0;
        for (int next = 1; next <= 9; next++) {
            // only when visited[i] is not visited and (two numbers are adjacent or skip number is already visited)
            // skip[cur][i] == 0 代表 cur和next是adajacent
            // visited[skip[cur][next]] => cur和next之间的点
            if (!visited[next] && (skip[cur][next] == 0 || visited[skip[cur][next]]))
                res += dfs(visited, skip, next, remain - 1);
        }
        visited[cur] = false;
        return res;
    }
}