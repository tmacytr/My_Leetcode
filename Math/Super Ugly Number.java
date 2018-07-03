/*
	Super Ugly Number
	Write a program to find the nth super ugly number.

	Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. 
	For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.

	Note:
	(1) 1 is a super ugly number for any given primes.
	(2) The given numbers in primes are in ascending order.
	(3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.

	Companies: Google
	Related Topics: Math, Heap
	Similar: Ugly Number II
*/

//Solution: 用一个index数组来保存当前的位置，然后我们从每个子链中取出一个数，找出其中最小值，然后更新index数组对应位置，注意有可能最小值不止一个，要更新所有最小值的位置
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        if (n == 1)
            return 1;
        int[] ugly = new int[n];
        int[] index = new int[primes.length];
        ugly[0] = 1;

        for (int i = 1; i < n; i++) {
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++) {
                ugly[i] = Math.min(ugly[i], primes[j] * ugly[index[j]]);
            }

            for (int j = 0; j < primes.length; j++) {
                if (ugly[i] == primes[j] * ugly[index[j]]) {
                    index[j]++;
                }
            }
        }
        return ugly[n - 1];
    }
}