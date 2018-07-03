/*
    533. Lonely Pixel II

    Given a picture consisting of black and white pixels, and a positive integer N, find the number of black pixels located at some specific row R and column C that align with all the following rules:

    Row R and column C both contain exactly N black pixels.
    For all rows that have a black pixel at column C, they should be exactly the same as row R
    The picture is represented by a 2D char array consisting of 'B' and 'W', which means black and white pixels respectively.

    Example:
    Input:
    [['W', 'B', 'W', 'B', 'B', 'W'],
     ['W', 'B', 'W', 'B', 'B', 'W'],
     ['W', 'B', 'W', 'B', 'B', 'W'],
     ['W', 'W', 'B', 'W', 'B', 'W']]

    N = 3
    Output: 6
    Explanation: All the bold 'B' are the black pixels we need (all 'B's at column 1 and 3).
            0    1    2    3    4    5         column index
    0    [['W', 'B', 'W', 'B', 'B', 'W'],
    1     ['W', 'B', 'W', 'B', 'B', 'W'],
    2     ['W', 'B', 'W', 'B', 'B', 'W'],
    3     ['W', 'W', 'B', 'W', 'B', 'W']]
    row index

    Take 'B' at row R = 0 and column C = 1 as an example:
    Rule 1, row R = 0 and column C = 1 both have exactly N = 3 black pixels.
    Rule 2, the rows have black pixel at column C = 1 are row 0, row 1 and row 2. They are exactly the same as row R = 0.

    Note:   The range of width and height of the input 2D array is [1,200].

    Companies: google

    Related Topics; Array, DFS

    Similar Questions: Lonely Pixel I
 */

/*
    怎么样算作是lonely pixel呢？ 在某行和某列上 都只有N个 'B'， 并且对于这样的行列组合，每一行的字符组成都要一样
    用hashmap把每一行当做key，value是 该行B的数量，
 */

class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        if (picture == null || picture.length == 0 || N == 0)
            return 0;
        int m = picture.length;
        int n = picture[0].length;
        int res = 0;

        HashMap<String, Integer> map = new HashMap();
        int[] colCount = new int[n];

        for (int i = 0; i < m; i++) {
            String key = scanRow(picture, i, N, colCount); // 构建key，以及计算colCount
            if (key.length() != 0) //如果该行B的count不等于N， 则method返回的是空字符串
                map.put(key, map.getOrDefault(key, 0) + 1);
        }

        for (String key : map.keySet()) {
            if (map.get(key) == N) {
                for (int i = 0; i < n; i++) {
                    if (key.charAt(i) == 'B' && colCount[i] == N)
                        res += N;
                }
            }
        }
        return res;
    }

    private String scanRow(char[][] picture, int row, int target, int[] colCount) {
        int n = picture[0].length;
        int rowCount = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (picture[row][i] == 'B') {
                rowCount++;
                colCount[i]++;
            }
            sb.append(picture[row][i]);
        }

        return rowCount == target ? sb.toString() : "";
    }

}