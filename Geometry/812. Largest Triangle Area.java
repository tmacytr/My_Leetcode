/*
    812. Largest Triangle Area

    You have a list of points in the plane. Return the area of the largest triangle that can be formed by any 3 of the points.

    Example:
    Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
    Output: 2
    Explanation:
    The five points are show in the figure below. The red triangle is the largest.


    Notes:

    3 <= points.length <= 50.
    No points will be duplicated.
     -50 <= points[i][j] <= 50.
    Answers within 10^-6 of the true value will be accepted as correct.

    Company: Google
    Tags: Math
 */

//Solution1: Math 数学公式Shoelace formula，求三个点组成的三角形面积


/*          C (e, f)
            /\
           /  \
          /    \
  A(m, n  ------ B (a, b)

  这三点组成的三角形的面积等于从任一点开始逆时针方向回到原点的路径上进行以下运算(这里以B作为原点):

                B  C  A  B
                a  e  m  a
                 \/ \/ \/       = 0.5 * (a * f + e * n + m * b - b * e - f * m - n * a
                b  f  n  b

 */

// Brute force 去遍历每三个点 算area
class Solution {
    public double largestTriangleArea(int[][] points) {
        int N = points.length;
        double res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    res = Math.max(res, area(points[i], points[j], points[k]));
                }
            }
        }
        return res;
    }

    private double area(int[] P, int[] Q, int[] R) {
        return 0.5 * Math.abs(P[0] * Q[1] + Q[0] * R[1] + R[0] * P[1] - P[1] * Q[0] - Q[1] * R[0] - R[1] * P[0]);
    }
}