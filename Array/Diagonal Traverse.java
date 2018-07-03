/*
    498. Diagonal Traverse

    Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.

    Example:
    Input:
    [
     [ 1, 2, 3 ],
     [ 4, 5, 6 ],
     [ 7, 8, 9 ]
    ]
    Output:  [1,2,4,7,5,3,6,8,9]
    Explanation:

    Note:
        The total number of elements of the given matrix will not exceed 10,000.
 */

/*
    向右上和左下两个对角线方向遍历的时候都会有越界的可能，但是除了左下角和右上角的位置越界需要改变两个坐标之外，其余的越界只需要改变一个。
    那么我们就先判断要同时改变两个坐标的越界情况，即在右上角和左下角的位置。
    如果在右上角位置还要往右上走时，那么要移动到它下面的位置的，那么如果col超过了n-1的范围，那么col重置为n-1，并且row自增2，然后改变遍历的方向。
    同理如果row超过了m-1的范围，那么row重置为m-1，并且col自增2，然后改变遍历的方向。然后我们再来判断一般的越界情况，如果row小于0，那么row重置0，然后改变遍历的方向。
    同理如果col小于0，那么col重置0，然后改变遍历的方向。
*/
class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return new int[0];
        int m = matrix.length;
        int n = matrix[0].length;
        int[] res = new int[m * n];
        int row = 0;
        int col = 0;
        int d = 0;

        int[][] dirs = {
                {-1, 1}, // -> 右上
                {1, -1}}; // -> 左下
        for (int i = 0; i < m * n; i++) {
            res[i] = matrix[row][col];
            row += dirs[d][0];
            col += dirs[d][1];

            if (row >= m) { // 超出下边界
                row = m - 1;
                col += 2;
                d = 1 - d; // 改变direction
            }
            if (col >= n) { // 超出右边界
                col = n - 1;
                row += 2;
                d = 1- d;
            }
            if (row < 0) { // 超出上边界
                row = 0;
                d = 1 - d;
            }
            if (col < 0) { // 超出左边界
                col = 0;
                d = 1 - d;
            }
        }
        return res;
    }
}