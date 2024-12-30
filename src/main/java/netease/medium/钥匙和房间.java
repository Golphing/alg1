package netease.medium;

import java.util.*;

/**
 * 有 n 个房间，房间按从 0 到 n - 1 编号。最初，除 0 号房间外的其余所有房间都被锁住。你的目标是进入所有的房间。然而，你不能在没有获得钥匙的时候进入锁住的房间。
 *
 * 当你进入一个房间，你可能会在里面找到一套 不同的钥匙，每把钥匙上都有对应的房间号，即表示钥匙可以打开的房间。你可以拿上所有钥匙去解锁其他房间。
 *
 * 给你一个数组 rooms 其中 rooms[i] 是你进入 i 号房间可以获得的钥匙集合。如果能进入 所有 房间返回 true，否则返回 false。
 *
 *
 *
 * 思路：
 *  只需要记录所有获得的钥匙就行,并不是按顺序
 *  String[] roomKeys = input.split(";", -1);
 *
 */
public class 钥匙和房间 {
    public static boolean canGetInAll(List<List<Integer>> roomKeys) {
        boolean[] visited = new boolean[roomKeys.size()];
        boolean[] getK = new boolean[roomKeys.size()];
        getK[0]=true;
        List<Integer> keys = roomKeys.get(0);
        LinkedList<Integer> queue = new LinkedList<>();
        for(Integer k : keys){
            queue.offer(k);
        }
        while(!queue.isEmpty()){
            int room = queue.poll();
            getK[room]=true;
            if(!visited[room]){
                visited[room] = true;
                List<Integer> rk = roomKeys.get(room);
                for(int a : rk){
                    queue.offer(a);
                }
            }
        }
        for(boolean c : getK){
            if(!c){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input = input.replace("[","").replace("]","");
        String[] roomKeys = input.split(";", -1);
        List<List<Integer>> roomKList = new ArrayList<>();
        for(String k : roomKeys){
            List<Integer> rk = new ArrayList<>();
            if(k==null || k.isEmpty()){
                roomKList.add(rk);
                continue;
            }else {
                for(String a : k.split(",")){
                    if(a!=null && !a.isEmpty()){
                        rk.add(Integer.parseInt(a));
                    }
                }
            }
            roomKList.add(rk);
        }
        System.out.println(canGetInAll(roomKList));
    }
}
