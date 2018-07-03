/*
    394. Decode String

    Given an encoded string, return it's decoded string.

    The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

    You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

    Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

    Examples:

    s = "3[a]2[bc]", return "aaabcbc".
    s = "3[a2[c]]", return "accaccacc".
    s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */

// Solution1: use two stack. one to store count of every level str, the other stack use to stroe str(StringBuilder type)
class Solution {
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack();
        Stack<StringBuilder> strStack = new Stack();

        int count = 0;
        StringBuilder cur = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                count = count * 10 + c - '0' ; // case: when we have like 122[a]
            } else if (c == '[') { // when we meet '[', which mean we should push the count and current string to the stack.
                countStack.push(count);
                strStack.push(cur);
                count = 0; // reset for next []
                cur = new StringBuilder();  // reset for next []
            } else if (c == ']') {
                String temp = cur.toString(); // this temp is the current string
                cur = strStack.pop(); // strStack.pop() is the pre string,
                for (count = countStack.pop(); count > 0; count--) { //  cur = pre + count * current
                    cur.append(temp);
                }
            } else {
                cur.append(c);
            }
        }
        return cur.toString();
    }
}

// Solution2: recursive
public class Solution {
    public String decodeString(String s) {
        // why we need it?
        // because it requires a global variable to store the index of the character that the algorithm is computing,
        // so after running the nested strings, this index should be the one after the nested string
        int i[] = {0};
        return decode(s, i);
    }

    private String decode(String s, int[] i) {
        StringBuilder res = new StringBuilder();
        while (i[0] < s.length() && s.charAt(i[0]) != ']') {
            if (!Character.isDigit(s.charAt(i[0]))) {
                res.append(s.charAt(i[0]++));
            }
            else {
                int n = 0;
                while (Character.isDigit(s.charAt(i[0]))) {
                    n = n * 10 + s.charAt(i[0]++) - '0';
                }

                i[0]++;    //'['
                String nestedString = decode(s, i);
                i[0]++;   //']', after running the nested strings, this index should be the one after the nested string

                while (n-- > 0) {
                    res.append(nestedString);
                }
            }
        }
        return res.toString();
    }
}