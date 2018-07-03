/*
	744. Find Smallest Letter Greater Than Target

	Given a list of sorted characters letters containing only lowercase letters, and given a target letter target, find the smallest element in the list that is larger than the given target.

	Letters also wrap around. For example, if the target is target = 'z' and letters = ['a', 'b'], the answer is 'a'.

	Examples:
	Input:
	letters = ["c", "f", "j"]
	target = "a"
	Output: "c"

	Input:
	letters = ["c", "f", "j"]
	target = "c"
	Output: "f"

	Input:
	letters = ["c", "f", "j"]
	target = "d"
	Output: "f"

	Input:
	letters = ["c", "f", "j"]
	target = "g"
	Output: "j"

	Input:
	letters = ["c", "f", "j"]
	target = "j"
	Output: "c"

	Input:
	letters = ["c", "f", "j"]
	target = "k"
	Output: "c"
	Note:
	letters has a length in range [2, 10000].
	letters consists of lowercase letters, and contains at least 2 unique letters.
	target is a lowercase letter.
*/


// Solution1: My Binary Search, O(logn)
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        // edge case: 如果target大于最后一个数或者小于第一个数直接返回letters[0]
        if (target >= letters[letters.length - 1] || target < letters[0]) {
            return letters[0];
        }

        int start = 0;
        int end = letters.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (letters[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        
        return letters[end]; // 只有target >= mid时 才会将mid赋值于start，也就意味着，start永远不可能比target大，所以我们选取end作为next greater。 
        
    }
}

// Solution2: hash O(n)
// 思路很巧妙，将target++， 如果hash array中发现了这个数则直接返回，到了最后还没发现就从0开始找首个出现的字符
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        boolean[] seen = new boolean[26];
        for (char c : letters)
            see[c - 'a'] = true;
        
        while (true) {
            target++;
            if (target > 'z')
                target = 'a';
            if (seen[target - 'a'])
                return target;
        }
    }
}


// Solution3: Binary Search 2
class Solution {
    public char nextGreatestLetter(char[] a, char x) {
        int n = a.length;

        //hi starts at 'n' rather than the usual 'n - 1'. 
        //It is because the terminal condition is 'lo < hi' and if hi starts from 'n - 1', 
        //we can never consider value at index 'n - 1'
        int lo = 0;
        int hi = n;

        //Terminal condition is 'lo < hi', to avoid infinite loop when target is smaller than the first element
				while (lo < hi) {
            int mi = lo + (hi - lo) / 2;
            if (letters[mi] <= target)
            	lo = mi + 1;
            else
            	hi = mi;
        }
 
        //Because lo can end up pointing to index 'n', in which case we return the first element
        return a[lo % n];
    }
}