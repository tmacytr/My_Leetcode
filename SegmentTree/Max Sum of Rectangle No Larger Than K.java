/*
    363. Max Sum of Rectangle No Larger Than K

    Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum is no larger than k.

    Example:
    Given matrix = [
      [1,  0, 1],
      [0, -2, 3]
    ]
    k = 2
    The answer is 2. Because the sum of rectangle [[0, 1], [-2, 3]] is 2 and 2 is the max number no larger than k (k = 2).

    Note:
    The rectangle inside the matrix must have an area > 0.
    What if the number of rows is much larger than the number of columns?
    Credits:
    Special thanks to @fujiaozhu for adding this problem and creating all test cases.
 */

//Solution: 2D Kadane's algorithm + 1D maxSum problem with sum limit k
class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0)
            return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        int res = Integer.MIN_VALUE;

        //outer loop should use smaller axis
        //now we assume we have more rows than cols, therefore outer loop will be based on cols
        for (int left = 0; left < n; left++) {
            int[] sums = new int[m];
            for (int right = left; right < n; right++) {
                //array that accumulate sums for each row from left to right
                for (int i = 0; i < m; i++) {
                    sums[i] += matrix[i][right];
                }
                //we use TreeSet to help us find the rectangle with maxSum <= k with O(logN) time
                TreeSet<Integer> set = new TreeSet();
                //add 0 to cover the single row case
                set.add(0);
                int curSum = 0;

                for (int sum : sums) {
                    curSum += sum;
                    //we use sum subtraction (curSum - sum) to get the subarray with sum <= k
                    //therefore we need to look for the smallest sum >= currSum - k
                    Integer num = set.ceiling(curSum - k); //ceiling()方法返回set中不小于给定元素的最小元素；如果不存在这样的元素，则返回 null。
                    if (num != null)
                        res = Math.max(res, curSum - num);
                    set.add(curSum);

                    /*
                        sums[i,j] = sums[i] - sums[j].
                        sums[i,j] is target subarray that needs to have sum <= k
                        sums[j] is known current cumulative sum
                        And we use binary search to find sums[i].
                        Therefore sums[i] needs to have sum >= sums[j] - k

                        curSum => sums[i], num => sums[j]
                        if  curSum - num has larger sum closet to K then we assign to res
                     */
                }
            }
        }
        return res;
    }
}