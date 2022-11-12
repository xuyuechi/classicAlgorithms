package org.algorithms.sort;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class SortList {
    //注意区分头结点和头指针！！LeetCode上148题目头结点就是有意义值的结点，不是空值结点！！
    public ListNode sortList(ListNode head/*此处头结点，是指有题目给的值的结点，而不是空值（java默认初始化为0）结点！！*/){
        if(head == null || head.next == null)
            return head;
        ListNode fast = head.next,slow = head;
        while(fast!=null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmp = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        ListNode newHead = new ListNode(0);
        ListNode backupNewHead = newHead;
        while(left!=null && right!=null){
            if(left.val < right.val){
                newHead.next = left;
                left = left.next;
            }else{
                newHead.next = right;
                right = right.next;
            }
            newHead = newHead.next;
        }
        newHead.next = left != null ? left:right;
        return backupNewHead.next;
    }



    /*
    public ListNode sortList(ListNode head){
        if(head.next==null)
            return head;
        ListNode next = sortList(head.next);
        ListNode p = next;
        ListNode q = next;
        while(p!=null) {
            if (head.val < p.val && p==next) {
                head.next = p;
                return head;
            }
            if (head.val < q.val) {
                p.next = head;
                head.next = q;
                return next;
            }
            if(q.next==null){
                q.next = head;
                head.next = null;
                return next;
            }
            p=p.next;
            if(p!=null && p.next!=null) {
                q = p.next;
            }
        }
        return null;
    }

    public ListNode sortList(ListNode head) {
        List<Integer> numbers = new LinkedList<>();
        ListNode p;
        while((p=head.next)!=null){
            numbers.add(p.val);
        }
        Integer[] nums = (Integer[]) numbers.toArray();
        quickSort(nums,0,nums.length-1);
        int i=0;
        while((p=head.next)!=null){
            p.val = nums[i];
            i++;
        }
        return head;
    }

    public static Integer hoarePartition(Integer[] arr,int left,int right){
        int pivot = arr[left];
        while(left<right){
            while(arr[right]>=pivot && left<right)right--;
            arr[left]=arr[right];
            while(arr[left]<=pivot && left<right)left++;
            arr[right]=arr[left];
        }
        arr[left] = pivot;
        return left;
    }
    public static void quickSort(Integer[] A,int l,int r){
        if(l<r){
            int s=hoarePartition(A,l,r);
            quickSort(A,l,s-1);
            quickSort(A,s+1,r);
        }
    }
    */
    @Test
    public void Test(){
        ListNode head = new ListNode(0);
        ListNode first = new ListNode(-1);
        ListNode second = new ListNode(5);
        ListNode third = new ListNode(3);
        ListNode forth = new ListNode(4);
        ListNode fifth = new ListNode(0);
        head.next = first;
        first.next = second;
        second.next = third;
        third.next = forth;
        forth.next = fifth;
        fifth.next = null;
        //注意区分头结点和头指针！！LeetCode上148题目头结点就是有意义值的结点，不是空值结点！！
        ListNode afterSort = sortList(head.next);

        while(afterSort!=null){
            System.out.print(afterSort.val+",");
            afterSort = afterSort.next;
        }
    }
}
