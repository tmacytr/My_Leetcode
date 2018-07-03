/*
    373. Find K Pairs with Smallest Sums

    You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.

    Define a pair (u,v) which consists of one element from the first array and one element from the second array.

    Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.

    Example 1:
    Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3

    Return: [1,2],[1,4],[1,6]

    The first 3 pairs are returned from the sequence:
    [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
    Example 2:
    Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2

    Return: [1,1],[1,1]

    The first 2 pairs are returned from the sequence:
    [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
    Example 3:
    Given nums1 = [1,2], nums2 = [3],  k = 3

    Return: [1,3],[2,3]

    All possible pairs are returned from the sequence:
    [1,3],[2,3]
 */

//Solution1: my solution, O(k*k*logk)
class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int len = Math.min(k, nums1.length * nums2.length);
        List<int[]> res = new LinkedList();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] + b[1] - a[0] - a[1]);

        for (int i = 0; i < Math.min(k, nums1.length); i++) {
            for (int j = 0; j < Math.min(k, nums2.length); j++) {
                pq.offer(new int[]{nums1[i], nums2[j]});
                if (pq.size() > len)
                    pq.poll();
            }
        }
        int index = 0;
        while (!pq.isEmpty()) {
            res.add(0, pq.poll());
        }
        return res;
    }
}

//Solution2: prefer, best, O(klogk)
/*
    算法思想： 1. 前k个最小的组合，一定是在nums1的前k + nums2的前k个数之间选， 所以我们先把nums1的前k个数和nums2的第一个数组合全部放到最小堆中。
             2. 最小堆所存的元组是(nums1Value, nums2Value, nums2Index), 这样我们每从最小堆中poll一个数的时候，相应的可以用该nums1Value 以及 nums2Index + 1 的value组成下一个元组放入heap中
             3. 如果发现nums2的index已经到最后，就应该continue
*/

class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        if (nums1.length == 0 || nums2.length == 0 || k == 0)
            return new ArrayList<>();

        List<int[]> res = new ArrayList();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] + a[1] - b[0] - b[1]);

        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            pq.offer(new int[]{nums1[i], nums2[0], 0});
        }

        while (k-- > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            res.add(new int[]{cur[0], cur[1]});

            if (cur[2] == nums2.length - 1)
                continue;
            int curNums1Val = cur[0];
            int nextNums2Index = cur[2] + 1;
            int nextNums2Val = nums2[nextNums2Index];

            pq.offer(new int[]{curNums1Val, nextNums2Val, nextNums2Index});
        }

        return res;
    }
}
