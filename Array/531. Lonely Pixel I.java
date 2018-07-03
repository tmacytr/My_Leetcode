/*
    531. Lonely Pixel I

    Given a picture consisting of black and white pixels, find the number of black lonely pixels.

    The picture is represented by a 2D char array consisting of 'B' and 'W', which means black and white pixels respectively.

    A black lonely pixel is character 'B' that located at a specific position where the same row and same column don't have any other black pixels.

    Example:
    Input:
    [['W', 'W', 'B'],
     ['W', 'B', 'W'],
     ['B', 'W', 'W']]

    Output: 3
    Explanation: All the three 'B's are black lonely pixels.
    Note:
    The range of width and height of the input 2D array is [1,500].

    Companies: Google

    Related Topics: Array, DFS

    Similar Questions: Lonely Pixel II
 */

// Solution1: my solution, time: O(max(m * m * n, m * n * n), space: O(m + n)
class Solution {
    public int findLonelyPixel(char[][] picture) {
        if (picture == null || picture.length == 0)
            return 0;
        int m = picture.length;
        int n = picture[0].length;

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B' && isLonely(picture, i, j)) {
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    private boolean isLonely(char[][] picture, int row, int col) {
        for (int i = 0; i < picture.length; i++) {
            if (i != row && picture[i][col] == 'B')
                return false;
        }
        for (int i = 0; i < picture[0].length; i++) {
            if (i != col && picture[row][i] == 'B')
                return false;
        }
        return true;
    }
}


//Solution2: time: O(m * n) ,space: O(m + n) prefer
class Solution {
    public int findLonelyPixel(char[][] picture) {
        if (picture == null || picture.length == 0)
            return 0;
        int m = picture.length;
        int n = picture[0].length;
        int[] rowCount = new int[m];
        int[] colCount = new int[n];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B' && rowCount[i] == 1 && colCount[j] == 1)
                    res++;
            }
        }
        return res;
    }
}

/*
    修改原数组的值用来表示B的数量
    The hack here is to mutate the first row and first column of the given matrix to store the counts of items in the row/column.

    W + 1 = X --> one item in the row/column
    B + 1 = C --> one item in the row/column, and the first row is the black pixel
    W + 2 = Y --> two items in the row/column
    W - 1 = V --> this prevents wrap-around past W if there are more than 255 black pixels in a row/column
*/
//Solution3: Time: O(mn), space: O(1)
class Solution {
    public int findLonelyPixel(char[][] picture) {
        if (picture == null || picture.length == 0)
            return 0;
        int m = picture.length;
        int n = picture[0].length;
        int firstRowCount = 0;
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    if (picture[0][j] < 'Y' && picture[0][j] != 'V')
                        picture[0][j]++;
                    if (i == 0)
                        firstRowCount++;
                    else if (picture[i][0] < 'Y' && picture[i][0] != 'V')
                        picture[i][0]++;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] < 'W' && (picture[0][j] == 'C' || picture[0][j] == 'X')) {
                    if (i == 0)
                        res += firstRowCount == 1 ? 1 : 0;
                    else if (picture[i][0] == 'C' || picture[i][0] == 'X')
                        res++;
                }
            }
        }
        return res;
    }
}

