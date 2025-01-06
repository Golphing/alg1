package leetcode;

/**
 * 151
 */
public class 反转字符串中的单词 {
    public static void main(String[] args) {
        System.out.println("a   b c".split(" ").length);
    }

    public String reverseWords(String s) {
        //先去除多余的空格
        //然后分割字符串，然后输出
        String a = "";
        Character prev = null;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == ' '){
                if(prev == null || prev == ' '){
                    continue;
                }else {
                    a += s.charAt(i);
                }
            }else {
                a += s.charAt(i);
            }
            prev = s.charAt(i);
        }
        String[] b = a.split(" ");
        String ret = "";
        for(int i=b.length-1;i>0;i--){
            ret += b[i] + " ";
        }
        ret += b[0];
        return ret;
    }
}
