/*
	Strobogrammatic Number

		A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

	Write a function to determine if a number is strobogrammatic. The number is represented as a string.

	For example, the numbers "69", "88", and "818" are all strobogrammatic.
	Tags: HashMap
*/

//Solution1: my solution
class Solution {
    public boolean isStrobogrammatic(String num) {
        HashMap<Character, Character> map = new HashMap();
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        map.put('6', '9');
        map.put('9', '6');

        StringBuilder sb = new StringBuilder();
        for (char c : num.toCharArray()) {
            if (!map.containsKey(c))
                return false;
            sb.insert(0, map.get(c));
        }

        return num.equals(sb.toString());
    }
}

//Solution2: Two Pointer + HashMap
class Solution {
    public boolean isStrobogrammatic(String num) {
        HashMap<Character, Character> map = new HashMap();
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        map.put('6', '9');
        map.put('9', '6');

        int start = 0;
        int end = num.length() - 1;

        while (start <= end) {
            if (!map.containsKey(num.charAt(start)) || !map.containsKey(num.charAt(end))) {
                return false;
            } else if (map.get(num.charAt(start)) != num.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}