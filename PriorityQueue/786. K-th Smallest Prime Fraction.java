/*
    786. K-th Smallest Prime Fraction

    A sorted list A contains 1, plus some number of primes.  Then, for every p < q in the list, we consider the fraction p/q.

    What is the K-th smallest fraction considered?  Return your answer as an array of ints, where answer[0] = p and answer[1] = q.

    Examples:
    Input: A = [1, 2, 3, 5], K = 3
    Output: [2, 5]
    Explanation:
    The fractions to be considered in sorted order are:
    1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
    The third fraction is 2/5.

    Input: A = [1, 7], K = 1
    Output: [1, 7]
    Note:

    A will have length between 2 and 2000.
    Each A[i] will be between 1 and 30000.
    K will be between 1 and A.length * (A.length - 1) / 2.

    Companies: Pony.ai

    Related Topics: Binary Search, Heap

    Similar Questions
        1. Kth Smallest Element in a Sorted Matrix
        2. Kth Smallest Number in Multiplication TableF
        3. ind K-th Smallest Pair Distance

        373. Find K Pairs with Smallest Sums
        378. Kth Smallest Element in a Sorted Matrix
        668. Kth Smallest Number in Multiplication Table
        719. Find K-th Smallest Pair Distance
        786. K-th Smallest Prime Fraction
 */

/*
    https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115819/Summary-of-solutions-for-problems-%22reducible%22-to-LeetCode-378

    Assume the rows are sorted in ascending order, which implies initially the smallest element of the matrix must be within the first column,

    therefore the pool can be initialized with elements from the first column. Now as we are extracting and removing smallest elements from the pool,

    we need to supplement more candiate elements. The key observation here is that for each extracted element,

    it suffices to add to the pool only the element immediately following it in the same row, without violating the property that the pool always contains the smallest element of the matrix (even after removing).

    The following is a solution based on this idea:
 */


//Solution1: Time Complexity: O(KlogN), where N is the length of A. The heap has up to N elements, which uses O(logN) work to perform a pop operation on the heap.
//           We perform O(K) such operations.
class Solution {
    public int[] kthSmallestPrimeFraction(int[] A, int K) {
        // 因为这里有a[0] / a[1] < b[0] / b[1] => a[0] * b[1] < a[1] * b[0]
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> A[a[0]] * A[b[1]] -  A[a[1]] * A[b[0]]);
        //Min heap
        for (int i = 1; i < A.length; i++)
            pq.add(new int[]{0, i}); // initialize the pool with elements from the first column
        while (--K > 0) { // remove the smallest elements from the matrix (k-1) times
            int[] fraction = pq.poll();
            if (++fraction[0] < fraction[1])
                pq.offer(fraction); // add the next element in the same row if it exists
        }
        int[] res = pq.poll();
        return new int[]{A[res[0]], A[res[1]]};
    }
}