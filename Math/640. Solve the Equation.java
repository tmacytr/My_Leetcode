/*
    640. Solve the Equation

    Solve a given equation and return the value of x in the form of string "x=#value". The equation contains only '+', '-' operation, the variable x and its coefficient.

    If there is no solution for the equation, return "No solution".

    If there are infinite solutions for the equation, return "Infinite solutions".

    If there is exactly one solution for the equation, we ensure that the value of x is an integer.

    Example 1:
    Input: "x+5-3+x=6+x-2"
    Output: "x=2"
    Example 2:
    Input: "x=x"
    Output: "Infinite solutions"
    Example 3:
    Input: "2x=x"
    Output: "x=0"
    Example 4:
    Input: "2x+3x-6x=x+2"
    Output: "x=-1"
    Example 5:
    Input: "x=x+2"
    Output: "No solution"

    Companies: Amazon

    Related Topics: Math

    Similar Questions: Fraction Addition and Subtraction
 */

//Solution1: divide to two part, left of equation and right or equation, and compute the coefficient of x and constant
class Solution {
    public String solveEquation(String equation) {
        int[] left = helper(equation.split("=")[0]);
        int[] right = helper(equation.split("=")[1]);
        left[0] -= right[0];
        left[1] = right[1] - left[1]; // why right[1] - left[1 ? if 5x + 2 = 3x + 5, would be  5x - 3x = 5 - 2, => left[0] - right[0] = right[1] - left[1]
        if (left[0] == 0 && left[1] == 0)
            return "Infinite solutions";
        if (left[0] == 0)
            return "No solution";
        return "x=" + left[1] / left[0];
    }

    private int[] helper(String exp) {
        String[] tokens = exp.split("(?=[-+])");// ()for match group; ?= for match and include in res; [+-] means + or -;
        int[] res = new int[2]; // res[0] = coefficient for x;  res[1] = coefficient for constant
        for (String token : tokens) {
            if (token.equals("+x") || token.equals("x"))
                res[0] += 1;
            else if (token.equals("-x"))
                res[0] -= 1;
            else if (token.contains("x"))
                res[0] += Integer.valueOf(token.substring(0, token.indexOf("x")));
            else
                res[1] += Integer.valueOf(token);
        }
        return res;
    }
}