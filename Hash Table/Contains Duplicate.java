/*
	Contains Duplicate.
	Given an array of integers, find if the array contains any duplicates. Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.
	Tags: Array, HashTable
*/

//Solution1: Time complexity: O(N^2), memory: O(1)
class Solution {
    public boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
//Solution2: HashSet Time complexity: O(N), memory: O(N)
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        if (nums.length == 0 || nums == null) {
            return false;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i : nums) {
            if (set.contains(i)) {
                return true;
            } else {
                set.add(i);
            }
        }
        return false;
    }
}


//Solution3: Time complexity: O(N lg N), memory: O(1) - not counting the memory used by sort
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int ind = 1; ind < nums.length; ind++) {
            if(nums[ind] == nums[ind - 1]) {
                return true;
            }
        }
        return false;
    }
}