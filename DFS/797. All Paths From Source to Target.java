/*
    Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in any order.

    The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for which the edge (i, j) exists.

    Example:
    Input: [[1,2], [3], [3], []]
    Output: [[0,1,3],[0,2,3]]
    Explanation: The graph looks like this:
    0--->1
    |    |
    v    v
    2--->3
    There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
    Note:

    The number of nodes in the graph will be in the range [2, 15].
    You can print different paths in any order, but you should keep the order of nodes inside one path.
 */

//Solution1: DFS my solution
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList();
        List<Integer> path = new ArrayList();
        path.add(0);
        helper(res, graph, 0, graph.length - 1, path);
        return res;
    }

    private void helper(List<List<Integer>> res, int[][] graph, int cur, int end, List<Integer> item) {
        if (cur == end) {
            res.add(new ArrayList(item));
            return;
        }

        for (int next : graph[cur]) {
            item.add(next);
            helper(res, graph, next, end, item);
            item.remove(item.size() - 1);
        }
    }
}


//Solution2: memorization
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        Map<Integer, List<List<Integer>>> map = new HashMap();
        return helper(graph, map, 0);
    }

    private List<List<Integer>> helper(int[][] graph, Map<Integer, List<List<Integer>>> map, int cur) {
        if (map.containsKey(cur))
            return map.get(cur);

        List<List<Integer>> res = new ArrayList();
        if (cur == graph.length - 1) {
            List<Integer> path = new ArrayList();
            path.add(cur);
            res.add(path);
        } else {
            for (int next : graph[cur]) {
                List<List<Integer>> subPaths = helper(graph, map, next);
                for (List<Integer> path : subPaths) {
                    List<Integer> newPath = new ArrayList(path);
                    newPath.add(0, cur);
                    res.add(newPath);
                }
            }
        }
        map.put(cur, res);
        return res;
    }
}