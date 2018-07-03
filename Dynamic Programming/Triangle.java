/*
	Triangle
	Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

	For example, given the following triangle
	[
	     [2],
	    [3,4],
	   [6,5,7],
	  [4,1,8,3]
	]
	The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
	Note:
	Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.

	Tags: DP, Array
*/

//Solution1: 2d dp
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        int n = triangle.size();
        int[][] dp = new int[n][n];
        //dp[i][j] 代表从位置(i,j)走到最后一行所需的最小的value
        //dp[i][j] means from position (i, j) to the last row, the minimum sum value;

        //初始化，初始化最后一行（因为要从最后一行开始往上走，因此最后一行是初始状态），
        //每一个dp[n - 1][i]就等于triangle对应位置的值
        //initialize the dp matrix from last row
        for (int i = 0; i < n; i++) {
            dp[n - 1][i] = triangle.get(n - 1).get(i);
        }

        //从倒数第二行开始， 因为倒数第一行已经被初始化
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }
}

//Solution2: 1d dp
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 1)
            return triangle.get(0).get(0);
        int[] dp = new int[triangle.size()];

        //initial by last row
        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            dp[i] = triangle.get(triangle.size() - 1).get(i);
        }

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }

        return dp[0];
    }
}

//Solution3: prefer use list instead of array
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0)
            return 0;
        int n = triangle.size();
        List<Integer> dp = new ArrayList();
        dp.addAll(triangle.get(n - 1));

        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp.set(j, Math.min(dp.get(j), dp.get(j + 1)) + triangle.get(i).get(j));
            }
        }
        return dp.get(0);
    }
}