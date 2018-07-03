/*
    562. Longest Line of Consecutive One in Matrix

    Given a 01 matrix M, find the longest line of consecutive one in the matrix. The line could be horizontal, vertical, diagonal or anti-diagonal.
    Example:
    Input:
    [[0,1,1,0],
     [0,1,1,0],
     [0,0,0,1]]
    Output: 3
    Hint: The number of elements in the given matrix will not exceed 10,000.
 */

/*
    求矩阵中最长的连续1，连续方向任意，可以是水平，竖直，对角线或者逆对角线均可。
    那么最直接最暴力的方法就是四个方向分别来统计最长的连续1，其中水平方向和竖直方向都比较容易，就是逐行逐列的扫描，使用一个计数器，如果当前位置是1，则计数器自增1，并且更新结果res，否则计数器清零。
    对于对角线和逆对角线需要进行些坐标转换，对于一个mxn的矩阵，对角线和逆对角线的排数都是m+n-1个，难点在于我们要确定每一排上的数字的坐标，
    如果i是从0到m+n-1之间遍历，j是在i到0之间遍历，那么对角线的数字的坐标就为(i-j, j)，逆对角线的坐标就为(m-1-i+j, j)
 */
//Solution1: Brute force
class Solution {
    public int longestLine(int[][] M) {
        if (M == null || M.length == 0)
            return 0;
        int m = M.length;
        int n = M[0].length;
        int res = 0, count = 0, inc = 0, desc = 0, count1 = 0, count2 = 0;

        // Vertical
        for (int i = 0; i < m; i++) {
            count = 0;
            for (int j = 0; j < n; j++) {
                count = M[i][j] == 1 ? count + 1 : 0;
                res = Math.max(count, res);
            }
        }

        // Horizontal
        for (int j = 0; j < n; j++) {
            count = 0;
            for (int i = 0; i < m; i++) {
                count = M[i][j] == 1 ? count + 1 : 0;
                res = Math.max(count, res);
            }
        }

        // Diagonal Solution1:
        for (int i = 0; i < m + n - 1; i++) {
            count1 = 0;
            count2 = 0;
            for (int j = i; j >= 0; j--) {
                if (i - j < m && j < n) { // Check diagonal
                    if (M[i - j][j] == 1)
                        res = Math.max(res, ++count1);
                    else
                        count1 = 0;
                }

                int k = m - 1 - i + j;
                if (k >= 0 && k < m && j < n) {  // Check anti-diagonal
                    if (M[k][j] == 1)
                        res = Math.max(res, ++count2);
                    else
                        count2 = 0;
                }
            }
        }

        // Diagonal Solution2:
        for (int k = 0; k < m + n; k++, inc = 0, desc = 0) {
            // increasing start from left cells then bottom cells
            for (int i = Math.min(k, m - 1), j = Math.max(0, k - m); i >= 0 && j < n; i--, j++) {
                inc = M[i][j] == 1 ? inc + 1 : 0;
                res = Math.max(res, inc);
            }

            // decreasing start from left cells then top cells;
            for (int i = Math.max(m - 1 - k, 0), j = Math.max(0, k - m); i < m && j < n; i++, j++) {
                desc = M[i][j] == 1 ? desc + 1 : 0;
                res = Math.max(res, desc);
            }
        }

        return res;
    }
}

//Solution2: DP
class Solution {
    public int longestLine(int[][] M) {
        if (M == null || M.length == 0)
            return 0;
        int m = M.length;
        int n = M[0].length;
        int res = 0;

        int[][][] dp = new int[m][n][4];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 0)
                    continue;
                for (int k = 0; k < 4; k++)
                    dp[i][j][k] = 1;
                if (j > 0)
                    dp[i][j][0] += dp[i][j - 1][0]; // horizontal line
                if (i > 0)
                    dp[i][j][1] += dp[i - 1][j][1]; // vertical line
                if (i > 0 && j > 0)
                    dp[i][j][2] += dp[i - 1][j - 1][2]; // diagonal line
                if (j < n - 1 && i > 0)
                    dp[i][j][3] += dp[i - 1][j + 1][3]; // anti-diagonal line
                res = Math.max(res, Math.max(dp[i][j][0], dp[i][j][1]));
                res = Math.max(res, Math.max(dp[i][j][2], dp[i][j][3]));

            }
        }
        return res;
    }
}