/*
	Group Shifted Strings
	Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:

	"abc" -> "bcd" -> ... -> "xyz"
	Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.

	For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"], 
	Return:

	[
	  ["abc","bcd","xyz"],
	  ["az","ba"],
	  ["acef"],
	  ["a","z"]
	]
	Tags: HashTable, String
*/

/*
	题解：


	这道题作法实际上就是 对于每一个String，比如 eqdf, 如下算出ASCII码，
	当然编码方式有很多种，比如: abc , 以第一个字符为参照，全部减第一个， abc = 012 , xyz = 012
	给的例子太不具说明性了。应该举这个例子：
	["eqdf", "qcpr"]。

	((‘c’ - 'q') + 26) % 26 = 12, ((‘p’ - 'c') + 26) % 26 = 13, ((‘r’ - 'p') + 26) % 26 = 2
    ((‘q’ - 'e') + 26) % 26 = 12, ((‘d’ - 'q') + 26) % 26 = 13, ((‘f’ - 'd') + 26) % 26 = 2
	12 13 2， 12132  这样就是一个hashmap key
	所以"eqdf"和"qcpr"是一组shifted strings。
*/

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> res = new ArrayList();
        HashMap<String, ArrayList<String>> map = new HashMap();

        for (String s : strings) {
            String key = "";
            for (int i = 0; i < s.length(); i++) {
                key += (char)((s.charAt(i) - s.charAt(0) + 26) % 26); // 精妙之处
                if (!map.containsKey(key))
                    map.put(key, new ArrayList());
                map.get(key).add(s);
            }

            res.addAll(map.values());
//            for(List<String> list: map.values()){
//                Collections.sort(list); // 如果需要sort结果.
//                res.add(list);
//            }
            return res;
        }
    }