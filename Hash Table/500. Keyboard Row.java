/*
    500. Keyboard Row

    Given a List of words, return the words that can be typed using letters of alphabet on only one row's of American keyboard like the image below.


    American keyboard


    Example 1:
    Input: ["Hello", "Alaska", "Dad", "Peace"]
    Output: ["Alaska", "Dad"]
    Note:
    You may use one character in the keyboard more than once.
    You may assume the input string will only contain letters of alphabet.

    Companies: Mathworks

    Related Topics: Hash Table

 */

//Solution1
class Solution {
    public String[] findWords(String[] words) {
        if (words.length == 0)
            return new String[]{};
        String row1 = "qwertyuiop";
        String row2 = "asdfghjkl";
        String row3 = "zxcvbnm";
        int inRowOne = 0, inRowTwo = 0, inRowThree = 0;

        List<String> res = new ArrayList();
        for (String word : words) {
            int len = word.length();
            for (char c : word.toLowerCase().toCharArray()) {
                String letter = c + "";
                if (row1.contains(letter))
                    inRowOne += 1;
                if (row2.contains(letter))
                    inRowTwo += 1;
                if (row3.contains(letter))
                    inRowThree += 1;
            }
            if (inRowOne == len || inRowTwo == len || inRowThree == len)
                res.add(word);
            inRowOne = inRowTwo = inRowThree = 0;
        }
        return res.toArray(new String[res.size()]);
    }
}