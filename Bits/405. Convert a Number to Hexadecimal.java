/*
    405. Convert a Number to Hexadecimal

    Given an integer, write an algorithm to convert it to hexadecimal. For negative integer, two’s complement method is used.

    Note:

    All letters in hexadecimal (a-f) must be in lowercase.
    The hexadecimal string must not contain extra leading 0s. If the number is zero, it is represented by a single zero character '0'; otherwise, the first character in the hexadecimal string will not be the zero character.
    The given number is guaranteed to fit within the range of a 32-bit signed integer.
    You must not use any method provided by the library which converts/formats the number to hex directly.
    Example 1:

    Input:
    26

    Output:
    "1a"
    Example 2:

    Input:
    -1

    Output:
    "ffffffff"
 */

/*
    按位与来获取。既然是得到十六进制，那么每次与上0xF（二进制就是1111）15
    得到一个值，然后数字向右移动4位，这里需要注意的是数字是有符号的，刚好可以利用Java提供的无符号移动>>>
 */
//Solution
class Solution {
    char[] map = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public String toHex(int num) {
        if (num == 0)
            return "0";
        String res = "";
        while (num != 0) {
            res = map[(num & 15)] + res;
            num >>>= 4;
        }
        return res;
    }
}