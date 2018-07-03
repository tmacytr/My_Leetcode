/*
    539. Minimum Time Difference

    Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes difference between any two time points in the list.
    Example 1:
    Input: ["23:59","00:00"]
    Output: 1
    Note:
    The number of time points in the given list is at least 2 and won't exceed 20000.
    The input time is legal and ranges from 00:00 to 23:59.

    Companies: Palantir
    Related Topics: String
 */

//Solution1: calculate the integer time(minutes) , sorting and compute the neighbour time
class Solution {
    public int findMinDifference(List<String> timePoints) {
        int min = Integer.MAX_VALUE;
        List<Integer> times = new ArrayList();
        for (String time : timePoints) {
            int hour = Integer.valueOf(time.substring(0, 2));
            int minute = Integer.valueOf(time.substring(3));

            int num = hour * 60 + minute;
            times.add(num);
        }

        Collections.sort(times);
        int res = Integer.MAX_VALUE;
        for (int i = 1; i < times.size(); i++) {
            res = Math.min(res, Math.abs(times.get(i) - times.get(i - 1)));
        }
        return Math.min(res, times.get(0) + 1440 - times.get(times.size() - 1)); //比较第一个和最后一个时可以将他们的和加上1440
    }
}


//Solution2: use bucket sort instead of Collections.sort
class Solution {
    public int findMinDifference(List<String> timePoints) {
        boolean[] mark = new boolean[24 * 60];
        for (String time : timePoints) {
            int hour = Integer.valueOf(time.substring(0, 2));
            int minute = Integer.valueOf(time.substring(3));
            if (mark[hour * 60 + minute])
                return 0;
            mark[hour * 60 + minute] = true;
        }
        int pre = 0, res = Integer.MAX_VALUE;
        int first = Integer.MAX_VALUE, last = Integer.MIN_VALUE;
        for (int cur = 0; cur < 24 * 60; cur++) {
            if (mark[cur]) {
                if (first != Integer.MAX_VALUE) {
                    res = Math.min(res, cur - pre);
                }
                pre = cur;
                first = Math.min(first, cur); //find the first time
                last = Math.max(last, cur); //find the last time;
            }
        }
        res = Math.min(res, (24 * 60 + first - last));
        return res;
    }
}