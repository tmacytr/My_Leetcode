/*
    594. Longest Harmonious Subsequence

    We define a harmonious array is an array where the difference between its maximum value and its minimum value is exactly 1.

    Now, given an integer array, you need to find the length of its longest harmonious subsequence among all its possible subsequences.

    Example 1:
    Input: [1,3,2,2,5,2,3,7]
    Output: 5
    Explanation: The longest harmonious subsequence is [3,2,2,2,3].
    Note: The length of the input array will not exceed 20,000.

    Companies: LiveRamp

    Related Topics: Hash Table
 */

//Solution1: TreeMap, O(n)
class Solution {
    public int findLHS(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Integer lastKey = null;
        int res = 0;
        for (Integer curKey : map.keySet()) {
            if (lastKey != null && lastKey + 1 == curKey) {
                res = Math.max(res, map.get(lastKey) + map.get(curKey));
            }
            lastKey = curKey;
        }
        return res;
    }
}

//Solution2: just count the number and compare the neighbor num + num, O(n)
class Solution {
    public int findLHS(int[] nums) {
        Map<Integer, Integer> map = new HashMap();
        int res = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.containsKey(num - 1)) {
                res = Math.max(res, map.get(num) + map.get(num - 1));
            }
            if (map.containsKey(num + 1)) {
                res = Math.max(res, map.get(num) + map.get(num + 1));
            }
        }
        return res;
    }
}

//Solution3: sorting and compare neighbors, O(nlogn)
class Solution {
    public int findLHS(int[] nums) {
        Arrays.sort(nums);
        int preCount = 1, res = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 1;
            if (i > 0 && nums[i] - nums[i - 1] == 1) {
                while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                    count++;
                    i++;
                }
                res = Math.max(res, count + preCount);
            } else {
                while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                    count++;
                    i++;
                }
            }
            preCount = count;
        }
        return res;
    }
}

//Solution3: sorting and compare neighbors concise version, O(nlogn)
class Solution {
    public int findLHS(int[] nums) {
        Arrays.sort(nums);
        int preCount = 1, res = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 1;
            boolean isHarmonious = i > 0 && nums[i] - nums[i - 1] == 1;
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                count++;
                i++;
            }
            if (isHarmonious)
                res = Math.max(res, count + preCount);
            preCount = count;
        }
        return res;
    }
}