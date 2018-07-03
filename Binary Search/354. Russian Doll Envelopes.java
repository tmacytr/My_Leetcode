/*
    354. Russian Doll Envelopes

    You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

    What is the maximum number of envelopes can you Russian doll? (put one inside other)

    Example:
    Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).

    Companies: Google

    Related Topics: Binary Search, DP

    Similar Questions: Longest Increasing Subsequence
 */
//Solution1: binary search, O(NlogN), 要将数组按width升序排列按， width相等的height降序排列
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0)
            return 0;
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] dp = new int[envelopes.length];
        int len = 0;
        for (int[] envelope : envelopes) {
            int index = Arrays.binarySearch(dp, 0, len, envelope[1]);
            if (index < 0)
                index = -(index + 1);
            dp[index] = envelope[1];
            if (index == len)
                len++;
        }
        return len;
    }
}


consider its action on the following set of envelopes: [[4,5],[4,6],[6,7],[2,3],[1,1]]

Sort width ascending, then height descending for ties (accepted):
    [[1, 1], [2, 3], [4, 6], [4, 5], [6, 7]]
    Searching for height of 1
    Result of search: -1
    Inserting height at index: 0
    1 0 0 0 0
    l is now 1
    Searching for height of 3
    Result of search: -2
    Inserting height at index: 1
    1 3 0 0 0
    l is now 2
    Searching for height of 6
    Result of search: -3
    Inserting height at index: 2
    1 3 6 0 0
    l is now 3
    Searching for height of 5
    Result of search: -3
    Inserting height at index: 2
    1 3 5 0 0
    l is now 3
    Searching for height of 7
    Result of search: -4
    Inserting height at index: 3
    1 3 5 7 0
    l is now 4
    Max number of envelopes: 4

Sort width ascending, then height ascending for ties:
    [[1, 1], [2, 3], [4, 5], [4, 6], [6, 7]]
    Searching for height of 1
    Result of search: -1
    Inserting height at index: 0
    1 0 0 0 0
    l is now 1
    Searching for height of 3
    Result of search: -2
    Inserting height at index: 1
    1 3 0 0 0
    l is now 2
    Searching for height of 5
    Result of search: -3
    Inserting height at index: 2
    1 3 5 0 0
    l is now 3
    Searching for height of 6
    Result of search: -4
    Inserting height at index: 3
    1 3 5 6 0
    l is now 4
    Searching for height of 7
    Result of search: -5
    Inserting height at index: 4
    1 3 5 6 7
    l is now 5
    Max number of envelopes: 5