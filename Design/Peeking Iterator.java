/*
	Peeking Iterator
	Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().

	Here is an example. Assume that the iterator is initialized to the beginning of the list: [1, 2, 3].

	Call next() gets you 1, the first element in the list.

	Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.

	You call next() the final time and it returns 3, the last element. Calling hasNext() after that should return false


	Hint:

		Think of "looking ahead". You want to cache the next element.
		Is one variable sufficient? Why or why not?
		Test your design with call order of peek() before next() vs next() before peek().
		For a clean implementation, check out Google's guava library source code.
	Follow up: How would you extend your design to be generic and work with all types, not just integer?
*/



/*
	题意是:  1. 设置一个peek方法，每次都可以取得list中的peek值（就是next值，但是却又不会移动next指针，next指针每次可以取得list的 peek值但是会移位）
		    2. 设置一个nextVal, 在用next()之前先用nextVal保存next（）的值，这样每次peek（）就返回nextVal
		    

*/

// Solution1:
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {
    private Integer nextVal = null;
    private Iterator<Integer> iter;
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    iter = iterator;
	    if (iter.hasNext()) {
	        nextVal = iter.next();
	    }
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
		return nextVal;
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
	    Integer res = nextVal;
	    nextVal = iter.hasNext() ? iter.next() : null;
	    return res;
	}

	@Override
	public boolean hasNext() {
		return nextVal != null;
	}
}

// Follow up: How would you extend your design to be generic and work with all types, not just integer?
// Solution2: 使用Generic的方式，代码中已经将Integer替换为Generic的T
class PeekingIterator<T> implements Iterator<T> {
	T nextVal;
	Iterator<T> iter;

	public PeekingIterator(Iterator<T> iterator) {
		// initialize any member here.
		iter = iterator;
		if (iter.hasNext()) {
			nextVal = iter.next();
		}
	}

	// Returns the next element in the iteration without advancing the iterator.
	public T peek() {
		return nextVal;
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public T next() {
		T res = nextVal;
		nextVal = iter.hasNext() ? iter.next() : null;
		return res;
	}

	@Override
	public boolean hasNext() {
		return nextVal != null;
	}
}