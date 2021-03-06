/*
	605. Can Place Flowers

	Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot be planted in adjacent plots - they would compete for water and both would die.

	Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.

	Example 1:
	Input: flowerbed = [1,0,0,0,1], n = 1
	Output: True
	Example 2:
	Input: flowerbed = [1,0,0,0,1], n = 2
	Output: False

	Note:
	The input array won't violate no-adjacent-flowers rule.
	The input array size is in the range of [1, 20000].
	n is a non-negative integer which won't exceed the input array size.
*/


// My solution
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0)
            return true;
        
        for (int i = 0; i < flowerbed.length; i++) {
            if (canPlace(flowerbed, i)) {
                flowerbed[i] = 1;
                n--;
                if (n == 0)
                    return true;
            }
        }
        
        return false;
    }
    
    public boolean canPlace(int[] nums, int i) {
        if (nums.length == 1 && nums[0] == 0)
            return true;
        if (i == 0 && i + 1 < nums.length) {
            return nums[i] == 0 && nums[i + 1] == 0;
        } else if (i == nums.length - 1 && i - 1 >= 0) {
            return nums[i] == 0 && nums[i - 1] == 0;
        } else if (i - 1 >= 0 && i + 1 < nums.length) {
            return nums[i - 1] == 0 && nums[i] == 0 && nums[i + 1] == 0;
        }
        return false;
    }
}


// Solution2: concise
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length && count < n; i++) {
            if (flowerbed[i] == 0) {
                int pre = (i == 0) ? 0 : flowerbed[i - 1];
                int next = (i == flowerbed.length - 1) ? 0 :  flowerbed[i + 1];

                if (pre == 0 && next == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            }
        }
        return count == n;
    }
}