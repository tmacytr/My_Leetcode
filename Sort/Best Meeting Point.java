/*
	Best Meeting Point
	A group of two or more people wants to meet and minimize the total travel distance. You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. 
    The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

	For example, given three people living at (0,0), (0,4), and (2,2):

	1 - 0 - 0 - 0 - 1
	|   |   |   |   |
	0 - 0 - 0 - 0 - 0
	|   |   |   |   |
	0 - 0 - 1 - 0 - 0
	The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.

	Hint:

	Try to solve it in one dimension first. How can this solution apply to the two dimension case?
	Answer: find the median of these point
*/

/*
	Solution:
		1. 如何找到2D 图中各个点的 最短中间距离？find the median, 注意是median而不是平均值
		2. 因为是二维坐标， 因此我们需要找到所有居民点在x轴上的median以及y轴上的median， 比如 (0, 0), (0, 4) , (2, 2)， X轴（0, 0, 2)， Y轴（0, 2 , 4)
			注意Y轴需要排序，因为原始的数据无序， medianX = 0， medianY = 2；
		3. 将每个坐标点和median进行res += Math.abs(pointX-  medianX) + Math.abs(pointY - medianY),就是最短距离，注意并不需要求出这个点，只需要求出距离！
		4. 小技巧，求一组数中所有数与median的差，
			正常方法: 先找出median = size() - 2(这里的median如果是偶数，中间两个数任意一个都可以),再对每个点对median做abs差值相加
			文艺方法: list(end--) - list(start++);

	Why median?
	    Let’s think about simple cases in one dimension first:
            house location: [1,6] -> best point can be anywhere between 1~6
            house location: [1,2,6] -> best point is 2, because 1 and 6 don’t care where it is as long as the point is between them
            house location: [1,2,3,6] -> best point is 2.5(actually 2 or 3)
            For a sequence [a1, a2 … an], dist(a1,bestPoint)+dist(an,bestPoint) is constant and equal to dist(a1,an)
*/

//Solution1: O(mnlogmn)
class Solution {
    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> posX = new ArrayList();
        List<Integer> posY = new ArrayList();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    posX.add(i);
                    posY.add(j);
                }
            }
        }
        return minDist(posX) + minDist(posY);
    }

    private int minDist(List<Integer> pos) {
        Collections.sort(pos);
        int start = 0;
        int end = pos.size() - 1;
        int res = 0;
        while (start < end)
            res += pos.get(end--) - pos.get(start++);
        return res;
    }
}

//Solution2: O(mn) no sorting, prefer! 分别按行或者列取值可以免掉排序
class Solution {
    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> posX = new ArrayList();
        List<Integer> posY = new ArrayList();

        // 这样拿到的row值一定是排好序的
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    posX.add(i);
                }
            }
        }

        // 这样拿到的col值一定是排好序的
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[j][i] == 1) {
                    posY.add(i);
                }
            }
        }
        return minDist(posX) + minDist(posY);
    }

    private int minDist(List<Integer> pos) {
        int start = 0;
        int end = pos.size() - 1;
        int res = 0;
        while (start < end)
            res += pos.get(end--) - pos.get(start++);
        return res;
    }
}