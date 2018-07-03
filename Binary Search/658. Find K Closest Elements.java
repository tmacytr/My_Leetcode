/*
    658. Find K Closest Elements

    Given a sorted array, two integers k and x, find the k closest elements to x in the array. The result should also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.

    Example 1:
    Input: [1,2,3,4,5], k=4, x=3
    Output: [1,2,3,4]
    Example 2:
    Input: [1,2,3,4,5], k=4, x=-1
    Output: [1,2,3,4]
    Note:
    The value k is positive and will always be smaller than the length of the sorted array.
    Length of the given array is positive and will not exceed 104
    Absolute value of elements in the array and x will not exceed 104

    UPDATE (2017/9/19):
    The arr parameter had been changed to an array of integers (instead of a list of integers). Please reload the code definition to get the latest changes.

    Companies: Google
    Related Topics: Binary Search
    Similar Questions:
        1. Guess Number Higher or Lower
        2. Guess Number Higher or Lower II
        3. Find K-th Smallest Pair Distance
 */

//Solution1: Time: O(nlogn)
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new LinkedList();
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap();
        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(Math.abs(arr[i] - x), new ArrayList());
            map.get(Math.abs(arr[i] - x)).add(i);
        }

        for (int key : map.keySet()) {
            for (int pos : map.get(key)) {
                res.add(arr[pos]);
                if (res.size() == k) {
                    Collections.sort(res);
                    return res;
                }
            }
        }
        return res;
    }
}

// 每次比较的是mid位置和x的距离跟mid + k跟x的距离，以这两者的大小关系来确定二分法折半的方向，最后找到最近距离子数组的起始位置
//Solution2: Time: O(log n)
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int start = 0;
        int end = arr.length - k;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (Math.abs(arr[mid] - x) > Math.abs(arr[mid + k] - x)) // 因为数组是递增序列，如果 abs(arr[mid] - x) > abs(arr[mid + k] - x), 意味着arr[mid + 1] ~ arr[mid + k] 更接近x，所以我们移动start到mid + 1，
//                if (x - arr[mid] > arr[mid + k] - x)
                start = mid + 1;
            else
                end = mid;
        }

        List<Integer> res = new ArrayList();
        for (int i = 0; i < k; i++) {
            res.add(arr[start + i]);
        }

        return res;
    }
}