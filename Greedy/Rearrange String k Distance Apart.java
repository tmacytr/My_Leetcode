/*
    358. Rearrange String k Distance Apart

    Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.

    All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".

    Example 1:
    s = "aabbcc", k = 3

    Result: "abcabc"

    The same letters are at least distance 3 from each other.
    Example 2:
    s = "aaabc", k = 3

    Answer: ""

    It is not possible to rearrange the string.
    Example 3:
    s = "aaadbbcc", k = 2

    Answer: "abacabcd"

    Another possible answer is: "abcabcda"

    The same letters are at least distance 2 from each other.
 */

// Solution: Heap + Gready + Queue
// Time complexity : O(n). Number of iterations will be equal to resultant time
/*
    需要一个哈希表来建立字符和其出现次数之间的映射，然后需要一个字符次数的最大堆堆来保存这每一堆映射，按照出现次数来排序。

    然后如果堆不为空我们就开始循环，找出k和s长度之间的较小值，因为最后会有剩余要插入的字符数比k小的情况，比如 aabbc, k = 2 -> (ab) (ab) c, 此时c这里只需要插入1个字符。

    然后从0遍历到这个较小值，对于每个遍历到的值，如果此时堆为空，说明此位置无法填入有效的字符，返回空字符串

    否则我们从堆顶取出一对映射，然后把字母加入结果res中，此时映射的个数减1，如果减1后的个数仍大于0，则我们将此映射加入临时队列queue中，同时需要插入的字符个数len减1，遍历完一次，我们把临时队列中的映射对加入堆中
 */
class Solution {
    class Turple {
        char letter;
        int count;
        public Turple(char letter, int count) {
            this.letter = letter;
            this.count = count;
        }
    }
    public String rearrangeString(String s, int k) {
        if (k == 0)
            return s;
        int total = s.length();
        HashMap<Character, Integer> map = new HashMap();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Turple> pq = new PriorityQueue<>(
            (a, b) -> a.count == b.count ? a.letter - b.letter : b.count - a.count // 不能仅仅比较count, 如果count相等letter要按照升序
        );

        for (char key : map.keySet()) {
            pq.offer(new Turple(key, map.get(key)));
        }

        Queue<Turple> queue = new ArrayDeque(); // 用队列来避免一个turple在距离k内重复取多次

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int n = Math.min(k, total); //
            for (int i = 0; i < n; i++) {
                if (pq.isEmpty()) // 如果没有合适的字符可以加入返回空
                    return "";
                Turple cur = pq.poll();
                sb.append(cur.letter);
                if (--cur.count > 0)
                    queue.offer(cur);
                --total; //successfully add a character
            }

            for (Turple turple : queue)
                pq.offer(turple);
            queue.clear(); // reset为了下一轮遍历
        }
        return sb.toString();
    }
}