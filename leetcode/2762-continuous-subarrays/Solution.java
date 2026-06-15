import java.util.*;

class Solution {
    /**
     * 统计连续子数组的个数
     * 
     * 连续子数组定义：子数组内任意两个元素的差的绝对值 <= 2
     * 
     * 思路：滑动窗口 + 双单调队列
     * - 用 maxDeque 维护窗口内递减序列（队首是最大值）
     * - 用 minDeque 维护窗口内递增序列（队首是最小值）
     * - 当窗口 [left, right] 满足 max - min <= 2 时，
     *   以 right 结尾的连续子数组有 (right - left + 1) 个
     * 
     * 时间复杂度：O(n) — 每个元素最多进出队列各一次
     * 空间复杂度：O(n) — 队列空间
     */
    public long continuousSubarrays(int[] nums) {
        int n = nums.length;
        Deque<Integer> maxDeque = new ArrayDeque<>(); // 递减队列，队首是窗口最大值
        Deque<Integer> minDeque = new ArrayDeque<>(); // 递增队列，队首是窗口最小值
        
        int left = 0;
        long result = 0;
        
        for (int right = 0; right < n; right++) {
            // 维护递减队列：移除比当前元素小的元素
            while (!maxDeque.isEmpty() && nums[maxDeque.peekLast()] <= nums[right]) {
                maxDeque.pollLast();
            }
            maxDeque.addLast(right);
            
            // 维护递增队列：移除比当前元素大的元素
            while (!minDeque.isEmpty() && nums[minDeque.peekLast()] >= nums[right]) {
                minDeque.pollLast();
            }
            minDeque.addLast(right);
            
            // 收缩窗口：当 max - min > 2 时移动左指针
            while (nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > 2) {
                left++;
                if (maxDeque.peekFirst() < left) {
                    maxDeque.pollFirst();
                }
                if (minDeque.peekFirst() < left) {
                    minDeque.pollFirst();
                }
            }
            
            // 以 right 结尾的连续子数组个数
            result += right - left + 1;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Solution s = new Solution();
        
        // 测试用例 1
        assert s.continuousSubarrays(new int[]{5, 4, 2, 4}) == 8 : "Test 1 failed";
        
        // 测试用例 2
        assert s.continuousSubarrays(new int[]{1, 2, 3}) == 6 : "Test 2 failed";
        
        // 额外测试：单个元素
        assert s.continuousSubarrays(new int[]{100}) == 1 : "Test 3 failed";
        
        // 额外测试：所有元素相同
        assert s.continuousSubarrays(new int[]{3, 3, 3, 3}) == 10 : "Test 4 failed";
        
        // 额外测试：差值刚好为2，所有子数组都连续
        assert s.continuousSubarrays(new int[]{1, 3, 1, 3}) == 10 : "Test 5 failed";
        
        // 额外测试：差值超过2，部分子数组不连续
        assert s.continuousSubarrays(new int[]{1, 5, 1}) == 3 : "Test 6 failed";
        
        System.out.println("All test cases passed!");
    }
}
