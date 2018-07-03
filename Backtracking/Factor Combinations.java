/*
	Factor Combinations

	Numbers can be regarded as product of its factors. For example,

	8 = 2 x 2 x 2;
	  = 2 x 4.
	Write a function that takes an integer n and return all possible combinations of its factors.

	Note: 
	Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
	You may assume that n is always positive.
	Factors should be greater than 1 and less than n.
	
	Examples: 

	input: 1
	output: 
	[]

	input: 37
	output: 
	[]

	input: 12
	output:
	[
	  [2, 6],
	  [2, 2, 3],
	  [3, 4]
	]

	input: 32
	output:
	[
	  [2, 16],
	  [2, 2, 8],
	  [2, 2, 2, 4],
	  [2, 2, 2, 2, 2],
	  [2, 4, 4],
	  [4, 8]
	]
*/


//Solution1: backtracking, 每次遍历到num时，如果n能被num整除才进行下一层的遍历，并且需要用n/num 替代n。 最后如果n=1并且item 非空则意味着找到了结果。
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList();
        if (n <= 1)
            return res;
        helper(res, new ArrayList(), n, 2);
        return res;
    }
    
    public void helper(List<List<Integer>> res, List<Integer> item, int n, int start) {
        if (n == 1 && item.size() > 1) {
            res.add(new ArrayList(item));
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                item.add(i);
                helper(res, item, n / i, i);
                item.remove(item.size() - 1);
            }
        }
    }
}

// Solution2: 剪枝，如果i已经大于upper 则直接赋值n， 我们注意到 由于因子是从小到大排序， 所以因子的大小肯定不超过n的平方根
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList();
        if (n <= 1)
            return res;
        helper(res, new ArrayList(), n, 2, (int)Math.sqrt(n));
        return res;
    }
    
    public void helper(List<List<Integer>> res, List<Integer> item, int n, int start, int upper) {
        if (n == 1 && item.size() > 1) {
            res.add(new ArrayList(item));
            return;
        }
        for (int i = start; i <= n; i++) {
            if (i > upper)
                i = n;
            if (n % i == 0) {
                item.add(i);
                helper(res, item, n / i, i, (int)Math.sqrt(n / i));
                item.remove(item.size() - 1);
            }
        }
    }
}
