/*
    668. Kth Smallest Number in Multiplication Table

    Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

    Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

    Example 1:
    Input: m = 3, n = 3, k = 5
    Output:
    Explanation:
    The Multiplication Table:
    1	2	3
    2	4	6
    3	6	9

    The 5-th smallest number is 3 (1, 2, 2, 3, 3).
    Example 2:
    Input: m = 2, n = 3, k = 6
    Output:
    Explanation:
    The Multiplication Table:
    1	2	3
    2	4	6

    The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
    Note:
    The m and n will be in the range [1, 30000].
    The k will be in the range [1, m * n]

    Companies: Google

    Related Topics: Binary Search

    Similar Questions : 1.Kth Smallest Element in a Sorted Matrix, 2.Find K-th Smallest Pair Distance, 3.K-th Smallest Prime Fraction
 */

//Binary Search
class Solution {
    public int findKthNumber(int m, int n, int k) {
        int start = 1, end = m * n;
        while (start < end) {
            int mid = start + (end - start) / 2;
            int c = count(mid, m, n);
            if (c >= k)
                end = mid;
            else
                start = mid + 1;
        }
        return end;
    }

    private int count(int value, int m, int n) {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            int temp = Math.min(value / i, n); //计算每一行比value小的数有多少个，因为每一个值都是 indexRow * indexCol
            count += temp;
        }
        return count;
    }
}