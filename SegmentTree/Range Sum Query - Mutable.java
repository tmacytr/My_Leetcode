/*
	Range Sum Query - Mutable
	Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

	The update(i, val) function modifies nums by updating the element at index i to val.
	Example:
	Given nums = [1, 3, 5]

	sumRange(0, 2) -> 9
	update(1, 2)
	sumRange(0, 2) -> 8
	Note:
	The array is only modifiable by the update function.
	You may assume the number of calls to update and sumRange function is distributed evenly.
	Tags: Segment Tree,Binary Indexed Tree
*/

/*
    需要求任意段的和，且会随机修改元素，用线段树(Segment Tree)再合适不过了 http://bookshadow.com/weblog/2015/08/13/segment-tree-set-1-sum-of-given-range/
*/
//Solution1 Segment Tree
class NumArray {
    SegmentTreeNode root;
    public NumArray(int[] nums) {
        if (nums == null || nums.length == 0)
            root = null;
        root = buildTree(nums, 0, nums.length - 1);
    }
    
    public void update(int i, int val) {
        updateTree(root, i, val);
    }
    
    public int sumRange(int i, int j) {
        return getSum(root, i, j);
    }
    
    public void updateTree(SegmentTreeNode root, int pos, int val) {
        // which mean we find the postion to update the value;
        if (root.start == root.end) {
            root.sum = val;
            return;
        }
        // dfs to find the postion
        int mid = root.start + (root.end  - root.start) / 2;
        updateTree((pos <= mid ? root.left : root.right), pos, val);
        root.sum = root.left.sum + root.right.sum; // update all the sum.
    }
    
        
    public int getSum(SegmentTreeNode root, int start, int end) {
        if (root.end == end && root.start == start) {
            return root.sum;
        }
        int mid = root.start + (root.end - root.start) / 2;
        if (start > mid) {
            return getSum(root.right, start, end);
        }
        
        if (end <= mid) {
            return getSum(root.left, start, end);
        }
        
        //  start <= mid < end
        return getSum(root.left, start, mid) + getSum(root.right, mid + 1, end);
    }
    
    public SegmentTreeNode buildTree(int[] nums, int start, int end) {
        if (start > end)
            return null;
        SegmentTreeNode root = new SegmentTreeNode(start, end);
        if (start == end) {
            root.sum = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            root.left = buildTree(nums, start, mid);
            root.right = buildTree(nums, mid + 1, end);
            root.sum = root.left.sum + root.right.sum;
        }
        return root;
    }
}

class SegmentTreeNode {
    int start;
    int end;
    int sum;
    SegmentTreeNode left;
    SegmentTreeNode right;
    
    public SegmentTreeNode(int start, int end) {
        this.start = start;
        this.end = end;
        this.sum = 0;
        this.left = this.right = null;
    }
}

//Solution2 Binary Indexed Tree Prefer!
/*
        Using Binary Indexed Tree, we can do both tasks in O(Logn) time. 
        The advantages of Binary Indexed Tree over Segment are, requires less space and very easy to implement..


        How does Binary Indexed Tree work?

        The idea is based on the fact that all positive integers can be represented as sum of powers of 2. 
        For example 19 can be represented as 16 + 2 + 1. Every node of BI Tree stores sum of n elements where n is a power of 2. 
        For example, in the above first diagram for getSum(), sum of first 12 elements can be obtained by sum of last 4 elements (from 9 to 12) plus sum of 8 elements (from 1 to 8). 
        The number of set bits in binary representation of a number n is O(Logn). Therefore, we traverse at-most O(Logn) nodes in both getSum() and update() operations. 
        Time complexity of construction is O(nLogn) as it calls update() for all n elements.

        https://leetcode.com/discuss/74222/java-using-binary-indexed-tree-with-clear-explanation
 */
public class NumArray {
    int[] nums;
    int[] BIT;
    int n;
    public NumArray(int[] nums) {
        this.nums = nums;
        n = nums.length; 
        BIT = new int[n + 1];
        for (int i = 0; i < n; i++) {
            init(i, nums[i]);
        }
    }
    
    public void init(int i, int val) {
        i++;
        while (i <= n) {
            BIT[i] += val;
            i += (i & -i); //创建的时候 不停的找右边的父节点   i & -i:返回i最右边的1的值
        }
    }

    void update(int i, int val) {
        int diff = val - nums[i];
        nums[i] = val;
        init(i, diff);
    }

    public int getSum(int i) {
        int sum = 0;
        i++;
        while (i > 0) {
            sum += BIT[i];
            i -= (i & -i);//取sum的时候，不停的找左边的父节点
        }
        return sum;
    }

    public int sumRange(int i, int j) {//因为getSum(k) 时 是取从0 ~ K的和，所以 取i ~ j时，如果是getSum(j) - getSum(i)会将i的值删除，因此取i - 1
        return getSum(j) - getSum(i - 1);
    }
}



//Solution3 Binary Indexed Tree
public class NumArray {
    int[] tree;
    int[] nums;
    int size;
    public NumArray(int[] nums) {
        this.size = nums.length;
        this.nums = new int[size];
        this.tree = new int[size];
        for (int i = 0; i < size; ++i)
            update(i, nums[i]);
    }
    public void update(int i, int val) {
        int delta = val - nums[i];
        nums[i] = val;
        for (; i < size; i |= i + 1)
            tree[i] += delta;
    }
    public int sumRange(int i, int j) {
        return sum(j) - sum(i - 1);
    }
    public int sum(int ind) {
        int ans = 0;
        while (ind >= 0) {
            ans += tree[ind];
            ind &= ind + 1;
            ind--;
        }
        return ans;
    }
}
// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.update(1, 10);
// numArray.sumRange(1, 2);