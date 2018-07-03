/*
    362. Design Hit Counter

    Design a hit counter which counts the number of hits received in the past 5 minutes.

    Each function accepts a timestamp parameter (in seconds granularity) and you may assume that calls are being made to the system in chronological order (ie, the timestamp is monotonically increasing). You may assume that the earliest timestamp starts at 1.

    It is possible that several hits arrive roughly at the same time.

    Example:
    HitCounter counter = new HitCounter();

    // hit at timestamp 1.
    counter.hit(1);

    // hit at timestamp 2.
    counter.hit(2);

    // hit at timestamp 3.
    counter.hit(3);

    // get hits at timestamp 4, should return 3.
    counter.getHits(4);

    // hit at timestamp 300.
    counter.hit(300);

    // get hits at timestamp 300, should return 4.
    counter.getHits(300);

    // get hits at timestamp 301, should return 3.
    counter.getHits(301);
    Follow up:
    What if the number of hits per second could be very large? Does your design scale?
 */


// Solution1: my solution
// 缺点: If huge amount of hits happened at the same timestamp, this solution will takes too much memory since each element in queue is a single hit.
// Time: O(1) hit(), O(s) getHits(), s = queue.size(), if queue is so huge, than cost to much
class HitCounter {
    Queue<Integer> queue;
    /** Initialize your data structure here. */
    public HitCounter() {
        queue = new ArrayDeque();
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        queue.offer(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        while (!queue.isEmpty() && timestamp - queue.peek() >= 300) {
            queue.poll();
        }
        return queue.size();
    }
}


// Solution2: Follow up: What if the number of hits per second could be very large? Does your design scale?
/*
    定义了两个大小为300的一维数组times和hits，分别用来保存时间戳和点击数，
    在点击函数中，将时间戳对300取余，然后看此位置中之前保存的时间戳和当前的时间戳是否一样，一样说明是同一个时间戳，那么对应的点击数自增1，如果不一样，说明已经过了五分钟了，那么将对应的点击数重置为1。
    那么在返回点击数时，我们需要遍历times数组，找出所有在5分中内的位置，然后把hits中对应位置的点击数都加起来即可
 */

// Time: O(1) hit(), O(s) getHits(), s = 300
class HitCounter {
    int[] times;
    int[] hits;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[300];
        hits = new int[300];
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] != timestamp) { // 说明不是同个时间到来
            times[index] = timestamp;
            hits[index] = 1;
        } else {
            hits[index]++;
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int res = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                res += hits[i];
            }
        }
        return res;
    }
}
