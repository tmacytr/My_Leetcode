/*
    719. Find K-th Smallest Pair Distance

    Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.

    Example 1:
    Input:
    nums = [1,3,1]
    k = 1
    Output: 0
    Explanation:
    Here are all the pairs:
    (1,3) -> 2
    (1,1) -> 0
    (3,1) -> 2
    Then the 1st smallest distance pair is (1,1), and its distance is 0.
    Note:
    2 <= len(nums) <= 10000.
    0 <= nums[i] < 1000000.
    1 <= k <= len(nums) * (len(nums) - 1) / 2.

    Companies: Google

    Related Topics: Array, Binary Search, Heap

    Similar Questions:
        1. Find K Pairs with Smallest Sums
        2. Kth Smallest Element in a Sorted Matrix
        3. Find K Closest Elements
        4. Kth Smallest Number in Multiplication Table
        5. K-th Smallest Prime Fraction
 */

/*
     The low is the min distance and high is the max distance. calculate a mid like usual binary search.
     Then it does countPairs(a, mid) to find number of pairs with absolute difference less than or equal to mid. then adjust low and high accordingly.

     But WHY the binary Search result MUST be one of the distances?
        imagine there is a sorted array d consists of all the diffs from items in nums.
        The size of this array will be n(n-1)/2, and we want to find d[k] in this problem.
        The condition used in the binary search is if(countPairs(a,mid)<k), it doesn’t return mid when countParis(a, mid)==k.
        Because if there is a gap between d[k] and d[k+1], then every number in [d[k], d[k+1]) would satisfy that condition,
        but we only want d[k]. So as long countPairs(a,mid)<k we increase low and we decrease high otherwise (including the case countPairs(a,mid)==k).
        By searching in this way. We get the first number that doesn’t satisfy countPairs(a,mid)<k, which is d[k]
 */


//Solution1: Binary Search + Sliding Window, 先将数组排序，找到最小和最大的pairs距离，以此为binarysearch的start和end点，找中间的距离，去计算小于这个距离的pairs有多少。
/*
    Time Complexity: O(NlogW + NlogN), where N is the length of nums, and W is equal to nums[nums.length - 1] - nums[0].
    The logW factor comes from our binary search, and we do O(N) work inside our call to possible (or to calculate count in Java).
    The final O(NlogN) factor comes from sorting.
 */
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);

        int low = nums[1] - nums[0];

        for (int i = 1; i < nums.length - 1; i++) {
            low = Math.min(low, nums[i + 1] - nums[i]);
        }

        int high = nums[n - 1] - nums[0];

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (countPairs(nums, mid) < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    // Returns number of pairs with absolute difference less than or equal to mid.
    private int countPairs(int[] nums, int mid) {
        int n = nums.length, res = 0;
        for (int start = 0; start < n; start++) {
            int end = start;
            while (end < n && nums[end] - nums[start] <= mid)
                end++;
            res += end - start - 1;
        }
        return res;
    }
}

//Solution2: prefer, Binary Search + Sliding Window
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);

        int lo = 0;
        int hi = nums[nums.length - 1] - nums[0];
        while (lo < hi) {
            int mi = (lo + hi) / 2;
            int count = 0, left = 0;
            // Returns number of pairs with absolute difference less than or equal to mid.
            for (int right = 0; right < nums.length; ++right) {
                while (nums[right] - nums[left] > mi)
                    left++;
                count += right - left;
            }
            //count = number of pairs with distance <= mi
            if (count >= k)
                hi = mi;
            else
                lo = mi + 1;
        }
        return lo;
    }
}