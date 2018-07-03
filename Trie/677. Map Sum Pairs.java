/*
    677. Map Sum Pairs

    Implement a MapSum class with insert, and sum methods.

    For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer represents the value. If the key already existed, then the original key-value pair will be overridden to the new one.

    For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the pairs' value whose key starts with the prefix.

    Example 1:
    Input: insert("apple", 3), Output: Null
    Input: sum("ap"), Output: 3
    Input: insert("app", 2), Output: Null
    Input: sum("ap"), Output: 5

    Companies: Akuna Capital

    Related Topics: Trie
 */

//Solution1: my solution trie + hashmap(count)
// Insert: O(K), Sum: O(K)
class MapSum {
    TrieNode root;
    Map<String, Integer> map;
    /** Initialize your data structure here. */
    public MapSum() {
        root = new TrieNode();
        map = new HashMap();
    }

    public void insert(String key, int val) {
        TrieNode cur = root;
        int diff = val - map.getOrDefault(key, 0);
        for (char c : key.toCharArray()) {
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur.children[c - 'a'].count += diff;
            cur = cur.children[c - 'a'];
        }
        map.put(key, val);
    }

    public int sum(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children[ c - 'a'] == null)
                return 0;
            else
                cur = cur.children[c - 'a'];
        }
        return cur.count;
    }
}

class TrieNode {
    public TrieNode[] children;
    public int count;
    public TrieNode() {
        children = new TrieNode[26];
        count = 0;
    }
}

//Solution2: use hashmap instead of array to init TrieNode
class MapSum {
    TrieNode root;
    Map<String, Integer> map;
    /** Initialize your data structure here. */
    public MapSum() {
        root = new TrieNode();
        map = new HashMap();
    }

    public void insert(String key, int val) {
        TrieNode cur = root;
        int diff = val - map.getOrDefault(key, 0);
        for (char c : key.toCharArray()) {
            cur.children.putIfAbsent(c, new TrieNode());
            cur.children.get(c).count += diff;
            cur = cur.children.get(c);
        }
        map.put(key, val);
    }

    public int sum(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children.get(c) == null)
                return 0;
            else
                cur = cur.children.get(c);
        }
        return cur.count;
    }
}

class TrieNode {
    Map<Character, TrieNode> children; Djikstra
    int count;
    public TrieNode() {
        children = new HashMap();
        count = 0;
    }
}


//Solution3: two hashmap, one for prefix the other is count map
// Insert: O(K^2(, Sum: O(1), 缺点在于需要太多的空间
class MapSum {
    Map<String, Integer> map;
    Map<String, Integer> count;
    /** Initialize your data structure here. */
    public MapSum() {
        map = new HashMap();
        count = new HashMap();
    }

    public void insert(String key, int val) {
        int diff = val - map.getOrDefault(key, 0);
        map.put(key, val);
        String prefix = "";
        for (char c : key.toCharArray()) {
            prefix += c;
            count.put(prefix, count.getOrDefault(prefix, 0) + diff);
        }
    }

    public int sum(String prefix) {
        return count.getOrDefault(prefix, 0);
    }
}