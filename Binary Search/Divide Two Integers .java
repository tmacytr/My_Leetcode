/*
	Divide Two Integers
	Divide two integers without using multiplication, division and mod operator.
	If it is overflow, return MAX_INT.
	Tags: Math, Binary Search
*/
//Solution1 prefer
//dividend 被除数
//divisor 除数
public class Solution {
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
             return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        if (dividend == 0) {
            return 0;
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        boolean isNegative = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);
        
        long a = Math.abs((long)dividend);
        long b = Math.abs((long)divisor);
        
        int res = 0;
        
        while (a >= b) {
            int shift = 0;
            while (a >= (b << shift)) {
                shift++;
            }
            a -= b << (shift - 1);
            res += 1 << (shift - 1);
        }
        return isNegative ? -res : res;
    }
}


//Solution2
public class Solution {
	public int divide (int dividend, int divisor) {
		long a = Math.abs((long)dividend);
		long b = Math.abs((long)divisor);

		long res = 0;

		/*
			a,b : a / b 

			a - b * (base)^n
				
		*/
		/*			tmp	
                 {   |   } 
			21 - 3 * 2^0 = 18  <-- (a - tmp) //res = 2^0
			|    |    |
			a    b   base
		example1:
			18 - 3 * 2^1 = 12   // res =  2^0 + 2^1
			12 - 3 * 2^2 = 0;   // res =  2^0 + 2^1 + 2^2
			res = 2^0 + 2^1 + 2^2 = 7;
		example2:
			35 - 3 * 2^0 = 32;  // res = 2^0
			32 - 3 * 2^1 = 26;  // res = 2^0 + 2^1
			26 - 3 * 2^2 = 14;  // res = 2^0 + 2^1 + 2^2 =7
			14 - 3 * 2^3 < 0;  < 0 ;break for loop，进行下一个while loop，此时a = 14, b = 3

			14 - 3 * 2^0 = 11; //  res = 2^0 + 2^1 + 2^2 + 2^0
			11 - 3 * 2^1 = 5;  //  res = 2^0 + 2^1 + 2^2 + 2^0 +2^1 = 11 //break


			
		*/
		while (a >= b) {
			for (long tmp = b, base = 1; a >= tmp; tmp <<= 1, base <<= 1) {
				res = res + base;
				a = a - tmp;
			}
		}
		//dividend和divisor按位异或之后 右移31位则只剩下符号位，再将最高位的符号位与1进行与操作确定符号（1正,0负）。
		res = (((dividend ^ divisor) >> 31) & 1) == 1 ? -res: res;
		if ( res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) 
			return Integer.MAX_VALUE;
		return (int) res;
	}
}

public int divide(int dividend, int divisor) {
	//Reduce the problem to positive long integer to make it easier.
	//Use long to avoid integer overflow cases.
	int sign = 1;
	if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0))
		sign = -1;
	long ldividend = Math.abs((long) dividend);
	long ldivisor = Math.abs((long) divisor);
	
	//Take care the edge cases.
	if (ldivisor == 0) return Integer.MAX_VALUE;
	if ((ldividend == 0) || (ldividend < ldivisor))	return 0;
	
	long lans = ldivide(ldividend, ldivisor);
	
	int ans;
	if (lans > Integer.MAX_VALUE){ //Handle overflow.
		ans = (sign == 1)? Integer.MAX_VALUE : Integer.MIN_VALUE;
	} else {
		ans = (int) (sign * lans);
	}
	return ans;
}

private long ldivide(long ldividend, long ldivisor) {
	// Recursion exit condition
	if (ldividend < ldivisor) return 0;
	
	//  Find the largest multiple so that (divisor * multiple <= dividend), 
	//  whereas we are moving with stride 1, 2, 4, 8, 16...2^n for performance reason.
	//  Think this as a binary search.
	long sum = ldivisor;
	long multiple = 1;
	while ((sum+sum) <= ldividend) {
		sum += sum;
		multiple += multiple;
	}
	//Look for additional value for the multiple from the reminder (dividend - sum) recursively.
	return multiple + ldivide(ldividend - sum, ldivisor);
}

