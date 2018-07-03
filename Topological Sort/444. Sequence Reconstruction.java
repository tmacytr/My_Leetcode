/*
    Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.

    Example 1:

    Input:
    org: [1,2,3], seqs: [[1,2],[1,3]]

    Output:
    false

    Explanation:
    [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
    Example 2:

    Input:
    org: [1,2,3], seqs: [[1,2]]

    Output:
    false

    Explanation:
    The reconstructed sequence can only be [1,2].
    Example 3:

    Input:
    org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

    Output:
    true

    Explanation:
    The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
    Example 4:

    Input:
    org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]

    Output:
    true
    UPDATE (2017/1/8):
    The seqs parameter had been changed to a list of list of strings (instead of a 2d array of strings). Please reload the code definition to get the latest changes.

    Companies: Google
    Related Topics: Graph, Topological Sort
    Similar Questions: Course Schedule II
 */

//Solution1: Topological sort
class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap(); //建邻接表
        HashMap<Integer, Integer> indegree = new HashMap(); //构建入度map
        for (List<Integer> seq : seqs) {
            for (int i = 0; i < seq.size(); i++) {
                graph.putIfAbsent(seq.get(i), new ArrayList());
                indegree.putIfAbsent(seq.get(i), 0);
                if (i > 0) {
                    graph.get(seq.get(i - 1)).add(seq.get(i));
                    indegree.put(seq.get(i), indegree.getOrDefault(seq.get(i), 0) + 1);
                }
            }
        }

        if (org.length != indegree.size())
            return false;

        Queue<Integer> queue = new ArrayDeque();

        for (int vertex : indegree.keySet()) {
            if (indegree.get(vertex) == 0)
                queue.offer(vertex);
        }
        int index = 0;
        while (!queue.isEmpty()) {
            if (queue.size() > 1 || org[index++] != queue.peek()) // 1. queue中的元素不能超过1个，因为每次只能有一个顶点的入度为0，否则就会有multiple answer
                return false;                                     // 2. 用index来track遍历的点是否exactly满足顺序。
            int vertex = queue.poll();
            for (int neighbor : graph.get(vertex)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0)
                    queue.offer(neighbor);
            }
        }
        return index == org.length;
    }
}