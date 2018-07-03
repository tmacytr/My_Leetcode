/*
    587. Erect the Fence

    There are some trees, where each tree is represented by (x,y) coordinate in a two-dimensional garden. Your job is to fence the entire garden using the minimum length of rope as it is expensive. The garden is well fenced only if all the trees are enclosed. Your task is to help find the coordinates of trees which are exactly located on the fence perimeter.

    Example 1:
    Input: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
    Output: [[1,1],[2,0],[4,2],[3,3],[2,4]]
    Explanation:

    Example 2:
    Input: [[1,2],[2,2],[4,2]]
    Output: [[1,2],[2,2],[4,2]]
    Explanation:

    Even you only have trees in a line, you need to use rope to enclose them.
    Note:

    All trees should be enclosed together. You cannot cut the rope to enclose trees that will separate them in more than one group.
    All input integers will range from 0 to 100.
    The garden has at least one tree.
    All coordinates are distinct.
    Input points have NO order. No order required for output.

 */

/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */



/*
    Solution1: Monotone Chain,

    概念： 从原点出发求个点P和Q的方向，如果Q在P的左边（逆时针方向）或者右边（顺时针方向), 则Q X P（corss product，向量积） > 0, 也就是Q的斜率大于P的斜率
            Q: (x1, y1),
            P: (x2, y2),
            slopeP(斜率)=y1/x1,
            slopeQ(斜率)=y2/x2,  if slopeQ > slopeP =>  y2/x2 > y1/x1 => y2 * x1 - x2 * y1 > 0, 也就是说q点在p点左边（逆时针方向）

            顺时针：pq的斜率 > qr的斜率
            逆时针：pq的斜率 < qr的斜率
            一条直线上：斜率相等。

    算法思想：Monotone Chain,
            排序所有的点，按照x坐标升序，y坐标降序排列。 以最左边的坐标开始遍历，每次比较p（已经确认过的点） q（需要确认的点） r（以r为比较点),  通过判断pq - qr验证pqr是顺时针还是逆时针路径。
            先从左到右扫描一遍算lower hull，只要要验证的点是顺时针就出栈
            再从右到左扫描一遍算upper hull，只要要验证的点是顺时针就出栈

    Analysis:
        Time complexity : O(nlogn). Sorting the given points takes O(nlogn) time. Further, after sorting the points can be considered in two cases,
                          while being pushed onto the hull or while popping from the hull. At most, every point is touched twice(both push and pop) taking 2n(O(n)) time in the worst case.

        Space complexity : O(n). hullhull stack can grow upto size nn.
 */
class Solution {
    public List<Point> outerTrees(Point[] points) {
        Arrays.sort(points, (p, q) -> p.x == q.x ? q.y - p.y : p.x - q.x);
        Stack<Point> stack = new Stack();

        // from left -> right to compute the lower hull
        for (int i = 0; i < points.length; i++) {
            while (stack.size() >= 2 && orientation(stack.get(hull.size() - 2), stack.get(stack.size() - 1), points[i]) > 0)
                stack.pop();
            stack.push(points[i]);
        }

        stack.pop(); //要把最右边的点pop出去，重新进栈开始确认
        // from right -> left to compute the upper hull
        for (int i = points.length - 1; i >= 0; i--) {
            while (stack.size() >= 2 && orientation(stack.get(stack.size() - 2), stack.get(hull.size() - 1), points[i]) > 0)
                stack.pop();
            stack.push(points[i]);
        }
        return new ArrayList(new HashSet(stack));
    }

    // 判断从p点出发经过q点到达r点，是顺时针还是逆时针的，如果是> 0,则意味着是顺时针方向，why ? because pq - qr > 0 => slope pq > slope qr, 所以是顺时针
    public int orientation(Point p, Point q, Point r) { //  p -> q -> r,
        return (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y); // pq - qr   (y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1)
    }
}