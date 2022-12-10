package org.algorithms.tree;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        String[] s = scanner.nextLine().split(" ");
//        int[] data = new int[s.length];
//        for(int i=0;i<s.length;i++){
//            data[i] = Integer.parseInt(s[i]);
//        }
        int j1 = scanner.nextInt();
        int j2 = scanner.nextInt();
        int t = scanner.nextInt();
        System.out.println(canMeasureWater(j1,j2,t));
    }
    public static boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        int num = gcd(jug1Capacity,jug2Capacity);
        if(targetCapacity%num==0 && targetCapacity<=jug1Capacity+jug2Capacity)
            return true;
        else
            return false;
    }
    public static int gcd(int a,int b){
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }
}

