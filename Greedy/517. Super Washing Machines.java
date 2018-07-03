/*
    517. Super Washing Machines

    You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty.

    For each move, you could choose any m (1 ≤ m ≤ n) washing machines, and pass one dress of each washing machine to one of its adjacent washing machines at the same time .

    Given an integer array representing the number of dresses in each washing machine from left to right on the line, you should find the minimum number of moves to make all the washing machines have the same number of dresses. If it is not possible to do it, return -1.

    Example1

    Input: [1,0,5]

    Output: 3

    Explanation:
    1st move:    1     0 <-- 5    =>    1     1     4
    2nd move:    1 <-- 1 <-- 4    =>    2     1     3
    3rd move:    2     1 <-- 3    =>    2     2     2
    Example2

    Input: [0,3,0]

    Output: 2

    Explanation:
    1st move:    0 <-- 3     0    =>    1     2     0
    2nd move:    1     2 --> 0    =>    1     1     1
    Example3

    Input: [0,2,0]

    Output: -1

    Explanation:
    It's impossible to make all the three washing machines have the same number of dresses.
    Note:
    The range of n is [1, 10000].
    The range of dresses number in a super washing machine is [0, 1e5].

    Companies: Amazon

    Related Topics: Math, DP
 */


/*
    解题思路:
    我们有许多洗衣机，每个洗衣机里的衣服数不同，每个洗衣机每次只允许向相邻的洗衣机转移一件衣服，问要多少次才能使所有洗衣机的衣服数相等。

    注意这里的一次移动是说所有洗衣机都可以移动一件衣服到其相邻的洗衣机。

    test case [0, 0, 11, 5]，有四个洗衣机，装的衣服数为[0, 0, 11, 5]，最终的状态会变为[4, 4, 4, 4]，

    那么我们将二者做差，得到[-4, -4, 7, 1]，这里负数表示当前洗衣机还需要的衣服数，正数表示当前洗衣机多余的衣服数。

    我们要做的是要将这个差值数组每一项都变为0，对于第一个洗衣机来说，需要四件衣服可以从第二个洗衣机获得，那么就可以把-4移给二号洗衣机，

    那么差值数组变为[0, -8, 7, 1]，此时二号洗衣机需要八件衣服，那么至少需要移动8次。然后二号洗衣机把这八件衣服从三号洗衣机处获得，那么差值数组变为[0, 0, -1, 1]，

    此时三号洗衣机还缺1件，就从四号洗衣机处获得，此时差值数组成功变为了[0, 0, 0, 0]，成功。那么移动的最大次数就是差值数组中出现的绝对值最大的数字，8次
 */

/*
    证明:
    First we check the sum of dresses in all machines. if that number cannot be divided by count of machines, there is no solution.

    Otherwise, we can always transfer a dress from one machine to another, one at a time until every machines reach the same number, so there must be a solution. In this way, the total actions is sum of operations on every machine.

    Since we can operate several machines at the same time, the minium number of moves is the maximum number of necessary operations on every machine.

    For a single machine, necessary operations is to transfer dresses from one side to another until sum of both sides and itself reaches the average number. We can calculate (required dresses) - (contained dresses) of each side as L and R:

    L > 0 && R > 0: both sides lacks dresses, and we can only export one dress from current machines at a time, so result is abs(L) + abs(R)
    L < 0 && R < 0: both sides contains too many dresses, and we can import dresses from both sides at the same time, so result is max(abs(L), abs(R))
    L < 0 && R > 0 or L >0 && R < 0: the side with a larger absolute value will import/export its extra dresses from/to current machine or other side, so result is max(abs(L), abs(R))

    For example, [1, 0, 5], average is 2
    for 1, L = 0 * 2 - 0 = 0, R = 2 * 2 - 5= -1, result = 1
    for 0, L = 1 * 2 - 1= 1, R = 1 * 2 - 5 = -3, result = 3
    for 5, L = 2 * 2 - 1= 3, R = 0 * 2 - 0= 0, result = 3
    so minium moves is 3
 */
//Solution1:
class Solution {
    public int findMinMoves(int[] machines) {
        int n = machines.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += machines[i];
        }

        if (count % n != 0)
            return -1;
        int avg = count / n;
        int res = 0, cur = 0;
        for (int m : machines) {
            int diff = m - avg; //求出每个位置需要多少衣服，负数代表欠缺，整数表示富余
            res = Math.max(res, diff);
            cur += diff;
            res = Math.max(res, Math.abs(cur));
        }
        return res;
    }
}