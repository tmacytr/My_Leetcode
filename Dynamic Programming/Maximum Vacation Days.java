/*
    568. Maximum Vacation Days

    LeetCode wants to give one of its best employees the option to travel among N cities to collect algorithm problems. But all work and no play makes Jack a dull boy, you could take vacations in some particular cities and weeks. Your job is to schedule the traveling to maximize the number of vacation days you could take, but there are certain rules and restrictions you need to follow.

    Rules and restrictions:
    You can only travel among N cities, represented by indexes from 0 to N-1. Initially, you are in the city indexed 0 on Monday.
    The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical), called flights representing the airline status from the city i to the city j. If there is no flight from the city i to the city j, flights[i][j] = 0; Otherwise, flights[i][j] = 1. Also, flights[i][i] = 0 for all i.
    You totally have K weeks (each week has 7 days) to travel. You can only take flights at most once per day and can only take flights on each week's Monday morning. Since flight time is so short, we don't consider the impact of flight time.
    For each city, you can only have restricted vacation days in different weeks, given an N*K matrix called days representing this relationship. For the value of days[i][j], it represents the maximum days you could take vacation in the city i in the week j.
    You're given the flights matrix and days matrix, and you need to output the maximum vacation days you could take during K weeks.

    Example 1:
    Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
    Output: 12
    Explanation:
    Ans = 6 + 3 + 3 = 12.

    One of the best strategies is:
    1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day.
    (Although you start at city 0, we could also fly to and start at other cities since it is Monday.)
    2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
    3rd week : stay at city 2, and play 3 days and work 4 days.
    Example 2:
    Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
    Output: 3
    Explanation:
    Ans = 1 + 1 + 1 = 3.

    Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks.
    For each week, you only have one day to play and six days to work.
    So the maximum number of vacation days is 3.
    Example 3:
    Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
    Output: 21
    Explanation:
    Ans = 7 + 7 + 7 = 21

    One of the best strategies is:
    1st week : stay at city 0, and play 7 days.
    2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
    3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
    Note:
    N and K are positive integers, which are in the range of [1, 100].
    In the matrix flights, all the values are integers in the range of [0, 1].
    In the matrix days, all the values are integers in the range [0, 7].
    You could stay at a city beyond the number of vacation days, but you should work on the extra days, which won't be counted as vacation days.
    If you fly from the city A to the city B and take the vacation on that day, the deduction towards vacation days will count towards the vacation days of city B in that week.
    We don't consider the impact of flight hours towards the calculation of vacation days.
 */
// Solution1: DFS  time: O(n^k), 一共有K周 每个周有N个选择，所以是O(n^k)， space: k , recursion tree space
class Solution {
    public int maxVacationDays(int[][] flights, int[][] days) {
        return dfs(flights, days, 0, 0);
    }

    public int dfs(int[][] flights, int[][] days, int curCity, int j) {
        if (j == days[0].length)
            return 0;

        int max = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[curCity][i] == 1 || i == curCity) {
                int vacationDays = days[i][j] + dfs(flights, days, i, j + 1);
                max = Math.max(vacationDays, max);
            }
        }
        return max;
    }
}

// Solution2: DFS AND (memorization) 优化成O(n*n*k), cache array of size n*k is filled and each cell filling takes O(n) time. space: O(nk)
class Solution {
    public int maxVacationDays(int[][] flights, int[][] days) {
        int[][] cache = new int[flights.length][days[0].length];
        return dfs(flights, days, cache, 0, 0);
    }

    public int dfs(int[][] flights, int[][] days, int[][] cache, int curCity, int j) {
        if (j == days[0].length)
            return 0;
        if (cache[curCity][j] != 0)
            return cache[curCity][j];

        int max = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[curCity][i] == 1 || i == curCity) {
                int vacationDays = days[i][j] + dfs(flights, days, cache, i, j + 1);
                max = Math.max(vacationDays, max);
                cache[curCity][j] = max; // 存 当第j周时在curCity 能得到的最大days值
            }
        }
        return max;
    }
}

// Solution3: DP space: 2D array, time: N*N*K, we need to traverse from backward
class Solution {
    public int maxVacationDays(int[][] flights, int[][] days) {
        int N = days.length;
        int K = days[0].length;
        int[][] dp = new int[N][K + 1];

        for (int week = K - 1; week >= 0; week--) {
            for (int curCity = 0; curCity < N; curCity++) {
                dp[curCity][week] = days[curCity][week] + dp[curCity][week + 1];
                for (int destCity = 0; destCity < N; destCity++) {
                    if (flights[curCity][destCity] == 1) {
                        dp[curCity][week] = Math.max(dp[curCity][week], days[destCity][week] + dp[destCity][week + 1]);
                    }
                }
            }
        }
        return dp[0][0];
    }
}