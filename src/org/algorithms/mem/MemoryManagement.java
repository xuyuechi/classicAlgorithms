package org.algorithms.mem;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryManagement {
    public static class MemoryBlock{
        public int address;//起始地址
        public int size;//大小
        public boolean used;//使用情况

        public MemoryBlock(int address, int size, boolean used) {
            this.address = address;
            this.size = size;
            this.used = used;
        }

        @Override
        public String toString() {
            return "MemoryBlock{" +
                    "address=" + address +
                    ", size=" + size +
                    ", used=" + used +
                    '}';
        }
    }
    //以下涉及内存的数字单位都为KB
    private static final int SIZE = 1;//不可再分割大小
    private static final int TOTAL_SIZE = 64*1024;
    private static ConcurrentHashMap<Integer,MemoryBlock> memory = new ConcurrentHashMap<>();
    private static CopyOnWriteArrayList<MemoryBlock> emptyList = new CopyOnWriteArrayList<>();

    private static void init(){
        System.out.println("连续动态内存分配");
        System.out.println("可用命令：");
        System.out.println("query 查询分配情况");
        System.out.println("ask(size) 请求分配大小为size的内存");
        System.out.println("clear(address) 请求释放地址为address的内存块");
        System.out.println("break 退出");
        MemoryBlock first = new MemoryBlock(0,TOTAL_SIZE,false);
        memory.put(first.address,first);
        emptyList.add(first);
    }

    //内存分配
    private static boolean assign(MemoryBlock block,int requiredSize){
        if(requiredSize>block.size)
            return false;
        else if(block.size - requiredSize < SIZE){
            emptyList.remove(block);
            block.used = true;
            return true;
        }else{
            MemoryBlock fragment = new MemoryBlock(block.address+requiredSize, block.size-requiredSize, false);
            block.size = requiredSize;
            block.used = true;
            emptyList.remove(block);
            emptyList.add(fragment);
            memory.put(fragment.address,fragment);
            return true;
        }
    }

    //内存回收
    private static void withdraw(MemoryBlock block){
        if(!block.used)
            return;
        block.used = false;
        boolean merge = false;
        for(MemoryBlock b:emptyList){
            if(b.address+b.size == block.address){
                b.size += block.size;
                memory.remove(block.address);
                if(emptyList.contains(block))
                    emptyList.remove(block);
                block = b;
                merge = true;
                System.out.println("发生向前合并，合并后大小："+b.size);
            }
            if(block.address+block.size == b.address){
                block.size += b.size;
                emptyList.remove(b);
                memory.remove(b.address);
                if(!emptyList.contains(block))
                    emptyList.add(block);
                merge=true;
                System.out.println("发生向后合并，合并后大小："+block.size);
            }
        }
        if(!merge){
            emptyList.add(block);
        }
    }

    //使用最佳适应算法
    public static MemoryBlock findBest(int requiredSize){
        emptyList.sort(new Comparator<MemoryBlock>() {
            @Override
            public int compare(MemoryBlock block, MemoryBlock t1) {
                if(block.size>t1.size)
                    return 1;
                else if(block.size==t1.size)
                    return 0;
                else
                    return -1;
            }
        });
        for(int i=0;i<emptyList.size();i++){
            if(emptyList.get(i).size>=requiredSize){
                return emptyList.get(i);
            }
        }
        return null;
    }

    public static void query(){
        System.out.println("当前空闲内存块：");
        System.out.println(emptyList);
        System.out.println("当前所有内存使用情况：");
        Set<Integer> integers = memory.keySet();
        for(Integer i:integers){
            System.out.println("地址："+i+" 是否已使用："+memory.get(i).used+" 大小："+memory.get(i).size);
        }
    }

    //命令行交互
    public static void command(String command){
        if(command.startsWith("ask(")){
            int requiredSize  = Integer.parseInt(command.substring(command.indexOf("(")+1,command.lastIndexOf(")")));
            System.out.println("请求分配内存，大小："+requiredSize);
            MemoryBlock best = findBest(requiredSize);
            if(best==null){
                System.out.println("内存不足！取消分配");
                return;
            }else{
                boolean assign = assign(best, requiredSize);
                if(assign)
                    System.out.println("分配成功！");
                else
                    System.out.println("分配失败！");
            }
            query();
        }
        if(command.startsWith("clear(")){
            int address  = Integer.parseInt(command.substring(command.indexOf("(")+1,command.lastIndexOf(")")));
            System.out.println("请求释放地址为"+address+"的内存块");
            withdraw(memory.get(address));
            System.out.println("释放成功！");
            query();
        }
        if(command.equals("query")){
            query();
        }
    }

    public static void main(String[] args) {
        init();
        Scanner scan = new Scanner(System.in);
        String comm;
        while(!(comm=scan.nextLine()).equals("break")){
            command(comm);
        }
    }
}
