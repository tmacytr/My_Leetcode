/*
    728. Self Dividing Numbers

    A self-dividing number is a number that is divisible by every digit it contains.

    For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.

    Also, a self-dividing number is not allowed to contain the digit zero.

    Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds if possible.

    Example 1:
    Input:
    left = 1, right = 22
    Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
    Note:

    The boundaries of each input argument are 1 <= left <= right <= 10000.
 */

//Solution1: Brute force, math solution
class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new ArrayList();
        for (int i = left; i <= right; i++) {
            if (isValid(i))
                res.add(i);
        }
        return res;
    }

    private boolean isValid(int num) {
        if (num == 0)
            return false;
        int digit = num;
        while (digit > 0) {
            if (digit % 10 == 0 || (num % (digit % 10) != 0))
                return false;
            digit = digit / 10;
        }
        return true;
    }
}

//Solution2: Brute force, String solution
class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> ans = new ArrayList();
        for (int n = left; n <= right; ++n) {
            if (selfDividing(n)) ans.add(n);
        }
        return ans;
    }

    public boolean selfDividing(int n) {
        for (char c : String.valueOf(n).toCharArray()) {
            if (c == '0' || (n % (c - '0') > 0))
                return false;
        }
        return true;
    }
}