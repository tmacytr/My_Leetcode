/*
	Strobogrammatic Number II
		A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

	Find all strobogrammatic numbers that are of length = n.

	For example,
	Given n = 2, return ["11","69","88","96"].

	Hint:

	Try to use recursion and notice that it should recurse with n - 2 instead of n - 1.
	Tags: Math, Recursive
*/


// Solution1: Recursion
class Solution {
    private int N;
    public List<String> findStrobogrammatic(int n) {
        N = n;
        return helper(n);
    }

    public List<String> helper(int n) {
        if (n == 0) {
            return new ArrayList(Arrays.asList(""));
        }
        if (n == 1) {
            return new ArrayList(Arrays.asList("0", "1", "8"));
        }

        List<String> temp = helper(n - 2); //why n - 2? since every time we add two number, one from head,the other from end
        List<String> res = new ArrayList();

        for (int i = 0; i < temp.size(); i++) {
            String s = temp.get(i);
            if (n != N) { // n == N, 就是最外层的时候，不能在最外围加上0， 会导致出现010这样的无效数字
                res.add("0" + s + "0");
            }
            res.add("1" + s + "1");
            res.add("6" + s + "9");
            res.add("9" + s + "6");
            res.add("8" + s + "8");
        }
        return res;
    }
}

// Solution2: iterative
class Solution {
    public List<String> findStrobogrammatic(int n) {
        List<String> one = Arrays.asList("0", "1", "8");
        List<String> two = Arrays.asList("");
        List<String> res = n % 2 == 1 ? one : two; //初始化

        for (int i = (n % 2) + 2; i <= n; i += 2) {
            List<String> temp = new ArrayList();
            for (String s : res) {
                if (i != n) {
                    temp.add("0" + s + "0");
                }
                temp.add("1" + s + "1");
                temp.add("6" + s + "9");
                temp.add("9" + s + "6");
                temp.add("8" + s + "8");
            }
            res = temp;
        }
        return res;
    }
}