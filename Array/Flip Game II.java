/*
	Flip Game II
	You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

	Write a function to determine if the starting player can guarantee a win.

	For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

	Follow up:
	Derive your algorithm's runtime complexity.

	Tags: backtracking
*/

/*
    For the time complexity, here is what I thought, let's say the length of the input string s is n,
    there are at most n - 1 ways to replace "++" to "--" (imagine s is all "+++..."), once we replace one "++",
    there are at most (n - 2) - 1 ways to do the replacement, it's a little bit like solving the N-Queens problem,

    the time complexity is (n - 1) x (n - 3) x (n - 5) x ..., so it's O(n!!)
*/
// Solution1: recursion
public class Solution {
    public boolean canWin(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.startsWith("++", i)) {
                String opponent = s.substring(0, i) + "--" + s.substring(i + 2);
                if(!canWin(opponent)) {
                    return true;
                }
            }
        }
        return false;
    }
}

// Solution2: recursion + memorization
class Solution {
    public boolean canWin(String s) {
        if (s == null || s.length() < 2)
            return false;
        Set<String> winSet = new HashSet();
        return canWin(s, winSet);
    }

    public boolean canWin(String s, Set<String> winSet) {
        if (winSet.contains(s))
            return true;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.startsWith("++", i)) {
                String opponent = s.substring(0, i) + "--" + s.substring(i + 2);
                if (!canWin(opponent, winSet)) {
                    winSet.add(s);
                    return true;
                }
            }
        }
        return false;
    }
}