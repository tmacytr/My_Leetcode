/*
    469. Convex Polygon

    Given a list of points that form a polygon when joined sequentially, find if this polygon is convex (Convex polygon definition).

    Note:

    There are at least 3 and at most 10,000 points.
    Coordinates are in the range -10,000 to 10,000.
    You may assume the polygon formed by given points is always a simple polygon (Simple polygon definition). In other words, we ensure that exactly two edges intersect at each vertex, and that edges otherwise don't intersect each other.
    Example 1:

    [[0,0],[0,1],[1,1],[1,0]]

    Answer: True

    Explanation:
    Example 2:

    [[0,0],[0,10],[10,10],[10,0],[5,5]]

    Answer: False

    Explanation:
 */

// Solution1: 每次选3个相邻的点出来检查是否是逆时针或者顺时针， 只要确定了orientation， 后面遍历的时候出现相反的就一定不是Convex Polygon.
class Solution {
    public boolean isConvex(List<List<Integer>> points) {
        int len = points.size();
        int flag = 0; //为什么要有flag？因为给的点的顺序可能是顺时针或者是逆时针，所以需要用flag确定方向，之后orientation如果拿到相反的方向那就返回false
        for (int i = 0; i < points.size(); i++) {
            List<Integer> p = points.get(i);
            List<Integer> q = points.get((i + 1) % len);
            List<Integer> r = points.get((i + 2) % len);
            int orientation = orientation(p, q, r); // orientation返回0 就是平行，如果>0 p->q>r顺时针， 如果<0: p->q>r逆时针
            if (orientation == 0)
                continue;
            if (flag == 0)
                flag = orientation > 0 ? 1 : -1; // 确定遍历点的方向
            else if (flag * orientation < 0)
                return false;
        }
        return true;
    }

    private int orientation(List<Integer> p, List<Integer> q, List<Integer> r) {
        return (q.get(1) - p.get(1)) * (r.get(0) - q.get(0)) - (q.get(0) - p.get(0)) * (r.get(1) - q.get(1));
    }
}