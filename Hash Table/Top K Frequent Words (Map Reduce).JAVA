/*
    LintCode: 549. Top K Frequent Words (Map Reduce)

    Find top k frequent words with map reduce framework.

    The mapper's key is the document id, value is the content of the document, words in a document are split by spaces.

    For reducer, the output should be at most k key-value pairs, which are the top k words and their frequencies in this reducer.
    The judge will take care about how to merge different reducers' results to get the global top k frequent words, so you don't need to care about that part.

    The k is given in the constructor of TopK class.

     Notice
    For the words with same frequency, rank them with alphabet.

    Have you met this question in a real interview?
    Example
    Given document A =

    lintcode is the best online judge
    I love lintcode
    and document B =

    lintcode is an online judge for coding interview
    you can test your code online at lintcode
    The top 2 words and their frequencies should be

    lintcode, 4
    online, 3
 */

/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 * Definition of Document:
 * class Document {
 *     public int id;
 *     public String content;
 * }
 */
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