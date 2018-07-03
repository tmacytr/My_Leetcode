/*
    731. My Calendar II

    Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a triple booking.

    Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

    A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)

    For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.

    Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
    Example 1:
    MyCalendar();
    MyCalendar.book(10, 20); // returns true
    MyCalendar.book(50, 60); // returns true
    MyCalendar.book(10, 40); // returns true
    MyCalendar.book(5, 15); // returns false
    MyCalendar.book(5, 10); // returns true
    MyCalendar.book(25, 55); // returns true
    Explanation:
    The first two events can be booked.  The third event can be double booked.
    The fourth event (5, 15) can't be booked, because it would result in a triple booking.
    The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
    The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
    the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.

    Note:

    The number of calls to MyCalendar.book per test case will be at most 1000.
    In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].

    Companies: Google

    Related Topics: Array, Binary Search Tree

    Similar Questions: My Calendar I, My Calendar III
 */

/*
    这道题说最多容忍两个重叠区域，注意是重叠区域，不是事件。比如事件A，B，C互不重叠，但是有一个事件D，和这三个事件都重叠，这样是可以的，因为重叠的区域最多只有两个。
    所以关键还是要知道具体的重叠区域，如果两个事件重叠，那么重叠区域就是它们的交集，求交集的方法是两个区间的起始时间中的较大值，到结束时间中的较小值。
    那么我们可以用一个集合来专门存重叠区间，再用一个集合来存完整的区间，那么我们的思路就是，先遍历专门存重叠区间的集合，
    因为能在这里出现的区间，都已经是出现两次了，如果当前新的区间跟重叠区间有交集的话，说明此时三个事件重叠了，直接返回false。
 */
//Solution1:
class MyCalendarTwo {
    List<int[]> calendar;
    List<int[]> overlaps;
    public MyCalendarTwo() {
        calendar = new ArrayList();
        overlaps = new ArrayList();
    }

    public boolean book(int start, int end) {
        for (int[] event : overlaps) {
            if (event[1] > start && event[0] < end)
                return false;
        }
        for (int[] event : calendar) {
            if (event[1] > start && event[0] < end)
                overlaps.add(new int[]{Math.max(event[0], start), Math.min(event[1], end)});
        }
        calendar.add(new int[]{start, end});
        return true;
    }
}

/*
    建立一个时间点和次数之间的映射，规定遇到起始时间点，次数加1，遇到结束时间点，次数减1。那么我们首先更改新的起始时间start和结束时间end的映射，start对应值增1，end对应值减1。然后定义一个变量cnt，来统计当前的次数。
    我们使用treemap具有自动排序的功能，所以我们遍历的时候就是按时间顺序的，最先遍历到的一定是一个起始时间，所以加上其映射值，一定是个正数。
 */
//Solution2:
class MyCalendarTwo {
    TreeMap<Integer, Integer> map;
    public MyCalendarTwo() {
        map = new TreeMap();
    }

    public boolean book(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) - 1);

        int count = 0;
        for (int value : map.values()) {
            count += value;
            if (count >= 3) {
                map.put(start, map.get(start) - 1); //为什么要进行这一步？ 因为count >= 3 就意外着这个booking是失败的，除了要返回false外，我们还必须把之前加到map中的start和end remove
                map.put(end, map.get(end) + 1);
                if (map.get(start) == 0)
                    map.remove(start);
                return false;
            }
        }
        return true;
    }
}