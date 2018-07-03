/*
    462. Minimum Moves to Equal Array Elements II

    Given a non-empty integer array, find the minimum number of moves required to make all array elements equal, where a move is incrementing a selected element by 1 or decrementing a selected element by 1.

    You may assume the array's length is at most 10,000.

    Example:

    Input:
    [1,2,3]

    Output:
    2

    Explanation:
    Only two moves are needed (remember each move increments or decrements one element):

    [1,2,3]  =>  [2,2,3]  =>  [2,2,2]

    Related Topics: Math

    Similar Questions
    1.Best Meeting Point
    2. Minimum Moves to Equal Array Elements
 */

//Solution1: O(nlogn) find median and sum all the diff between every number with median. median是中位数而不是平均值，如果数组size是偶数，则左右两个中位数可以！
class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            start++;
            end--;
        }
        int res = 0;
        for (int num : nums) {
            res += Math.abs(num - nums[start]);
        }
        return res;
    }
}

//Solution2: one pass
public class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        int count = 0;
        while(i < j){
            count += nums[j]-nums[i];
            i++;
            j--;
        }
        return count;
    }
}


//Solution3: 既然我们需要使用median，在时间复杂度上我们可以使用quickselect去找median， 使得总的time complexcity reduce到 O（n)
class Solution {
    public int minMoves2(int[] nums) {
        int res = 0;
        int median = quickselect(nums, nums.length / 2 + 1, 0, nums.length - 1);
        for (int num : nums)
            res += Math.abs(num - median);
        return res;
    }

    public int quickselect(int[] a, int k, int start, int end) {
        int i = start;
        int j = end;
        while (true) {
            while (i < j && a[i] < a[end]) {
                i++;
            }
            while (i < j && a[j] >= a[end]) {
                j--;
            }
            if (i == j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, i, end);
        if (i + 1 == k) {
            return a[i];
        } else if (i + 1 < k) {
            return quickselect(a, k, i + 1, end);
        } else {
            return quickselect(a, k, start, i - 1);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}