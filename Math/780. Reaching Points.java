/*
    780. Reaching Points

    A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).

    Given a starting point (sx, sy) and a target point (tx, ty), return True if and only if a sequence of moves exists to transform the point (sx, sy) to (tx, ty). Otherwise, return False.

    Examples:
    Input: sx = 1, sy = 1, tx = 3, ty = 5
    Output: True
    Explanation:
    One series of moves that transforms the starting point to the target is:
    (1, 1) -> (1, 2)
    (1, 2) -> (3, 2)
    (3, 2) -> (3, 5)

    Input: sx = 1, sy = 1, tx = 2, ty = 2
    Output: False

    Input: sx = 1, sy = 1, tx = 1, ty = 1
    Output: True

    Note:

    sx, sy, tx, ty will all be integers in the range [1, 10^9].

    Companies: Google, Coursera
    Tags: Math
 */

//Solution1: Recursive, O(2^(tx + ty)), space: O(tx * ty)
class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if (sx > tx || sy > ty)
            return false;
        if (sx == tx && sy == ty)
            return true;
        return reachingPoints(sx + sy, sy, tx, ty) || reachingPoints(sx, sx + sy, tx, ty);
    }
}

//Solution2: backword, 从terminal开始往前
class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx >= sx && ty >= sy) {
            if (sx == tx && sy == ty)
                return true;
            if (tx > ty)
                tx -= ty;
            else
                ty -= tx;
        }
        return false;
    }
}

//Solution3:
class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx >= sx && ty >= sy) {
            if (tx > ty) {
                if (sy == ty)
                    return (tx - sx) % ty == 0;
                tx %= ty; //ax = tx-ty. Now we can optimize this subtraction a bit by doing ax = tx % ty since we will keep subtracting ty from tx until tx > ty.
            } else {
                if (sx == tx)
                    return (ty - sy) % tx == 0;
                ty %= tx;
            }
        }
        return false;
    }
}