/*
    56. Pyramid Transition Matrix

    We are stacking blocks to form a pyramid. Each block has a color which is a one letter string, like `'Z'`.

    For every block of color `C` we place not in the bottom row, we are placing it on top of a left block of color `A` and right block of color `B`. We are allowed to place the block there only if `(A, B, C)` is an allowed triple.

    We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples allowed. Each allowed triple is represented as a string of length 3.

    Return true if we can build the pyramid all the way to the top, otherwise false.

    Example 1:
    Input: bottom = "XYZ", allowed = ["XYD", "YZE", "DEA", "FFF"]
    Output: true
    Explanation:
    We can stack the pyramid like this:
        A
       / \
      D   E
     / \ / \
    X   Y   Z

    This works because ('X', 'Y', 'D'), ('Y', 'Z', 'E'), and ('D', 'E', 'A') are allowed triples.
    Example 2:
    Input: bottom = "XXYX", allowed = ["XXX", "XXY", "XYX", "XYY", "YXZ"]
    Output: false
    Explanation:
    We can't stack the pyramid to the top.
    Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
    Note:
    bottom will be a string with length in range [2, 8].
    allowed will have length in range [0, 200].
    Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.

    Companies: Google

    Related Topics: Bit, DFS
 */
/*
     构建一个HashMap, key为allowed数组的每个字符串的前两位， value位一个set 包含所有可以和这俩characters组成的pyramd的char，
     递归终止条件:
        1.如果当前行有且只有一个元素，意味着已经遍历到了顶层， 返回true
        2.如果下一行 + 1 等于当前行的长度，说明我们应该进行下一层循环
     如果不符合终止条件，则对当前的row的所有相邻的char 判断是否能够构建下一层pyramid

 */
//dfs:
class Solution {
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        HashMap<String, HashSet<Character>> map = new HashMap();
        for (String s : allowed) {
            String pre = s.substring(0, 2);
            map.putIfAbsent(pre, new HashSet());
            map.get(pre).add(s.charAt(2));
        }
        return helper(bottom, "", map, 1);
    }

    private boolean helper(String row, String nextRow, HashMap<String, HashSet<Character>> map, int start) {
        // 如果当前行有且只有一个元素，意味着已经遍历到了顶层
        if (row.length() == 1)
            return true;
        if (nextRow.length() + 1 == row.length())
            return helper(nextRow, "", map, 1);
        for (int i = start; i < row.length(); i++) {
            // row.substring(i - 1, i + 1) -> 要将相邻的 i - 1和i 组成的string去搜hashset是否有满足条件的字符
            for (Character c : map.getOrDefault(row.substring(i - 1, i + 1), new HashSet<>()))
                if (helper(row, nextRow + c, map, i + 1))
                    return true;
        }
        return false;
    }
}