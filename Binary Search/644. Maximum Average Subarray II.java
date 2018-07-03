/*
    644. Maximum Average Subarray II

    Given an array consisting of n integers, find the contiguous subarray whose length is greater than or equal to k that has the maximum average value. And you need to output the maximum average value.

    Example 1:
    Input: [1,12,-5,-6,50,3], k = 4
    Output: 12.75
    Explanation:
    when length is 5, maximum average value is 10.8,
    when length is 6, maximum average value is 9.16667.
    Thus return 12.75.
    Note:
    1 <= k <= n <= 10,000.
    Elements of the given array will be in range [-10,000, 10,000].
    The answer with the calculation error less than 10-5 will be accepted.

    Companies: Google

    Related Topics: Array, Binary Search

    Similar Questions
        Maximum Average Subarray I
 */

//Solution1: brute force
class Solution {
    public double findMaxAverage(int[] nums, int n) {
        double res = Integer.MIN_VALUE;
        double sum = 0;
        for (int k = n; k <= nums.length; k++) {
            sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (i == k - 1)
                    res = Math.max(res, sum / k);
                if (i >= k) {
                    sum -= nums[i - k];
                    res = Math.max(res, sum / k);
                }
            }
        }
        return res;
    }
}

//Solution2: Binary Search
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        double l = Integer.MIN_VALUE;
        double r = Integer.MAX_VALUE;

        while (r - l > 0.000004) {
            double mid = (l + r) / 2;
            if (check(nums, k, mid))
                l = mid;
            else
                r = mid;
        }
        return r;
    }

    private boolean check(int[] nums, int k, double x) {
        int n = nums.length;
        double[] a = new double[n];
        for (int i = 0; i < n; i++)
            a[i] = nums[i] - x;
        double now = 0, last = 0;
        for (int i = 0; i < k; i++)
            now += a[i];
        if (now >= 0)
            return true;
        for (int i = k; i < n; i++) {
            now += a[i];
            last += a[i - k];
            if (last < 0) {
                now -= last;
                last = 0;
            }
            if (now >= 0)
                return true;
        }
        return false;
    }
}