/*
    816. Ambiguous Coordinates

    We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)".  Then, we removed all commas, decimal points, and spaces, and ended up with the string S.  Return a list of strings representing all possibilities for what our original coordinates could have been.

    Our original representation never had extraneous zeroes, so we never started with numbers like "00", "0.0", "0.00", "1.0", "001", "00.01", or any other number that can be represented with less digits.  Also, a decimal point within a number never occurs without at least one digit occuring before it, so we never started with numbers like ".1".

    The final answer list can be returned in any order.  Also note that all coordinates in the final answer have exactly one space between them (occurring after the comma.)

    Example 1:
    Input: "(123)"
    Output: ["(1, 23)", "(12, 3)", "(1.2, 3)", "(1, 2.3)"]
    Example 2:
    Input: "(00011)"
    Output:  ["(0.001, 1)", "(0, 0.011)"]
    Explanation:
    0.0, 00, 0001 or 00.01 are not allowed.
    Example 3:
    Input: "(0123)"
    Output: ["(0, 123)", "(0, 12.3)", "(0, 1.23)", "(0.1, 23)", "(0.1, 2.3)", "(0.12, 3)"]
    Example 4:
    Input: "(100)"
    Output: [(10, 0)]
    Explanation:
    1.0 is not allowed.


    Note:

    4 <= S.length <= 12.
    S[0] = "(", S[S.length - 1] = ")", and the other elements in S are digits.
 */

/*
    We can split S to two parts for two coordinates.
    Then we use sub function helper to find all possible strings for each coordinate.

    In sub functon helper(S)
    if S == “”: return []
    if S == “0”: return [S]
    if S == “0XXX0”: return []
    if S == “0XXX”: return [“0.XXX”]
    if S == “XXX0”: return [S]
    return [S, “X.XXX”, “XX.XX”, “XXX.X”…]

    Then we add the product of two lists to result.
 */
//Solution1:
class Solution {
    public List<String> ambiguousCoordinates(String S) {
        int n = S.length();
        List<String> res = new ArrayList();
        for (int i = 1; i < n - 2; i++) {
            List<String> A = helper(S.substring(1, i + 1));
            List<String> B = helper(S.substring(i + 1, n - 1));

            for (String a : A) {
                for (String b : B) {
                    res.add("(" + a + ", " + b + ")");
                }
            }
        }
        return res;
    }

    private List<String> helper(String S) {
        int n = S.length();
        List<String> res = new ArrayList();
        if (n == 0 || (n > 1 && S.charAt(0) == '0' && S.charAt(n - 1) == '0'))
            return res;
        if (n > 1 && S.charAt(0) == '0') {
            res.add("0." + S.substring(1));
            return res;
        }
        res.add(S);
        if (n == 1 || S.charAt(n - 1) == '0')
            return res;
        for (int i = 1; i < n; i++)
            res.add(S.substring(0, i) + '.' + S.substring(i));
        return res;
    }
}


/*
    Time Complexity: O(N^3), where N is the length S.

    Space Complexity: O(N^3)
 */
//Solution2:
class Solution {
    public List<String> ambiguousCoordinates(String S) {
        List<String> res = new ArrayList();
        for (int i = 2; i < S.length() - 1; i++) {
            for (String left : helper(S, 1, i)) {
                for (String right : helper(S, i, S.length() - 1)) {
                    res.add("(" + left + ", " + right + ")");
                }
            }
        }
        return res;
    }

    private List<String> helper(String S, int i, int j) {
        List<String> res = new ArrayList();
        for (int k = 1; k <= j - i; k++) {
            String left = S.substring(i, i + k);
            String right = S.substring(i + k, j);
            if ((!left.startsWith("0") || left.equals("0")) && !right.endsWith("0"))
                res.add(left + (k < j - i ? "." : "") + right);
        }
        return res;
    }
}
