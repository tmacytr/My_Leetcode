/*
	800. Similar RGB Color

	In the following, every capital letter represents some hexadecimal digit from 0 to f.

	The red-green-blue color "#AABBCC" can be written as "#ABC" in shorthand.  For example, "#15c" is shorthand for the color "#1155cc".

	Now, say the similarity between two colors "#ABCDEF" and "#UVWXYZ" is -(AB - UV)^2 - (CD - WX)^2 - (EF - YZ)^2.

	Given the color "#ABCDEF", return a 7 character color that is most similar to #ABCDEF, and has a shorthand (that is, it can be represented as some "#XYZ"

	Example 1:
	Input: color = "#09f166"
	Output: "#11ee66"
	Explanation:  
	The similarity is -(0x09 - 0x11)^2 -(0xf1 - 0xee)^2 - (0x66 - 0x66)^2 = -64 -9 -0 = -73.
	This is the highest among any shorthand color.
	Note:

	color is a string of length 7.
	color is a valid RGB color: for i > 0, color[i] is a hexadecimal digit from 0 to f
	Any answer which has the same (highest) similarity as the best answer will be accepted.
	All inputs and outputs should use lowercase letters, and the output is 7 characters.
*/

// Solution1: my solution
class Solution {
    public String similarRGB(String color) {
        String num = "0123456789abcdef";
        String a = color.substring(1, 3);
        String b = color.substring(3, 5);
        String c = color.substring(5);
     
        int min = Integer.MIN_VALUE;
        String res = "";
        for (int i = 0; i < num.length(); i++) {
            for (int j = 0; j < num.length(); j++) {
                for (int k = 0; k < num.length(); k++) {
                    String a1 = Character.toString(num.charAt(i)) + Character.toString(num.charAt(i));
                    String b1 = Character.toString(num.charAt(j)) + Character.toString(num.charAt(j));
                    String c1 = Character.toString(num.charAt(k)) + Character.toString(num.charAt(k));
                    
                    int value = (int)(-Math.pow(valueOf(a) - valueOf(a1), 2) - Math.pow(valueOf(b) - valueOf(b1), 2) - Math.pow(valueOf(c) - valueOf(c1), 2));
                    if (value > min) {
                        min = value;
                        res = "#" + a1 + b1 + c1;
                    }
                }
            }
        }
        return res;
    }
    
    public int valueOf(String s) {
        int v1 = s.charAt(0) <= 'f' && s.charAt(0) >= 'a' ? s.charAt(0) - 'a' + 10 : s.charAt(0) - '0';
        int v2 = s.charAt(1) <= 'f' && s.charAt(1) >= 'a' ? s.charAt(1) - 'a' + 10 : s.charAt(1) - '0';
        
        return v1 * 16 + v2;
    }
}

//Solution2:
class Solution {
    public String similarRGB(String color) {
        return "#" + helper(color.substring(1, 3)) + helper(color.substring(3, 5)) + helper(color.substring(5));
    }
    private String helper(String color) {
        int val = Integer.parseInt(color, 16);
        val = val / 17 + (val % 17 > 8 ? 1 : 0);
        return String.format("%02x", 17 * val);
    }
}