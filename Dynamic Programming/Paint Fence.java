/*
	Paint Fence
	There is a fence with n posts, each post can be painted with one of the k colors.

	You have to paint all the posts such that no more than two adjacent fence posts have the same color.

	Return the total number of ways you can paint the fence.

	Note:
	n and k are non-negative integers.
	Tags: Dynamic Programming
*/

// clarify: 0011 is a valid solution, 0001 is not valid
// Solution1: O(n) space
class Solution {
    public int numWays(int n, int k) {
        if (n == 0 || k == 0)
            return 0;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                dp[0] = k;
            } else if (i == 1) {
                dp[1] = k * k;
            } else {
                dp[i] = dp[i - 1] * (k - 1) + dp[i - 2] * (k - 1);
            }
        }
        return dp[n - 1];
    }
}

/*
	https://leetcode.com/discuss/56173/o-n-time-java-solution-o-1-space
	We divided it into two cases.

	1. the last two posts have the same color, the number of ways to paint in this case is sameColorCounts.

	2. the last two posts have different colors, and the number of ways in this case is diffColorCounts.

	The reason why we have these two cases is that we can easily compute both of them, and that is all I do.
	When adding a new post, we can use the same color as the last one (if allowed) or different color.
	If we use different color, there're k-1 options, and the outcomes shoule belong to the diffColorCounts category.
	If we use same color, there's only one option, and we can only do this when the last two have different colors (which is the diffColorCounts).
	There we have our induction step.

	Here is an example, let's say we have 3 posts and 3 colors.
		The first two posts we have 9 ways to do them, (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3).
		Now we know that diffColorCounts = 6, And sameColorCounts = 3;
    Now for the third post, we can compute these two variables like this:
	1) If we use different colors than the last one (the second one), these ways can be added into diffColorCounts,
	   so if the last one is 3, we can use 1 or 2, if it's 1, we can use 2 or 3, etc.
	   Apparently there are (diffColorCounts + sameColorCounts) * (k-1) possible ways.

	2) If we use the same color as the last one, we would trigger a violation in these three cases (1,1,1), (2,2,2) and (3,3,3).
	   This is because they already used the same color for the last two posts.
	   So is there a count that rules out these kind of cases? YES, the diffColorCounts.
	   So in cases within diffColorCounts, we can use the same color as the last one without worrying about triggering the violation.
	   And now as we append a same-color post to them, the former diffColorCounts becomes the current sameColorCounts.

	Then we can keep going until we reach the n. And finally just sum up these two variables as result.
*/
// Solution2: O(1) space
public class Solution {
    public int numWays(int n, int k) {
        if (n == 0){
            return 0;
        } else if (n == 1) {
            return k;
        }
        
        int diffColorCounts = k * (k - 1);// 前面有K种可能的颜色，后面就只能放除了前面的颜色的颜色，就是K - 1种可能, k * (k - 1)
        int sameColorCounts = k; //前面有K种颜色，但是必须和前面的相同因此只有1种可选 k * 1
        
        for (int i = 2; i < n; i++) {
            int temp = diffColorCounts;
            diffColorCounts = (diffColorCounts + sameColorCounts) * (k - 1);// k * (k - 1) * (k - 1) + k * 1 * (k - 1) 
            sameColorCount = temp;//如何追朔连续俩相同， 只要将diffColor赋给sameColor，就意味着肯定不会出现连续3个重复的情况，因为之前的2种颜色都是diff
        }
        return diffColorCounts + sameColorCounts;
    }
}