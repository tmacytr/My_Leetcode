/*
    678. Valid Parenthesis String

    Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:

    Any left parenthesis '(' must have a corresponding right parenthesis ')'.
    Any right parenthesis ')' must have a corresponding left parenthesis '('.
    Left parenthesis '(' must go before the corresponding right parenthesis ')'.
    '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
    An empty string is also valid.
    Example 1:
    Input: "()"
    Output: True
    Example 2:
    Input: "(*)"
    Output: True
    Example 3:
    Input: "(*))"
    Output: True
    Note:
    The string size will be in the range [1, 100].

    Companies: Alibaba

    Related Topics: String

    Similar Questions: Special Binary String
 */

/*
    left : take '*' as ')', if there are some '(' not matched
    right : take '*' as '('
    
    if right < 0 means too much ')'
    if left > 0 , after the count finished, means too much '('
    
    since left take '*' as ')', there might be too much ')', so that left might less than 0.

    That's why left-- should happen only left>0. This can thought as, left only take as much as '(''s ')' and ignore other ')' s. This will not cause problem since :
    
    '*' can be treated as empty
    right has deal with the situation that too much ')' exist

 */

/*
    维护了两个变量left和right，其中left表示在有左括号的情况下，将星号当作右括号时左括号的个数(这样做的原因是尽量不多增加右括号的个数)，
    
    而right表示将星号当作左括号时左括号的个数。那么当right小于0时，说明就算把星号都当作左括号了，还是不够抵消右括号，返回false。
    
    而当left大于0时，说明左括号的个数太多了，没有足够多的右括号来抵消，返回false。
    
    那么开始遍历字符串，当遇到左括号时，left和right都自增1；当遇到右括号时，只有当left大于0时，left才自减1，保证了left不会小于0，而right直接自减1；
    
    当遇到星号时，只有当left大于0时，left才自减1，保证了left不会小于0，而right直接自增1，因为right把星号当作左括号。当此时right小于0，说明右括号太多，返回false。当循环退出后，我们看left是否为0
 */

//Solution1:
class Solution {
    public boolean checkValidString(String s) {
        if (s.isEmpty())
            return true;
        int left = 0;
        int right = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
                right++;
            } else if (c == ')') {
                if (left > 0)
                    left--;
                right--;
            } else {
                if (left > 0)
                    left--;
                right++;
            }
            if (right < 0)
                return false;
        }
        return left == 0;
    }
}

//Solution2:
class Solution {
    public boolean checkValidString(String s) {
        int lo = 0, hi = 0;
        for (char c: s.toCharArray()) {
            lo += c == '(' ? 1 : -1;
            hi += c != ')' ? 1 : -1;
            if (hi < 0)
                break;
            lo = Math.max(lo, 0);
        }
        return lo == 0;
    }
}