/*
    632. Smallest Range

    You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the k lists.

    We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

    Example 1:
    Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
    Output: [20,24]
    Explanation:
    List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
    List 2: [0, 9, 12, 20], 20 is in range [20,24].
    List 3: [5, 18, 22, 30], 22 is in range [20,24].
    Note:
    The given list may contain duplicates, so ascending order means >= here.
    1 <= k <= 3500
    -105 <= value of elements <= 105.
    For Java users, please note that the input type has been changed to List<List<Integer>>. And after you reset the code template, you'll see this point.

    Companies: Lyft

    Related Topics: HashTable, two pointers, string, heap

    Similar Questions: Minimum Window Substring
 */

//mage you are merging k sorted array using a heap. Then everytime you pop the smallest element out and add the next element of that array to the heap. By keep doing this, you will have the smallest range.
//Solution1: O(nlog(m))
class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        if (nums == null || nums.size() == 0)
            return new int[]{};
        PriorityQueue<int[]> minHeap = new PriorityQueue<int[]>(nums.size(), (a, b) -> a[0] - b[0]);

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            minHeap.offer(new int[]{nums.get(i).get(0), i, 0}); // pair[0] -> value,  pair[1] -> group index, pair[2] -> group里的index
            max = Math.max(max, nums.get(i).get(0));
        }

        int minRange = Integer.MAX_VALUE;
        int start = -1;
        while (minHeap.size() == nums.size()) { // 如果heap的size不等于nums， 说明已经有其中一组的num已经遍历完
            int[] pair = minHeap.poll();
            if (max - pair[0] < minRange) {
                minRange = max - pair[0];
                start = pair[0];
            }

            if (pair[2] + 1 < nums.get(pair[1]).size()) { // make sure doesn't overflow
                pair[0] = nums.get(pair[1]).get(pair[2] + 1); // get the value which as next pointer
                pair[2]++; // update the index
                minHeap.offer(pair);
                max = Math.max(max, pair[0]);
            }
        }
        return new int[]{start, start + minRange};
    }
}