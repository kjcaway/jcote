package com.example.jcote;

import org.junit.jupiter.api.Test;

import java.util.*;

public class Common {
    /**
     * 윤년 일때 특장 날짜(MM-DD)의 요일 구하기
     */
    @Test
    public void _윤년_() {
        int a = 5;
        int b = 24;
        ////

        String[] dayOfWeek = {"FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU"};
        int[] dayCountByMonth = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int month = 1;
        int day = 1;
        int i = 0;
        while (true) {
            if (a == month && b == day) {
                break;
            } else if (a == month) {
                day++;
                i++;
            } else {
                if (dayCountByMonth[month] == day) {
                    day = 1;
                    month++;
                } else {
                    day++;
                }
                i++;
            }
        }

        i = i % 7;

        System.out.println(dayOfWeek[i]);
    }

    @Test
    public void _셔플_() {
        String s = "codeleet";
        int[] indices = {4, 5, 6, 7, 0, 2, 1, 3};
        ////

        char[] arr = new char[indices.length];
        char[] sArr = s.toCharArray();
        for (int i = 0; i < indices.length; i++) {
            arr[indices[i]] = sArr[i];
        }

        System.out.println(String.valueOf(sArr));
    }

    /**
     * Set
     * - 데이터를 중복해서 저장하지 않는다.
     * - 순서가 보장되지 않음
     * Q. 주어진 배열에서 유일하게 1개만 있는 숫자 찾기.
     * 단, 나머지 숫자는 2개씩 있음.
     */
    @Test
    public void _Set_() {
        int[] input = {1, 2, 5, 7, 3, 5, 7, 2, 3};
        ////
        HashSet<Integer> hSet = new HashSet<>();

        for (int i = 0; i < input.length; i++) {
            int i1 = input[i];
            if (hSet.contains(i1)) {
                hSet.remove(i1);
            } else {
                hSet.add(i1);
            }
        }

        Iterator<Integer> iterator = hSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // 아래는 xor을 이용했을떄
        int res = 0;
        for (int i = 0; i < input.length; i++) {
            int i1 = input[i];
            res ^= i1;
        }
        System.out.println(res);
    }

    static class Print implements Comparable<Print> {
        int idx;
        int priority;

        public Print(int idx, int priority) {
            this.idx = idx;
            this.priority = priority;
        }

        @Override
        public int compareTo(Print target) {
            if (this.priority < target.priority) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Test
    public void _PriorityQueue_() {
        int[] priorities = new int[]{1, 1, 9, 1, 1, 1};
        int location = 0;
        ////
        int answer = -1;
        PriorityQueue<Print> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < priorities.length; i++) {
            Print print = new Print(i, priorities[i]);
            priorityQueue.offer(print);
        }
        Object[] arr = priorityQueue.toArray();
        for (int i = 0; i < arr.length; i++) {
            Print p = (Print) arr[i];
            if (p.idx == location) {
                answer = i + 1;
            }
        }

        System.out.println("result = " + answer);
    }

    @Test
    public void _PrimeNumber_() {
        int N = 100;
        ////
        List<Integer> primeList = new ArrayList<>();
        for (int num = 1; num <= N; num++) {
            boolean isPrime = true;

            if (num == 1) continue;
            if (num != 2 && num % 2 == 0) continue;

            for (int i = 0; i < primeList.size(); i++) {
                if (num % primeList.get(i) == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primeList.add(num);
            }
        }

        System.out.println(primeList);
    }

    public static void backtracking(List<List<Integer>> result, List<Integer> nums, List<Integer> sub) {
        if (sub.size() == nums.size()) {
            result.add(new ArrayList(sub));
        } else {
            for (int i = 0; i < nums.size(); i++) {
                if (!sub.contains(nums.get(i))) {
                    List<Integer> set = new ArrayList(sub);
                    set.add(nums.get(i));
                    backtracking(result, nums, set);
                }
            }
        }
    }

    /**
     * 순열 구하기
     * 1,2,3 -> [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 3*2*1 -> 6개
     */
    @Test
    public void _Permutations_() {
        int[] nums = {1, 2, 3};
        ////
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();
        for (int i : nums) {
            numsList.add(i);
        }

        backtracking(result, numsList, new ArrayList<>());

        System.out.println(result);
    }

    /**
     * leetcode
     * 347. Top K Frequent Elements
     * <p>
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     */

    @Test
    public void _PriorityQueue2_() {
        int[] nums = {1, 1, 1, 2, 2, 2, 2, 3, 4, 5, 5, 5, 5, 3};
        int k = 2;
        ////
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
                (a, b) -> {
                    if (a.getValue() < b.getValue()) return 1;
                    else return -1;
                }
        );

        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
            pq.offer(m);
        }

        int[] answer = new int[k];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = pq.poll().getKey();
        }

        System.out.println(Arrays.toString(answer));
    }

    private static int count = 0;

    public static void recursion(int[] arr, int size, int idx, int[] des) {
        if (idx == size) {
            count++;
            System.out.println(Arrays.toString(des));
        } else {
            for (int i = 0; i < arr.length; i++) {
                des[idx] = arr[i];
                recursion(arr, size, idx + 1, des);
            }
        }
    }

    /**
     * 중복 순열
     * 경우의 수 개수 = arraySize^n
     */
    @Test
    public void _DuplicatedPermutations_() {
        int[] arr = {1, 2, 3, 4};

        int size = 4;
        recursion(arr, size, 0, new int[size]);
        System.out.println("Count: " + count);
    }

    /**
     * 10원과 50원으로 120원을 지불할 수 있는 모든 방법의 수와 최소 동전의 개수를 구해보자.
     */
    @Test
    public void _BrutalForce_() {
        int r = 120;
        ////
        int minCoinCnt = Integer.MAX_VALUE;
        for (int i = 0; i * 10 <= r; i++) {
            for (int j = 0; j * 50 <= r; j++) {
                int f = i * 10;
                int s = j * 50;
                if (f + s == 120) {
                    System.out.println("10: " + i + ", 50: " + j);
                    if (minCoinCnt > i + j) {
                        minCoinCnt = i + j;
                    }
                }
            }
        }
        System.out.println("minCoinCnt: " + minCoinCnt);
    }

    /**
     * 이진 탐색
     * O(log(n))의 시간복잡도
     */
    @Test
    public void _BinarySearch_() {
        int[] arr = {1, 2, 3, 5, 6, 12, 13, 15, 17, 18, 20, 23, 26, 50};
        int t = 6;
        ////
        int idx;
        int start = 0;
        int end = arr.length - -1;

        while (start <= end) {
            idx = (int) Math.floor((start + end) / 2);
            if (arr[idx] == t) {
                System.out.println(idx);
                break;
            } else if (arr[idx] < t) {
                start = idx + 1;
            } else {
                end = idx - 1;
            }
        }
    }


}
