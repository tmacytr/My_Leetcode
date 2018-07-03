/*
    391. Perfect Rectangle
    DescriptionHintsSubmissionsDiscussSolution
    Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular region.

    Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is represented as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).


    Example 1:

    rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [3,2,4,4],
      [1,3,2,4],
      [2,3,3,4]
    ]

    Return true. All 5 rectangles together form an exact cover of a rectangular region.

    Example 2:

    rectangles = [
      [1,1,2,3],
      [1,3,2,4],
      [3,1,4,2],
      [3,2,4,4]
    ]

    Return false. Because there is a gap between the two rectangular regions.

    Example 3:

    rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [1,3,2,4],
      [3,2,4,4]
    ]

    Return false. Because there is a gap in the top center.

    Example 4:

    rectangles = [
      [1,1,3,3],
      [3,1,4,2],
      [1,3,2,4],
      [2,2,4,4]
    ]

    Return false. Because two of the rectangles overlap with each other.
 */


/*
    1. 这里的图 是从左下到右上的
    2. 如果给的一组矩形可以完美组合成一个正方形的话，一定需要满足以下条件:
       （1）除了4个顶点以外的所有顶点必须要出现两次，4个顶点出现1次
        (2) 所有小矩形组成的和等于perfect square的和

        第一个限制条件可以用HashSet解决，进去一次add,再出现一次remove,最后算这个set是否包含4个顶点以及size == 4,  HashSet的key可以yong X + " " + Y 表示
        第二个限制条件可以叠加记录每个小矩形和，跟最后找到的4个顶点的大矩形的和进行对比是否相等
 */

class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        if (rectangles == null || rectangles.length == 0)
            return false;
        int m = rectangles.length;
        int n = rectangles[0].length;

        int x1 = Integer.MAX_VALUE;
        int x2 = Integer.MIN_VALUE;
        int y1 = Integer.MAX_VALUE;
        int y2 = Integer.MIN_VALUE;

        HashSet<String> set = new HashSet();
        int res = 0;

        for (int[] rect : rectangles) {
            x1 = Math.min(rect[0], x1);
            y1 = Math.min(rect[1], y1);
            x2 = Math.max(rect[2], x2);
            y2 = Math.max(rect[3], y2);

            res += (rect[2] - rect[0]) * (rect[3] - rect[1]);

            String s1 = rect[0] + " " + rect[1];
            String s2 = rect[2] + " " + rect[1];
            String s3 = rect[0] + " " + rect[3];
            String s4 = rect[2] + " " + rect[3];

            if (!set.add(s1))
                set.remove(s1);
            if (!set.add(s2))
                set.remove(s2);
            if (!set.add(s3))
                set.remove(s3);
            if (!set.add(s4))
                set.remove(s4);
        }

        if (!set.contains(x1 + " " + y1) || !set.contains(x2 + " " + y2) || !set.contains(x1 + " " + y2) || !set.contains(x2 + " " + y1) || set.size() != 4)
            return false;
        return res == (x2 - x1) * (y2 - y1);
    }
}