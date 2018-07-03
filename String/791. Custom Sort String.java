/*
    791. Custom Sort String

    S and T are strings composed of lowercase letters. In S, no letter occurs more than once.

    S was sorted in some custom order previously. We want to permute the characters of T so that they match the order that S was sorted. More specifically, if x occurs before y in S, then x should occur before y in the returned string.

    Return any permutation of T (as a string) that satisfies this property.

    Example :
    Input:
    S = "cba"
    T = "abcd"
    Output: "cbad"
    Explanation:
    "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
    Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.


    Note:

    S has length at most 26, and no character is repeated in S.
    T has length at most 200.
    S and T consist of lowercase letters only.

    Companies: Amazon

    Related Topics: String
 */

//Soltuion: count the String T, and traverse by S, add the number of T character which is in the S, and then all the left non existing character in S
class Solution {
    public String customSortString(String S, String T) {
        if (S.isEmpty() || T.isEmpty())
            return T;
        int[] count = new int[26];
        for (char c : T.toCharArray()) {
            count[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : S.toCharArray()) {
            while (count[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                sb.append((char)(i + 'a'));
            }
        }
        return sb.toString();
    }
}