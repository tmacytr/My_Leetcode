/*
    679. 24 Game

    DescriptionHintsSubmissionsDiscussSolution
    You have 4 cards each containing a number from 1 to 9. You need to judge whether they could operated through *, /, +, -, (, ) to get the value of 24.

    Example 1:
    Input: [4, 1, 8, 7]
    Output: True
    Explanation: (8-4) * (7-1) = 24
    Example 2:
    Input: [1, 2, 1, 2]
    Output: False

    Note:
    The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
    Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
    You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.
 */


/*
    任意的两个数字之间都可能进行加减乘除，其中加法和乘法对于两个数字的前后顺序没有影响，但是减法和除法是有影响的，而且做除法的时候还要另外保证除数不能为零。

    我们要遍历任意两个数字，然后对于这两个数字，尝试各种加减乘除后得到一个新数字，将这个新数字加到原数组中，记得原来的两个数要移除掉，然后调用递归函数进行计算，

    我们可以发现每次调用递归函数后，数组都减少一个数字，那么当减少到只剩一个数字了，就是最后的计算结果，所以我们在递归函数开始时判断，如果数组只有一个数字，且为24，说明可以算出24，结果res赋值为true返回。

    这里我们的结果res是一个全局的变量，如果已经为true了，就没必要再进行运算了，所以第一行应该是判断结果res，为true就直接返回了。

    我们遍历任意两个数字，分别用num1和num2来取出，然后进行两者的各种加减乘除的运算，将结果保存进数组临时数组next，记得要判断除数不为零。

    然后将原数组nums中的num1和num2移除，遍历临时数组next中的数字，将其加入数组nums，然后调用递归函数，记得完成后要移除数字，恢复状态，这是递归解法很重要的一点。最后还要把num1和num2再加回原数组nums，这也是还原状态
 */

/*
 What is eps:  eps is just like a tolerance when you’re calculating doubles, and you can assign the value you want as long as it’s reasonable. Here, any small number should be okay
               for example if a number like 24.00009 we can accept this output as ‘24’
*/

// Solution: Backtracking, 分别对每对数字尝试用 +，-，*，/， 四则计算，将value传到下一层递归，再backtrack
class Solution {
    private boolean res = false;
    private double eps = 0.001;
    public boolean judgePoint24(int[] nums) {
        List<Double> item = new ArrayList(); //item初始化为所有的init数字， 如果最后list只剩下一个元素并且等于24，则找到答案
        for (int num : nums)
            item.add((double)num); //需要将int转成double
        helper(item);
        return res;
    }

    public void helper(List<Double> item) {
        if (res) //剪枝加速，由于我们只需要检测是否能构成24
            return;
        if (item.size() == 1) {
            if (Math.abs(item.get(0) - 24.0) < eps)
                res = true;
            return;
        }

        for (int i = 0; i < item.size(); i++) {
            for (int j = 0; j < i; j++) {
                //在这一层里，尝试item.get(i)和item.get(j)两个数字
                List<Double> next = new ArrayList();
                Double num1 = item.get(i);
                Double num2 = item.get(j);
                next.addAll(Arrays.asList(num1 + num2, num1 - num2, num2- num1, num1 * num2));
                if (Math.abs(num1) > eps) // in case num1 = 0
                    next.add(num2 / num1);
                if (Math.abs(num2) > eps) // in case num2 = 0
                    next.add(num1 / num2);

                item.remove(i); // 已经有新的运算结果了，我们需要把之前的数remove
                item.remove(j); // 已经有新的运算结果了，我们需要把之前的数remove

                for (Double num : next) { // 轮流加进去每一个新的运算结果去backtracking
                    item.add(num);
                    helper(item);
                    item.remove(item.size() - 1);
                }

                item.add(j, num2); // 上一层的dfs已经结束，我们需要add back之前的数
                item.add(i, num1); // 上一层的dfs已经结束，我们需要add back之前的数
            }
        }
    }
}