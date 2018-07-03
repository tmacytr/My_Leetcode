/*
    604. Design Compressed String Iterator

    Design and implement a data structure for a compressed string iterator. It should support the following operations: next and hasNext.

    The given compressed string will be in the form of each letter followed by a positive integer representing the number of this letter existing in the original uncompressed string.

    next() - if the original string still has uncompressed characters, return the next letter; Otherwise return a white space.
    hasNext() - Judge whether there is any letter needs to be uncompressed.

    Note:
    Please remember to RESET your class variables declared in StringIterator, as static/class variables are persisted across multiple test cases. Please see here for more details.

    Example:

    StringIterator iterator = new StringIterator("L1e2t1C1o1d1e1");

    iterator.next(); // return 'L'
    iterator.next(); // return 'e'
    iterator.next(); // return 'e'
    iterator.next(); // return 't'
    iterator.next(); // return 'C'
    iterator.next(); // return 'o'
    iterator.next(); // return 'd'
    iterator.hasNext(); // return true
    iterator.next(); // return 'e'
    iterator.hasNext(); // return false
    iterator.next(); // return ' '
*/

class StringIterator {
    private String res;
    private int index; // 当前字符的index
    private int curCharCount; // 当前index指向的字符的数量
    private char ch = ' '; // 当前字符
    public StringIterator(String compressedString) {
        index = 0;
        curCharCount = 0;
        res = compressedString;
    }

    public char next() {
        if (!hasNext())
            return ' ';
        if (curCharCount == 0) {
            ch = res.charAt(index++);
            while (index < res.length() && Character.isDigit(res.charAt(index))) { // 取字符的压缩数
                curCharCount = 10 * charCount + (res.charAt(index++) - '0');
            }
        }
        curCharCount--;
        return ch;
    }

    public boolean hasNext() {
        // 只有index已经遍历到最后一个字符，并且count已经为0，才会返回false
        return index != res.length() || curCharCount != 0;
    }
}

/**
 * Your StringIterator object will be instantiated and called as such:
 * StringIterator obj = new StringIterator(compressedString);
 * char param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */