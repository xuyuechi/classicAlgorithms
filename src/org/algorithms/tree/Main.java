package org.algorithms.tree;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        String numStr = num+"";
        int len = numStr.length();
        int i = len/2;
        int j = len/2;
        if(len%2==0){
            i--;
        }
        int sum = 0;
        for(int k=0;k<len;k++){
            sum += Integer.parseInt(numStr.charAt(k)+"");
        }
        while(i>=0 && j<=len-1){
            if(numStr.charAt(i)!=numStr.charAt(j)){
                System.out.println(-1);
                return;
            }
            i--;
            j++;
        }
        System.out.println(sum);
    }

    public static int hoarePartition(int[] arr,int left,int right,int p){
        int pivot = arr[p];
        int temp = 0;
        while(left<right){
            while(arr[right]>=pivot && left<right)right--;
            while(arr[left]<=pivot && left<right)left++;
            temp=arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        arr[left] = pivot;
        return left;
    }

    public static boolean transfer(String s,String t){
        if(t.length()<s.length() || (t.length()==s.length() && !s.equals(t)))
            return false;
        int i=0,j=0;
        while(i<s.length() && j<t.length() && s.charAt(i)==t.charAt(j)){
            j++;
            int originalI = i;
            if(j<t.length() && s.charAt(i)!=t.charAt(j)){
                i++;
                while(i<s.length()-1 && s.charAt(i-1)==s.charAt(i))i++;
                for(int k=j-2;k>=j-(i-originalI);k--){
                    if(t.charAt(k)!=t.charAt(k+1))
                        return false;
                }
            }
        }
        if(j>=t.length()-1 && i>=s.length()-1){
            return true;
        }
        return false;
    }
}

