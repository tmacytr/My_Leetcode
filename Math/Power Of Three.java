/*
	Power Of Three
	Given an integer, write a function to determine if it is a power of three.

	Follow up:
	Could you do it without using any loop / recursion?
 */
//问一个数是不是三的幂
//Iterative
class Solution {
    public boolean isPowerOfThree(int n) {
        while (n > 1 && n % 3 == 0) {
            n = n / 3;
        }
        return n == 1;
    }
}
//Recursive 
public boolean isPowerOfThree(int n) {
	return n > 0 && (n == 1 || (n % 3 == 0 && isPowerOfThree(n/3)));
}
//Math,Best
//换底公式为logab = logcb / logca，那么如果n是3的倍数，则log3n一定是整数，我们利用换底公式可以写为log3n = log10n / log103，现在问题就变成了判断log10n / log103是否为整数
public boolean isPowerOfThree(int n) {
    return (Math.log10(n) / Math.log10(3)) % 1 == 0;
}