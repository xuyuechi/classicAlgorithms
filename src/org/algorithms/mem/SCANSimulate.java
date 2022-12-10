package org.algorithms.mem;

import java.util.LinkedList;

public class SCANSimulate {
    private final int track = 30;//磁道数
    private final int costTime = 100;//单位寻道时间（ms）
    private LinkedList<Integer> request = new LinkedList<>();//请求队列
    private final int startPos = 10;//磁头的起始位置
    //默认先从里向外，再从外向里

    //模拟请求队列
    private void setRequest() {
        request.add(6);
        request.add(8);
        request.add(15);
        request.add(18);
        request.add(15);
        request.add(27);
        request.add(2);
        request.add(22);
        request.add(25);
        request.add(30);
        request.add(14);
        request.add(8);
        request.add(29);
        request.add(21);
        request.add(17);
        request.add(10);
        request.add(23);
    }

    //返回调度序列，参数start：磁头起始位置，req：请求队列
    private LinkedList<Integer> SCAN(int start, LinkedList<Integer> req) {
        boolean toInside = false;//为true时，表示正在从外向里，否则表示正在从里向外
        LinkedList<Integer> result = new LinkedList<>();
        while (!req.isEmpty()) {
            if (start >= track)
                toInside = true;
            else if (start <= 0)
                toInside = false;
            if (!toInside) {
                start++;
                LinkedList<Integer> toRemove = new LinkedList<>();
                for (int i=0;i<req.size();i++){
                    if(req.get(i)==start)
                        toRemove.add(req.get(i));
                }
                req.removeAll(toRemove);
                result.addAll(toRemove);
            }
            if (toInside) {
                start--;
                LinkedList<Integer> toRemove = new LinkedList<>();
                for (int i=0;i<req.size();i++){
                    if(req.get(i)==start)
                        toRemove.add(req.get(i));
                }
                req.removeAll(toRemove);
                result.addAll(toRemove);
            }
        }
        return result;
    }

    //计算总寻道时间
    private int calTime(LinkedList<Integer> result) {
        int totalTime = 0;
        for (int i = 1; i < result.size(); i++) {
            totalTime += (Math.abs(result.get(i) - result.get(i - 1))) * costTime;
        }
        return totalTime;
    }

    public static void main(String[] args) {
        SCANSimulate scan = new SCANSimulate();
        System.out.println("SCAN磁盘调度算法");
        System.out.println("参数设置：");
        System.out.printf("总磁道数：%2d 磁头起始位置：%2d 单位寻道时间：%2d\n", scan.track, scan.startPos, scan.costTime);
        System.out.println("请求队列：");
        scan.setRequest();
        for (int r : scan.request) {
            System.out.printf("%4d", r);
        }
        System.out.println();
        LinkedList<Integer> result = scan.SCAN(scan.startPos, scan.request);
        System.out.println("调度队列：");
        for (int k : result) {
            System.out.printf("%4d", k);
        }
        System.out.println();
        System.out.println("总寻道时间：");
        System.out.println(scan.calTime(result));
    }
}
