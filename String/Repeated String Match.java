/*
	686. Repeated String Match

	Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If no such solution, return -1.

	For example, with A = "abcd" and B = "cdabcdab".

	Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of A repeated two times ("abcdabcd").

	Note:
	The length of A and B will be between 1 and 10000.
*/

// 这里让求的是需要repeat 多少次A才能 contains B，而不是A中B的index，所以只需要判断repeat几次可以contains B 就好了， 用 indexOf方法
// O(N*(N+M)), where M, N, are the lengths of strings A, B
class Solution {
    public int repeatedStringMatch(String A, String B) {
        int index = 1;
        StringBuilder sb = new StringBuilder(A);
        while (sb.length() < B.length()) {
            index++;
            sb.append(A);
        }
        if (sb.indexOf(B) >= 0)
            return index;
        if (sb.append(A).indexOf(B) >= 0)
            return index + 1;
        return -1;
    }
}

// 用KMP intead of built-in function
class Solution {
    public int repeatedStringMatch(String A, String B) {
        int index = 1;
        StringBuilder sb = new StringBuilder(A);
        while (sb.length() < B.length()) {
            index++;
            sb.append(A);
        }
        if (contains(sb.toString(), B))
            return index;
        if (contains(sb.append(A).toString(), B))
            return index + 1;
        return -1;
    }
    
    public boolean contains(String haystack, String needle) {
        if (haystack == null)
            return false;
        if (needle.length() == 0)
            return true;
        int[] next = new int[needle.length()];
        int i = 0;
        int j = 0;
        setNext(needle, next);
        while (i < haystack.length()) {
            if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
            if (j == needle.length())
                return true;
        }
        return false;
    }
    
    public void setNext(String m, int[] next) {
        char[] s = m.toCharArray();
        int len = m.length();
        int k = -1;
        int j = 0;
        next[0] = -1;
        
        while (j < len - 1) {
            if (k == - 1|| s[k] == s[j])
                next[++j] = ++k;
            else 
                k = next[k];
        }           
    }
}