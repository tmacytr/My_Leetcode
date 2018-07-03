/*
    556. Next Greater Element III

    Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive 32-bit integer exists, you need to return -1.

    Example 1:

    Input: 12
    Output: 21


    Example 2:

    Input: 21
    Output: -1

    Companies

    Related Topics: String

    Similar Questions:
        1.Next Greater Element I
        2.Next Greater Element II
 */

/*
    解体思路：
        1. 从后往前遍历，找到第一个nums[i] < nums[i + 1]的数
        2. 再从后往前遍历找到第一个大于nums[i]的数nums[j], swap(i, j)， 并排序i之后的数，即为最小的greater数
        3. 判断是否溢出，最大只能为2^32 = Integer.MAX_VALUE, 否则返回-1
 */

//Solution1:
class Solution {
    public int nextGreaterElement(int n) {
        String numString = String.valueOf(n);
        int len = numString.length();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = numString.charAt(i) - '0';
        }
        int pos = -1;

        // 从后往前遍历，找到第一个nums[i] < nums[i + 1]的数
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                pos = i;
                break;
            }
        }

        if (pos == -1)
            return -1;

        // 再从后往前遍历找到第一个大于nums[i]的数nums[j]并 swap
        for (int i = len - 1; i > pos; i--) {
            if (nums[i] > nums[pos]) {
                int temp = nums[pos];
                nums[pos] = nums[i];
                nums[i] = temp;
                break;
            }
        }
        Arrays.sort(nums, pos + 1, nums.length);
        StringBuilder sb = new StringBuilder();
        for (int i : nums)
            sb.append(i);
        long res = Long.valueOf(sb.toString());
        return res > Integer.MAX_VALUE ? -1 : (int)res;
    }
}

//Solution2: prefer, concise 把n转化为character 数组可以省下很多类型转换的步骤
class Solution {
    public int nextGreaterElement(int n) {
        char[] nums = String.valueOf(n).toCharArray();
        int len = nums.length;
        int pos = -1;

        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                pos = i;
                break;
            }
        }

        if (pos == -1)
            return -1;

        for (int i = len - 1; i > pos; i--) {
            if (nums[i] > nums[pos]) {
                char temp = nums[pos];
                nums[pos] = nums[i];
                nums[i] = temp;
                break;
            }
        }
        Arrays.sort(nums, pos + 1, nums.length);
        String resString = new String(nums);
        long res = Long.valueOf(resString);
        return res > Integer.MAX_VALUE ? -1 : (int)res;
    }
}