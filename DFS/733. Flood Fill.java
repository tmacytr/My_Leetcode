/*
    733. Flood Fill

    An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).

    Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value newColor, "flood fill" the image.

    To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the newColor.

    At the end, return the modified image.

    Example 1:
    Input:
    image = [[1,1,1],[1,1,0],[1,0,1]]
    sr = 1, sc = 1, newColor = 2
    Output: [[2,2,2],[2,2,0],[2,0,1]]
    Explanation:
    From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected
    by a path of the same color as the starting pixel are colored with the new color.
    Note the bottom corner is not colored 2, because it is not 4-directionally connected
    to the starting pixel.
    Note:

    The length of image and image[0] will be in the range [1, 50].
    The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
    The value of each color in image[i][j] and newColor will be an integer in [0, 65535].

    Companies: Uber

    Related Topics: DFS

    Similar Questions: Island Perimeter
 */
//Solution1: my solution
class Solution {
    int m, n;
    private static final int[] shift = {0, -1, 0, 1, 0};
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0)
            return image;
        m = image.length;
        n = image[0].length;
        dfs(image, sr, sc, newColor, new boolean[m][n], image[sr][sc]);
        return image;
    }

    private void dfs(int[][] image, int x, int y, int newColor, boolean[][] visited, int originalColor) {
        if (x < 0 || y < 0 || x >= m || y >= n || visited[x][y] || image[x][y] != originalColor)
            return;
        image[x][y] = newColor;
        visited[x][y] = true;
        dfs(image, x + 1, y, newColor, visited, originalColor);
        dfs(image, x - 1, y, newColor, visited, originalColor);
        dfs(image, x, y + 1, newColor, visited, originalColor);
        dfs(image, x, y - 1, newColor, visited, originalColor);
    }
}


//Solution2: not using visited array
class Solution {
    int m, n;
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) //非常关键的一步 否则会栈溢出
            return image;
        m = image.length;
        n = image[0].length;
        dfs(image, sr, sc, newColor, image[sr][sc]);
        return image;
    }

    private void dfs(int[][] image, int x, int y, int newColor, int originalColor) {
        if (x < 0 || y < 0 || x >= m || y >= n || image[x][y] != originalColor)
            return;
        image[x][y] = newColor;
        dfs(image, x + 1, y, newColor, originalColor);
        dfs(image, x - 1, y, newColor, originalColor);
        dfs(image, x, y + 1, newColor, originalColor);
        dfs(image, x, y - 1, newColor, originalColor);
    }
}