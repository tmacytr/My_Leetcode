/*
    735. Asteroid Collision

    We are given an array asteroids of integers representing asteroids in a row.

    For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

    Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

    Example 1:
    Input:
    asteroids = [5, 10, -5]
    Output: [5, 10]
    Explanation:
    The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.
    Example 2:
    Input:
    asteroids = [8, -8]
    Output: []
    Explanation:
    The 8 and -8 collide exploding each other.
    Example 3:
    Input:
    asteroids = [10, 2, -5]
    Output: [10]
    Explanation:
    The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.
    Example 4:
    Input:
    asteroids = [-2, -1, 1, 2]
    Output: [-2, -1, 1, 2]
    Explanation:
    The -2 and -1 are moving left, while the 1 and 2 are moving right.
    Asteroids moving the same direction never meet, so no asteroids will meet each other.
    Note:

    The length of asteroids will be at most 10000.
    Each asteroid will be a non-zero integer in the range [-1000, 1000]..

    Companies: Uber

    Related Topics: Stack

    Similar Questions: Can Place Flowers
 */

// Solution1: my solution
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null || asteroids.length <= 1)
            return asteroids;
        Stack<Integer> stack = new Stack();

        for (int asteroid : asteroids) {
            boolean shouldPush = true;
            while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
                int collision = compare(stack.peek(), asteroid);
                if (collision == 0 || collision == 1) {
                    shouldPush = false;
                    if (collision == 0)
                        stack.pop();
                    break;
                } else {
                    stack.pop();
                }
            }
            if (shouldPush)
                stack.push(asteroid);
        }

        int[] res = new int[stack.size()];

        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    private int compare(int i, int j) {
        if (Math.abs(i) == Math.abs(j))
            return 0;
        return Math.abs(i) > Math.abs(j) ? 1 : -1;
    }
}

//Solution2: more concise way prefer.
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null || asteroids.length <= 1)
            return asteroids;
        Stack<Integer> stack = new Stack();

        for (int asteroid : asteroids) {
            if (asteroid > 0)
                stack.push(asteroid);
            else {
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -asteroid)
                    stack.pop();
                if (!stack.isEmpty() && stack.peek() == -asteroid)
                    stack.pop();
                else if (stack.isEmpty() || stack.peek() < 0)
                    stack.push(asteroid);
            }
        }
        return stack.stream().mapToInt(i -> i).toArray();
    }
}