package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

//学剑篇 1.4数据结构设计
public class DataStructure {

    //leetcode 224
    public int calculate(String s){
        LinkedList<Character> list = new LinkedList<>();
        for(int i=0;i<s.length();i++){
            list.add(s.charAt(i));
        }
        return cal(list);
    }

    public int cal(LinkedList<Character> list){
        Stack<Integer> results = new Stack<>();
        char sign = '+';
        int num = 0;
        while(list.size()>0){
            char c = list.pollFirst();
            if(isDigit(c)){
                num = 10 * num + (c-'0');
            }

            if(c=='(')
                num = cal(list);

            if((!isDigit(c) && c!=' ') || list.size() == 0){
                if(sign == '+')
                    results.push(num);
                else if(sign == '-')
                    results.push(-num);
                else if(sign == '*')
                    results.push(results.peek()*num);
                else if(sign == '/')
                    results.push(results.peek()/num);
                num = 0;
                sign = c;
            }

            if(c == ')')
                break;
        }
        int ans =  0;
        while(!results.isEmpty()){
            ans += results.pop();
        }
        return ans;
    }

    public boolean isDigit(char c){
        if( c - '0' < 10 && c - '0' >= 0)
            return true;
        else
            return false;
    }

    //leetcode 295
    class MedianFinder{

        //大顶堆
        PriorityQueue<Integer> small;

        //小顶堆
        PriorityQueue<Integer> large;
        public MedianFinder(){
            small = new PriorityQueue<>();
            large = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer t1) {
                    return t1 - integer;
                }
            });
        }

        public void addNum(int num){
            if(small.size()>= large.size()){
                small.offer(num);
                large.offer(small.poll());
            }else{
                large.offer(num);
                small.offer(large.poll());
            }
        }

        public double findMedian(){
            if(small.size() == large.size())
                return (small.peek() + large.peek())/2.0;
            else
                return large.peek();
        }
    }

    //leetcode 710
    class AvoidBlackList{
        int[] black;
        HashMap<Integer,Integer> map;
        int sz;
        public AvoidBlackList(int n,int[] blacklist){
            black = blacklist;
            sz = n - blacklist.length;
            map = new HashMap<>();
            int last = n-1;
            for(int i:black){
                map.put(i,666);
            }
            for(int i:black){
                while(map.containsKey(last)){
                    last--;
                }
                if(i >= sz)
                    continue;
                map.put(i,last);
                last--;
            }
        }

        public int pick(){
            int toIndex = Math.abs(new Random().nextInt(sz));
            if(map.containsKey(toIndex)){
                return map.get(toIndex);
            }
            return toIndex;
        }
    }

    //leetcode 380
    class RandomizedSet{
        ArrayList<Integer> base;
        HashMap<Integer,Integer> valToIndex;

        public RandomizedSet(){
            base = new ArrayList<>();
            valToIndex = new HashMap<>();
        }
        public boolean insert(int val){
            if(valToIndex.containsKey(val))
                return false;
            base.add(val);
            valToIndex.put(val,base.size()-1);
            return true;
        }
        public boolean remove(int val){
            if(!valToIndex.containsKey(val))
                return false;
            moveToEnd(valToIndex.get(val));
            base.remove(base.size()-1);
            valToIndex.remove(val);
            return true;
        }
        private void moveToEnd(int index){
            int temp = base.get(index);
            int endValue = base.get(base.size()-1);
            base.set(index,endValue);
            base.set(base.size()-1,temp);
            valToIndex.put(temp,base.size()-1);
            valToIndex.put(endValue,index);
        }
        public int getRandom(){
            Random r = new Random();
            return base.get(Math.abs(r.nextInt())%(base.size()));
        }
    }

    //leetcode 146
    class MyNode {
        public int key,val;
        public MyNode prev,next;
        public MyNode(int key, int val){
            this.key = key;
            this.val = val;
        }

    }

    @Test
    public void testForRandomizedSet(){
        RandomizedSet rs = new RandomizedSet();
        rs.insert(1);
        rs.remove(2);
        rs.insert(2);
        System.out.println(rs.getRandom());
        rs.remove(1);
        rs.insert(2);
        System.out.println(rs.getRandom());
    }

    class DoubleList{
        private MyNode head,tail;
        private int size;

        public DoubleList(){
            head = new MyNode(0,0);
            tail = new MyNode(0,0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addLast(MyNode x){
            x.prev = tail.prev;
            x.next = tail;
            tail.prev = x;
            x.prev.next = x;
            size++;
        }

        public void remove(MyNode x){
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        public MyNode removeFirst(){
            if(head.next == tail)
                return null;
            MyNode first = head.next;
            remove(first);
            return first;
        }

        public int size(){
            return size;
        }
    }

    class MyLRUCache{
        private HashMap<Integer, MyNode> map;
        private DoubleList cache;
        private int cap;

        public MyLRUCache(int capacity){
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }

        private void makeRecently(int key){
            MyNode re = map.get(key);
            cache.remove(re);
            cache.addLast(re);
        }

        private void addRecently(int key,int val){
            MyNode x = new MyNode(key,val);
            cache.addLast(x);
            map.put(key,x);
        }

        private void deleteKey(int key){
            MyNode x = map.get(key);
            cache.remove(x);
            map.remove(key);
        }

        private void removeLeastRecently(){
            MyNode deletedNode = cache.removeFirst();
            int deletedKey = deletedNode.key;
            map.remove(deletedKey);
        }

        public int get(int key){
            if(!map.containsKey(key))
                return -1;
            makeRecently(key);
            return map.get(key).val;
        }

        public void put(int key,int val){
            if(map.containsKey(key)){
                deleteKey(key);
                addRecently(key,val);
                return;
            }
            if(cache.size() == cap){
                removeLeastRecently();
            }
            addRecently(key,val);
        }
    }

    //leetcode 146
    class LRUCache extends LinkedHashMap<Integer,Integer>{
        int cap;
        public LRUCache(int capacity){
            super(capacity,0.75F,true);
            this.cap = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > cap;
        }

        public int get(int key){
            return super.getOrDefault(key,-1);
        }
    }

    //leetcode 460
    class LFUCache{
        HashMap<Integer,Integer> keyToVal;
        HashMap<Integer,Integer> keyToFreq;
        HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;
        int minFreq;
        int cap;

        public LFUCache(int capacity){
            keyToVal = new HashMap<>();
            keyToFreq = new HashMap<>();
            freqToKeys = new HashMap<>();
            this.cap = capacity;
            this.minFreq = 0;
        }

        public int get(int key){
            if(!keyToVal.containsKey(key)){
                return -1;
            }
            increaseFreq(key);
            return keyToVal.get(key);
        }

        public void put(int key,int val){
            if(this.cap<=0)
                return;
            if(keyToVal.containsKey(key)){
                keyToVal.put(key,val);
                increaseFreq(key);
                return;
            }
            if(keyToVal.size()>=cap){
                removeMinKey();
            }
            keyToVal.put(key,val);
            keyToFreq.put(key,1);
            freqToKeys.putIfAbsent(1,new LinkedHashSet<>());
            freqToKeys.get(1).add(key);
            this.minFreq = 1;
        }

        private void increaseFreq(int key){
            int freq = keyToFreq.get(key);
            keyToFreq.put(key,freq+1);
            freqToKeys.get(freq).remove(key);
            freqToKeys.putIfAbsent(freq+1,new LinkedHashSet<>());
            freqToKeys.get(freq+1).add(key);
            if(freqToKeys.get(freq).isEmpty()){
                freqToKeys.remove(freq);
                if(freq==this.minFreq){
                    this.minFreq++;
                }
            }
        }

        private void removeMinKey(){
            LinkedHashSet<Integer> keyList = freqToKeys.get(minFreq);
            int deletedKey = keyList.iterator().next();
            keyList.remove(deletedKey);
            if(keyList.isEmpty()){
                freqToKeys.remove(this.minFreq);
            }
            keyToFreq.remove(deletedKey);
            keyToVal.remove(deletedKey);
        }

    }
}
