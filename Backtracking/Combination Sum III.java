/*
	Combination Sum III 
	Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

	Ensure that numbers within the set are sorted in ascending order.

	Example 1:

		Input: k = 3, n = 7

		Output:

		[[1,2,4]]
	Example 2:

		Input: k = 3, n = 9

		Output:

		[[1,2,6], [1,3,5], [2,3,4]]
*/

public class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList();
        if (n <= 0 || k == 0) {
            return res;
        }
        
        List<Integer> item = new ArrayList();
        helper(res, item, 0, 0, 1, k, n);
        return res;
    }
    
    public void helper(List<List<Integer>> res, List<Integer> item, int count, int sum, int start, int k, int n) {
        if (sum > n || count > k)
            return;
        if (sum == n && count == k) {
            res.add(new ArrayList(item));
            return;
        }
        for (int i = start; i <= 9; i++) {
            item.add(i);
            helper(res, item, count + 1, sum + i, i + 1, k, n);
            item.remove(item.size() - 1);
        }
    }
}