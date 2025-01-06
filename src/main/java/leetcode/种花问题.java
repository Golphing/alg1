package leetcode;

/**
 * 605
 * 注意：是判断前后是否为0，而不是判断连续的三个0
 */
public class 种花问题 {
        public boolean canPlaceFlowers(int[] flowerbed, int n) {
            int prev = 0;
            int canPlan = 0;
            for(int i=0;i<flowerbed.length;i++){
                if(i+1 < flowerbed.length){
                    if(prev==0 && flowerbed[i] == 0 && flowerbed[i+1]==0){
                        prev = 1;
                        canPlan++;
                    }else {
                        prev = flowerbed[i];
                    }
                }else {
                    if(prev==0 && flowerbed[i] == 0){
                        canPlan++;
                    }
                }

            }

            return canPlan >= n;
        }
}
