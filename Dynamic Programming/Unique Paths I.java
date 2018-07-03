/*
	A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
    The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
    How many possible unique paths are there?

    Companies: BB

    Related Topics: Array, DP

    Similar Questions: Unique Paths II, Minimum Path Sum, Dungeon Game
*/

//Solution1: O(m*n) space
public class Solution {
    public int uniquePaths(int m, int n) {
    	if (m == 1 || n == 1)
    		return 1;
    	int[][] dp = new int[m][n];
    	for (int i = 0; i < m; i++) 
    		dp[i][0] = 1;
    	for (int j = 0; j < n; j++) 
    		dp[0][j] = 1;
    	dp[0][0] = 1;
    	for (int i = 1; i < m; i++) 
    		for (int j = 1; j < n; j++) 
    			dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
    	return dp[m - 1][n - 1];
    }
}


//Solution2: O(n) space
class Solution {
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];  //我们只需要保存上一层的信息就行，因此可以节省space，
                //这里的 dp[j] = dp[i][j] =  (dp[j] = dp[i - 1][j]) + (dp[j - 1] = dp[i][j - 1])
            }
        }
        return dp[n - 1];
    }
}

//Solution3: O(n) time, O(1) space
public int uniquePaths(int m, int n) {
    double value = 1;
    for (int i = 1; i <= n - 1; i++) {
        value *= ((double) (m + i - 1) / (double) i);
    }
    return (int) Math.round(value);
}