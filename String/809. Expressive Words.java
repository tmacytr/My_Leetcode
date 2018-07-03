/*
    809. Expressive Words

    Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii".  Here, we have groups, of adjacent letters that are all the same character, and adjacent characters to the group are different.  A group is extended if that group is length 3 or more, so "e" and "o" would be extended in the first example, and "i" would be extended in the second example.  As another example, the groups of "abbcccaaaa" would be "a", "bb", "ccc", and "aaaa"; and "ccc" and "aaaa" are the extended groups of that string.

    For some given string S, a query word is stretchy if it can be made to be equal to S by extending some groups.  Formally, we are allowed to repeatedly choose a group (as defined above) of characters c, and add some number of the same character c to it so that the length of the group is 3 or more.  Note that we cannot extend a group of size one like "h" to a group of size two like "hh" - all extensions must leave the group extended - ie., at least 3 characters long.

    Given a list of query words, return the number of words that are stretchy.

    Example:
    Input:
    S = "heeellooo"
    words = ["hello", "hi", "helo"]
    Output: 1
    Explanation:
    We can extend "e" and "o" in the word "hello" to get "heeellooo".
    We can't extend "helo" to get "heeellooo" because the group "ll" is not extended.
    Notes:

    0 <= len(S) <= 100.
    0 <= len(words) <= 100.
    0 <= len(words[i]) <= 100.
    S and all words in words consist only of lowercase letters
 */

//Solution1:
class Solution {
    public int expressiveWords(String S, String[] words) {
        int count = 0;
        for (String word : words) {
            int i, j; // S & w's pointers.
            for (i = 0, j = 0; i < S.length(); i++) {
                if (j < word.length() && S.charAt(i) == word.charAt(j))  // matches, w pointer j 1 step forward to move together with i.
                    ++j;
                else if (i > 0 && S.charAt(i - 1) == S.charAt(i) && i + 1 < S.length() && S.charAt(i) == S.charAt(i + 1)) // previous, current & next are same.
                    ++i;
                else if (!(i > 1 && S.charAt(i) == S.charAt(i - 1) && S.charAt(i) == S.charAt(i - 2))) {  // current & previous 2 are not same.
                    break;
                }
            }
            if (i == S.length() && j == word.length())
                ++count;
        }
        return count;
    }
}


//Solution2: For some word, write the head character of every group, and the count of that group. For example, for "abbcccddddaaaaa",
// we'll write the "key" of "abcda", and the "count" [1,2,3,4,5]. and we comapre the key and count for S and words.
class Solution {
    public int expressiveWords(String S, String[] words) {
        RLE r1 = new RLE(S);
        int res = 0;

        search: for (String word : words) {
            RLE r2 = new RLE(word);
            if (!r1.key.equals(r2.key))
                continue;
            for (int i = 0; i < r1.counts.size(); i++) {
                int c1 = r1.counts.get(i);
                int c2 = r2.counts.get(i);
                if ((c1 < 3 && c1 != c2) || c1 < c2)
                    continue search; //为什么用这种写法？因为我们不能直接用break和continue，而是希望search继续进行
            }
            res++;
        }
        return res;
    }
}

class RLE {
    String key;
    List<Integer> counts;

    public RLE(String s) {
        StringBuilder sb = new StringBuilder();
        counts = new ArrayList();

        char[] ch = s.toCharArray();

        int len = ch.length;
        int pre = -1;

        // 精髓， 将 abbcccddddaaaaa -> abcda
        for (int i = 0; i < len; i++) {
            if (i == len - 1 || ch[i] != ch[i + 1]) {
                sb.append(ch[i]);
                counts.add(i - pre);
                pre = i;
            }
        }
        key = sb.toString();
    }
}