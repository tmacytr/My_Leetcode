/*
    743. Network Delay Time

    There are N network nodes, labelled 1 to N.

    Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

    Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.

    Note:
    N will be in the range [1, 100].
    K will be in the range [1, N].
    The length of times will be in the range [1, 6000].
    All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <= 100.

    Companies: Akuna Capital

    Related Topics: Heap, DFS, BFS, Graph
 */

//Solution1: DFS
// Time: O(N^N + ElogE), where E is the length of times. We can only fully visit each node up to N-1 times, one per each other node.
// Plus, we have to explore every edge and sort them. Sorting each small bucket of outgoing edges is bounded by sorting all of them,
// because of repeated use of the inequality xlog x + ylogy <= (x + y)log(x+y).
class Solution {
    Map<Integer, Integer> dist;
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge : times) {
            graph.putIfAbsent(edge[0], new ArrayList());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        for (int node : graph.keySet())
            Collections.sort(graph.get(node), (a, b) -> a[1] - b[1]);

        dist = new HashMap();

        for (int node = 1; node <= N; node++)
            dist.put(node, Integer.MAX_VALUE);
        dfs(graph, K, 0);

        int res = 0;
        for (int candidate : dist.values()) {
            if (candidate == Integer.MAX_VALUE)
                return -1;
            res = Math.max(res, candidate);
        }
        return res;
    }

    private void dfs(Map<Integer, List<int[]>> graph, int node, int elapsed) {
        if (elapsed >= dist.get(node))
            return;
        dist.put(node, elapsed);
        if (graph.containsKey(node)) {
            for (int[] info : graph.get(node)) {
                dfs(graph, info[0], elapsed + info[1]);
            }
        }
    }
}

//Solution2: use Dijkstra's algorithm to find the shortest path from our source to all targets
// Dijkstra's algorithm is based on repeatedly making the candidate move that has the least distance travelled.
// Time Complexity: O(N^2 + E) where E is the length of times in the basic implementation, and O(ElogE) in the heap implementation, as potentially every edge gets added to the heap.
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge : times) {
            graph.putIfAbsent(edge[0], new ArrayList());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{K, 0});
        Map<Integer, Integer> distMap = new HashMap();

        while (!pq.isEmpty()) {
            int[] info = pq.poll();
            int dist = info[1], node = info[0];
            if (distMap.containsKey(node))
                continue;
            distMap.put(node, dist);
            if (graph.containsKey(node)) {
                for (int[] edge : graph.get(node)) {
                    int nei = edge[0];
                    int neiDist = edge[1];
                    if (!distMap.containsKey(nei))
                        pq.offer(new int[]{nei, dist + neiDist});
                }
            }
        }

        if (distMap.size() != N)
            return -1;
        int res = 0;
        for (int candidate : distMap.values()) {
            res = Math.max(res, candidate);
        }
        return res;
    }
}