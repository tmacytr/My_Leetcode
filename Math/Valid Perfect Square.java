/*
	367. Valid Perfect Square

	Given a positive integer num, write a function which returns True if num is a perfect square else False.

	Note: Do not use any built-in library function such as sqrt.

	Example 1:

	Input: 16
	Returns: True
	Example 2:

	Input: 14
	Returns: False
*/

// Solution1: Binary Search, O(logN)
class Solution {
    public boolean isPerfectSquare(int num) {
        long start = 1;
        long end = num;
        while (start + 1 < end) {
            long mid = start + (end - start) / 2;
            if (mid * mid < num)
                start = mid;
            else 
                end = mid;
        }
        return end * end == num; // why just checkt end? since if mid * mid >= num, end = mid
    }
}


// Solution2: Math O(sqrt(n))
/*
	数学解法了，利用到了这样一条性质，完全平方数是一系列奇数之和，例如：

	1 = 1
	4 = 1 + 3
	9 = 1 + 3 + 5
	16 = 1 + 3 + 5 + 7
	25 = 1 + 3 + 5 + 7 + 9
	36 = 1 + 3 + 5 + 7 + 9 + 11
	....
	1+3+...+(2n-1) = (2n-1 + 1)n/2 = n*n
*/
class Solution {
    public boolean isPerfectSquare(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i += 2;
        }
        return num == 0;
    }
}