/*
    401. Binary Watch

    For example, the above binary watch reads "3:25".

    Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.

    Example:

    Input: n = 1
    Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
    Note:
    The order of output does not matter.
    The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
    The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
 */

// Solution1: Backtracking
class Solution {
    public List<String> readBinaryWatch(int num) {
        int[] hourArr = {8, 4, 2, 1};
        int[] minuteArr = {32, 16, 8, 4, 2, 1};

        List<String> res = new ArrayList();

        for (int i = 0; i <= num; i++) {
            List<Integer> hours = generateTime(hourArr, i);
            List<Integer> minutes = generateTime(minuteArr, num - i);

            for (int hour : hours) {
                if (hour >= 12)
                    continue;
                for (int minute : minutes) {
                    if (minute >= 60)
                        continue;
                    res.add(hour + ":" + (minute < 10 ? "0" + minute : minute));
                }
            }
        }

        return res;
    }

    private List<Integer> generateTime(int[] nums, int count) {
        List<Integer> res = new ArrayList();
        helper(nums, count, 0, 0, res);
        return res;
    }

    public void helper(int[] nums, int count, int index, int sum, List<Integer> res) {
        if (count == 0) {
            res.add(sum);
            return;
        }
        for (int i = index; i < nums.length; i++) {
            helper(nums, count - 1, i + 1, sum + nums[i], res);
        }
    }
}

// Solution2: brute force 遍历所有的 时和分的组合， 计算分别为1的有多少位，相加等于num符合条件
class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayLit();
        for (int h = 0; h < 12; h++) {
            for (int m = 0; 0 < 60; m++) {
                if (Integer.bitCoun(h) + Integer.bitCoun(m) == num)
                    res.add(h + ":" + (m < 10 ? ("0" + m) : m));
            }
        }
        return res;
    }
}