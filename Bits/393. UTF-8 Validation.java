/*
    393. UTF-8 Validation

    A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:

    For 1-byte character, the first bit is a 0, followed by its unicode code.
    For n-bytes character, the first n-bits are all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10.
    This is how the UTF-8 encoding would work:

       Char. number range  |        UTF-8 octet sequence
          (hexadecimal)    |              (binary)
       --------------------+---------------------------------------------
       0000 0000-0000 007F | 0xxxxxxx
       0000 0080-0000 07FF | 110xxxxx 10xxxxxx
       0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
       0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
    Given an array of integers representing the data, return whether it is a valid utf-8 encoding.

    Note:
    The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.

    Example 1:

    data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.

    Return true.
    It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
    Example 2:

    data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.

    Return false.
    The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
    The next byte is a continuation byte which starts with 10 and that's correct.
    But the second continuation byte does not start with 10, so it is invalid.
 */

/*
    这题思路很简单，所给的数字序列必须满足以下4种情况之一
    1. 0xxxxxxx
    2. 110xxxxx 10xxxxxx
    3. 1110xxxx 10xxxxxx 10xxxxxx
    4. 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 */
//Solution1: brute force
class Solution {
    public boolean validUtf8(int[] data) {
        if (data == null || data.length == 0)
            return false;
        boolean isValid = true;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > 255)
                return false;
            int numeberOfBytes = 0;
            if ((data[i] & 128) == 0) { // 0xxxxxxx, 1 byte, 128(10000000)
                numeberOfBytes = 1;
            } else if ((data[i] & 224) == 192) { // 110xxxxx, 2 bytes, 224(11100000), 192(11000000)
                numeberOfBytes = 2;
            } else if ((data[i] & 240) == 224) { // 1110xxxx, 3 bytes, 240(11110000), 224(11100000)
                numeberOfBytes = 3;
            } else if ((data[i] % 248) == 240) { // 11110xxx, 4 bytes, 248(11111000), 240(11110000)
                numeberOfBytes = 4;
            } else {
                return false;
            }

            for (int j = 1; j < numeberOfBytes; j++) { // check that the next n bytes start with 10xxxxxx
                if (i + j >= data.length)
                    return false;
                if ((data[i + j] & 192) != 128)
                    return false; // 192(11000000), 128(10000000)
            }
            i = i + numeberOfBytes - 1;
        }
        return isValid;
    }
}

/*
    这种方法是要记连续1的个数，
        如果是标识字节，先将其向右平移五位，如果得到110，则说明后面跟了一个字节，否则向右平移四位，
        如果得到1110，则说明后面跟了两个字节，否则向右平移三位，
        如果得到11110，则说明后面跟了三个字节，否则向右平移七位，
        如果为1的话，说明是10000000这种情况，不能当标识字节，直接返回false。
        在非标识字节中，向右平移六位，如果得到的不是10，则说明不是以10开头的，直接返回false，否则count自减1，成功完成遍历返回true
 */
//Solution2: more concise
public boolean validUtf8(int[] data) {
    int count = 0;
    for(int d:data){
        if(count == 0){
            if((d >> 5) == 0b110)
                count = 1;
            else if((d >> 4) == 0b1110)
                count = 2;
            else if((d >> 3) == 0b11110)
                count = 3;
            else if((d >> 7) ==  1)
                return false;
        } else {
            if((d>>6) != 0b10) return false;
            else count--;
        }
    }
    return count == 0;
}