/*
    598. Range Addition II

    Given an m * n matrix M initialized with all 0's and several update operations.

    Operations are represented by a 2D array, and each operation is represented by an array with two positive integers a and b, which means M[i][j] should be added by one for all 0 <= i < a and 0 <= j < b.

    You need to count and return the number of maximum integers in the matrix after performing all the operations.

    Example 1:
    Input:
    m = 3, n = 3
    operations = [[2,2],[3,3]]
    Output: 4
    Explanation:
    Initially, M =
    [[0, 0, 0],
     [0, 0, 0],
     [0, 0, 0]]

    After performing [2,2], M =
    [[1, 1, 0],
     [1, 1, 0],
     [0, 0, 0]]

    After performing [3,3], M =
    [[2, 2, 1],
     [2, 2, 1],
     [1, 1, 1]]

    So the maximum integer in M is 2, and there are four of it in M. So return 4.
    Note:
    The range of m and n is [1,40000].
    The range of a is [1,m], and the range of b is [1,n].
    The range of operations size won't exceed 10,000.

    Companies: IXL

    Related Topics: Math

    Similar Questions: Range Addition
 */

//Solution1: brute force, time exceeding
class Solution {
    public int maxCount(int m, int n, int[][] ops) {
        if (ops == null || ops.length == 0)
            return m * n;
        int[][] grid = new int[m][n];
        int max = 0;
        int count = 0;
        for (int[] op : ops) {
            int x = op[0];
            int y = op[1];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    grid[i][j]++;
                    if (grid[i][j] == max) {
                        count++;
                    } else if (grid[i][j] > max) {
                        count = 1;
                        max = grid[i][j];
                    }
                }
            }
        }
        return count;
    }
}

//Solution2:
class Solution {
    public int maxCount(int m, int n, int[][] ops) {
        int minX = m;
        int minY = n;
        for (int[] op : ops) {
            minX = Math.min(op[0], minX);
            minY = Math.min(op[1], minY);
        }
        return minX * minY;
    }
}