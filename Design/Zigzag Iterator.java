/*
	Zigzag Iterator
	Given two 1d vectors, implement an iterator to return their elements alternately.

	For example, given two 1d vectors:

	v1 = [1, 2]
	v2 = [3, 4, 5, 6]

	By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].

	Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?

	Clarification for the follow up question - Update (2015-09-18):
	The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:

	[1,2,3]
	[4,5,6,7]
	[8,9]
	It should return [1,4,8,2,5,9,3,6,7].
*/
//Solution1
public class ZigzagIterator {
    //给List v1, v2 添加iterator
    private Iterator<Integer> i, j, temp;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        i = v1.iterator();
        j = v2.iterator();
        
    }
    //这里的if语句实现zigzag的功能，交替的返回v1 以及v2的值
    public int next() {
        if (i.hasNext()) {
            temp = i;
            i = j;
            j = temp;
        }
        return j.next();
    }
    public boolean hasNext() {
        return i.hasNext() || j.hasNext();
    }
}

//Solution2: prefer, 用一个queue去存list的iterator，每次要取next的时候就poll一个iterator出来， 当前iterator的next()就是所求的next， 再判断当前iterator是否有hasNext 如果有的话才重新offer进队列
public class ZigzagIterator {
    Queue<Iterator> queue;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        queue = new ArrayDeque();
        if (!v1.isEmpty()) {
            queue.offer(v1.iterator());
        } 
        if (!v2.isEmpty()) {
            queue.offer(v2.iterator());
        }
    }

    public int next() {
        Iterator iter = queue.poll();
        int res = (int)iter.next();
        if (iter.hasNext())
            queue.offer(iter);
        return res;
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
