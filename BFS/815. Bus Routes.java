/*
    815. Bus Routes

    We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever. For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 1->5->7->1->5->7->1->... forever.

    We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by buses only, what is the least number of buses we must take to reach our destination? Return -1 if it is not possible.

    Example:
    Input:
    routes = [[1, 2, 7], [3, 6, 7]]
    S = 1
    T = 6
    Output: 2
    Explanation:
    The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
    Note:

    1 <= routes.length <= 500.
    1 <= routes[i].length <= 500.
    0 <= routes[i][j] < 10 ^ 6.

    Companies: Google

    Related Topics: BFS
 */

// Solution: BFS
// 注意这里求的是所需要乘坐的最少bus数 而不是最少站数，构建graph，key为station，value为经过这个station的bus集合，
// 对起点station开始做bfs，如果能到达终点T，经过的bus数即为最少所需。
class Solution {
    public int numBusesToDestination(int[][] routes, int S, int T) {
        if (S == T)
            return 0;
        HashSet<Integer> visited = new HashSet();
        HashMap<Integer, List<Integer>> graph = new HashMap();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                graph.putIfAbsent(routes[i][j], new ArrayList());
                graph.get(routes[i][j]).add(i); // key: routes[i][j]是车站， value: 经过这个站的bus的集合
            }
        }
        Queue<Integer> queue = new ArrayDeque();
        queue.offer(S);
        int res = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int station = queue.poll();
                for (int bus : graph.get(station)) {
                    if (visited.add(bus)) {
                        for (int i = 0; i < routes[bus].length; i++) { //可以再建一个map，key为bus，value为经过的站点，空间换时间
                            if (routes[bus][i] == T)
                                return res;
                            else
                                queue.offer(routes[bus][i]);
                        }
                    }
                }
            }
            res++;
        }
        return -1;
    }
}