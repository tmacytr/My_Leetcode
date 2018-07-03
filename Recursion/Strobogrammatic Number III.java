/*
	Strobogrammatic Number III
	A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

	Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.

	For example,
	Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.

	Note:
	Because the range might be a large number, the low and high numbers are represented as string.
*/


// Solution1: use Strobogrammatic Number II to generate and calculate the count
public class Solution {
    public int strobogrammaticInRange(String low, String high) {
        int count = 0;
        List<String> res = new ArrayList<String>();
        for (int i = low.length(); i <= high.length(); i++) {
            res.addAll(helper(i, i));
        }
        
        for (String num : res) {
            if ((num.length() == low.length() && num.compareTo(low) < 0) || (num.length() == high.length() && num.compareTo(high) > 0))
                continue;
            count++;
        }
        return count;
    }
    
    public List<String> helper(int cur, int max) {
        if (cur == 0) {
            return new ArrayList<String>(Arrays.asList(""));
        }
        if (cur == 1) {
            return new ArrayList<String>(Arrays.asList("1", "8", "0"));
        }
        
        List<String> res = new ArrayList<String>();
        List<String> center = helper(cur - 2, max);
        
        for (int i = 0; i < center.size(); i++) {
            String tmp = center.get(i);
            if (cur != max) {
                res.add("0" + tmp + "0");
            }
            res.add("1" + tmp + "1");
            res.add("6" + tmp + "9");
            res.add("8" + tmp + "8");
            res.add("9" + tmp + "6");
        }
        return res;
    }
}

// Solution2: dfs
class Solution {
    private static final char[][] pairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'9', '6'}, {'8', '8'}};
    private int res;
    public int strobogrammaticInRange(String low, String high) {
        res = 0;
        for (int len = low.length(); len <= high.length(); len++) {
            char[] c = new char[len];
            dfs(low, high, c, 0, len - 1);
        }
        return res;
    }

    private void dfs(String low, String high, char[] c, int left, int right) {
        if (left > right) {
            String s = new String(c);
            if ((s.length() == low.length() && s.compareTo(low) < 0 || s.length() == high.length() && s.compareTo(high) > 0)) {
                return;
            }
            res++;
            return;
        }

        for (char[] pair : pairs) {
            c[left] = pair[0];
            c[right] = pair[1];
            if (c.length != 1 && c[0] == '0') // skip 0 + c + 0
                continue;
            if (left == right && pair[0] != pair[1]) // skip x + 6 + x || x + 9 + x
                continue;
            dfs(low, high, c, left + 1, right - 1);
        }
    }
}