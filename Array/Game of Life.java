/*
	Game of Life
	According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

	Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

	Any live cell with fewer than two live neighbors dies, as if caused by under-population.
	Any live cell with two or three live neighbors lives on to the next generation.
	Any live cell with more than three live neighbors dies, as if by over-population..
	Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

	Write a function to compute the next state (after one update) of the board given its current state.

	Follow up: 
	Could you solve it in-place? Remember that the board needs to be updated at the same time: 
	You cannot update some cells first and then use their updated values to update other cells.
	In this question, we represent the board using a 2D array. 
	In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. 
	How would you address these problems?

*/

//Solution1: my solution, use extra space to store the state
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] state = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                state[i][j] = isValid(board, i, j, m, n);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = state[i][j] ? 1 : 0;
            }
        }
    }

    public boolean isValid(int[][]board, int x, int y, int m, int n) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int row = x + i;
                int col = y + j;
                if (row < 0 || col < 0 || row > m - 1 || col > n - 1 || (row == x && col == y))
                    continue;
                if (board[row][col] == 1)
                    count++;
            }
        }
        if (board[x][y] == 1 && count <= 3 && count >= 2) {
            return true;
        }
        if (board[x][y] == 0 && count == 3)
            return true;
        return false;
    }
}

// Solution2: my solution, improved, in place
// 更新每一个board[i][j] 的时候用新的数字-1， 2进行表示， -1表示之前是1但是以后要dead的点，2表示之前是0但是以后要live的点
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] state = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                setState(board, i, j, m, n);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 1;
                } else if (board[i][j] == -1)
                    board[i][j] = 0;
            }
        }
    }

    public void setState(int[][]board, int x, int y, int m, int n) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int row = x + i;
                int col = y + j;
                if (row < 0 || col < 0 || row > m - 1 || col > n - 1 || (row == x && col == y))
                    continue;
                if (board[row][col] == 1 || board[row][col] == -1)
                    count++;
            }
        }
        if (board[x][y] == 0 && count == 3) {
            board[x][y] = 2;
        } else if (board[x][y] == 1 && (count > 3 || count < 2)) {
            board[x][y] = -1;
        }
    }
}

//Solution 3: best bits
public void gameOfLife(int[][] board) {
    int m = board.length;
    int n = board[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int count = 0;
            for (int I = Math.max(i - 1, 0); I < Math.min(i + 2, m); I++) {
                for (int J = Math.max(j - 1, 0); J < Math.min(j + 2, n); J++) {
                    count += board[I][J] & 1;
                }
            }
            if (count == 3 || count - board[i][j] == 3) {
                board[i][j] |= 2;
            }
        }
    }
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            board[i][j] >>= 1;
        }
    }
}
