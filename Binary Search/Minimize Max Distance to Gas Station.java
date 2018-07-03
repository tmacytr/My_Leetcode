/*
    774. Minimize Max Distance to Gas Station

    On a horizontal number line, we have gas stations at positions stations[0], stations[1], ..., stations[N-1], where N = stations.length.

    Now, we add K more gas stations so that D, the maximum distance between adjacent gas stations, is minimized.

    Return the smallest possible value of D.

    Example:

    Input: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], K = 9
    Output: 0.500000
    Note:

    stations.length will be an integer in range [10, 2000].
    stations[i] will be an integer in range [0, 10^8].
    K will be an integer in range [1, 10^6].
    Answers within 10^-6 of the true value will be accepted as correct.
 */

/*
    思路：
        首先求出每个station之间的距离，考虑如下问题：两个station为[1, 9]，gap为8。要插入一个station使得最大的最小，显然插入后应该为[1, 5, 9]，最大间隔为4。
        举个反例，如果插入后为[1, 6, 9], [1, 3, 9]，它们的最大间隔分别为5， 6，明显不是最小。从这里可以看出，对于插入k个station使得最大的最小的唯一办法是均分。
        一种贪心的做法是，找到最大的gap，插入1个station，依此类推，但很遗憾，这种贪心策略是错误的。问题的难点在于我们无法确定到底哪两个station之间需要插入station，插入几个station也无法得知。

        我们容易得知，minmaxGap的最小值left = 0，最大值right = stations[n - 1] - stations[0]。
        我们每次取mid为left和right的均值，然后计算如果mimaxGap为mid，那么最少需要添加多少个新的stations，记为count。
        所以如果count > K，说明均值mid选取的过小，使得我们必须新加更多的stations才能满足要求，所以我们就更新left的值；
        否则说明均值mid选取的过大，使得我们需要小于K个新的stations就可以达到要求，那么我们此时就可以寻找更小的mid，使得count增加到K。
        如果我们假设stations[N - 1] - stations[0] = m，那么这种实现的空间复杂度是O(1)，时间复杂度是O(nlogm)，可以发现与k无关了。
 */

// Solution: Binary Search
// Time Complexity: O(nlogW). n = stations.length. W = 10^14. 從10^8範圍猜到10^-6範圍.
class Solution {
    public double minmaxGasDist(int[] stations, int K) {
        double left = 0;
        double right = 1e8;
        while (right - left > 1e-6) {
            double mid = left + (right - left) / 2.0;
            if (possible(stations, K, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left;
    }

    private boolean possible(int[] stations, int K, double distance) {
        int count = 0;
        for (int i = 0; i < stations.length - 1; i++) {
            count += (int)((stations[i + 1] - stations[i]) / distance);
        }
        return count <= K;
    }
}