/*
    475. Heaters
    Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all the houses.

    Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that all houses could be covered by those heaters.

    So, your input will be the positions of houses and heaters seperately, and your expected output will be the minimum radius standard of heaters.

    Note:
    Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
    Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
    As long as a house is in the heaters' warm radius range, it can be warmed.
    All the heaters follow your radius standard and the warm radius will the same.

    Example 1:
    Input: [1,2,3],[2]
    Output: 1
    Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.

    Example 2:
    Input: [1,2,3,4],[1,4]
    Output: 1
    Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.

    Companies: Google

    Related Topics: Binary Search

    Similar:
 */
/*
    Understand Math.abs(heaters[i] - house) >= Math.abs(heaters[i + 1] - house
    Let’s us understand this with a example:
    houses: 1, 2, 3, 4
    heaters: 1, 4
    For house 1, heater 1 is closer to it than heater 4, so we don’t move i to i + 1.
    For house 2, it is same. heater 1 is closer.
    For house 3, it is clear that heater 1 no longer closer, so we move i to i + 1.
    For house 4, continue…

    The idea here is we move the heater to rightward in case it is closer to the given house.
 */
// Solution1: Two pointers
// Sorting of houses O(nlogn) and heaters O(mlogm), and the main part of this algorithm with two pointer is O(n + m)
// Why O(n + m) ? each element in houses and heaters are visited once
class Solution {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int j = 0, res = 0;

        for (int i = 0; i < houses.length; i++) {
            while (j < heaters.length - 1 && Math.abs(heaters[j + 1] - houses[i]) <= Math.abs(heaters[j] - houses[i])) {
                j++;
            }
            res = Math.max(res, Math.abs(heaters[j] - houses[i]));
        }
        return res;
    }
}

/*
    对每个house，找出其左右最接近的heater的范围。

    For each house, find its position between those heaters (thus we need the heaters array to be sorted).
    Calculate the distances between this house and left heater and right heater, get a MIN value of those two values. Corner cases are there is no left or right heater.
    Get MAX value among distances in step 2. It’s the answer.
    Time complexity: max(O(nlogn), O(mlogn)) - m is the length of houses, n is the length of heaters.
 */
//Solution2: binarySearch
class Solution {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = Integer.MIN_VALUE;
        for (int house : houses) {
            int index = Arrays.binarySearch(heaters, house);
            if (index < 0)
                index = -(index + 1);
            int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
            int dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;

            res = Math.max(res, Math.min(dist1, dist2));
        }
        return res;
    }
}