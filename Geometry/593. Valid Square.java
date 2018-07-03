/*
    593. Valid Square

    Given the coordinates of four points in 2D space, return whether the four points could construct a square.

    The coordinate (x,y) of a point is represented by an integer array with two integers.

    Example:
    Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
    Output: True
    Note:

    All the input integers are in the range [-10000, 10000].
    A valid square has four equal sides with positive length and four equal angles (90-degree angles).
    Input points have no order.

    Companies: Purestorage

    Related Topics: Math
 */

/*
    这道题给了我们四个点，让我们验证这四个点是否能组成一个正方形，我们可以仅通过边的关系的来判断是否是正方形，

    根据初中几何的知识我们知道正方形的四条边相等，两条对角线相等，满足这两个条件的四边形一定是正方形。

    我们只需要对四个点，两两之间算距离，如果计算出某两个点之间距离为0，说明两点重合了，直接返回false，如果不为0，那么我们就建立距离和其出现次数之间的映射，

    最后如果我们只得到了两个不同的距离长度，那么就说明是正方形了
 */

class Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        Set<Integer> set = new HashSet(Arrays.asList(dis(p1, p2), dis(p1, p3), dis(p1, p4), dis(p2, p3), dis(p2, p4), dis(p3, p4)));
        return !set.contains(0) && set.size() == 2;
    }

    private int dis(int[] a, int [] b) {
        return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
    }
}