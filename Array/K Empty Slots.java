/*
	683. K Empty Slots

	There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.

	Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.

	For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.

	Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.

	If there isn't such day, output -1.

	Example 1:
	Input: 
	flowers: [1,3,2]
	k: 1
	Output: 2
	Explanation: In the second day, the first and the third flower have become blooming.
	Example 2:
	Input: 
	flowers: [1,2,3]
	k: 1
	Output: -1
	Note:
	The given array will be in the range [1, 20000].
*/

// Solution1: my solution,  brute force: O(n)
// 每次赋值之后 如果左边 slot[pos - k - 1] == 1 && slot[pos] == 1, 只需之间的 pos - k <=> pos - 1 都为0 就找到满足条件的解
// 如果右边 slot[pos] == 1 && slot[pos + k + 1] == 1 , 只需之间的 pos + 1 <=> pos + k 都为0 就找到满足条件的解
class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        if (flowers == null || flowers.length <= 1)
            return -1;
        int len = flowers.length;
        int[] slot = new int[flowers.length];

        for (int i = 0; i < len; i++) {
            slot[flowers[i] - 1] = 1;
            int count = 0;
            int pos = flowers[i] - 1;
            
            if (k == 0 && (pos - 1 >= 0 && slot[pos - 1] == 1 || pos + 1 < len && slot[pos + 1] == 1))
                return i + 1;
            
            if (pos - k - 1 >= 0 && slot[pos - k - 1] == 1) {
                for (int j = pos - k; j < pos; j++) {
                    if (slot[j] == 1)
                        break;
                    if (++count == k && j == pos - 1)
                        return i + 1; 
                }
            }
            
            count = 0;
            if (pos + k + 1 < len && slot[pos + k + 1] == 1) {
                for (int j = pos + 1; j <= pos + k; j++) {
                    if (slot[j] == 1)
                        break;
                    if (++count == k && j == pos + k) 
                        return i + 1; 
                }
            }
            
        }
        return -1;
    }
}

// Solution2: time, sliding window: o(n)
/*
		flowers[i]   i是按顺序的天数，flowers[i]是坑的位置
		days[i]      i是坑的位置, days[i]是指第i + 1天
		所以 days[flowers[i] - 1] = i + 1

		days[i] is the blooming day of the flower in position i+1.
		 We just need to find a subarray days[left, left+1,..., left+k-1, right] which satisfies: for any i = left+1,..., left+k-1, we can have days[left] < days[i] && days[right] < days[i]. Then, the result is max(days[left], days[right]).
*/
class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        int len = flowers.length;
        int[] days = new int[len];
        for (int i = 0; i < len; i++)
            days[flowers[i] - 1] = i + 1;
 
        int left = 0;
        int right = k + 1;
        int res = Integer.MAX_VALUE;
        
        for (int i = 0; right < days.length; i++) {
        	  // right - left 维护一个大小为k的窗口， 如果窗口之间的值不满足这个条件，则窗口往right移
            if (days[i] < days[left] || days[i] <= days[right]) {
                if (i == right)
                    res = Math.min(res, Math.max(days[left], days[right]));
                left = i;
                right = i + k + 1; // right - left = k
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}

 
// Solution3: TreeSet, O(NlogN) N is the length of flowers. Every insertion to treeset is O(logN)
class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        TreeSet<Integer> set = new TreeSet();
        // 按天数顺序遍历
        for (int i = 0; i < flowers.length; i++) {
            int cur = flowers[i];
            Integer next = set.higher(cur);
            if (next != null && next - cur == k + 1)
                return i + 1;
            
            Integer pre = set.lower(cur);
            if (pre != null && cur - pre == k + 1)
                return i + 1;
            set.add(cur);
        }
        return -1;
    }
}