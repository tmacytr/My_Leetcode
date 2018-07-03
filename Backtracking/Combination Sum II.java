/*
	Combination Sum II 


	Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

	Each number in C may only be used once in the combination.

	Note:
	All numbers (including target) will be positive integers.
	Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
	The solution set must not contain duplicate combinations.
	For example, given candidate set 10,1,2,7,6,1,5 and target 8, 
	A solution set is: 
	[1, 7] 
	[1, 2, 5] 
	[2, 6] 
	[1, 1, 6]

	和I的区别在于这里的数只能使用一次
*/

//Solution1: Recursion
class Solution {
    public List<List<Integer>> combinationSum2(int[] num, int target) {
        // write your code here
        List<List<Integer>> res = new ArrayList();
        if (num == null || num.length == 0) {
            return res;
        }
        Arrays.sort(num);
        List<Integer> item = new ArrayList();
        helper(num, target, 0, item, res);
        return res;
    }
    
    public void helper(int[] num, int target, int start, List<Integer> item, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList(item);
            return;
        }
        
        for (int i = start; i < num.length; i++) {
            if (i > start && num[i] == num[i - 1]) {
                continue;
            }
            item.add(num[i]);
            helper(num, target - num[i], i + 1, item, res);
            item.remove(item.size() - 1);
        }
    }
}


//Solution1: Iterative
class Solution {
    public List<List<Integer>> combinationSum2(int[] num, int target) {
        List<List<Integer>> rslt = new ArrayList();
        Set<List<Integer>> set = new HashSet();
        Stack<List<Integer>> stack = new Stack();
        Arrays.sort(num);
        // initial list
        List<Integer> root = new ArrayList();
        root.add(0);
        root.add(-1);
        // DFS
        stack.push(root);
        while(!stack.isEmpty()){
            List<Integer> list = stack.pop();
            // check if target found
            if(list.get(0)==target){
                List<Integer> path = new ArrayList();
                for(int i = 0;i < list.size()-2;i++)
                    path.add(list.get(i+2));
                set.add(path);
            }
            // push child list
            for(int i = list.get(1)+1;i < num.length;i++){
                if(list.get(0)+num[i] > target) break;
                List<Integer> path = new ArrayList(list);
                path.set(0, path.get(0)+num[i]);
                path.set(1, i);
                path.add(num[i]);
                stack.push(path);
            }
        }
        rslt.addAll(set);

        return rslt;  
    }

}