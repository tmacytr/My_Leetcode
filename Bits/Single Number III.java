/*
    Single Number III
    Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.

    For example:

    Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].

    Note:
    The order of the result is not important. So in the above example, [5, 3] is also correct.
    Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?
*/

/*
    设最后两个只出现一次的数分别为 x1, x2. 那么遍历数组时根据两两异或的方法可得最后的结果为 x1 ^ x2, 如果我们要分别求得 x1 和 x2, 我们可以根据 x1 ^ x2 ^ x1 = x2 求得 x2,

    同理可得 x1. 那么问题来了，如何得到x1和x2呢？看起来似乎是个死循环。我们可以利用x1 ^ x2的结果对原数组进行了分组，进而将x1和x2分开了。

    具体方法则是利用了x1 ^ x2不为0的特性，如果x1 ^ x2不为0，那么x1 ^ x2的结果必然存在某一二进制位不为0（即为1），

    我们不妨将最低位的1提取出来，由于在这一二进制位上x1和x2必然相异，即x1, x2中相应位一个为0，另一个为1，所以我们可以利用这个最低位的1将x1和x2分开。

    又由于除了x1和x2之外其他数都是成对出现，故与最低位的1异或时一定会抵消
 */

public class Solution {
    public int[] singleNumber(int[] nums) {
        // Pass 1 : 
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get its last set bit
        diff &= -diff;//这样可以得到两个single number 不相同的最靠近右边的位数 

        // Pass 2 :
        //我们根据最右边这位数 去划分整个数组，一定会将两个single number分开
        int[] res = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums) {
            if ((num & diff) == 0) {// the bit is not set
                res[0] ^= num;
            }
            else {// the bit is set
                res[1] ^= num;
            }
        }
        return res;
    }
}