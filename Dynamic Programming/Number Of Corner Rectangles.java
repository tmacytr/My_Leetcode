/*
	750. Number Of Corner Rectangles

	Given a grid where each entry is only 0 or 1, find the number of corner rectangles.

	A corner rectangle is 4 distinct 1s on the grid that form an axis-aligned rectangle. Note that only the corners need to have the value 1. Also, all four 1s used must be distinct.

	 

	Example 1:

	Input: grid = 
	[[1, 0, 0, 1, 0],
	 [0, 0, 1, 0, 1],
	 [0, 0, 0, 1, 0],
	 [1, 0, 1, 0, 1]]
	Output: 1
	Explanation: There is only one corner rectangle, with corners grid[1][2], grid[1][4], grid[3][2], grid[3][4].
	 

	Example 2:

	Input: grid = 
	[[1, 1, 1],
	 [1, 1, 1],
	 [1, 1, 1]]
	Output: 9
	Explanation: There are four 2x2 rectangles, four 2x3 and 3x2 rectangles, and one 3x3 rectangle.
	 

	Example 3:

	Input: grid = 
	[[1, 1, 1, 1]]
	Output: 0
	Explanation: Rectangles must have four distinct corners.
	 

	Note:

	The number of rows and columns of grid will each be in the range [1, 200].
	Each grid[i][j] will be either 0 or 1.
	The number of 1s in the grid will be at most 6000.

	Companies: FB
*/

/*
	Solution: 
		两行同时遍历，如果两行中相同列位置的值都为1，则计数器COUNT自增1，那么最后就相当于有了count - 1个相邻的格子，
		问题就转化为了求count - 1个相邻的格子能组成多少个矩形，就变成了初中数学问题了，共有count*(count-1)/2个
*/
// time: O(m^2 * n)
class Solution {
    public int countCornerRectangles(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = i + 1; j < grid.length; j++) {
                int count = 0;
                for (int k = 0; k < grid[0].length; k++) {
                    if (grid[i][k] == 1 && grid[j][k] == 1)
                        count++;
                }
                res += count * (count - 1) / 2;
            }
        }
        return res;
    }
}