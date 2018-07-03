/*
    386. Lexicographical Numbers

    Given an integer n, return 1 - n in lexicographical order.

    For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].

    Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.

    Companies: Bloomberg
 */

//Solution1: DFS prefer, 按个位数遍历，在遍历下一个个位数之前，先遍历十位数，十位数的高位为之前的个位数，只要这个多位数并没有超过n，就可以一直往后遍历
class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList();
        for (int i = 1; i < 10; i++)
            helper(i, n, res);
        return res;
    }

    private void helper(int cur, int n, List<Integer> res) {
        if (cur > n)
            return;
        res.add(cur);
        for (int i = 0; i < 10; i++) {
            if (10 * cur + i > n)
                return;
            helper(10 * cur + i, n, res);
        }
    }
}


//Solution2: iterative
class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList();
        int cur = 1;
        for (int i = 1; i <= n; i++) {
            res.add(cur);
            if (cur * 10 <= n) {
                cur *= 10;
            } else if (cur % 10 != 9 && cur + 1 <= n) {
                cur++;
            } else {
                cur = cur / 10;
                while(cur % 10 == 9){ //we hit the right most node on this level
                    cur = cur / 10; //go back to the upper level
                }
                cur++; //preparing to do the level traversal again on upper level
            }
        }
        return res;
    }
}