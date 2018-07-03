/*
    399. Evaluate Division

    Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

    Example:
    Given a / b = 2.0, b / c = 3.0.
    queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
    return [6.0, 0.5, -1.0, 1.0, -1.0 ].

    The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

    According to the example above:

    equations = [ ["a", "b"], ["b", "c"] ],
    values = [2.0, 3.0],
    queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
    The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 */

// Solution1: DFS, Prefer
// 把字符之间的关系放到map中，比如 a / b = 2.0, 则有a = 2b :  a -> (b, 2.0)   反过来则有 b = 1/2a : b -> (a, 1/2), 用dfs遍历连接两点的所有邻居
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] res = new double[queries.length];
        if (equations == null || equations.length == 0 || values == null || values.length == 0 || queries == null || queries.length == 0)
            return res;
        HashMap<String, HashMap<String, Double>> map = new HashMap();

        int len = equations.length;
        for (int i = 0; i < len; i++) {
            String first = equations[i][0];
            String second = equations[i][1];
            if (map.get(first) == null)
                map.put(first, new HashMap());
            if (map.get(second) == null)
                map.put(second, new HashMap());
            map.get(first).put(second, values[i]);
            map.get(second).put(first, 1.0 / values[i]);
        }

        for (int i = 0; i < queries.length; i++) {
            res[i] = dfs(queries[i][0], queries[i][1], map, new HashSet<String>(), 1.0);
        }
        return res;
    }

    private double dfs(String start, String end, HashMap<String, HashMap<String, Double>> map, HashSet<String> set, double value) {
        if (set.contains(start) || !map.containsKey(start))
            return -1.0;
        if (start.equals(end))
            return value;
        set.add(start);
        double res = -1.0;
        for (String s : map.keySet()) {
            if (map.get(start).containsKey(s)) {
                res = dfs(s, end, map, set, value * map.get(start).get(s));
            }
            if (res != -1.0)
                break;
        }

        set.remove(start);
        return res;
    }
}


// Solution2: Union Find
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] res = new double[queries.length];
        if (equations == null || equations.length == 0 || values == null || values.length == 0 || queries == null || queries.length == 0)
            return res;
        HashMap<String, String> rootMap = new HashMap();
        HashMap<String, Double> valueMap = new HashMap();

        for (int i = 0; i < equations.length; i++) {
            String first = equations[i][0];
            String second = equations[i][1];

            rootMap.putIfAbsent(first, first);
            rootMap.putIfAbsent(second, second);
            valueMap.putIfAbsent(first, 1.0);
            valueMap.putIfAbsent(second, 1.0);

            String root1 = find(rootMap, first);
            String root2 = find(rootMap, second);

            rootMap.put(root2, root1);
            valueMap.put(root2, valueMap.get(first) * values[i] / valueMap.get(second));
        }

        for (int i = 0; i < queries.length; i++) {
            res[i] = -1.0;
            String first = queries[i][0];
            String second = queries[i][1];

            if (!rootMap.containsKey(first) || !rootMap.containsKey(second))
                continue;
            String root1 = find(rootMap, first);
            String root2 = find(rootMap, second);

            if (root1.equals(root2))
                res[i] = get(rootMap, valueMap, second) / get(rootMap, valueMap, first);
        }

        return res;
    }

    private String find(Map<String, String> rootMap, String target) {
        if (rootMap.get(target).equals(target))
            return target;
        return find(rootMap, rootMap.get(target));
    }

    private double get(Map<String, String> rootMap, Map<String, Double> valueMap, String target) {
        String root = rootMap.get(target);
        double res = valueMap.get(target);

        if (root.equals(target))
            return res;
        return res * get(rootMap, valueMap, root);
    }
}