/*
	Implement pow(x, n)
	Tag: Binary Search, Math
	Hints:we notice that x^n = x^(n/2) * x^(n/2) * x^(n % 2) 
*/

//Solution1: recursive, prefer
class Solution {
    public double myPow(double x, int n) {
        return n < 0 ? 1 / pow(x, -n) : pow(x, n);
    }

    private double pow(double x, int n) {
        if (n == 0)
            return 1;
        double res = pow(x, n / 2);
        if (n % 2 == 0)
            return res * res;
        return res * res * x;
    }

}

//Solution2: Iterative logn
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double res = 1;
        double currentProduct = x;

        for (long i = N; i > 0; i /= 2) {
            if (i % 2 == 1) {
                res = res * currentProduct;
            }
            currentProduct = currentProduct * currentProduct;
        }
        return res;
    }

}