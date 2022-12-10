package org.algorithms.labuladong;

import org.algorithms.sort.ListNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//学剑篇 1.1数组/链表
public class ArrayAndLinkedList {
    //leetcode 142
    public ListNode detectCycle(){
        return null;
    }
    //leetcode 141
    public boolean hasCycle(ListNode head){
        ListNode fast = head,slow = head;
        while(fast!=null && fast.next!=null && slow!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow){
                return true;
            }
        }
        return false;
    }

    //leetcode 876
    public ListNode middleNode(ListNode head){
        ListNode pre = head, p = head;
        while(pre!=null && pre.next!=null){
            pre = pre.next.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 19
    public ListNode removeNthFromEnd(ListNode head,int n){
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy, p = dummy;
        for(int i=0;i<n+1;i++){
            pre = pre.next;
        }
        while(pre!=null){
            pre = pre.next;
            p = p.next;
        }
        p.next = p.next.next;
        return dummy.next;
    }

    //leetcode Offer22
    public ListNode getKthFromEnd(ListNode head,int k){
        ListNode pre = head, p=head;
        for(int i=0;i<k;i++){
            pre = pre.next;
        }
        while(pre!=null){
            pre = pre.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 23
    public ListNode mergeKLists(ListNode[] lists){
        ListNode dummy = new ListNode(-1), p = dummy;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode listNode, ListNode t1) {
                return listNode.val - t1.val;
            }
        });
        for(ListNode head:lists){
            if(head!=null)
                pq.add(head);
        }
        while(!pq.isEmpty()){
            ListNode least = pq.poll();
            p.next = least;
            p = p.next;
            if(least.next!=null)
                pq.add(least.next);
        }
        return dummy.next;
    }

    //leetcode 86
    public ListNode partition(ListNode head,int x){
        ListNode smaller = new ListNode(-1);
        ListNode bigger = new ListNode(-1);
        ListNode p1 = smaller,p2 = bigger;
        ListNode p = head;
        while(p!=null){
            if(p.val<x){
                p1.next = p;
                p1 = p1.next;
            }else{
                p2.next = p;
                p2 = p2.next;
            }
            ListNode tmp = p;
            p = p.next;
            tmp.next = null;
        }
        p1.next = bigger.next;
        return smaller.next;
    }

    //leetcode 21
    public ListNode mergeTwoList(ListNode l1,ListNode l2){
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        ListNode p1 = l1, p2 = l2;
        while(p1 != null&& p2 != null){
            if(p1.val>p2.val){
                p.next = p2;
                p2 = p2.next;
            }else{
                p.next = p1;
                p1 = p1.next;
            }
            p = p.next;
        }
        if(p1 == null){
            p.next = p2;
        }
        if(p2 == null){
            p.next = p1;
        }
        return dummy.next;
    }

    //差分数组
    public class Difference{
        int[] array;
        int[] diff;
        int size;
        public Difference(int[] arr){
            array = arr;
            size = array.length;
            diff = new int[size];
            diff[0] = array[0];
            for(int i=1;i<size;i++){
                diff[i] = array[i]-array[i-1];
            }
        }

        public void increment(int i,int j,int k){
            diff[i] += k;
            if(j<size-1)
                diff[j+1] -= k;
        }

        public int[] result(){
            array[0] = diff[0];
            for(int i=1;i<size;i++){
                array[i] = array[i-1] + diff[i];
            }
            return array;
        }
    }

    //leetcode 1109
    public int[] corpFlightBookings(int[][] bookings,int n){
        Difference dif = new Difference(new int[n]);
        for(int[] op:bookings){
            dif.increment(op[0]-1,op[1]-1,op[2]);
        }
        return dif.result();
    }

    //leetcode 1094
    public boolean carPooling(int[][] trips,int capacity){
        Difference dif = new Difference(new int[1001]);
        for(int[] trip:trips) {
            dif.increment(trip[1], trip[2]-1, trip[0]);
        }
        int[] result = dif.result();
        for(int i=0;i< result.length;i++){
            if(result[i]>capacity)
                return false;
        }
        return true;
    }

    @Test
    public void testForDifference(){
        System.out.println(carPooling(new int[][]{{2,1,5},{3,5,7}},3));
    }

    //leetcode 304 二维数组前缀和
    public class NumMatrix{
        int[][] myMatrix;
        int height = 0;
        int width = 0;
        int[][] preSum;
        public NumMatrix(int[][] matrix){
            myMatrix = matrix;
            height = matrix.length;
            width = matrix[0].length;
            preSum = new int[height+1][width+1];
            sumPre();
        }
        public void sumPre(){
            for(int i=0;i<width+1;i++){
                preSum[0][i] = 0;
            }
            for(int j=0;j<height+1;j++){
                preSum[j][0] = 0;
            }
            for(int i=1;i<height+1;i++){
                for(int j=1;j<width+1;j++){
                    preSum[i][j] = preSum[i-1][j] + preSum[i][j-1] + myMatrix[i-1][j-1] - preSum[i-1][j-1];
                }
            }
            for(int i=0;i<height+1;i++){
                for(int j=0;j<width+1;j++){
                    System.out.printf("%4d",preSum[i][j]);
                }
                System.out.println();
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2){
            return preSum[row2+1][col2+1] - preSum[row2+1][col1] - preSum[row1][col2+1] + preSum[row1][col1];
        }

    }

    //leetcode 303 一维数组的前缀和
    public class NumArray{
        private int[] array;
        private int[] preSum;
        private int size;
        public NumArray(int[] nums){
            array = nums;
            size = nums.length;
            preSum = new int[size+1];
            sumPre();
        }
        public void sumPre(){
            preSum[0] = 0;
            for(int i=1;i<=size;i++){
                preSum[i] = preSum[i-1] + array[i-1];
            }
        }
        public int sumRange(int i,int j){
            return preSum[j+1]-preSum[i];
        }
    }

    @Test
    public void test(){
        NumMatrix nm = new NumMatrix(new int[][]{{-4,-5}});
        System.out.println(nm.sumRegion(0,0,0,0));
        System.out.println(nm.sumRegion(0,0,0,1));
        System.out.println(nm.sumRegion(0,1,0,1));
    }
}
