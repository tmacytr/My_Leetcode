/*
    451. Sort Characters By Frequency
    Given a string, sort it in decreasing order based on the frequency of characters.

    Example 1:

    Input:
    "tree"

    Output:
    "eert"

    Explanation:
    'e' appears twice while 'r' and 't' both appear once.
    So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
    Example 2:

    Input:
    "cccaaa"

    Output:
    "cccaaa"

    Explanation:
    Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
    Note that "cacaca" is incorrect, as the same characters must be together.
    Example 3:

    Input:
    "Aabb"

    Output:
    "bbAa"

    Explanation:
    "bbaA" is also a valid answer, but "Aabb" is incorrect.
    Note that 'A' and 'a' are treated as two different characters.

    Companies: Google, Amazon

    Related Topics: Hashtable, heap

    Similar Questions : Top K Frequent Elements, First Unique Character in a String
 */

//Solution1: Hashmap + heap, O(nlogn)
class Solution {
    public String frequencySort(String s) {
        if (s == null || s.length() == 0)
            return s;
        Map<Character, Integer> map = new HashMap();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue()
        );

        pq.addAll(map.entrySet());
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            for (int i = 0; i < pq.peek().getValue(); i++)
                sb.append(pq.peek().getKey());
            pq.poll();
        }
        return sb.toString();
    }
}

//Solution2: bucket sort, O(n)，构建List 数组，以字符出现的次数为index，把同count的字符放一起。最后遍历bucket和map 组成res
class Solution {
    public String frequencySort(String s) {
        if (s == null || s.length() == 0)
            return s;
        Map<Character, Integer> map = new HashMap();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Character>[] bucket = new List[s.length() + 1];
        for (char key : map.keySet()) {
            int count = map.get(key);
            if (bucket[count] == null)
                bucket[count] = new ArrayList();
            bucket[count].add(key);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = bucket.length - 1; i >= 0; i--) {
            if (bucket[i] != null) {
                for (char c : bucket[i]) {
                    for (int j = 0; j < map.get(c); j++) {
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }
}