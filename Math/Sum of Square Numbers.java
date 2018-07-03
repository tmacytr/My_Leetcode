/*
	633. Sum of Square Numbers

	Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.

	Example 1:
	Input: 5
	Output: True
	Explanation: 1 * 1 + 2 * 2 = 5
	Example 2:
	Input: 3
	Output: False
*/


// Solution1: Math sqrt, Time complexity: O(√clogc)
// We iterate over √c for values for choosing a. For every a chosen, finding square root of c - a^2 takes O(log(c)) time in the worst case

class Solution {
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            double b = Math.sqrt(c - a * a);
            if (b == (int)b)
                return true;
        }
        return false;
    }
}

// Solution2: double pointer
class Solution {
    public boolean judgeSquareSum(int c) {
        int start = 0;
        int end = (int)Math.sqrt(c);
        
        while (start <= end) {
            int res = start * start + end * end;
            if (res == c) 
                return true;
            if (res > c) {
                end--;
            } else {
                start++;
            }
        }
        return false;
    }
}