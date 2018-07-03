/*
	Count of Range Sum
	Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
	Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

	Note:
	A naive algorithm of O(n2) is trivial. You MUST do better than that.

	Example:
	Given nums = [-2, 5, -1], lower = -2, upper = 2,
	Return 3.
	The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 */

//Solution1: native method, O(n*n) timeout
/*
	Java - Naive Solution(这个做法为了让所有区间都能表示成一个区间减另一个区间，size额外增加了1，
	sums[i]定义为前i个元素之和，这样连只包含第一个元素的区间的和都可以表示为sums[1]-sums[0],这样写不用再分类讨论，挺好的)
 */
public class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (sum[j] - sum[i] >= lower && sum[j] - sum[i] <= upper) {
                    res++;
                }
            }
        }
        return res;
    }
}


/*
    The merge sort based solution counts the answer while doing the merge. During the merge stage, we have already sorted the left half [start, mid) and right half [mid, end). We then iterate through the left half with index i. For each i, we need to find two indices k and j in the right half where

    j is the first index satisfy sums[j] - sums[i] > upper and
    k is the first index satisfy sums[k] - sums[i] >= lower.

    Then the number of sums in [lower, upper] is j-k. We also use another index t to copy the elements satisfy sums[t] < sums[i] to a cache in order to complete the merge sort.
    Despite the nested loops, the time complexity of the “merge & count” stage is still linear. Because the indices k, j, t will only increase but not decrease,
    each of them will only traversal the right half once at most. The total time complexity of this divide and conquer solution is then O(n log n).
 */
//Solution2:mergesort, O(NlogN) running time best, but hardly understand
public class Solution {
    int count = 0;
    int lower;
    int upper;
    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] sum = new long[nums.length + 1];
        long[] temp = new long[nums.length + 1];
        sum[0] = 0;
        this.lower = lower;
        this.upper = upper;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + (long) nums[i - 1];
        }

        mergesort(sum, 0, sum.length - 1, temp);
        return count;
    }

    private void mergesort(long[] sum, int start, int end, long[] temp) {
        if (start >= end) {
            return;
        }
        int mid = start + (end - start) / 2;
        mergesort(sum, start, mid, temp);
        mergesort(sum, mid + 1, end, temp);
        merge(sum, start, mid, end, temp);
    }

    private void merge(long[] sum, int start, int mid, int end, long[] temp) {
        int right = mid + 1;
        int index = start;
        int low = mid + 1, high = mid + 1;
        for (int left = start; left <= mid; left++) {
            while (low <= end && sum[low] - sum[left] < lower) {
                low++;
            }
            while (high <= end && sum[high] - sum[left] <= upper) {
                high++;
            }
            while (right <= end && sum[right] < sum[left]) {
                temp[index++] = sum[right++];
            }
            temp[index++] = sum[left];
            count += high - low;
        }
        while (right <= end) {
            temp[index++] = sum[right++];
        }

        for (int i = start; i <= end; i++) {
            sum[i] = temp[i];
        }
    }
}

//Solution3: construct BST(Segment Tree)
/*
	默认方法：construct BST (好理解很多) ， Time: O(NlogN)

	这个做法是建立BST，把prefix sum作为TreeNode.val存进去，为了避免重复的TreeNode.val处理麻烦，设置一个count记录多少个重复TreeNode.val, 
	维护leftSize, 记录比该节点value小的节点个数，rightSize同理

	由于RangeSum S(i,j)在[lower,upper]之间的条件是lower<=sums[j+1]-sums[i]<=upper, 所以我们每次insert一个新的PrefixSum sums[k]进这个BST之前，
	先寻找一下（rangeSize）该BST内已经有多少个PrefixSum（叫它sums[t]吧）满足lower<=sums[k]-sums[t]<=upper, 即寻找有多少个sums[t]满足： 

	sums[k]-upper<=sums[t]<=sums[k]-lower

	BST提供了countSmaller和countLarger的功能，计算比sums[k]-upper小的RangeSum数目和比sums[k]-lower大的数目，再从总数里面减去，就是所求
 */


public class Solution {
    private class TreeNode {
        long val = 0;
        int count = 1;
        int leftSize = 0;
        int rightSize = 0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(long v) {
            this.val = v;
            this.count = 1;
            this.leftSize = 0;
            this.rightSize = 0;
        }
    }

    private TreeNode insert(TreeNode root, long val) {
        if(root == null) {
            return new TreeNode(val);
        }
        if(root.val == val) {
            root.count++;
        } else if(val < root.val) {
            root.leftSize++;
            root.left = insert(root.left, val);
        } else if(val > root.val) {
            root.rightSize++;
            root.right = insert(root.right, val);
        }
        return root;
    }

    private int countSmaller(TreeNode root, long val) {
        if(root == null) {
            return 0;
        }
        if(root.val == val) {
            return root.leftSize;
        }
        if(root.val > val) {
            return countSmaller(root.left, val);
        }
        return root.leftSize + root.count + countSmaller(root.right, val);
    }

    private int countLarger(TreeNode root, long val) {
        if(root == null) {
            return 0;
        }
        if(root.val == val) {
            return root.rightSize;
        }
        if(root.val < val) {
            return countLarger(root.right, val);
        }
        return countLarger(root.left, val) + root.count + root.rightSize;
    }

    private int rangeSize(TreeNode root, long lower, long upper) {
        int total = root.count + root.leftSize + root.rightSize;
        int smaller = countSmaller(root, lower);    // Exclude everything smaller than lower
        int larger = countLarger(root, upper);      // Exclude everything larger than upper
        return total - smaller - larger;
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        if(nums.length == 0) {
            return 0;
        }
        long[] sums = new long[nums.length + 1];
        for(int i = 0; i < nums.length; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        TreeNode root = new TreeNode(sums[0]);
        int res = 0;
        for(int i = 1; i < sums.length; i++) {
            res += rangeSize(root, sums[i] - upper, sums[i] - lower);
            insert(root, sums[i]);
        }
        return res;
    }
}