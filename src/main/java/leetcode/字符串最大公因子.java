package leetcode;

/**
 * 1071
 *
 * 思路：主要是要先判断一下数字是不是能整除，避免做无效判断。然后通过字符串替换后是否为空来实现
 */
public class 字符串最大公因子 {
    public static String gcdOfStrings(String str1, String str2) {
        int l = Math.min(str1.length(), str2.length());
        for(int i=l;i>=0;i--){
            String sub = str1.substring(0, i);
            if(check(sub, str1) && check(sub, str2)){
                return sub;
            }
        }
        return "";
    }

    public static boolean check(String sub, String all){
        if(sub == null || sub.length()==0){
            return false;
        }
        if(all.length() % sub.length() == 0){
            if(all.replaceAll(sub, "").equals("")){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String str1 = "ABCABC";
        String str2 = "ABC";
        System.out.println(gcdOfStrings(str1, str2));
    }
}
