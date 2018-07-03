/*
    Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...

    Note:
    n is positive and will fit within the range of a 32-bit signed integer (n < 231).

    Example 1:

    Input:
    3

    Output:
    3
    Example 2:

    Input:
    11

    Output:
    0

    Explanation:
    The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
 */

class Solution {
    public int findNthDigit(int n) {
        int len = 1; //每个区间单个数字的长度， 比如1 ~ 9是1， 10 ~ 99是2， 100 ~ 999是3
        long count = 9; // 每个区间总共有多少数字， 1 ~ 9是9， 10 ~ 99是90， 100 ~ 999是900
        int start = 1; // 每个区间开头的第一个数， 1 ~ 9是1， 10 ~ 99是10， 100 ~ 999是100
        while (n > len * count) {
            n -= len * count;
            len += 1;
            count *= 10;
            start *= 10;
        }
        int target = start + (n - 1) / len; // target = 该区间起始数字 + 在这个区间的第几位
        int reminder = (n - 1) % len; // 找到了数字以后，所求nth是在这个数的第几位
        return Integer.valueOf(Integer.toString(target).charAt(reminder) - '0');
    }
}