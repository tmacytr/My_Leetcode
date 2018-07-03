/*
    421. Maximum XOR of Two Numbers in an Array

    Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.

    Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.

    Could you do this in O(n) runtime?

    Example:

    Input: [3, 10, 5, 25, 2, 8]

    Output: 28

    Explanation: The maximum result is 5 ^ 25 = 28.

    Companies: Google
    Related Topics: Bit Manipulation, Trie
 */

//Solution1: Brute force
class Solution {
    public int findMaximumXOR(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                res = Math.max(res, nums[i] ^ nums[j]);
            }
        }
        return res;
    }
}

/*
    讲解1: 利用XOR的性质，a^b = c, 则有 a^c = b，且 b^c = a;
        所以每次从高位开始，到某一位为止，所能获得的最大的数。设置变量mask用来表示能形成的值，每一次将mask和其他的num相与得到的值加入set，表示在当前这一位上，数组里有这么多prefix。
        假定在某一位上的任意两数xor能得到的最大值是tmp,那么他一定满足a(xor)b = tmp,其中set.contains(a) && set.contains(b).
        所以，我们只需要判断b(xor)tmp的结果是不是在当前这一位下的set内，就可以知道这个tmp能不能又这个set中的任意两个数组成。

    讲解2: 按位遍历，题目中给定了数字的返回不会超过231,那么最多只能有32位，我们用一个从左往右的mask，用来提取数字的前缀，然后将其都存入set中，
        我们用一个变量temp，用来验证当前位为1再或上之前结果res，看结果和set中的前缀异或之后在不在set中，
        这里用到了一个性质，若a^b=c，那么a=b^c，因为t是我们要验证的当前最大值，所以我们遍历set中的数时，和t异或后的结果仍在set中，说明两个前缀可以异或出t的值，所以我们更新res为t，继续遍历
 */
//Solution2: Bits: O(32n),
class Solution {
    public int findMaximumXOR(int[] nums) {
        int res = 0;
        int mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num & mask); // reserve the most left bits and ignore right bits
            }

            // 假设当前所能达到的最大值是这个temp值；
            int temp = res | (1 << i);
            for (Integer prefix : set) {
                if (set.contains(prefix ^ temp)) {
                    // 如果能组成就直接break
                    res = temp;
                    break;
                }
            }
        }
        return res;
    }
}

/*
    讲解:
        遍历数组，对每个数的二进制构建一棵字典树。
        再次遍历数组，对每个数的每个二进制位与1异或：数组元素的二进制位为0，则寻找到值为1的子树，数组元素的二进制位为1，则寻找到值为0的子树。(当然也可以与0异或)

        与1异或后子树不为空，则可计数并累加；与1异或后子树为空，则继续向下查找。

    重点:
        对数组元素的二进制构建Trie树。
        寻找子树的时候，与1(或0)异或。
 */
//Solution3: Trie
class Solution {
    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        Trie root = new Trie();

        for (int num : nums) {
            Trie cur = root;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >>> i) & 1;
                if (cur.children[curBit] == null)
                    cur.children[curBit] = new Trie();
                cur = cur.children[curBit];
            }
        }

        int res = Integer.MIN_VALUE;

        for (int num : nums) {
            Trie cur = root;
            int curSum = 0;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >>> i) & 1;
                if (cur.children[curBit ^ 1] != null) {
                    curSum += (1 << i);
                    cur = cur.children[curBit ^ 1];
                } else {
                    cur = cur.children[curBit];
                }

                // for this case: even if all left bits results are 1s, curSum still cannot catch up max value
                if (curSum < res && res - curSum >= (1 << i) - 1) {
                    break;
                }
            }
            res = Math.max(res, curSum);
        }
        return res;
    }

    class Trie {
        Trie[] children;
        public Trie() {
            children = new Trie[2];
        }
    }
}