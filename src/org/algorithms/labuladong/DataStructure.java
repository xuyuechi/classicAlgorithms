package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

//学剑篇 1.4数据结构设计
public class DataStructure {

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
