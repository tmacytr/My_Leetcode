/*
    565. Array Nesting

    A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.

    Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S should be A[A[i]], and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.

    Example 1:
    Input: A = [5,4,0,3,1,6,2]
    Output: 4
    Explanation:
    A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.

    One of the longest S[K]:
    S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
    Note:
    N is an integer within the range [1, 20,000].
    The elements of A are all distinct.
    Each element of A is an integer within the range [0, N-1].

    Companies

    Related Topics

    Similar Questions
        1. Nested List Weight Sum
        2. Flatten Nested List Iterator
        3. Nested List Weight Sum II
 */

//Solution1: using hashset O(n) time and space
class Solution {
    public int arrayNesting(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int res = 0;
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i]))
                continue;
            int count = 0;
            while (set.add(nums[i])) {
                nums[i] = nums[nums[i]];
                count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }
}

//Solution1: O(1) space
class Solution {
    public int arrayNesting(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int res = 1;

        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            int index = i;
            while (nums[index] != -1) {
                int newIndex = nums[index];
                nums[index] = -1;
                index = newIndex;
                count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }
}