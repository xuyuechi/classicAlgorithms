package org.algorithms.labuladong;

import java.util.*;

public class Main {

//    if(head.next==null || head.next.next==null)
//            return head;
//    ListNode fast = head;
//    ListNode slow = head;
//    ListNode superSlow = head;
//    ListNode newHead = head;
//    ListNode originalFast = head;
//    ListNode lateFast = new ListNode();
//    boolean first = true;
//        while(fast.next!=null){
//        superSlow = slow;
//        slow=slow.next;
//        if(slow.next.next!=null)
//            fast = slow.next.next;
//        else
//            fast = slow.next;
//        originalFast = slow.next;
//        lateFast.next = originalFast;
//        slow.next = fast.next;
//        fast.next = superSlow;
//        slow = slow.next;
//        if(first){
//            newHead = lateFast.next;
//            first = false;
//        }
//        lateFast = slow;
//    }

    class ListNode{
        int val;
        ListNode next;
    }

    public ListNode reorderList (ListNode head) {
        if(head.next==null || head.next.next==null)
            return head;
        ListNode slow = head;
        int step = 0;
        while(slow!=null){
            if(slow.next==null){
                break;
            }
            if(slow.next.next == null){
                break;
            }
            if(slow.next.next.next==null){
                int temp = slow.val;
                slow.val = slow.next.next.val;
                slow.next.next.val = temp;
                break;
            }
            if(slow.next.next.next.next!=null){
                int temp = slow.val;
                slow.val = slow.next.next.val;
                slow.next.next.val = temp;

                slow = slow.next;

                temp = slow.val;
                slow.val = slow.next.next.val;
                slow.next.next.val = temp;
            }
            slow = slow.next.next.next;
        }
        return head;
    }

    private static int res;

    public static void main(String[] args) {
        res = Integer.MAX_VALUE;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n+1];
        int[] b = new int[n+1];
        boolean[] used = new boolean[n+1];
        for(int i=1;i<=n;i++){
            a[i] = scanner.nextInt();
        }
        for(int i=1;i<=n;i++){
            b[i] = scanner.nextInt();
        }
        backTrack(1,n,used,a,b,new LinkedList<>());
        System.out.println(res);
    }

    public static void backTrack(int index,int n,boolean[] used,int[] a,int[] b,LinkedList<Integer> track){
        if(index == n+1){
            int total = 0;
            for(int k=0;k<track.size();k++){
                total +=  Math.abs(track.get(k)-a[k+1]);
            }
            res = Math.min(res,total);
            return;
        }
        for(int i=1;i<=n;i++){
            if(used[i])
                continue;
            boolean flag = true;
                for(int p=0;p<track.size();p++){
                    if(b[index-1]>b[p+1] && i>=track.get(p))
                        flag = false;
                }
            if(!flag)
                continue;
            track.addLast(i);
            used[i] = true;
            backTrack(index+1,n,used,a,b,track);
            used[i] = false;
            track.pollLast();
        }
    }

}
