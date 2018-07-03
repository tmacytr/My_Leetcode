/*
    352. Data Stream as Disjoint Intervals

    Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.

    For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:

    [1, 1]
    [1, 1], [3, 3]
    [1, 1], [3, 3], [7, 7]
    [1, 3], [7, 7]
    [1, 3], [6, 7]
    Follow up:
    What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?

    Related Topics: Binary Search Tree

    Similar Questions: Summary RangesFind Right IntervalRange Module
 */

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class SummaryRanges {
    TreeMap<Integer, Interval> map;

    /** Initialize your data structure here. */
    public SummaryRanges() {
        map = new TreeMap();
    }

    public void addNum(int val) {
        if (map.containsKey(val))
            return;
        Integer low = map.lowerKey(val);
        Integer hi = map.higherKey(val);
        if (low != null && hi != null && map.get(low).end + 1 == val && hi == val + 1) {
            map.get(low).end = map.get(hi).end;
            map.remove(hi);
        } else if (low != null && map.get(low).end + 1 >= val) {
            map.get(low).end = Math.max(map.get(low).end, val);
        } else if (hi != null && hi == val + 1) {
            map.put(val, new Interval(val, map.get(hi).end));
            map.remove(hi);
        } else {
            map.put(val, new Interval(val, val));
        }
    }

    public List<Interval> getIntervals() {
        return new ArrayList(map.values());
    }
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(val);
 * List<Interval> param_2 = obj.getIntervals();
 */