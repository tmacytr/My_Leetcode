/*
    801. Minimum Swaps To Make Sequences Increasing

    We have two integer sequences A and B of the same non-zero length.

    We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position in their respective sequences.

    At the end of some number of swaps, A and B are both strictly increasing.  (A sequence is strictly increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)

    Given A and B, return the minimum number of swaps to make both sequences strictly increasing.  It is guaranteed that the given input always makes it possible.

    Example:
    Input: A = [1,3,5,4], B = [1,2,3,7]
    Output: 1
    Explanation:
    Swap A[3] and B[3].  Then the sequences are:
    A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
    which are both strictly increasing.
    Note:

    A, B are arrays with the same length, and that length will be in the range [1, 1000].
    A[i], B[i] are integer values in the range [0, 2000].
 */

//Solution1: DFS 超时
class Solution {
    int res = Integer.MAX_VALUE;
    public int minSwap(int[] A, int[] B) {
        helper(A, B, 1, 0);
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    private void helper(int[] A, int[] B, int index, int count) {
        if (count >= res)
            return;

        if (index == A.length) {
            res = Math.min(res, count);
            return;
        }

        if (A[index] > A[index - 1] && B[index] > B[index - 1]) {
            helper(A, B, index + 1, count);
        }

        if (A[index] > B[index - 1] && B[index] > A[index - 1]) {
            swap(A, B, index);
            helper(A, B, index + 1, count + 1);
            swap(A, B, index);
        }
    }

    private void swap(int[] A, int[] B, int i) {
        int temp = A[i];
        A[i] = B[i];
        B[i] = temp;
    }
}

//Solution2: DP
class Solution {
    public int minSwap(int[] A, int[] B) {
        int n = A.length;

        int[] keep = new int[n]; // keep[i]表示在遍历到第i个数时，不swap
        int[] swap = new int[n]; // swap[i]表示在遍历到第i个数时，swap
        Arrays.fill(keep, n);
        Arrays.fill(swap, n);

        keep[0] = 0;
        swap[0] = 1;

        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
                keep[i] = keep[i - 1]; // 已经符合条件了，不需要swap
                swap[i] = swap[i - 1] + 1; //
            }

            if (B[i] > A[i - 1] && A[i] > B[i - 1]) {
                keep[i] = Math.min(keep[i], swap[i - 1]);
                swap[i] = Math.min(swap[i], keep[i - 1] + 1);

            }
        }
        return Math.min(keep[n - 1], swap[n - 1]);
    }
}