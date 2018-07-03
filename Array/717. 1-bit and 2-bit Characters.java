/*
    717. 1-bit and 2-bit Characters

    We have two special characters. The first character can be represented by one bit 0. The second character can be represented by two bits (10 or 11).

    Now given a string represented by several bits. Return whether the last character must be a one-bit character or not. The given string will always end with a zero.

    Example 1:
    Input:
    bits = [1, 0, 0]
    Output: True
    Explanation:
    The only way to decode it is two-bit character and one-bit character. So the last character is one-bit character.
    Example 2:
    Input:
    bits = [1, 1, 1, 0]
    Output: False
    Explanation:
    The only way to decode it is two-bit character and two-bit character. So the last character is NOT one-bit character.
    Note:

    1 <= len(bits) <= 1000.
    bits[i] is always 0 or 1.

    Companies: Quora

    Related Topics: Array

    Similar Questions: Gray Code
 */


/*
    这道题说有两种特殊的字符，一种是两位字符，只能是二进制的11和10，另一种是单个位字符，只能是二进制的0。
    现在给了我们一个只包含0和1的数组，问我们能否将其正确的分割，使得最后一个字符是个单个位字符。

    注意题目给的input数组，最后一位一定是0
 */

/*
    因为两种字符互不干扰，只要我们遍历到了数字1，那么其必定是两位字符，所以后面一位也得跟着，而遍历到了数字0，那么就必定是单个位字符。

    所以我们可以用一个变量i来记录当前遍历到的位置，如果遇到了0，那么i自增1，如果遇到了1，那么i自增2，我们循环的条件是i < n-1，即留出最后一位，

    所以当循环退出后，当i正好停留在n-1上，说明最后一位是单独分割开的，因为题目中限定了最后一位一定是0，所以没必要再判断了
 */

//Solution1: Greedy1
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length, i = 0;
        while (i < n - 1) {
            if (bits[i] == 0)
                i++;
            else
                i += 2;
        }
        return i == n - 1;
    }
}

//Solution2: Greedy2
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length, i = 0;
        while (i < n - 1) {
            i += bits[i] + 1;
        }
        return i == n - 1;
    }
}