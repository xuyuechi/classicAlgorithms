package org.algorithms.tree;

import java.util.*;

public class Main {

    public static int max = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = scanner.nextInt();
        }
        bring(arr,0,0,new LinkedList<>(),k);
        System.out.println(max);
//        if(n==1){
//            System.out.println(0);
//            return;
//        }
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = scanner.nextInt();
//        }
//        int maxVolume = Integer.MIN_VALUE;
//        for(int i=0;i<n-1;i++) {
//            int p = i;
//            int q = i+1;
//            while (p >= 0 && q < n) {
//                int leftIndex = find(p,arr,true);
//                int rightIndex = find(q,arr,false);
//                if(leftIndex!=-1 && rightIndex!=-1){
//                    p = leftIndex;
//                    q = rightIndex;
//                }
//                else if(leftIndex!=-1){
//                    p = leftIndex;
//                }
//                else if(rightIndex!=-1){
//                    q = rightIndex;
//                }
//                else{
//                    break;
//                }
//            }
//            System.out.println("p="+p+",q="+q+",arr[p]="+arr[p]+",arr[q]="+arr[q]+",volume:"+Math.min(arr[p],arr[q])*(q-p));
//            maxVolume = Math.max(Math.min(arr[p],arr[q])*(q-p),maxVolume);
//        }
//        System.out.println(maxVolume);
    }

    public static void bring(int[] stones,int now,int value,LinkedList<Integer> choose,int k){
        if(value>max){
            max = value;
        }
        if(now == stones.length){
            return;
        }
        bring(stones,now+1,value,choose,k);

        choose.addLast(now);
        if(choose.size()!=0 && choose.size()%3==0){
            bring(stones,now+1,value+stones[now]+k,choose,k);
            choose.pollLast();
        }
        else{
            bring(stones,now+1,value+stones[now],choose,k);
            choose.pollLast();
        }
    }

    public static int find(int start, int[] arr, boolean dir) {
        //向0搜索
        if (dir) {
            for (int i = start - 1; i >= 0; i--) {
                if (arr[i] >= arr[start])
                    return i;
            }
            return -1;
        }
        //向1搜索
        else {
            for (int i = start + 1; i < arr.length; i++) {
                if (arr[i] >= arr[start])
                    return i;
            }
            return -1;
        }
    }
}

