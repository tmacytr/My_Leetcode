/*
    439. Ternary Expression Parser

    Given a string representing arbitrarily nested ternary expressions, calculate the result of the expression. You can always assume that the given expression is valid and only consists of digits 0-9, ?, :, T and F (T and F represent True and False respectively).

    Note:

    The length of the given string is ≤ 10000.
    Each number will contain only one digit.
    The conditional expressions group right-to-left (as usual in most languages).
    The condition will always be either T or F. That is, the condition will never be a digit.
    The result of the expression will always evaluate to either a digit 0-9, T or F.
    Example 1:

    Input: "T?2:3"

    Output: "2"

    Explanation: If true, then result is 2; otherwise result is 3.
    Example 2:

    Input: "F?1:T?4:5"

    Output: "4"

    Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

                 "(F ? 1 : (T ? 4 : 5))"                   "(F ? 1 : (T ? 4 : 5))"
              -> "(F ? 1 : 4)"                 or       -> "(T ? 4 : 5)"
              -> "4"                                    -> "4"
    Example 3:

    Input: "T?T?F:5:3"

    Output: "F"

    Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

                 "(T ? (T ? F : 5) : 3)"                   "(T ? (T ? F : 5) : 3)"
              -> "(T ? F : 3)"                 or       -> "(T ? F : 5)"
              -> "F"                                    -> "F"

  Companies: Snapchat

  Related Topics: Stack, DFS

  Similar Questions:
    1. Mini Parser
    2. Remove Comments
    3. Parse Lisp Expression
 */


//Solution1: DFS
class Solution {
    public String parseTernary(String exp) {
        if (exp.length() == 1)
            return exp;
        int i = 1, count = 1;
        while (count != 0){
            char c = exp.charAt(++i);
            count += c =='?' ? 1 : c ==':' ? -1: 0; // 因为每一个?都会对应一个:, 我们要将 ？ ~ :之间的exp 以及 ：之后的exp的index都要记录下来
        }
        return exp.charAt(0) == 'T' ? parseTernary(exp.substring(2, i)) : parseTernary(exp.substring(i + 1));
    }
}

//Solution2: Stack, traverse from back to front， 每当栈顶元素为 ?时，开始计算栈内的操作和元素
class Solution {
    public String parseTernary(String expression) {
        if (expression.isEmpty() || expression.length() == 1)
            return expression;
        Stack<Character> stack = new Stack();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (!stack.isEmpty() && stack.peek() == '?') {
                stack.pop();
                char first = stack.pop();
                stack.pop();
                char second = stack.pop();
                if (c == 'T')
                    stack.push(first);
                else
                    stack.push(second);
            } else {
                stack.push(c);
            }
        }
        return String.valueOf(stack.peek());
    }
}