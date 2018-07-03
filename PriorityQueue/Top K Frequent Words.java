/*
	692. Top K Frequent Words

	Given a non-empty list of words, return the k most frequent elements.

	Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.

	Example 1:
	Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
	Output: ["i", "love"]
	Explanation: "i" and "love" are the two most frequent words.
	    Note that "i" comes before "love" due to a lower alphabetical order.
	Example 2:
	Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
	Output: ["the", "is", "sunny", "day"]
	Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
	    with the number of occurrence being 4, 3, 2 and 1 respectively.
	Note:
	You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
	Input words contain only lowercase letters.
	Follow up:
	Try to solve it in O(n log k) time and O(n) extra space.
*/


//Solution1: prefer
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new ArrayList();
        if (words == null || words.length == 0)
            return res;
        HashMap<String, Integer> map = new HashMap();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        
        // In order to achieve O(nlogk) we need to use min heap instead of max heap.
        PriorityQueue<String> pq = new PriorityQueue<String>(
            (a, b) -> map.get(a) == map.get(b) ? b.compareTo(a) : map.get(a) - map.get(b)
        );
        
        for (String word: map.keySet()) {
            pq.offer(word);
            if (pq.size() > k)
                pq.poll();
        }
        
        while (!pq.isEmpty())
            res.add(pq.poll());
        Collections.reverse(res);
        
        return res;
    }
}

//Solution2: PriorityQueue use Map.Entry as type
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new ArrayList();
        if (words == null || words.length == 0)
            return res;
        HashMap<String, Integer> map = new HashMap();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        
        // In order to achieve O(nlogk) we need to use min heap instead of max heap.
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            (a, b) -> a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue()
        );
        
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k)
                pq.poll();
        }
        
        while (!pq.isEmpty())
            res.add(0, pq.poll().getKey());
        
        return res;
    }
}

// Follow-up: What if we have tons of list words? ---> use Map Reduce
public class TopKFrequentWords {
    public static class Map {
        public void map(String _, Document value,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
            String content = value.content;
            String[] words = content.split(" ");
            for (String word : words) {
                if (word.length() > 0) {
                    output.collect(word, 1);
                }
            }
        }
    }

    public static class Reduce {
        private PriorityQueue<Pair> pq;
        private int k;

        public void setup(int k) {
            // initialize your data structure here
            this.k = k;
            pq = new PriorityQueue<Pair>(
                    (a, b) -> a.value != b.value ? a.value - b.value : b.key.compareTo(a.key)
            );
        }

        public void reduce(String key, Iterator<Integer> values) {
            // Write your code here
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next();
            }
            Pair pair = new Pair(key, sum);
            if (pq.size() < k)
                pq.add(pair);
            else {
                Pair peak = pq.peek();
                if (pair.value > peak.value || (pair.value == peak.value && pair.key.compareTo(peak.key) < 0)) {
                    pq.poll();
                    pq.add(pair);
                }
            }
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            // Output the top k pairs <word, times> into output buffer.
            // Ps. output.collect(String key, Integer value);
            List<Pair> pairs = new ArrayList();
            while (!pq.isEmpty()) {
                pairs.add(pq.poll());
            }
            int n = pairs.size();
            for (int i = n - 1; i >= 0; i--) {
                Pair pair = pairs.get(i);
                output.collect(pair.key, pair.value);
            }
        }
    }
}

class Pair {
    String key;
    int value;

    Pair(String key, int value) {
        this.key = key;
        this.value = value;
    }
}