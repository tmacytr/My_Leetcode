/*
	The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
	Given an integer n, return all distinct solutions to the n-queens puzzle.
	Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.
	For example,	There exist two distinct solutions to the 4-queens puzzle:

		[
		 [".Q..",  // Solution 1
		  "...Q",
		  "Q...",
		  "..Q."],

		 ["..Q.",  // Solution 2
		  "Q...",
		  "...Q",
		  ".Q.."]
		]
*/

/*
    判断点(row, col)在对角线上是否存在一个(i, j)与其重合 => Math.abs(row - i) == Math.abs(col - j)
 */

//Solution1: dfs by row prefer, space(n^2)
public class Solution {
        public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n < 0) {
            return res;
        }
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        dfs(n, 0, board, res);
        return res;
        
    }
    public void dfs(int n, int row, char[][] board, List<List<String>> res) {
        if (row == n) {
            List<String> item = new ArrayList();
            for (char[] rows : board) {
                item.add(new String(rows));
            }
            res.add(item);
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (isValid(board, row, i)) {
                 board[row][i] = 'Q';
                 dfs(n, row + 1, board, res);
                 board[row][i] = '.';
            }
        }
    }
    
    public boolean isValid(char[][] board, int row, int col) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 'Q' && (i == row || j == col || Math.abs(row - i) == Math.abs(col - j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

//Solution2: prefer, time: O(n^n) space: O(n)
public class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        int[] board = new int[n];
        dfs(res, board, n, 0);
        return res;
    }

    public void dfs(List<List<String>> res, int[] board, int n, int row) {
        if (row == n) {
            List<String> item = new ArrayList();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    if (j == board[i]) {
                        sb.append("Q");
                    } else {
                        sb.append(".");
                    }
                }
                item.add(sb.toString());
            }
            res.add(item);
            return ;
        }
        for (int i = 0; i < n; i++) {
            matrix[row] = i;
            if (isValid(board, row, board[row])) {
                dfs(res, board, n, row + 1);
            }
        }
    }

    public boolean isValid(int[] board, int row, int colValue) {
        for (int i = 0; i < row; i++) {
            if (board[i] == board[row] || Math.abs(i - row) == Math.abs(board[i] - colValue) ) {
                return false;
            }
        }
        return true;
    }
}