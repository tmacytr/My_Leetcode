/*
	Moving Average from Data Stream   

	Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

	For example,
	MovingAverage m = new MovingAverage(3);
	m.next(1) = 1
	m.next(10) = (1 + 10) / 2
	m.next(3) = (1 + 10 + 3) / 3
	m.next(5) = (10 + 3 + 5) / 3
*/


// Solution1: my Solution
class MovingAverage {
    Queue<Integer> queue;
    int size;
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        queue = new ArrayDeque();
        this.size = size;
    }
    
    public double next(int val) {
        if (queue.size() == size) {
            queue.poll();
        }
        queue.offer(val);
        
        int count = 0;
        double res = 0;
        for (int num : queue) {
            res += num;
            count++;
        }
        return res / count;
    }
}


// Solution2: improved O(1)
class MovingAverage {
    Queue<Integer> queue;
    int size;
    double sum;
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        queue = new ArrayDeque();
        this.size = size;
        sum = 0;
    }
    
    public double next(int val) {
        if (queue.size() == size) {
            sum -= queue.poll();
        }
        queue.offer(val);
        sum += val;
        
        return sum / queue.size();
    }
}

class Solution {
    public int repeatedStringMatch(String A, String B) {
        int q = 1;
        StringBuilder S = new StringBuilder(A);
        for (; S.length() < B.length(); q++) S.append(A);
        if (S.indexOf(B) >= 0) return q;
        if (S.append(A).indexOf(B) >= 0) return q+1;
        return -1;
    }
}
