/*
    493. Reverse Pairs

    Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

    You need to return the number of important reverse pairs in the given array.

    Example1:

    Input: [1,3,2,3,1]
    Output: 2
    Example2:

    Input: [2,4,3,5,1]
    Output: 3

    Companies

    Related Topics:
        1. Divide and Conquer
        2. Binary Indexed Tree
        3. Segment Tree
        4. Binary Search Tree

    Similar Questions:
        1. Count of Smaller Numbers After Self
        2. Count of Range Sum
 */

//Solution1: Binary Indexed Tree, 不推荐 不太好理解
class Solution {
    public int reversePairs(int[] nums) {
        int res = 0;
        int[] copy = Arrays.copyOf(nums, nums.length);
        int[] bit = new int[copy.length + 1];

        Arrays.sort(copy);

        for (int num : nums) {
            res += search(bit, findIndex(copy, 2L * num + 1));
            insert(bit, findIndex(copy, num));
        }
        return res;
    }

    private int findIndex(int[] nums, long val) {
        int start = 0;
        int end = nums.length - 1;
        int mid = 0;

        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] >= val) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start + 1;
    }
    // 找比当前值大的，要往后找
    private int search(int[] bit, int i) {
        int res = 0;
        while (i < bit.length) {
            res += bit[i];
            i += i & -i;
        }
        return res;
    }
    // 更新所有小于等于位置i的值所在的bit
    private void insert(int[] bit, int i) {
        while (i > 0) {
            bit[i] += 1;
            i -= i & -i;
        }
    }
}

//Solution2: prefer, merge sort, divide and conquer，分别在左右两边找reverse pairs
// Time complexity: T(n) = 2 * T(n/2) + nlgn(排序子数组 + n（计算split后两边的reverse pair) = O(nlg^2n)
class Solution {
    public int reversePairs(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int start, int end) {
        if (start >= end)
            return 0;
        int mid = start + (end - start) / 2;
        int res = helper(nums,start, mid) + helper(nums, mid + 1, end);
        Arrays.sort(nums, start, mid + 1);
        Arrays.sort(nums, mid + 1, end + 1);
        int i = start;
        int j = mid + 1;
        while (i <= mid && j <= end) {
            if ((long)nums[i] > (long)2 * nums[j]) {  //因为乘以2， 需要判断是否溢出
                res += (mid - i + 1);
                j++;
            } else {
                i++;
            }
        }
        return res;
    }
}

//Solution3: soluton2的改进版本， 不需要调用Arrays.sort()
class Solution {
    public int reversePairs(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int start, int end) {
        if (start >= end)
            return 0;
        int mid = start + (end - start) / 2;
        int res = helper(nums,start, mid) + helper(nums, mid + 1, end);
        int i = start, j = mid + 1, k = 0, p = mid + 1;
        int[] merge = new int[end - start + 1];

        while (i <= mid) {
            while (p <= end && (long)nums[i] > (long)2 * nums[p])
                p++;
            res += p - (mid + 1);
            while (j <= end && nums[i] >= nums[j])
                merge[k++] = nums[j++];
            merge[k++] = nums[i++];
        }

        while (j <= end)
            merge[k++] = nums[j++];
        System.arraycopy(merge, 0, nums, start, merge.length);
        return res;
    }
}