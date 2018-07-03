/*
    624. Maximum Distance in Arrays

    Given m arrays, and each array is sorted in ascending order. Now you can pick up two integers from two different arrays (each array picks one) and calculate the distance. We define the distance between two integers a and b to be their absolute difference |a-b|. Your task is to find the maximum distance.

    Example 1:
    Input:
    [[1,2,3],
     [4,5],
     [1,2,3]]
    Output: 4
    Explanation:
    One way to reach the maximum distance 4 is to pick 1 in the first or third array and pick 5 in the second array.
    Note:
    Each given array will have at least 1 number. There will be at least two non-empty arrays.
    The total number of the integers in all the m arrays will be in the range of [2, 10000].
    The integers in the m arrays will be in the range of [-10000, 10000].
 */

//Solution: 用两个变量min和max分别表示当前遍历过的数组中最小的首元素，和最大的尾元素，那么每当我们遍历到一个新的数组时，只需计算新数组尾元素和min绝对差，跟max和新数组首元素的绝对差，取二者之间的较大值来更新结果res
class Solution {
    public int maxDistance(List<List<Integer>> arrays) {
        if (arrays == null || arrays.size() == 0)
            return 0;
        int min = arrays.get(0).get(0);
        int max = arrays.get(0).get(arrays.get(0).size() - 1);
        int res = 0;
        for (int i = 1; i < arrays.size(); i++) {
            res = Math.max(res, Math.max(Math.abs(arrays.get(i).get(0) - max), Math.abs(arrays.get(i).get(arrays.get(i).size() - 1) - min)));
            min = Math.min(min, arrays.get(i).get(0));
            max = Math.max(max, arrays.get(i).get(arrays.get(i).size() - 1));
        }
        return res;
    }
}