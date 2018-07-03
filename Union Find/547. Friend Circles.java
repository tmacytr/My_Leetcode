/*
    547. Friend Circles

    There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

    Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.

    Example 1:
    Input:
    [[1,1,0],
     [1,1,0],
     [0,0,1]]
    Output: 2
    Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
    The 2nd student himself is in a friend circle. So return 2.
    Example 2:
    Input:
    [[1,1,0],
     [1,1,1],
     [0,1,1]]
    Output: 1
    Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
    so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
    Note:
    N is in range [1,200].
    M[i][i] = 1 for all students.
    If M[i][j] = 1, then M[j][i] = 1.

    Companies; Bloomberg, Twosigma

    Related Topics: DFS, Union Find

    Similar Questions:
        1. Number of Connected Components in an Undirected Graph
        2. Judge Route Circle
        3. Sentence Similarity
        4. Sentence Similarity II
 */

//Solution1: Union Find 1
class Solution {
    public int findCircleNum(int[][] M) {
        int n = M.length;
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 1 && i != j)
                    union(parent, i, j);
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1)
                count++;
        }
        return count;
    }

    private void union(int[] parent, int x, int y) {
        int xSet = find(parent, x);
        int ySet = find(parent, y);
        if (xSet != ySet)
            parent[xSet] = ySet;
    }


    private int find(int[] parent, int i) {
        if (parent[i] == i)
            return i;
        parent[i] = find(parent, parent[i]);
        return parent[i];
    }
}

//Solution2: Union Find 2
class Solution {
    public int findCircleNum(int[][] M) {
        int n = M.length;
        int[] parent = new int[n];
        for (int i = 0; i < parent.length; i++)
            parent[i] = i;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 1 && i != j) {
                    int xSet = find(parent, i);
                    int ySet = find(parent, j);
                    if (xSet != ySet)
                        parent[xSet] = ySet;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i)
                count++;
        }
        return count;
    }

    private int find(int[] parent, int i) {
        if (parent[i] == i)
            return i;
        parent[i] = find(parent, parent[i]); //路径压缩
        return parent[i];
    }
}

//Solution3: dfs
class Solution {
    public int findCircleNum(int[][] M) {
        boolean[] visited = new boolean[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(int[][] M, boolean[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(M, visited, j);
            }
        }
    }
}