/*
    503. Next Greater Element II

    Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.

    Example 1:
    Input: [1,2,1]
    Output: [2,-1,2]
    Explanation: The first 1's next greater number is 2;
    The number 2 can't find next greater number;
    The second 1's next greater number needs to search circularly, which is also 2.
 */

//Solution1: O(n), idea from I, so circle array just like give 2 * original len array.
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap();
        int len = nums.length;
        int[] res = new int[len];
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < nums.length * 2; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % len]) {
                int lastIndex = stack.pop();
                map.putIfAbsent(lastIndex, nums[i % len]);
            }
            if (!map.containsKey(i % len)) {
                stack.push(i % len);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            res[i] = map.getOrDefault(i, -1);
        }
        return res;
    }
}

//Solution2: O(n), prefer, same idea with Solution 1, but we can just check whether the res[] array is -1 then we can decide assign value and push cur index to stack or not
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < nums.length * 2; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % len]) {
                int lastIndex = stack.pop();
                res[lastIndex] = res[lastIndex] == -1 ? nums[i % len] : res[lastIndex];
            }
            if (res[i % len] == -1) {
                stack.push(i % len) ;
            }
        }

        return res;
    }
}