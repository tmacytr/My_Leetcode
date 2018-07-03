/*
    356. Line Reflection

    Given n points on a 2D plane, find if there is such a line parallel to y-axis that reflect the given points.

    Example 1:
    Given points = [[1,1],[-1,1]], return true.

    Example 2:
    Given points = [[1,1],[-1,-1]], return false.

    Follow up:
    Could you do better than O(n2)?

    Companies: Google
    Topics: Hashtable, Math
    Similar Questions: Max Points on a Line, Number of Boomerangs
 */

/*
    给了我们一堆点，问我们存不存在一条平行于y轴的直线，使得所有的点关于该直线对称。
    首先我们找到所有点的横坐标的最大值和最小值，那么二者的平均值就是中间直线的横坐标，然后我们遍历每个点，如果都能找到直线对称的另一个点，则返回true，反之返回false
 */
class Solution {
    public boolean isReflected(int[][] points) {
        HashSet<String> set = new HashSet();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (int[] point : points) {
            minX = Math.min(minX, point[0]);
            maxX = Math.max(maxX, point[0]);
            String line = point[0] + "|" + point[1];
            set.add(line);
        }

        int sum = maxX + minX;
        for (int[] point : points) {
            String line = (sum - point[0]) + "|" + point[1];
            if (!set.contains(line))
                return false;
        }
        return true;
    }
}