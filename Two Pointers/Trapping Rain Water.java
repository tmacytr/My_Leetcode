/*
	Trapping Rain Water
	Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.


	For example, 
	Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

	The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
    In this case, 6 units of rain water (blue section) are being trapped.
	Tags: Array, Stack,  TwoPointers
	Time Complexity : O(2n), space:O(n)
*/

// Solution1: Stack
/*
    遍历高度，如果此时栈为空，或者当前高度小于等于栈顶高度，则把当前高度的坐标压入栈，注意我们不直接把高度压入栈，而是把坐标压入栈，这样方便我们在后来算水平距离。

    当我们遇到比栈顶高度大的时候，就说明有可能会有坑存在，可以装雨水。

    此时我们栈里的height >= 1，但如果只有一个的话，那么不能形成坑，我们直接跳过。
    如果栈里的height >= 2的话，那么此时把栈顶元素取出来当作坑，新的栈顶元素就是左边界，当前高度是右边界，只要取二者较小的减去刚pop的坑的高度就是所能装装水的高度。长度就是右边界坐标减去左边界坐标再减1，二者相乘就是盛水量
 */
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack();
        int i = 0;
        int res = 0;
        while (i < height.length) {
            if (stack.isEmpty() || height[stack.peek()] >= height[i]) {
                stack.push(i++);
            } else {
                int top = stack.pop();
                if (stack.isEmpty())
                    continue;
                int topHeightBound = Math.min(height[i], height[stack.peek()]);
                int bottomHeightBound = height[top];
                int width = i - stack.peek() - 1;
                res += (topHeightBound - bottomHeightBound) * width;
            }
        }
        return res;
    }
}


// Solution2: double pointer, prefer
/*
    1. 两个指针从两端往中间扫， 在当前窗口中，如果哪一侧高度小，则从该侧开始扫描
    2. 如果在扫描的过程中发现有比它还小的，就可以把minHeight - curHeight 加入结果，为什么呢？因为另外一边的高度肯定大于左边开始的高度也就意味着肯定能装下缺的水
       所以这个装水量一定会满足
    3. 当左右窗口相遇结束
*/
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0)
            return 0;
        int left = 0;
        int right = height.length - 1;
        int res = 0;
        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            if (height[left] == minHeight) {
                while (left < right && minHeight > height[left + 1]) {
                    res += minHeight - height[left + 1];
                    left++;
                }
                left++;
            } else {
                while (left < right && minHeight > height[right - 1]) {
                    res += minHeight - height[right - 1];
                    right--;
                }
                right--;
            }
        }
        return res;
    }

    public int tra(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int res = 0;
        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            if (height[left] == minHeight) {
                while (left < right && minHeight > height[left + 1]) {
                    res += minHeight - height[left + 1];
                    left++;
                }
                left++;
            } else {
                while (left < right && minHeight > height[right - 1]) {
                    res += minHeight - heigh[right - 1];
                    right--;
                }
            }
        }
        return res;
    }
}