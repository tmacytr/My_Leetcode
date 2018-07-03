/*
	Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an array of the k digits. You should try to optimize your time and space complexity.

	Example 1:
	nums1 = [3, 4, 6, 5]
	nums2 = [9, 1, 2, 5, 8, 3]
	k = 5
	return [9, 8, 6, 5, 3]

	Example 2:
	nums1 = [6, 7]
	nums2 = [6, 0, 4]
	k = 5
	return [6, 7, 6, 0, 4]

	Example 3:
	nums1 = [3, 9]
	nums2 = [8, 9]
	k = 3
	return [9, 8, 9]
 */

//Time: O((m+n)^3), getMaxArray method using the remove K digits solution
class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] res = new int[k];
        //为什么会有 i = Math.max(0, k - n), 当k >  n 时，nums1 至少取 k - n 个数， 因为只靠nums2肯定不够
        //                                 当k <= n 时，可以任意在nums1取k个数，或者nums2取k个数， 或者在nums1取 k - i, nums2取 i
        for (int i = Math.max(0, k - n); i <= k && i <= m; i++) {
            int[] candidate = merge(getMaxArray(nums1, i), getMaxArray(nums2, k - i), k);
            if (greater(candidate, 0, res, 0))
                res = candidate;
        }
        return res;
    }

    private int[] merge (int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];
        for (int i = 0, j = 0, r = 0; r < k; r++)
            res[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        return res;
    }

    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    private int[] getMaxArray(int[] nums, int k) {
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < nums.length; i++) {
            while (stack.size() + nums.length - i > k && !stack.isEmpty() && stack.peek() < nums[i]) {
                stack.pop();
            }
            if (stack.size() < k)
                stack.push(nums[i]);
        }
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--)
            res[i] = stack.pop();
        return res;
    }


}