/*
	Maximum Gap
		Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
	Try to solve it in linear time/space.
	Return 0 if the array contains less than 2 elements.
	You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
*/


//Solution1: o(nlogn) sort
class Solution {
    public int maximumGap(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            res = Math.max(nums[i + 1] - nums[i], res);
        }
        return res;
    }
}

/*
    首先找出数组的最大值和最小值，然后要确定每个桶的容量，即为(最大值 - 最小值) / 个数 + 1，
    再确定桶的个数，即为(最大值 - 最小值) / 桶的容量 + 1，然后需要在每个桶中找出局部最大值和最小值，
    因为同一个桶内的元素之间的差值至多为len - 1，因此最终答案不会从同一个桶中选择，而是一个桶的最小值和另一个桶的最大值之间的间距,

    桶排序:
	1. 找到数组的min 和max 值，因为在一个有n个元素的数组，最小值为min，最大值为max，则两数最大的差值不会小于 ceiling[(max - min) / (n - 1)]
	2. 根据桶排序的原理，我们令bucket桶的大小 size = ceiling[(max - min) / (n - 1)],
	3. 对于数组中的任意数k， 可以通过 (k - min)/ size, 求出其属于的桶index，然后维护每个桶的最大值和最小值
	4. 则max 的差值 一定是前面桶的max 和相邻的后面的非空桶的最小值的差 , max = post.min - pre.max;
*/
//Solution2: O(n) time and space
class Solution {
    public int maximumGap(int[] nums) {
        if (nums.length < 2)
            return 0;
        int N = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        //get the max and min value of the array
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        //the minimum possible gap, ceiling of the integer division
        int bucketSize = (max - min - 1) / (N - 1) + 1;
        //int bucketSize = (int)Math.ceil((double)(max - min) / (N - 1));

        int[] bucketsMin = new int[N - 1];
        int[] bucketsMax = new int[N - 1];

        Arrays.fill(bucketsMin, Integer.MAX_VALUE);
        Arrays.fill(bucketsMax, Integer.MIN_VALUE);
        //put numbers into buckets
        for (int num : nums) {
            if (num == min || num == max)
                continue;
            int index = (num - min) / bucketSize; //index of the right position in the buckets
            bucketsMin[index] = Math.min(num, bucketsMin[index]);
            bucketsMax[index] = Math.max(num, bucketsMax[index]);
        }
        //Scan the buckets for the max gap
        int res = Integer.MIN_VALUE;
        int pre = min;
        for (int i = 0; i < N - 1; i++) {
            //empty bucket
            if (bucketsMin[i] == Integer.MAX_VALUE && bucketsMax[i] == Integer.MIN_VALUE)
                continue;
            // min value minus the previous value is the current gap
            res = Math.max(res, bucketsMin[i] - pre);
            // update previous bucket value
            pre = bucketsMax[i];
        }
        res = Math.max(res, max - pre);//Update the final max value gap
        return res;
    }
}
