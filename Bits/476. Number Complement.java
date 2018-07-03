/*
    476. Number Complement

    Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.

    Note:
    The given integer is guaranteed to fit within the range of a 32-bit signed integer.
    You could assume no leading zero bit in the integer’s binary representation.
    Example 1:
    Input: 5
    Output: 2
    Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need to output 2.
    Example 2:
    Input: 1
    Output: 0
    Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is 0. So you need to output 0.
 */

/*
    as long as num and mask has bit 1 at the same place (i.e. overlap), shift mask left by one bit

    For example

    num          = 00000101
    mask         = 11111111
                        ^ ^ overlap!
    num          = 00000101
    mask         = 11111110
                        ^   overlap!
    num          = 00000101
    mask         = 11111100
                        ^   overlap!
    num          = 00000101
    mask         = 11111000
                              clear!


    For example,

    num          = 00000101
    mask         = 11111000
    ~mask & ~num = 00000010
 */
class Solution {
    public int findComplement(int num) {
        int mask = ~0;
        while ((mask & num) > 0)
            mask <<= 1;
        return ~mask & ~num;
    }
}