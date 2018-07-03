/*
    468. Validate IP Address

    Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.

    IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;

    Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.

    IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading zeros and using upper cases).

    However, we don't replace a consecutive group of zero value with a single empty group using two consecutive colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.

    Besides, extra leading zeros in the IPv6 is also invalid. For example, the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.

    Note: You may assume there is no extra space or special characters in the input string.

    Example 1:
    Input: "172.16.254.1"

    Output: "IPv4"

    Explanation: This is a valid IPv4 address, return "IPv4".
    Example 2:
    Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"

    Output: "IPv6"

    Explanation: This is a valid IPv6 address, return "IPv6".
    Example 3:
    Input: "256.256.256.256"

    Output: "Neither"

    Explanation: This is neither a IPv4 address nor a IPv6 address.
 */

//Solution: 依次比较是否是IPV4, IPV6，没什么特别的，小心头尾出现不符合题意的字符的edge case
class Solution {
    String ipv6String = "0123456789abcdefABCDEF";
    String invalid = "Neither";
    public String validIPAddress(String IP) {
        if (IP.isEmpty())
            return invalid;
        int len = IP.length();
        // edge case, Check the first and last character whether is valid char
        if (!ipv6String.contains(IP.charAt(0) + "") || !ipv6String.contains(IP.charAt(len - 1) + ""))
            return invalid;

        boolean isIPV4 = IP.indexOf('.') != -1;
        if (isIPV4) {
            String[] ips = IP.split("\\.");
            if (ips.length != 4)
                isIPV4 = false;
            if (isIPV4) {
                for (String ip : ips) {
                    if (!isValidIPV4(ip)) {
                        isIPV4 = false;
                        break;
                    }
                }
            }
        }
        if (isIPV4)
            return "IPv4";

        boolean isIPV6 = true;

        String[] ips = IP.split(":");
        if (ips.length != 8)
            isIPV6 = false;
        if (isIPV6) {
            for (String ip : ips) {
                System.out.println(ip);
                if (!isValidIPV6(ip)) {
                    isIPV6 = false;
                    break;
                }
            }
        }
        return isIPV6 ? "IPv6" : invalid;
    }

    private boolean isValidIPV4(String s) {
        int len = s.length();
        if (s.isEmpty() || len > 4)
            return false;
        if (s.charAt(0) == '0')
            return len == 1;
        for (char c : s.toCharArray())
            if (!Character.isDigit(c))
                return false;
        int num = Integer.valueOf(s);
        return num <= 255 && num >= 1 && len <= 3 && num >= 1;
    }

    private boolean isValidIPV6(String s) {
        int len = s.length();
        if (s.isEmpty() || len > 4)
            return false;

        for (char c : s.toCharArray())
            if (!ipv6String.contains(c + ""))
                return false;
        return true;
    }
}