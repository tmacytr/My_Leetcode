/*
	716. Max Stack

	Design a max stack that supports push, pop, top, peekMax and popMax.

	push(x) -- Push element x onto stack.
	pop() -- Remove the element on top of the stack and return it.
	top() -- Get the element on the top.
	peekMax() -- Retrieve the maximum element in the stack.
	popMax() -- Retrieve the maximum element in the stack, and remove it. If you find more than one maximum elements, only remove the top-most one.

	Example 1:
	MaxStack stack = new MaxStack();
	stack.push(5); 
	stack.push(1);
	stack.push(5);
	stack.top(); -> 5
	stack.popMax(); -> 5
	stack.top(); -> 1
	stack.peekMax(); -> 5
	stack.pop(); -> 1
	stack.top(); -> 5
	Note:
	-1e7 <= x <= 1e7
	Number of operations won't exceed 10000.
	The last four operations won't be called when stack is empty.
*/

// Solution1: My solution, so bad :( 
/*
	Time Complexity: O(N) for the popMax and pop operation, and O(1) for the other operations, where N is the number of operations performed.
	Space Complexity: O(N), the maximum size of the stack.
*/
class MaxStack {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    TreeSet<Integer> set;
    int max;
    /** initialize your data structure here. */
    public MaxStack() {
        stack1 = new Stack();
        stack2 = new Stack();
        max = Integer.MIN_VALUE;
    }
    
    public void push(int x) {
        stack1.push(x);
        if (max < x) {
            max = x;
        }
    }
    
    public int pop() {
        int res = stack1.pop();
        setMax(); 
        return res;
    }
    
    public int top() {
        return stack1.peek();
    }
    
    public int peekMax() {
        return max;
    }
    
    public int popMax() {
        int res = max;
        
        while (!stack1.isEmpty()) {
            int num = stack1.pop();
            if (num == max) {
                break;
            }
            stack2.push(num);
        }
        
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        
        setMax();
        return res;
        
    }
    
    public void setMax() {
        max = Integer.MIN_VALUE;
        for (int num : stack1) {
            max = Math.max(max, num);
        } 
    }
}

// Solution2: improved solution 使用两个栈来模拟，stack为普通的栈，用来保存所有的数字，而maxStack为最大栈，用来保存出现的最大的数字。
/*
	push: 我们先来看maxStack，如果maxStack为空或者maxStack的栈顶元素小于等于x，将x压入maxStack中。
			  因为maxStack保存的是目前为止最大的数字，所以一旦新数字大于等于栈顶元素，说明遇到更大的数字了，压入栈。然后将数压入stack，stack保存所有的数字，所以都得压入栈。

	pop:  当maxStack的栈顶元素和stack的栈顶元素相同时，我们要移除maxStack的栈顶元素，因为一个数字不在stack中了，就不能在maxStack中。pop stack的栈顶元素返回即可。

	top:  直接返回stack的top()。

	peekMax: 直接返回maxStack的top()。

	popMax: 重点，先将maxStack的栈顶元素保存到一个变量max中，然后我们要在stack中删除这个元素，由于栈无法直接定位元素，
				  所以我们用一个临时栈temp，将stack的出栈元素保存到临时栈temp中，当stack的栈顶元素和maxStack的栈顶元素相同时退出while循环，此时我们在stack中找到了maxStack的栈顶元素，分别将stack和maxStack的栈顶元素移除，
				  然后要做的是将临时栈temp中的元素加回stack中，
				  Key Point -> 注意此时容易犯的一个错误是，没有同时更新maxStack，所以我们直接调用push()函数即可:

  Time Complexity: O(N) for the popMax operation, and O(1) for the other operations, where N is the number of operations performed.
	Space Complexity: O(N), the maximum size of the stack.
*/

class MaxStack {
    Stack<Integer> stack;
    Stack<Integer> maxStack; // use maxStack to store the max value
    /** initialize your data structure here. */
    public MaxStack() {
        stack = new Stack();
        maxStack = new Stack();
    }
    
    public void push(int x) {
        stack.push(x);
        // if we found x is larger than current max value in maxStack, we just push it to maxStack
        if (maxStack.isEmpty() || maxStack.peek() <= x) {
            maxStack.push(x);
        }
    }
    
    public int pop() {
        int peek = stack.pop();
        // 如果要pop的值 正好是最大值，要更新maxStack
        if (peek == maxStack.peek())
            maxStack.pop();
        return peek;
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int peekMax() {
        return maxStack.peek();
    }
    
    public int popMax() {
        int max = maxStack.pop();
        
        Stack<Integer> temp = new Stack();
        
        while (stack.peek() != max) {
            temp.push(stack.pop());
        }
        
        stack.pop();
        
        while (!temp.isEmpty()) {
            push(temp.pop()); // 用MaxStack里的push方法，这样我们可以更新 maxStack 
        }
        return max;
    }
}

// Solution3: Double Linked List + TreeMap
/*
	Time Complexity: O(log N) for all operations except peek which is O(1), where N is the number of operations performed. 
								   Most operations involving TreeMap are O(logN).
*/
class MaxStack {
    TreeMap<Integer, List<Node>> map;
    DoubleLinkedList list;
    /** initialize your data structure here. */
    public MaxStack() {
        map = new TreeMap();
        list = new DoubleLinkedList();
    }
    
    public void push(int x) {
        Node node = list.add(x);
        if (!map.containsKey(x))
            map.put(x, new ArrayList());
        map.get(x).add(node);
    }
    
    public int pop() {
        int val = list.pop();
        List<Node> L = map.get(val);
        L.remove(L.size() - 1);
        if (L.isEmpty())
            map.remove(val);
        return val;
    }
    
    public int top() {
        return list.peek();
    }
    
    public int peekMax() {
        return map.lastKey();
    }
    
    public int popMax() {
        int max = peekMax();
        List<Node> L = map.get(max);
        Node node = L.remove(L.size() - 1);
        list.unlink(node);
        if (L.isEmpty())
            map.remove(max);
        return max;
    }
}

class DoubleLinkedList {
    Node head;
    Node tail;
    
    public DoubleLinkedList() {
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.pre = head;
    }
    
    public Node add(int val) {
        Node node = new Node(val);
        node.next = tail;
        node.pre = tail.pre;
        tail.pre.next = node;
        tail.pre = node;
        return node;
    }
    
    public int pop() {
        return unlink(tail.pre).val;
    }
    
    public int peek() {
        return tail.pre.val;
    }
    
    public Node unlink(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        return node;
    }
}

class Node {
    int val;
    Node pre;
    Node next;
    public Node(int val) {
        this.val = val;
    }
}
