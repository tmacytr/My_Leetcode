/*
    546. Remove Boxes

    Given several boxes with different colors represented by different positive numbers.
    You may experience several rounds to remove boxes until there is no box left. Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1), remove them and get k*k points.
    Find the maximum points you can get.

    Example 1:
    Input:

    [1, 3, 2, 2, 2, 3, 4, 3, 1]
    Output:
    23
    Explanation:
    [1, 3, 2, 2, 2, 3, 4, 3, 1]
    ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
    ----> [1, 3, 3, 3, 1] (1*1=1 points)
    ----> [1, 1] (3*3=9 points)
    ----> [] (2*2=4 points)
    Note: The number of boxes n would not exceed 100.

    Companies: Tencent

    Related Topics: DP, DFS

    Similar Questions: Strange Printer
 */

// https://leetcode.com/problems/remove-boxes/discuss/101310/Java-top-down-and-bottom-up-DP-solutions
//Solution1: dp
class Solution {
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return helper(boxes, 0, n - 1, 0, dp);
    }

    private int helper(int[] boxes, int i, int j, int k, int[][][] dp) {
        if (i > j)
            return 0;
        if (dp[i][j][k] > 0)
            return dp[i][j][k];
        while (i + 1 <= j && boxes[i + 1] == boxes[i]) {
            i++;
            k++;
        }
        int res = (k + 1) * (k + 1) + helper(boxes, i + 1, j, 0, dp);
        for (int m = i + 1; m <= j; m++) {
            if (boxes[i] == boxes[m]) {
                res = Math.max(res, helper(boxes, i + 1, m - 1, 0, dp) + helper(boxes, m, j, k + 1, dp));
            }
        }
        dp[i][j][k] = res;
        return res;
    }
}