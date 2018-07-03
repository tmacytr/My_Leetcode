/*
	364. Nested List Weight Sum II

	Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

	Each element is either an integer, or a list -- whose elements may also be integers or other lists.

	Different from the previous question where weight is increasing from root to leaf, now the weight is defined from bottom up. i.e., the leaf level integers have weight 1, and the root level integers have the largest weight.

	Example 1:
	Given the list [[1,1],2,[1,1]], return 8. (four 1's at depth 1, one 2 at depth 2)

	Example 2:
	Given the list [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17)
*/

// Solution1: tedious solution, use bfs caculate the level and use DFS to get the result
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0)
            return 0;
        int level = 0;
        Queue<NestedInteger> queue = new ArrayDeque(nestedList);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                NestedInteger num = queue.poll();
                if (!num.isInteger()) {
                    queue.addAll(num.getList());
                }
            }
            level++;
        }
        
        return helper(nestedList, level);
    }
    
    public int helper(List<NestedInteger> nestedList, int level) {
        int res = 0;
        for (NestedInteger num : nestedList) {
            res += num.isInteger() ? num.getInteger() * level : helper(num.getList(), level - 1);
        }
        return res;
    }
}

//Solution2: DFS prefer
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0)
            return 0;
        return helper(nestedList, 0);
    }
    
    public int helper(List<NestedInteger> nestedList, int res) {
        List<NestedInteger> nextList = new ArrayList();
        for (NestedInteger num : nestedList) {
            if (num.isInteger()) {
                res += num.getInteger();
            } else {
                nextList.addAll(num.getList()); // 传入下一层计算
            }
        }
        res += nextList.isEmpty() ? 0 : helper(nextList, res);
        return res;
    }
}

//Soluton3: BFS
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0)
            return 0;
        Queue<NestedInteger> queue = new ArrayDeque();
        int preSum = 0;
        int res = 0;
        for (NestedInteger next : nestedList)
            queue.offer(next);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger cur = queue.poll();
                if (cur.isInteger()) {
                    preSum += cur.getInteger();
                } else {
                    queue.addAll(cur.getList());
                }
            }
            res += preSum;
        }
        return res;
    }
}

// Solution4: BFS2, prefer
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int unweighted = 0;
        int weighted = 0;
        
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList();
            for (NestedInteger nest : nestedList) {
                if (nest.isInteger()) 
                    unweighted += nest.getInteger();
                else
                    nextLevel.addAll(nest.getList());
            }
            weighted += unweighted;
            nestedList = nextLevel;
        }
        return weighted;
    }
}