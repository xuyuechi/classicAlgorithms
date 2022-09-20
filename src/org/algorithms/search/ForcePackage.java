package org.algorithms.search;

import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ForcePackage {

    public static void forcePackage(int[] value,int[] weight,int limit) throws IOException, ClassNotFoundException {
        LinkedList<LinkedList<Integer>> choice = BRGC(weight.length);
        int maxValue = -1;
        int myChoice = -1;
        boolean flag = false;
        for(int k=0;k<choice.size();k++){
            LinkedList<Integer> list = choice.get(k);
            int tempValue = 0;
            int tempWeight = 0;
            for(int i=0;i<list.size();i++){
                if(list.get(i) == 1){
                    tempValue+=value[i];
                    tempWeight+=weight[i];
                    if(tempWeight>limit){
                        flag = true;
                        break;
                    }
                }
            }
            if(tempValue>maxValue && !flag){
                maxValue = tempValue;
                myChoice = k;
            }
        }
        System.out.print("价值最大化应选择的物品：");
        LinkedList<Integer> finalChoice = choice.get(myChoice);
        for(int j=0;j<finalChoice.size();j++){
            if(finalChoice.get(j)==1){
                System.out.print((j+1)+"号");
                if(j<(finalChoice.size()-1))
                    System.out.print(",");
            }

        }
        System.out.println();
        System.out.println("最大价值为："+maxValue);
    }
    public static LinkedList<LinkedList<Integer>> BRGC(int n) throws IOException, ClassNotFoundException {
        //二进制反射格雷码
        if(n==1){
            LinkedList<LinkedList<Integer>> L = new LinkedList<>();
            LinkedList<Integer> zero = new LinkedList<>();
            LinkedList<Integer> first = new LinkedList<>();
            zero.add(0);
            first.add(1);
            L.add(zero);
            L.add(first);
            return L;
        }
        else{
            LinkedList<LinkedList<Integer>> L1 = BRGC(n-1);
            LinkedList<LinkedList<Integer>> L2 = deepCopy(L1);
            Collections.reverse(L2);
            for(LinkedList<Integer> list:L1){
                list.add(0,0);
            }
            for(LinkedList<Integer> list:L2){
                list.add(0,1);
            }
            L1.addAll(L2);
            return L1;
        }
    }

    public static <T> LinkedList<T> deepCopy(LinkedList<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        LinkedList<T> dest = (LinkedList<T>) in.readObject();
        return dest;
    }

    @Test
    public void testForPackage(){
        int[] value = new int[]{42,12,40,25};
        int[] weight = new int[]{7,3,4,5};
        int limit = 10;
        try {
            forcePackage(value,weight,limit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
