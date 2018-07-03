/*
	Permutations II
	Given a collection of numbers that might contain duplicates, return all possible unique permutations.

	For example,
	[1,1,2] have the following unique permutations:
	[1,1,2], [1,2,1], and [2,1,1].
*/

/*
    跟一的不同是需要先排序，并且需要判断重复
    if (i > 0 && nums[i - 1] == nums[i] && visited[i - 1] == false) {
        continue;
    }
*/

//Solution1: Recursive
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList();
        List<Integer> item = new ArrayList();
        boolean[] visited = new boolean[nums.length];
        dfs(res, visited, nums, item);
        return res;
    }
    public void dfs( List<List<Integer>> res, boolean[] visited, int[] nums, List<Integer> item) {
        if (item.size() == nums.length) {
            res.add(new ArrayList(item));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] == nums[i] && visited[i - 1] == false) {
                continue;
            }
            if (!visited[i]) {
                visited[i] = true;
                item.add(nums[i]);
                dfs(res, visited, nums, item);
                item.remove(item.size() - 1);
                visited[i] = false;
            }
        }
    }
}

//Solution2: Iterative, 原理跟pemutation一样，关键在于去重
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums.length == 0 || nums == null) {
            return res;
        }
        Arrays.sort(nums);
        List<Integer> initList = new ArrayList();
        initList.add(nums[0]);
        res.add(initList);
        HashSet<List<Integer>> set = new HashSet(); //对拿到的结果去重

        for (int i = 1; i < nums.length; i++) {
            List<List<Integer>> tempRes = new ArrayList();
            for (int insertIndex = 0; insertIndex <= i; insertIndex++) {//遍历插入位置
                for (List<Integer> list : res) {
                    List<Integer> newList = new ArrayList(list);
                    newList.add(insertIndex, nums[i]);
                    if (!set.contains(newList)) { //和permutation I 唯一不同之处，用set在这里对List去重，如果一样的不用放入tempRes，这样下一层新结果就不会有重复
                        set.add(newList);
                        tempRes.add(newList);
                    }

                }
            }
            res = tempRes;
        }
    }
    return res;
}