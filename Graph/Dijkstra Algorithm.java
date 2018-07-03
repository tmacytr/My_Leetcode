/*
    Dijkstra's Algorithm is a very famous graph algorithm, which is used to find the shortest path from any startstart node to any destinationdestination node in the given weighted graph(the edges of the graph represent the distance between the nodes).

    The algorithm consists of the following steps:

    1. Assign a tentative distance value to every node: set it to zero for our startstart node and to infinity for all other nodes.

    2. Set the startstart node as currentcurrent node. Mark it as visited.

    3. For the currentcurrent node, consider all of its neighbors and calculate their tentative distances.
       Compare the newly calculated tentative distance to the current assigned value and assign the smaller one to all the neighbors.
       For example, if the current node A is marked with a distance of 6, and the edge connecting it with a neighbor B has length 2,
       then the distance to B (through A) will be 6 + 2 = 8. If B was previously marked with a distance greater than 8 then change it to 8. Otherwise, keep the current value.

    4. When we are done considering all of the neighbors of the current node, mark the currentcurrent node as visited. A visited node will never be checked again.

    5. If the destinationdestination node has been marked visited or if the smallest tentative distance among all the nodes left is infinity(indicating that the destinationdestination can't be reached), then stop. The algorithm has finished.

    6. Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new currentcurrent node, and go back to step 3.
 */

https://leetcode.com/problems/the-maze-ii/solution/
