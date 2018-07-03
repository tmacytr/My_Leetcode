/*
	Flatten 2D Vector
	Flatten 2D Vector My Submissions Question Solution 
	Total Accepted: 1639 Total Submissions: 5875 Difficulty: Medium
	Implement an iterator to flatten a 2d vector.

	For example,
	Given 2d vector =

	[
	  [1,2],
	  [3],
	  [4,5,6]
	]
	By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].

	Hint:

		How many variables do you need to keep track?
		Two variables is all you need. Try with x and y.
		Beware of empty rows. It could be the first few rows.
		To write correct code, think about the invariant to maintain. What is it?
		The invariant is x and y must always point to a valid point in the 2d vector. Should you maintain your invariant ahead of time or right when you need it?
		Not sure? Think about how you would implement hasNext(). Which is more complex?
		Common logic in two different places should be refactored into a common method.
		Follow up:
		As an added challenge, try to code it using only iterators in C++ or iterators in Java.
*/

// Solution1: Prefer
public class Vector2D implements Iterator<Integer> {
    //分两个Iterator，一个用来遍历vec2d，一个用来遍历子 List<Integer>中的元素
    private Iterator<List<Integer>> res;
    private Iterator<Integer> temp;
    public Vector2D(List<List<Integer>> vec2d) {
        res = vec2d.iterator();
    }

    @Override
    public Integer next() {
        this.hasNext();
        return temp.next();
    }

    @Override
    public boolean hasNext() {
        while ((temp == null || !temp.hasNext()) && res.hasNext()) {
            temp = res.next().iterator();
        }
        return temp != null && temp.hasNext();
    }
}

// Solution2: My solution
public class Vector2D implements Iterator<Integer> {
    List<Iterator<Integer>> res;
    public Vector2D(List<List<Integer>> vec2d) {
        res = new ArrayList();
        for (List<Integer> values : vec2d) {
            Iterator<Integer> iter = values.iterator();
            res.add(iter);
        }
    }

    @Override
    public Integer next() {
        for (Iterator<Integer> iter : res) {
            if (iter.hasNext())
                return iter.next();
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        for (Iterator<Integer> iter : res) {
            if (iter.hasNext())
                return true;
        }
        return false;
    }
}
