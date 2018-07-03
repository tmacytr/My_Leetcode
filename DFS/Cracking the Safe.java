/*
    753. Cracking the Safe

    There is a box protected by a password. The password is n digits, where each letter can be one of the first k digits 0, 1, ..., k-1.

    You can keep inputting the password, the password will automatically be matched against the last n digits entered.

    For example, assuming the password is "345", I can open it when I type "012345", but I enter a total of 6 digits.

    Please return any string of minimum length that is guaranteed to open the box after the entire string is inputted.

    Example 1:
    Input: n = 1, k = 2
    Output: "01"
    Note: "10" will be accepted too.
    Example 2:
    Input: n = 2, k = 2
    Output: "00110"
    Note: "01100", "10011", "11001" will be accepted too.
    Note:
    n will be in the range [1, 4].
    k will be in the range [1, 10].
    k^n will be at most 4096.
 */

// NP hard问题，用brute force.
/*
    密码共有n位，每一个位可以有k个数字，那么总共不同的密码总数就有k的n次方个。我们的思路是先从n位都是0的密码开始，取出钥匙串的最后n个数字，然后将最后一个数字依次换成其他数字，我们用一个HashSet来记录所有遍历过的密码，
    这样如果不在集合中，说明是一个新密码，而生成这个新密码也只是多加了一个数字，这样能保证我们的钥匙串最短，这是一种贪婪的解法
 */

//Solution1: dfs
class Solution {
    public String crackSafe(int n, int k) {
        StringBuilder sb = new StringBuilder();
        // 长度为n的密码序列，有k的n次方种密码
        int total = (int)Math.pow(k, n);

        // 初始化长度为n的密码序列
        for (int i = 0; i < n; i++)
            sb.append('0');
        Set<String> visited = new HashSet();
        visited.add(sb.toString());

        dfs(sb, total, visited, n, k);
        return sb.toString();
    }

    public boolean dfs(StringBuilder sb, int total, Set<String> visited, int n, int k) {
        if (visited.size() == total)
            return true;
        // 用之前后面的 n - 1位 + 当前k个数组里的其中一个组成新的string
        // 两个长度为n的密码最好能共享n-1个数字，这样累加出来的钥匙串肯定是最短的。
        String pre = sb.substring(sb.length() - (n - 1), sb.length());
        for (int i = 0; i < k; i++) {
            String next = pre + i;
            if (!visited.contains(next)) {
                visited.add(next);
                sb.append(i);
                if (dfs(sb, total, visited, n, k)) {
                    return true;
                }
                visited.remove(next);
                sb.delete(sb.length() - 1, sb.length());
            }
        }
        return false;
    }
}