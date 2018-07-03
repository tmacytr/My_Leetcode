/*
    Fenwick tree
    https://www.hrwhisper.me/binary-indexed-tree-fenwick-tree/
    它又叫 Binary indexed tree ，也叫树状数组。

    能在log(n)查询区间和，并且在log(n)时间内进行结点更新操作。

    lowbit(x)函数: lowbit(x) = x & (-x)
        定义lowbit(x)就是x的二进制表达式中最右边的1所对应的值。

        比如，1234的二进制是0100 1101 0010  lowbit(1234)=10 = 2，在程序的实现中，Lowbit(x)=x&-x;（为什么这样写呢？因为计算机内部采用补码表示，-x是x按位取反，尾数+1的结果）
 */

class Solution {

    private int[] sums;

    public void init(int n) {
        sums = new int[n + 1]; // 如果有n个元素必须初始化长度为n + 1, 因为BIT tree的下标从1开始
    }

    public int search(int i) { // 求前i个元素的和
        int res = 0;
        while (i > 0) {
            res += sums[i];
            i -= lowbit(i);
        }
        return res;
    }

    public void update(int i, int val) { // O(logn) update是更新第i个元素的值
        while (i < sums.length) {
            sums[i] += val;
            i += lowbit(i);
        }
    }

    int lowbit(i) {
        return i & (-i);
    }
}