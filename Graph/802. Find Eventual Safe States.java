/*
    802. Find Eventual Safe States

    In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.

    Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.
    More specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a terminal node in less than K steps.

    Which nodes are eventually safe?  Return them as an array in sorted order.

    The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.

    Example:
    Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
    Output: [2,4,5,6]
    Here is a diagram of the above graph.

    Illustration of graph

    Note:

    graph will have length at most 10000.
    The number of edges in the graph will not exceed 32000.
    Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 */

/*
    The crux of the problem is whether you can reach a cycle from the node you start in.

    If you can, then there isn't a way to avoid stopping indefinitely;

    and if you can't, then after some finite number of steps you'll stop.
*/


//Solution1: BFS, 构建入度以及出度graph，每次将出度为0的graph加入到res中。
// 为什么需要一个入度矩阵？因为每当我们找到一个出度为0的node时，我们需要知道有哪些点连接这个点，好在graph中去掉连接这个点的边。
// 如果我们没有入度矩阵，则必须在出度矩阵中遍历一遍找到哪些点与其相连，时间效率上大打折扣。
class Solution {
    public List<Integer> eventualSafeNodes(int[][] G) {
        List<Integer> res = new ArrayList();
        if (G == null || G.length == 0)
            return res;
        Map<Integer, Set<Integer>> outDegreeGraph = new HashMap();
        Map<Integer, Set<Integer>> inDegreeGraph = new HashMap();

        for (int i = 0; i < G.length; i++) {
            outDegreeGraph.put(i, new HashSet());
            inDegreeGraph.put(i, new HashSet());
        }
        Queue<Integer> queue = new ArrayDeque();
        for (int i = 0; i < G.length; i++) {
            if (G[i].length == 0)
                queue.offer(i);
            for (int j : G[i]) {
                inDegreeGraph.get(j).add(i);
                outDegreeGraph.get(i).add(j);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            res.add(node);
            for (int nei : inDegreeGraph.get(node)) {
                outDegreeGraph.get(nei).remove(node);
                if (outDegreeGraph.get(nei).size() == 0)
                    queue.offer(nei);
            }
        }
        Collections.sort(res);
        return res;
    }
}

/*
    We mark a node gray on entry, and black on exit. If we see a gray node during our DFS,

    it must be part of a cycle. In a naive view, we'll clear the colors between each search.
 */
//Solution2: 染色算法 DFS, 初始化每个点的颜色为0，第一次经过一个点时标1，遍历结束标2，但凡在遍历过程中遇到了1点，意味着重复经过一个点，有环不满足条件
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int[] color = new int[graph.length];
        List<Integer> res = new ArrayList();

        for (int i = 0; i < graph.length; i++) {
            if (dfs(i, color, graph))
                res.add(i);
        }
        return res;
    }

    // colors: WHITE 0, GRAY 1, BLACK 2;
    private boolean dfs(int node, int[] color, int[][] graph) {
        if (color[node] > 0)
            return color[node] == 2;
        color[node] = 1;
        for (int nei : graph[node]) {
            if (color[node] == 2)
                continue;
            if (color[nei] == 1 || !dfs(nei, color, graph))
                return false;
        }
        color[node] = 2;
        return true;
    }
}