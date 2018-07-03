/*
	Candy
	There are N children standing in a line. Each child is assigned a rating value.

	You are giving candies to these children subjected to the following requirements:

	Each child must have at least one candy.
	Children with a higher rating get more candies than their neighbors.
	What is the minimum candies you must give?

	Tags:Greedy , DP
*/
/*
	这道题和Trapping water那个是一样的想法，因为无论是水坑还是得到糖的小朋友，影响因素都不只一边，都是左右两边的最小值/最大值来决定的。

 	所以这道题跟上一道一样，也是左右两边遍历数组。

	leftnums数组存从左边遍历，当前小朋友对比其左边小朋友，他能拿到糖的数量；

	rightnums数组存从右边遍历，当前小朋友对比其右边小朋友，他能拿到的糖的数量。

	最后针对这两个数组，每个小朋友能拿到的糖的数量就是这两个数最大的那个数，求总加和就好了。
*/

class Solution {
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0)
            return 0;
        int m = ratings.length;
        int[] leftNum = new int[m];
        int[] rightNum = new int[m];
        leftNum[0] = 1;
        for (int i = 1; i < m; i++) {
            if (ratings[i] > ratings[i - 1]) {
                leftNum[i] = leftNum[i - 1] + 1;
            } else {
                leftNum[i] = 1;
            }
        }

        rightNum[m - 1] = 1;

        for (int i = m - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rightNum[i] = rightNum[i + 1] + 1;
            } else {
                rightNum[i] = 1;
            }
        }
        int res = 0;
        for (int i = 0; i < m; i++) {
            res += Math.max(leftNum[i], rightNum[i]);
        }
        return res;
    }
}

class Solution {
    //Solution2
    public int candy(int[] ratings) {
        int candy[] = new int[ratings.length];
        int sum = 0;
        Arrays.fill(candy, 1);
        
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candy[i] = candy[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                if (i - 1 >= 0 && ratings[i - 1] <= ratings[i]) {
                    candy[i] = Math.max(candy[i + 1] + 1, candy[i]);
                } else {
                    candy[i] = candy[i + 1] + 1;
                }
            }
        }
        for (int i = 0; i < candy.length; i++) {
            sum += candy[i];
        }
        return sum;
    }
}