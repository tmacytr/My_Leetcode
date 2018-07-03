/*
    Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.

    You may assume the integer do not contain any leading zero, except the number 0 itself.

    The digits are stored such that the most significant digit is at the head of the list.

    Example:
    Input:
    1->2->3

    Output:
    1->2->4
 */

/*
    思路是遍历链表，找到右起第一个不为9的数字，如果找不到这样的数字，说明所有数字均为9，那么在表头新建一个值为0的新节点，进行加1处理，然后把右边所有的数字都置为0即可。举例来说：

    比如1->2->3，那么第一个不为9的数字为3，对3进行加1，变成4，右边没有节点了，所以不做处理，返回1->2->4。

    再比如说8->9->9，找第一个不为9的数字为8，进行加1处理变成了9，然后把后面的数字都置0，得到结果9->0->0。

    再来看9->9->9的情况，找不到不为9的数字，那么再前面新建一个值为0的节点，进行加1处理变成了1，把后面的数字都置0，得到1->0->0->0。
 */
// Solution1: prefer
class Solution {
    public ListNode plusOne(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode lastNotNine = dummy;
        ListNode cur = head;

        // 找到右起第一个不为9的数字
        while (cur != null) {
            if (cur.val != 9)
                lastNotNine = cur;
            cur = cur.next;
        }

        //找到后+1
        lastNotNine.val++;

        // 从右起第一个不为9的节点的next开始遍历，全部置为0
        cur = lastNotNine.next;
        while (cur != null) {
            cur.val = 0;
            cur = cur.next;
        }
        // 如果找不到这样的数字，说明所有数字均为9，那么在表头新建一个值为0的新节点，进行加1处理，这里的dummy就是新加的节点。
        // 如果能找到则返回dummy.next
        return dummy.val == 1 ? dummy : dummy.next;
    }
}

//Solution2: My solution, Reverse Twice
class Solution {
    public ListNode plusOne(ListNode head) {
        if (head == null)
            return head;

        ListNode newhead = reverse(head);
        ListNode cur = newhead;

        int carry = 0;
        while (cur != null) {
            if (cur.val != 9) {
                cur.val += 1;
                carry = 0;
                break;
            } else {
                cur.val = 0;
                carry = 1;
                if (cur.next == null && carry == 1) {
                    cur.next = new ListNode(1);
                    break;
                }
            }
            cur = cur.next;
        }

        return reverse(newhead);
    }

    public ListNode reverse(ListNode head) {
        ListNode newhead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newhead;
            newhead = head;
            head = next;
        }
        return newhead;
    }
}

// Solution3: Recursive
class Solution {
    public ListNode plusOne(ListNode head) {
        if (dfs(head) == 0)
            return head;
        ListNode newHead = new ListNode(1);
        newHead.next = head;
        return newHead;
    }

    private int dfs(ListNode head) {
        if (head == null)
            return 1;
        int carry = dfs(head.next);
        if (carry == 0)
            return 0;
        int val = head.val + 1;
        head.val = val % 10;
        return val / 10;
    }
}