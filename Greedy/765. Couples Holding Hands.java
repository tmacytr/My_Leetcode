/*
    765. Couples Holding Hands

    N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps so that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and switch seats.

    The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).

    The couples' initial seating is given by row[i] being the value of the person who is initially sitting in the i-th seat.

    Example 1:

    Input: row = [0, 2, 1, 3]
    Output: 1
    Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
    Example 2:

    Input: row = [3, 2, 0, 1]
    Output: 0
    Explanation: All couples are already seated side by side.
    Note:

    len(row) is even and in the range of [4, 60].
    row is guaranteed to be a permutation of 0...len(row)-1.

    Companies: Google

    Related Topics: Greedy, Union Find, Graph

    Similar Questions: 1.First Missing Positive 2.Missing Number
 */

/*
    Greedy的难点在于如何证明
 */


//Solution1: O(n^2) greedy
class Solution {
    public int minSwapsCouples(int[] row) {
        int res = 0;
        for (int i = 0; i < row.length; i += 2) {
            // 一个数异或上1就是其另一个位，这个不难理解，如果是偶数的话，最后位是0，‘异或’上1等于加了1，变成了可以成对的奇数。
            // 如果是奇数的话，最后位是1，‘异或’上1后变为了0，变成了可以的成对偶数
            if (row[i + 1] == (row[i] ^ 1)) //如果下个位置就是自己的partner则continue
                continue;
            res++;
            for (int j = i + 1; j < row.length; j++) { //遍历数组寻找那个能配对的数
                if (row[j] == (row[i] ^ 1)) {
                    int temp = row[i];
                    row[j] = row[i + 1];
                    row[i + 1] = temp ^ 1;
                    break;
                }
            }
        }
        return res;
    }
}

//Solution2: O(n) greedy， 这用一个position数组就去记录每个元素所在的位置，所以可以reduce上solution1中 去遍历数组寻找partner的那一步骤
class Solution {
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        int[] pos = new int[n];
        for (int i = 0; i < n; i++)
            pos[row[i]] = i; //记录第i个人在row数组中的位置
        int res = 0;
        for (int i = 0; i < n; i += 2) {
            int j = row[i] ^ 1; // 找当前元素的partner
            if (row[i + 1] != j) {
                swap(row, pos, i + 1, pos[j]);
                res++;
            }
        }
        return res;
    }

    private void swap(int[] row, int[] pos, int x, int y) {
        // swap row
        int temp = row[x];
        row[x] = row[y];
        row[y] = temp;
        // swap pos
        int temp2 = pos[row[x]];
        pos[row[x]] = pos[row[y]];
        pos[row[y]] = temp2;
    }
}

/*
    核心思想是用一个root数组，每个点开始初始化为不同的值，如果两个点属于相同的组，就将其中一个点的root值赋值为另一个点的位置，这样只要是相同组里的两点，通过find函数会得到相同的值。

    那么如果总共有n个数字，则共有 n/2 对儿，所以我们初始化 n/2 个群组，我们还是每次处理两个数字。每个数字除以2就是其群组号，那么属于同一组的两个数的群组号是相同的，比如2和3，其分别除以2均得到1，所以其组号均为1。

    那么这对解题有啥作用呢？作用忒大了，由于我们每次取的是两个数，且计算其群组号，并调用find函数，那么如果这两个数的群组号相同，那么find函数必然会返回同样的值，我们不用做什么额外动作，因为本身就是一对儿。

    如果两个数不是一对儿，那么其群组号必然不同，在二者没有归为一组之前，调用find函数返回的值就不同，此时我们将二者归为一组，并且count自减1，忘说了，count初始化为总群组数，即 n/2。那么最终count减少的个数就是交换的步数


    [3   1   4   0   2   5]

    最开始的群组关系是：

    群组0：0，1

    群组1：2，3

    群组2：4，5

    取出前两个数字3和1，其群组号分别为1和0，带入find函数返回不同值，则此时将群组0和群组1链接起来，变成一个群组，则此时只有两个群组了，count自减1，变为了2。

    群组0 & 1：0，1，2，3

    群组2：4，5

    此时取出4和0，其群组号分别为2和0，带入find函数返回不同值，则此时将群组0&1和群组2链接起来，变成一个超大群组，count自减1，变为了1。

    群组0 & 1 & 2：0，1，2，3，4，5

    此时取出最后两个数2和5，其群组号分别为1和2，因为此时都是一个大组内的了，带入find函数返回相同的值，不做任何处理。最终交换的步数就是count减少值，为2
 */
//Solution3: Union Find
class Solution {
    private class UF {
        private int[] parents;
        public int count;
        UF(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
            count = n;
        }

        private int find(int i) {
            if (parents[i] == i) {
                return i;
            }
            parents[i] = find(parents[i]);
            return parents[i];
        }

        public void union(int i, int j) {
            int a = find(i);
            int b = find(j);
            if (a != b) {
                parents[a] = b;
                count--;
            }
        }
    }
    public int minSwapsCouples(int[] row) {
        int N = row.length/ 2;
        UF uf = new UF(N);
        for (int i = 0; i < N; i++) {
            int a = row[2 * i];
            int b = row[2 * i + 1];
            uf.union(a / 2, b / 2);
        }
        return N - uf.count;
    }
}