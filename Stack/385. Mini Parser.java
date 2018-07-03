/*
    385. Mini Parser

    Given a nested list of integers represented as a string, implement a parser to deserialize it.

    Each element is either an integer, or a list -- whose elements may also be integers or other lists.

    Note: You may assume that the string is well-formed:

    String is non-empty.
    String does not contain white spaces.
    String contains only digits 0-9, [, - ,, ].
    Example 1:

    Given s = "324",

    You should return a NestedInteger object which contains a single integer 324.
    Example 2:

    Given s = "[123,[456,[789]]]",

    Return a NestedInteger object containing a nested list with 2 elements:

    1. An integer containing value 123.
    2. A nested list containing two elements:
        i.  An integer containing value 456.
        ii. A nested list with one element:
             a. An integer containing value 789.

    Companies: Airbnb

    Related Topics: String, Stack

    Similar Questions:
        Flatten Nested List Iterator
        Ternary Expression Parser
        Remove Comments
 */

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/*
    This approach will just iterate through every char in the string (no recursion).

    If encounters '[', push current NestedInteger to stack and start a new one.
    If encounters ']', end current NestedInteger and pop a NestedInteger from stack to continue.
    If encounters ',', append a new number to curr NestedInteger, if this comma is not right after a brackets.
    Update index l and r, where l shall point to the start of a integer substring, while r shall points to the end+1 of substring.
 */
//Solution1:
class Solution {
    public NestedInteger deserialize(String s) {
        if (s.isEmpty())
            return null;
        if (s.charAt(0) != '[')
            return new NestedInteger(Integer.valueOf(s));
        Stack<NestedInteger> stack = new Stack();
        NestedInteger cur = null;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (c == '[') {
                if (cur != null)
                    stack.push(cur);
                cur = new NestedInteger();
                left = right + 1;
            } else if (c == ']') {
                String num = s.substring(left, right);
                if (!num.isEmpty())
                    cur.add(new NestedInteger(Integer.valueOf(num)));
                if (!stack.isEmpty()) {
                    NestedInteger pop = stack.pop();
                    pop.add(cur);
                    cur = pop;
                }
                left = right + 1;
            } else if (c == ',') {
                if (s.charAt(right - 1) != ']') {
                    String num = s.substring(left, right);
                    cur.add(new NestedInteger(Integer.valueOf(num)));
                }
                left = right + 1;
            }
        }
        return cur;
    }
}

//Solution2: prefer
class Solution {
    public NestedInteger deserialize(String s) {
        if (s.isEmpty())
            return null;
        if (s.charAt(0) != '[')
            return new NestedInteger(Integer.valueOf(s));
        Stack<NestedInteger> stack = new Stack();
        NestedInteger res = new NestedInteger();
        stack.push(res);
        int start = 1;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                NestedInteger cur = new NestedInteger();
                stack.peek().add(cur);// 注意当add cur进当前栈顶元素的集合中时， 如果cur的值变化， 该栈顶元素中的cur也会相应发生变化
                stack.push(cur);
                start = i + 1;
            } else if (c == ',' || c == ']') {
                if (i > start) {
                    int val = Integer.valueOf(s.substring(start, i));
                    stack.peek().add(new NestedInteger(val));
                }
                start = i + 1;
                if (c == ']')
                    stack.pop();
            }
        }
        return res;
    }
}