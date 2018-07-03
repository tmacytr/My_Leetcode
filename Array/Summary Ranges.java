/*
	Summary Ranges 
	Given a sorted integer array without duplicates, return the summary of its ranges.

	For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
*/

//Solution1: Concise best
public class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            while (i + 1 < nums.length && nums[i + 1] - nums[i] == 1) {
                i++;
            }
            if (val != nums[i]) {
                res.add(val + "->" + nums[i]);
            } else {
                res.add(val + "");
            }
        }
        return res;
    }
}

//Solution2: My solution
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList();
        if (nums == null || nums.length == 0)
            return res;
        int right = 0;
        while (right < nums.length) {
            int leftValue = nums[right];
            while (right < nums.length - 1 && nums[right] == nums[right + 1] - 1) {
                right++;
            }
            if (leftValue == nums[right]) {
                res.add(nums[right++] + "");
            } else {
                res.add(leftValue + "->" + nums[right++]);
            }

        }
        return res;
    }
}

// Solution3: follow up with duplicates
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList();
        if (nums == null || nums.length == 0)
            return res;
        int right = 0;
        while (right < nums.length) {
            int leftValue = nums[right];
            while (right < nums.length - 1 && (nums[right] == nums[right + 1] - 1 || nums[right] == nums[right + 1])) { // only different
                right++;
            }
            if (leftValue == nums[right]) {
                res.add(nums[right++] + "");
            } else {
                res.add(leftValue + "->" + nums[right++]);
            }

        }
        return res;
    }
}