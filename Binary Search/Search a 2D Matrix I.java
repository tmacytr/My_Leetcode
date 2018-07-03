
/*
	Search a 2D Matrix 
	Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

	Integers in each row are sorted from left to right.
	The first integer of each row is greater than the last integer of the previous row.
	For example,

	Consider the following matrix:

	[
	  [1,   3,  5,  7],
	  [10, 11, 16, 20],
	  [23, 30, 34, 50]
	]
	Given target = 3, return true.
	Tag: Array, Binary Search
*/

/*
	虽然本题看似是矩阵问题，但是本着搜索题目关键字为第一步的原则，
	可以找到：each row are sorted，每一行按照顺序也是sorted。同时也是数组保存。
	但是本题的难点就是如何将2D矩阵转换成1D，然后利用二分查找法来解决问题。
	转换的重点就在于每个点的位置，在矩阵表示中，我们习惯用（i，j）来表示一个点，
	所以这就有碍于我们使用low high mid来指向需要的位置。
	为了解决问题，第一步就是需要将这个矩阵按照顺序拉成一条线。

position: 0   1   2   3   4   5   6   7   8   9    10   11   

values:   1   3   5   7   10  11  16  20  23  30   34   50

row:      0   0   0   0   1   1   1   1   2   2    2    2

column:   0   1   2   3   0   1   2   3   0   1    2    3 
*/

/*
	如果我们按S型遍历该二维数组，可以得到一个有序的一维数组，那么我们只需要用一次二分查找法，
	而关键就在于坐标的转换，如何把二维坐标和一维坐标转换是关键点: 把一个长度为n的一维数组转化为m*n的二维数组(m*n = n)后，
	那么原一维数组中下标为i的元素将出现在二维数组中的[i/n][i%n]的位置
*/

//Solution1:
public class Solution {
	public boolean searchMatrix(int[][] matrix, int target) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		int low = 0;
		int high = row * cols - 1;

		while (low <= high) {
			int mid = (low + high) / 2;
			//  mid/cols 可以得到属于哪一行， mid%cols 可以得到属于某一列
			if (matrix[mid / cols][mid % cols] == target)
				return true;
			else if (matrix[mid / cols][mid % cols] > target)
				high = mid - 1;
			else
				low = mid + 1;
		}
		return false;
	}
}

//Solution2:
class Solution {
	public boolean searchMatrix(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
			return false;
		int m = matrix.length;
		int n = matrix[0].length;

		int start = 0;
		int end = m * n - 1;

		while (start + 1< end) {
			int mid = start + (end - start) / 2;
			if (matrix[mid / n][mid % n] > target) {
				end = mid;
			} else {
				start = mid;
			}
		}
		return matrix[start / n][start % n] == target || matrix[end / n][end % n] == target;
	}
}

//我们可以从左下或者右上开始search,
//Solution1: O(m + n) time
public class Solution {
	public boolean searchMatrix(int[][] matrix, int target) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}
		int rowLen = matrix.length;
		int colLen = matrix[0].length;

		int i = 0;
		int j = colLen - 1;

		while (i < rowLen && j >= 0) {
			if (matrix[i][j] == target) {
				return true;
			} else if (matrix[i][j] > target) {
				j--;
			} else {
				i++;
			}
		}
		return false;
	}
}
