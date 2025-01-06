package leetcode;

/**
 * 345
 * 思路：
 *    双指针，从两端向中间遍历，遇到元音字符就交换,注意交换后指针需要再走一步
 *    转成字符数组
 */
public class 反转字符串中的元音字符1 {
    public String reverseVowels(String s) {
        int left = 0;
        int right = s.length()-1;
        char[] arr = s.toCharArray();
        while(left < right){
            //找左指针
            while(left < right){
                if(isYuan(arr[left])){
                    break;
                }else {
                    left++;
                }
            }
            while(left < right){
                if(isYuan(arr[right])){
                    break;
                }else {
                    right--;
                }
            }
            swap(arr, left, right);
            left++;right--;

        }
        return new String(arr);

    }

    public void swap(char[] arr, int l, int r){
        char t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;
    }
    public boolean isYuan(char c){
        if(c == 'a' || c == 'A'  || c == 'e'  || c == 'E'  || c == 'i'  || c == 'I'  || c == 'o'  || c == 'O'  || c == 'u'  || c == 'U'){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new 反转字符串中的元音字符1().reverseVowels("IceCreAm"));
    }
}
