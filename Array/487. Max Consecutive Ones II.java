/*
    Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.

    Example 1:
    Input: [1,0,1,1,0]
    Output: 4
    Explanation: Flip the first zero will get the the maximum number of consecutive 1s.
        After flipping, the maximum number of consecutive 1s is 4.
    Note:

    The input array will only contain 0 and 1.
    The length of input array is a positive integer and will not exceed 10,000

    Follow up:
    What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?

    Companies: Google

    Related Topics: Two Pointers

    Similar Questions: Max Consecutive Ones
*/

//Solution1: unable to deal with follow up
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, zero = 0, k = 1; // flip at most k zero
        for (int start = 0, end = 0; end < nums.length; end++) {
            if (nums[end] == 0)
                zero++;
            while (zero > k)
                if (nums[start++] == 0)
                    zero--;
            max = Math.max(max, end - start + 1);
        }
        return max;
    }
}

//Solution2: follow-up use Queue to store up to k indexes of zero within the window [start, end].so that we know where to move (start) next when the window contains more than k zero.
// If the input stream is infinite, then the output could be extremely large because there could be super long consecutive ones.
// In that case we can use BigInteger for all indexes. For simplicity, here we will use int
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0, k = 1;
        Queue<Integer> zeroIndex = new ArrayDeque();
        for (int start = 0, end = 0; end < nums.length; end++) {
            if (nums[end] == 0)
                zeroIndex.offer(end);
            if (zeroIndex.size() > k)
                start = zeroIndex.poll() + 1;
            res = Math.max(res, end - start + 1);
        }
        return res;
    }
}