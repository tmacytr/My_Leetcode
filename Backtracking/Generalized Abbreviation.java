/*
	Generalized Abbreviation
	Write a function to generate the generalized abbreviations of a word.

	Example:
	Given word = "word", return the following list (order does not matter):
	["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
*/

/*
	The idea is: for every character, we can keep it or abbreviate it. 
	1. To keep it, we add it to the current solution and carry on backtracking. 
	2. To abbreviate it, we omit it in the current solution, but increment the count, which indicates how many characters have we abbreviated. 
	When we reach the end or need to put a character in the current solution, and count is bigger than zero, we add the number into the solution.
*/
class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList();
        dfs(res, word, 0, 0, "");
        return res;
    }

    public void dfs(List<String> res, String word, int index, int count, String item) {
        if (index == word.length()) {
            if (count > 0)
                item += count;
            res.add(item);
            return;
        }
        dfs(res, word, index + 1, count + 1, item);
        dfs(res, word, index + 1, 0, item + (count > 0 ? count : "") + word.charAt(index));

    }
}