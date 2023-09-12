package org.algorithms.labuladong;

import org.algorithms.sort.ListNode;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

//学剑篇 1.1数组/链表
public class ArrayAndLinkedList {

    @Test
    public void test88(){
        merge(new int[]{4,0,0,0,0,0},1,new int[]{1,2,3,5,6},5);
    }

    //leetcode 23
    public ListNode mergeKLists2(ListNode[] lists){
        if(lists.length == 0)
            return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode listNode, ListNode t1) {
                return listNode.val - t1.val;
            }
        });
        for(ListNode list:lists){
            ListNode p = list;
            while(p!=null) {
                queue.add(p);
                p = p.next;
            }
        }
        ListNode dummy = new ListNode(-1);
        ListNode q = dummy;
        while(!queue.isEmpty()){
            q.next = queue.poll();
            q = q.next;
        }
        return dummy.next;
    }

    //leetcode 88 *
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0;
        int p2 = 0;
        int end = m;
        while(p1<end && p2<n){
            if(nums2[p2]<=nums1[p1]){
                move(p1,end,nums1);
                nums1[p1] = nums2[p2];
                p2++;
                end++;
            }
            else{
                p1++;
            }
        }
        if(p1 == end && p2<n){
            for(int i=p2;i<n;i++){
                nums1[end] = nums2[i];
                end++;
            }
        }
    }

    public void move(int i,int end,int[] nums1){
        while(end>i){
            nums1[end] = nums1[end-1];
            end--;
        }
    }

    //leetcode 25
    public ListNode reverseKGroup(ListNode head,int k){
        ListNode a = head;
        ListNode b = head;
        for(int i=0;i<k;i++){
            if(b==null)
                return head;
            b = b.next;
        }
        ListNode newHead = reverseBetween(a,b);
        a.next = reverseKGroup(b,k);
        return newHead;
    }
    public ListNode reverseBetween(ListNode a,ListNode b){
        ListNode pre = null;
        ListNode cur = a;
        ListNode nxt;
        while(cur!=b){
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    public ListNode successor = null;
    public ListNode reverseN(ListNode head,int n){
        if(n == 1){
            successor = head.next;
            return head;
        }
        ListNode last = reverseN(head.next,n-1);
        head.next.next = head;
        head.next = successor;
        return last;
    }

    //leetcode 92
    public ListNode reverseBetween(ListNode head,int left,int right){
        if(left == 1)
            return reverseN(head,right);
        else{
            head.next = reverseBetween(head.next,left-1,right-1);
        }
        return head;
    }

    //leetcode 206
    public ListNode superReverseList(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode last = superReverseList(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    //leetcode 206
    public ListNode advancedReverseList(ListNode head){
        if(head == null)
            return null;
        ListNode p = head;
        ListNode pioneer = p.next;
        p.next = null;
        while(pioneer!=null){
            ListNode temp = pioneer.next;
            pioneer.next = p;
            p = pioneer;
            pioneer = temp;
        }
        return p;
    }

    //leetcode 206
    public ListNode reverseList(ListNode head){
        if(head == null)
            return null;
        ListNode backup = head,p = head;
        int len = 0;
        while(p!=null) {
            p = p.next;
            len++;
        }
        p = backup;
        for(int i=len;i>0;i--){
            for(int j=0;j<i-1;j++){
                int temp = p.val;
                p.val = p.next.val;
                p.next.val = temp;
                p = p.next;
            }
            p = backup;
        }
        return backup;
    }

    //leetcode 870
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        PriorityQueue<int[]> maxNums2 = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return t1[1] - ints[1];
            }
        });
        for(int i=0;i<n;i++){
            maxNums2.offer(new int[]{i,nums2[i]});
        }
        Arrays.sort(nums1);
        int left = 0,right = n - 1;
        int[] res = new int[n];
        while (!maxNums2.isEmpty()){
            int[] pair = maxNums2.poll();
            if (nums1[right] <= pair[1]) {
                res[pair[0]] = nums1[left];
                left++;
            } else {
                res[pair[0]] = nums1[right];
                right--;
            }
        }
        return res;
    }

    @Test
    public void testForShuffle(){
        System.out.println(Arrays.toString(advantageCount(new int[]{12,24,8,32},new int[]{13,25,32,11})));
    }

    //leetcode 410
    public int splitArray(int[] nums, int k) {
        return shipWithinDays(nums, k);
    }

    public int shipTime(int[] weights, int deadweight) {
        long cargo = 0;
        int days = 0;
        for (int i = 0; i < weights.length; i++) {
            cargo += weights[i];
            if (cargo > deadweight) {
                days++;
                cargo = weights[i];
            }
        }
        days++;
        return days;
    }

    //leetcode 1011
    public int shipWithinDays(int[] weights, int days) {
        int left = Arrays.stream(weights).max().getAsInt();
        int right = Arrays.stream(weights).sum();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int d = shipTime(weights, mid);
            if (d == days)
                right = mid - 1;
            else if (d > days)
                left = mid + 1;
            else if (d < days)
                right = mid - 1;
        }
        return left;
    }

    public long needTime(int[] piles, int k) {
        long hours = 0;
        for (int i = 0; i < piles.length; i++) {
            hours += piles[i] / k + ((piles[i] % k) > 0 ? 1 : 0);
        }
        return hours;
    }

    //leetcode 875
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long hours = needTime(piles, mid);
            if (hours == h)
                right = mid - 1;
            else if (hours > h)
                left = mid + 1;
            else if (hours < h)
                right = mid - 1;
        }
        return left;
    }

    @Test
    public void testBanana() {
//        minEatingSpeed(new int[]{3,6,7,11},8);
    }

    //leetcode 5
    public String longestPalindrome(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            String s1 = palindrome(s, i, i);
            String s2 = palindrome(s, i, i + 1);
            if (Math.max(s1.length(), s2.length()) > ans.length())
                if (s2.length() > s1.length())
                    ans = s2;
                else
                    ans = s1;
        }
        return ans;
    }

    public String palindrome(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }


    //leetcode 344
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
            left++;
            right--;
        }
    }

    //leetcode 167
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target)
                return new int[]{left + 1, right + 1};
            else if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            }
        }
        return null;
    }

    public int left_bound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        if (left == nums.length)
            return -1;
        return nums[left] == target ? left : -1;
    }

    public int right_bound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        if (right < 0)
            return -1;
        return nums[right] == target ? right : -1;
    }

    //leetcode 704
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1;
            else if (nums[mid] > target)
                right = mid - 1;
        }
        return -1;
    }

    //leetcode 34
    public int[] searchRange(int[] nums, int target) {
        int left = left_bound(nums, target);
        int right = right_bound(nums, target);
        return new int[]{left, right};
    }

    //leetcode 3
    public int advancedLengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, res = 0;
        while (right < s.length()) {
            Character c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) {
                Character leave = s.charAt(left);
                left++;
                window.put(leave, window.get(leave) - 1);
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

    @Test
    public void testForLOLS() {
        String s = "pwwkew";
        System.out.println(lengthOfLongestSubstring(s));
    }

    //leetcode 3
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, len = 0;
        while (right < s.length()) {
            Character c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (shrink(window)) {
                Character leave = s.charAt(left);
                left++;
                window.put(leave, window.get(leave) - 1);
            }
            if (right - left > len)
                len = right - left;
        }
        return len;
    }

    public boolean shrink(HashMap<Character, Integer> window) {
        for (Character k : window.keySet()) {
            if (!window.get(k).equals(0) && !window.get(k).equals(1))
                return true;
        }
        return false;
    }

    //leetcode 438
    public List<Integer> findAnagrams(String s, String p) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            Character c = p.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0, valid = 0;
        LinkedList<Integer> starts = new LinkedList<>();
        while (right < s.length()) {
            Character c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++;
            }
            while (right - left >= p.length()) {
                if (valid == need.size())
                    starts.add(left);
                Character leave = s.charAt(left);
                left++;
                if (need.containsKey(leave)) {
                    if (window.get(leave).equals(need.get(leave)))
                        valid--;
                    window.put(leave, window.get(leave) - 1);
                }
            }
        }
        return starts;
    }

    //leetcode 567
    public boolean advancedCheckInclusion(String s1, String s2) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            Character c = s1.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0, valid = 0;
        while (right < s2.length()) {
            Character c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++;
            }
            while (right - left >= s1.length()) {
                if (valid == need.size())
                    return true;
                Character leave = s2.charAt(left);
                left++;
                if (need.containsKey(leave)) {
                    if (window.get(leave).equals(need.get(leave)))
                        valid--;
                    window.put(leave, window.get(leave) - 1);
                }
            }
        }
        return false;
    }

    //leetcode 567
    public boolean checkInclusion(String s1, String s2) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            Character c = s1.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        boolean flag;
        while (right < s2.length()) {
            Character c = s2.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (right - left >= s1.length()) {
                flag = true;
                for (Character k : window.keySet()) {
                    if (!need.getOrDefault(k, 0).equals(window.getOrDefault(k, 0))) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    return true;
                Character leave = s2.charAt(left);
                left++;
                window.put(leave, window.get(leave) - 1);
            }
        }
        return false;
    }

    //leetcode 76
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            Character c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        int start = 0, len = Integer.MAX_VALUE;
        int valid = 0;
        while (right < s.length()) {
            Character c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++;
            }
            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                Character leave = s.charAt(left);
                left++;
                if (need.containsKey(leave)) {
                    if (need.get(leave).equals(window.get(leave)))
                        valid--;
                    window.put(leave, window.get(leave) - 1);
                }
            }
        }
        if (len == Integer.MAX_VALUE)
            return "";
        else
            return s.substring(start, start + len);
    }

    //leetcode 283
    public void moveZeroes(int[] nums) {
        int len = removeElement(nums, 0);
        for (int i = len; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    //leetcode 27
    public int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    //leetcode 83
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        ListNode slow = head, fast = head, backup = head;
        while ((fast = fast.next) != null) {
            if (fast.val == slow.val)
                continue;
            slow = slow.next;
            slow.val = fast.val;
        }
        slow.next = null;
        return backup;
    }

    //leetcode 26
    public int removeDuplicates(int[] nums) {
        int slow = 0, fast = 0;
        while (++fast < nums.length) {
            if (nums[fast] == nums[slow])
                continue;
            slow++;
            nums[slow] = nums[fast];
        }
        return ++slow;
    }

    @Test
    public void testForRemoveElement() {
        int[] n = new int[]{3, 2, 2, 3};
        int i = removeElement(n, 3);
        System.out.println(i);
        System.out.println(Arrays.toString(n));
    }

    //leetcode 160
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pA = headA, pB = headB;
        while (pA != null && pB != null) {
            if (pA == pB)
                return pA;
            if (pA.next == null && pB.next == null) {
                return null;
            } else if (pA.next == null) {
                pA = headB;
                pB = pB.next;
            } else if (pB.next == null) {
                pB = headA;
                pA = pA.next;
            } else {
                pA = pA.next;
                pB = pB.next;
            }
        }
        return null;
    }

    //leetcode 142
    public ListNode AdvancedDetectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    //leetcode 142
    public ListNode detectCycle(ListNode head) {
        ListNode host = head;
        HashSet<ListNode> beenTo = new HashSet<>();
        while (host != null) {
            ListNode dog = host;
            while (dog.next != null) {
                dog = dog.next;
                if (dog == host)
                    return host;
                else if (beenTo.contains(dog))
                    break;
                else
                    beenTo.add(dog);
            }
            beenTo.clear();
            host = host.next;
        }
        return null;
    }

    @Test
    public void testForDetectCycle() {
//        ListNode head = new ListNode(3);
//        ListNode backup = head;
//        head.next = new ListNode(2);
//        head = head.next;
//        ListNode start = head;
//        head.next = new ListNode(0);
//        head = head.next;
//        head.next = new ListNode(-4);
//        head = head.next;
//        head.next = start;
        detectCycle(null);
    }

    //leetcode 141
    public boolean hasCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null && slow != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //leetcode 876
    public ListNode middleNode(ListNode head) {
        ListNode pre = head, p = head;
        while (pre != null && pre.next != null) {
            pre = pre.next.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 19
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy, p = dummy;
        for (int i = 0; i < n + 1; i++) {
            pre = pre.next;
        }
        while (pre != null) {
            pre = pre.next;
            p = p.next;
        }
        p.next = p.next.next;
        return dummy.next;
    }

    //leetcode Offer22
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode pre = head, p = head;
        for (int i = 0; i < k; i++) {
            pre = pre.next;
        }
        while (pre != null) {
            pre = pre.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 23
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode(-1), p = dummy;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode listNode, ListNode t1) {
                return listNode.val - t1.val;
            }
        });
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }
        while (!pq.isEmpty()) {
            ListNode least = pq.poll();
            p.next = least;
            p = p.next;
            if (least.next != null)
                pq.add(least.next);
        }
        return dummy.next;
    }

    //leetcode 86
    public ListNode partition(ListNode head, int x) {
        ListNode smaller = new ListNode(-1);
        ListNode bigger = new ListNode(-1);
        ListNode p1 = smaller, p2 = bigger;
        ListNode p = head;
        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
            } else {
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
    public ListNode mergeTwoList(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        ListNode p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            if (p1.val > p2.val) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
            p = p.next;
        }
        if (p1 == null) {
            p.next = p2;
        }
        if (p2 == null) {
            p.next = p1;
        }
        return dummy.next;
    }

    //差分数组
    public class Difference {
        int[] array;
        int[] diff;
        int size;

        public Difference(int[] arr) {
            array = arr;
            size = array.length;
            diff = new int[size];
            diff[0] = array[0];
            for (int i = 1; i < size; i++) {
                diff[i] = array[i] - array[i - 1];
            }
        }

        public void increment(int i, int j, int k) {
            diff[i] += k;
            if (j < size - 1)
                diff[j + 1] -= k;
        }

        public int[] result() {
            array[0] = diff[0];
            for (int i = 1; i < size; i++) {
                array[i] = array[i - 1] + diff[i];
            }
            return array;
        }
    }

    //leetcode 1109
    public int[] corpFlightBookings(int[][] bookings, int n) {
        Difference dif = new Difference(new int[n]);
        for (int[] op : bookings) {
            dif.increment(op[0] - 1, op[1] - 1, op[2]);
        }
        return dif.result();
    }

    //leetcode 1094
    public boolean carPooling(int[][] trips, int capacity) {
        Difference dif = new Difference(new int[1001]);
        for (int[] trip : trips) {
            dif.increment(trip[1], trip[2] - 1, trip[0]);
        }
        int[] result = dif.result();
        for (int i = 0; i < result.length; i++) {
            if (result[i] > capacity)
                return false;
        }
        return true;
    }

    @Test
    public void testForDifference() {
        System.out.println(carPooling(new int[][]{{2, 1, 5}, {3, 5, 7}}, 3));
    }

    //leetcode 304 二维数组前缀和
    public class NumMatrix {
        int[][] myMatrix;
        int height = 0;
        int width = 0;
        int[][] preSum;

        public NumMatrix(int[][] matrix) {
            myMatrix = matrix;
            height = matrix.length;
            width = matrix[0].length;
            preSum = new int[height + 1][width + 1];
            sumPre();
        }

        public void sumPre() {
            for (int i = 0; i < width + 1; i++) {
                preSum[0][i] = 0;
            }
            for (int j = 0; j < height + 1; j++) {
                preSum[j][0] = 0;
            }
            for (int i = 1; i < height + 1; i++) {
                for (int j = 1; j < width + 1; j++) {
                    preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] + myMatrix[i - 1][j - 1] - preSum[i - 1][j - 1];
                }
            }
            for (int i = 0; i < height + 1; i++) {
                for (int j = 0; j < width + 1; j++) {
                    System.out.printf("%4d", preSum[i][j]);
                }
                System.out.println();
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return preSum[row2 + 1][col2 + 1] - preSum[row2 + 1][col1] - preSum[row1][col2 + 1] + preSum[row1][col1];
        }

    }

    //leetcode 303 一维数组的前缀和
    public class NumArray {
        private int[] array;
        private int[] preSum;
        private int size;

        public NumArray(int[] nums) {
            array = nums;
            size = nums.length;
            preSum = new int[size + 1];
            sumPre();
        }

        public void sumPre() {
            preSum[0] = 0;
            for (int i = 1; i <= size; i++) {
                preSum[i] = preSum[i - 1] + array[i - 1];
            }
        }

        public int sumRange(int i, int j) {
            return preSum[j + 1] - preSum[i];
        }
    }

    @Test
    public void test() {
        NumMatrix nm = new NumMatrix(new int[][]{{-4, -5}});
        System.out.println(nm.sumRegion(0, 0, 0, 0));
        System.out.println(nm.sumRegion(0, 0, 0, 1));
        System.out.println(nm.sumRegion(0, 1, 0, 1));
    }
}
