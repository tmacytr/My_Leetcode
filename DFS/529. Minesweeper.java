/*
    529. Minesweeper

    Let's play the minesweeper game (Wikipedia, online game)!

    You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine, 'E' represents an unrevealed empty square, 'B' represents a revealed blank square that has no adjacent (above, below, left, right, and all 4 diagonals) mines, digit ('1' to '8') represents how many mines are adjacent to this revealed square, and finally 'X' represents a revealed mine.

    Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'), return the board after revealing this position according to the following rules:

    If a mine ('M') is revealed, then the game is over - change it to 'X'.
    If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of its adjacent unrevealed squares should be revealed recursively.
    If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number of adjacent mines.
    Return the board when no more squares will be revealed.
    Example 1:
    Input:

    [['E', 'E', 'E', 'E', 'E'],
     ['E', 'E', 'M', 'E', 'E'],
     ['E', 'E', 'E', 'E', 'E'],
     ['E', 'E', 'E', 'E', 'E']]

    Click : [3,0]

    Output:

    [['B', '1', 'E', '1', 'B'],
     ['B', '1', 'M', '1', 'B'],
     ['B', '1', '1', '1', 'B'],
     ['B', 'B', 'B', 'B', 'B']]

    Explanation:

    Example 2:
    Input:

    [['B', '1', 'E', '1', 'B'],
     ['B', '1', 'M', '1', 'B'],
     ['B', '1', '1', '1', 'B'],
     ['B', 'B', 'B', 'B', 'B']]

    Click : [1,2]

    Output:

    [['B', '1', 'E', '1', 'B'],
     ['B', '1', 'X', '1', 'B'],
     ['B', '1', '1', '1', 'B'],
     ['B', 'B', 'B', 'B', 'B']]

    Explanation:

    Note:
    The range of the input matrix's height and width is [1,50].
    The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains at least one clickable square.
    The input board won't be a stage when game is over (some mines have been revealed).
    For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal all the unrevealed mines when the game is over, consider any cases that you will win the game or flag any squares.
    Seen this question in a real interview before?
 */

//Solution1: DFS1
class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int m = board.length;
        int n = board[0].length;

        int x = click[0], y = click[1];

        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;
                int row = x + i;
                int col = y + j;
                if (row < 0 || col < 0 || row >= m || col >= n)
                    continue;
                if (board[row][col] == 'M' || board[row][col] == 'X')
                    count++;
            }
        }

        if (count > 0) {
            board[x][y] = (char)(count + '0');
        } else {
            board[x][y] = 'B';
            for (int i = -1; i < 2; i++) {
                for (int j = -1 ; j < 2; j++) {
                    if (i == 0 && j == 0)
                        continue;
                    int row = x + i;
                    int col = y + j;
                    if (row < 0 || col < 0 || row >= m || col >= n)
                        continue;
                    if (board[row][col] == 'E')
                        updateBoard(board, new int[]{row, col});
                }
            }
        }

        return board;
    }
}

//Solution2: DFS2 prefer
public class Solution {
    private static int[] dx = {-1, 0, 1, -1, 1, 0, 1, -1};
    private static int[] dy = {-1, 1, 1, 0, -1, -1, 0, 1};
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        dfs(board, x, y);
        return board;
    }
    private void dfs(char[][] board, int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E')
            return;

        int num = getNumsOfBombs(board, x, y);

        if (num == 0) {
            board[x][y] = 'B';
            for (int i = 0; i < 8; i++) {
                int row = x + dx[i];
                int col = y + dy[i];
                dfs(board, row, col);
            }
        } else {
            board[x][y] = (char)('0' + num);
        }

    }

    private int getNumsOfBombs(char[][] board, int x, int y) {
        int num = 0;
        for (int i = 0; i < 8; i++) {
            int row = x + dx[i];
            int col = y + dy[i];
            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
                continue;
            if (board[row][col] == 'M' || board[row][col] == 'X') {
                num++;
            }
        }
        return num;
    }
}

//Solution3: BFS
class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int m = board.length, n = board[0].length;
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(x * n + y);

        while (!queue.isEmpty()) {
            int idx = queue.poll();
            int curX = idx / n, curY = idx % n;
            int num = getNumberOfBombs(board, curX, curY);
            if (num == 0) {
                board[curX][curY] = 'B';
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int newX = curX + i, newY = curY + j;
                        if (newX < 0 || newY < 0 || newX >= board.length || newY >= board[0].length || board[newX][newY] != 'E') continue;
                        queue.offer(newX * n + newY);
                        board[newX][newY] = 'B'; // Avoid being added again
                    }
                }
            } else {
                board[curX][curY] = (char)(num + '0');
            }
        }
        return board;
    }

    private int getNumberOfBombs(char[][] board, int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i, newY = y + j;
                if (newX < 0 || newY < 0 || newX >= board.length || newY >= board[0].length) continue;
                if (board[newX][newY] == 'M' || board[newX][newY] == 'X') count++;
            }
        }

        return count;
    }
}