/*
	Valid Number
	Validate if a given string is numeric.

	Some examples:
	"0" => true
	" 0.1 " => true
	"abc" => false
	"1 a" => false
	"2e10" => true

	Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.

	Tags: Math, String
*/


/*
    Solution1: so complicated

	1. 移除前面的whitespace
	2. ignoring the symbol
	3. remove the postfix whitespace
    4. record the position of 'dot' and record the position of 'e';
    5. base on the position of dot and e ,consider all the case of  the number will be
        case1:xxx
        case2:xxx.yy
        case3:xxxeyy,xxxe-yy
        case4:xxx.yyezz

	//XX.XXeXX
*/
public class Solution {
    public boolean isNumber(String s) {
        int i = 0;

       //1.remove the white space in front
        while (s.charAt(i) == ' ') {
            i++;
            if (i >= s.length())
                return false;
        }


        //2.ignoring the symbol
        if (s.charAt(i) == '+' ||s.charAt(i) == '-')
            i++;

        //3.remove the postfix whitespace,iterative from the end to front,
        //if i <= j, use substring(i, j),or return false
        //
        int j = s.length() - 1;
        while (s.charAt(j) == ' ')
            j--;
            
        if (i <= j)
            s = s.substring(i, j + 1);
        else 
            return false;

        //4. record the position of 'dot' and record the position of 'e';
        int dot = -1;
        int ee = -1;

        for (i = 0; i < s.length(); i++) {
            if (dot == -1 && s.charAt(i) == '.')//find the position of 'e'
                dot = i;
            else if (ee == -1 && s.charAt(i) == 'e') {//find the position of '.'
                ee = i;
                //the symbol of '-' or '+',after symbol 'e'
                if (i + 1 < s.length() && (s.charAt(i + 1) == '-' || s.charAt(i + 1) == '+'))
                    i++;
            }
            //If is a digit, just continue to next loop.
            else {
                if (Character.isDigit(s.charAt(i)))
                    continue;
                else 
                    return false;
            }
        }

        //xxx.xxexx
        //根据 startStr midStr 和lastStr来判断
        String startStr; //.号前面的数
        String midStr;  //.号或者e号
        String lastStr;//mid后面的数字
        
        
        //case1: xxx--- 100
        if (dot == -1 && ee == -1) { 
            startStr = s;   //xxx
            if (startStr.length() < 1)
                return false;
                
        //case2: xxx.yyy --- 100.12     
        } else if (dot != -1 && ee == -1) { 
            startStr = s.substring(0, dot); //xxx
            midStr = s.substring(dot +1);   //yyy
            if (startStr.length() < 1 && midStr.length() < 1)
                return false;
                
        //case3: xxxeyyy --- 100e2
        } else if (dot == -1 && ee != -1) {
            //xxxezz,判断前面的字符
            startStr = s.substring(0, ee);
            if (startStr.length() < 1)
                return false;
                
            //xxxe-zz,e后面跟着-号或者+号的情况 100e(-2)，判断后面的字符
            //如果e后面跟着 - 或者 +,则后面的字符位置为ee + 2开始
            if (ee + 1 < s.length() && (s.charAt(ee + 1) == '-' || s.charAt(ee + 1) == '+')) 
                lastStr = s.substring(ee + 2);
            else //e后面没跟着 - 或则 + 则后面的字符位置为ee + 1 开始
                lastStr = s.substring(ee + 1);

            if (lastStr.length() < 1)
                return false;
                
        //case4: xxx.yyezz 100.12 e 2
        } else {
            //dot位置比ee还后面 必错
            if (dot > ee)
                return false;
            startStr = s.substring(0, dot);     //xxx
            midStr = s.substring(dot + 1, ee);  //yy
            if (startStr.length() < 1 && midStr.length() < 1)
                return false;
            if (ee + 1 < s.length() && (s.charAt(ee + 1) == '-' || s.charAt(ee + 1) == '+')) 
                lastStr = s.substring(ee + 2);
            else
                lastStr = s.substring(ee + 1);

            if (lastStr.length() < 1)
                return false;
        }
        return true;
    }
}


//Solution2:
/*
    We start with trimming.

    If we see [0-9] we reset the number flags.
    We can only see . if we didn’t see e or ..
    We can only see e if we didn’t see e but we did see a number. We reset numberAfterE flag.
    We can only see + and - in the beginning and after an e
    any other character break the validation.
    At the and it is only valid if there was at least 1 number and if we did see an e then a number after it as well.
*/
class Solution {
    public boolean isNumber(String s) {
        s = s.trim();
        
        boolean pointSeen = false;
        boolean eSeen = false;
        boolean numberSeen = false;
        boolean numberAfterE = true;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' <= c && c <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if (c == '.') {
                if (eSeen || pointSeen)
                    return false;
                pointSeen = true;
            } else if (c == 'e') {
                if (eSeen || !numberSeen)
                    return false;
                numberAfterE = false;
                eSeen = true;
            } else if (c == '-' || c == '+') {
                if (i != 0 && s.charAt(i - 1) != 'e')
                    return false;
            } else {
                return false;
            }
        }
        return numberSeen && numberAfterE;
    }
}


// Solution3: same with 2, more readable， prefer
class Solution {
    public boolean isNumber(String s) {
        boolean seenNum = false;
        boolean seenE = false;
        boolean seenD = false;
        
        s = s.trim();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            switch(c) {
                case '.' :
                    if (seenE || seenD)
                        return false;
                    seenD = true;
                    break;
                case 'e' :
                    if (seenE || !seenNum)
                        return false;
                    seenE = true;
                    seenNum = false;
                    break;
                case '+':
                case '-':
                    if (i != 0 && s.charAt(i - 1) != 'e') // +和- 如果在中间，则一定要follow by e
                        return false;
                    seenNum = false;
                    break;
                default :
                    if (c - '0' < 0 || c - '0' > 9)
                        return false;
                    seenNum = true;
            }
        }
        return seenNum;
    }