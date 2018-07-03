/*
	Unique Word Abbreviation
	An abbreviation of a word follows the form <first letter><number><last letter>. Below are some examples of word abbreviations:

	a) it                      --> it    (no abbreviation)

	     1
	b) d|o|g                   --> d1g

	              1    1  1
	     1---5----0----5--8
	c) i|nternationalizatio|n  --> i18n

	              1
	     1---5----0
	d) l|ocalizatio|n          --> l10n
	Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary. 
    A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.

	Example: 
	Given dictionary = [ "deer", "door", "cake", "card" ]

	isUnique("dear") -> false
	isUnique("cart") -> true
	isUnique("cane") -> false
	isUnique("make") -> true
*/

// 什么时候才是有效的abbreviation？ 1. dictionary里没有这个abb or 2.dictionary里有但是abb的原型word必须要等于你要查的word.  如果一个abb对应超过1个word 则永远不会unique

// Solution1
public class ValidWordAbbr {
    HashMap<String, String> map;
    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap<>();
        for (String word : dictionary) {
            String abbr = generateAbb(word);
            if (!map.containsKey(abbr)) {
                map.put(abbr, word);
            } else {
                if (!map.get(abbr).equals(word)) {
                    map.put(abbr, "-1");
                }
            }
        }
    } 

    public boolean isUnique(String word) {
        String key = generateAbb(word);
        return !map.containsKey(key) || map.get(key).equals(word);
    }
    
    public String generateAbb(String word) {
        if (word.length() <= 2) {
            return word;
        }
        String abb = word.charAt(0) + String.valueOf(word.length() - 2) + word.charAt(word.length() - 1);
        return abb;
    }
}

// Solution2: my solution, prefer
class ValidWordAbbr {
    Map<String, HashSet<String>> map;
    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap();
        for (String word : dictionary) {
            String abb = generateAbb(word);
            map.putIfAbsent(abb, new HashSet());
            map.get(abb).add(word);
        }
    }

    public boolean isUnique(String word) {
        String abb = generateAbb(word);
        return map.get(abb) == null || (map.get(abb).contains(word) && map.get(abb).size() == 1);
    }

    public String generateAbb(String s) {
        if (s.length() <= 2)
            return s;
        int len = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0)).append(len).append(s.charAt(len - 1));
        return sb.toString();
    }
}