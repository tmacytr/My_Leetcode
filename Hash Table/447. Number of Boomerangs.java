/*
    447. Number of Boomerangs

    Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) such that the distance between i and j equals the distance between i and k (the order of the tuple matters).

    Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the range [-10000, 10000] (inclusive).

    Example:
    Input:
    [[0,0],[1,0],[2,0]]

    Output:
    2

    Explanation:
    The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]

    Companies: Google
    Related Topics: Hashtable
    Similar: Line Reflection

 */

// 把每个点当做i，遍历其他的点算距离，距离放到hashtable中，看一共有多少点的距离一样，每个相同距离的点变换数量 = count * (count - 1)
// 如果我们有一个点a，还有两个点b和c，如果ab和ac之间的距离相等，那么就有两种排列方法abc和acb；
// 如果有三个点b，c，d都分别和a之间的距离相等，那么有六种排列方法，abc, acb, acd, adc, abd, adb，所以如果有n个点和a距离相等，那么排列方式为n(n-1)
class Solution {
    public int numberOfBoomerangs(int[][] points) {
        if (points == null || points.length < 3)
            return 0;
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            HashMap<Integer, Integer> map = new HashMap();
            for (int j = 0; j < points.length; j++) {
                if (i == j)
                    continue;
                int distance = distance(points[i], points[j]);
                map.put(distance, map.getOrDefault(distance, 0) + 1);
            }
            for (int count : map.values()) {
                if (count >= 2) {
                    res += count * (count - 1);
                }
            }
        }
        return res;
    }

    private int distance(int[] point1, int[] point2) {
        int distX = point1[0] - point2[0];
        int distY = point1[1] - point2[1];
        return distX * distX+ distY * distY;
    }
}