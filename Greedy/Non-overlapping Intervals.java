/*
	435. Non-overlapping Intervals

	Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

	Note:
	You may assume the interval's end point is always bigger than its start point.
	Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
	Example 1:
	Input: [ [1,2], [2,3], [3,4], [1,3] ]

	Output: 1

	Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
	Example 2:
	Input: [ [1,2], [1,2], [1,2] ]

	Output: 2

	Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
	Example 3:
	Input: [ [1,2], [2,3] ]

	Output: 0

	Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
*/

/*
 1. Actually, the problem is the same as “Given a collection of intervals, find the maximum number of intervals that are non-overlapping

	Why sort by end ?
	For example: [1,4], [2,3], [3,4] 
	 the interval with early start might be very long and incompatible with many intervals. But if we choose the interval that ends early, 
	 we’ll have more space left to accommodate more intervals. 
*/


//Solution1: use min priority queue
class Solution {
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;
        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.end - b.end);
        
        pq.offer(intervals[0]);
        
        int res = 0;
        for (int i = 1; i < intervals.length; i++) {
            Interval pre = pq.poll();
            if (pre.end <= intervals[i].start) {
                pre.end = intervals[i].end;
            }  else {
                pre.end = Math.min(intervals[i].end, pre.end);
                res++;
            }
            pq.offer(pre);
        }
        return res;
    }
}

//Solution2: prefer
class Solution {
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;
        Arrays.sort(intervals, (a, b) -> a.end - b.end);
        Interval pre = intervals[0];
        
        int count = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start >= pre.end) {
                pre.end = intervals[i].end;
                count++;
            }
        }
        return intervals.length - count;
    }
}