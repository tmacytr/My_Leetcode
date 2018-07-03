/*
	698. Partition to K Equal Sum Subsets

	Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

	Example 1:
	Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
	Output: True
	Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
	Note:

	1 <= k <= len(nums) <= 16.
	0 < nums[i] < 10000.
*/

/*
	要把数组分为K parts,并且每部分的sum相同，则数组之和sum 一定要能整除 k -> sum % k == 0
	所以我们可以找出一个target = sum / k, 我们的目标是找出k个subsets，每个subsets的和满足等于target

	为了排除无效数据，我们可以先将数组sort，从后面开始check
	1. 如果发现有单个元素大于target，则直接返回false
	2. 如果发现有单个元素等于target，那我们就只需要找剩下k - 1 组 subsets，因为这个数单独就可以成为一组
*/

/*
	Time Complexity: O(k ^(N-k) k!)
		Where N is the length of nums, and k is as given. As we skip additional zeroes in groups, 
		naively we will make O(k!) calls to search, then an additional O(k^{N-k}) calls after every element of groups is nonzero.
*/
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k > 0)
            return false;
        int target = sum / k;
        
        Arrays.sort(nums); // sort之后可以直接从后往前check 剪枝
        int beginIndex = nums.length - 1;
        if (nums[beginIndex] > target) // 剪枝1， 如果发现单个元素已经大于target，则肯定不可能
            return false;
        while (beginIndex >= 0 && nums[beginIndex] == target) { // 剪枝2， 如果发现有等于target的单独个元素，则我们需要找的组数减少
            beginIndex--;
            k--;
        }
        return search(new int[k], beginIndex, nums, target);
    }
    
    // groups[i] 的每一个index就是一组subset之和
    public boolean search(int[] groups, int beginIndex, int[] nums, int target) {
        if (beginIndex < 0) // beginIndex == 0时 我们还需要判断nums[0]是否可以加入
            return true;
        int num = nums[beginIndex--];
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] + num <= target) {
                groups[i] += num;
                if (search(groups, beginIndex, nums, target))
                    return true;
                groups[i] -= num;
            }
            if (groups[i] == 0) //剪枝3， 可以省略
                break;
        }
        return false;
    }
}