/*
    685. Redundant Connection II

    In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents.

    The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N), with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.

    The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a directed edge connecting nodes u and v, where u is a parent of child v.

    Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple answers, return the answer that occurs last in the given 2D-array.

    Example 1:
    Input: [[1,2], [1,3], [2,3]]
    Output: [2,3]
    Explanation: The given directed graph will be like this:
      1
     / \
    v   v
    2-->3
    Example 2:
    Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
    Output: [4,1]
    Explanation: The given directed graph will be like this:
    5 <- 1 -> 2
         ^    |
         |    v
         4 <- 3
    Note:
    The size of the input 2D-array will be between 3 and 1000.
    Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.

    Companies: Google
    Related Topics: Tree, DFS, Union Find, Graph
    Similar Questions: Redundant Connection
 */


/*
    这道题简明题意就是去掉入度为2的点的一条边，有环的话去掉环

    有三种invalid cases： -- remove入度为2的点的其中一条线

    Case1：无环，但是有结点入度为2的结点（结点3）

        [[1,2], [1,3], [2,3]]

          1
         / \
        v   v
        2-->3


    Case2：有环，没有入度为2的结点 -- remove环

    [[1,2], [2,3], [3,4], [4,1], [1,5]]

    5 <- 1 -> 2
         ^    |
         |    v
         4 <- 3

   Case3：有环，且有入度为2的结点（结点1） -- remove 经过入度为2的结点成为环的线段
    [[1,2],[2,3],[3,1],[1,4]]

         4
        /
       v
       1
     /  ^
    v    \
    2 -->3

*/


//Solution1: Union Find
class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] can1 = {-1, -1};
        int[] can2 = {-1, -1};
        int[] parent = new int[edges.length + 1];

        // step 1, check whether there is a node with two parents
        for (int i = 0; i < edges.length; i++) {
            if (parent[edges[i][1]] == 0) {
                // 如果一个点的入度超过2，第二次check这个点时parent就不会为0
                parent[edges[i][1]] = edges[i][0];
            } else {
                can1 = new int[] {parent[edges[i][1]], edges[i][1]}; //第一条经过edges[i][1]的直线
                can2 = new int[] {edges[i][0], edges[i][1]}; //第二条经过 edges[i][1]的直线
                edges[i][1] = 0;
            }
        }

        // step 2, set parent for union find
        for (int i = 0; i < edges.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < edges.length; i++) {
            if (edges[i][1] == 0)
                continue;
            int child = edges[i][1];
            int father = edges[i][0];
            if (findRoot(parent, father) == child) {
                if (can1[0] == -1)
                    return edges[i]; // 有环存在，且有入度等于2的点 case 3
                return can1; // 有环存在，没有入度等于2的点 case 2
            }
            parent[child] = father;
        }
        return can2; //没有环存在，有入度等于2的点 case 1
    }

    private int findRoot(int[] parent, int root) {
        if (root != parent[root])
            parent[root] = findRoot(parent, parent[root]);
        return parent[root];
    }
}