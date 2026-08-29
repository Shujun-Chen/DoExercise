package leetcode;

/**
 * LeetCode 128. Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * 难度：Medium
 *
 * 解法与 solution.py 一致：哈希集合 + 只从序列起点往后枚举。
 *
 * 复杂度：
 *   时间：O(n)
 *   空间：O(n)
 */

import java.util.HashSet;
import java.util.Set;

public class Solution {

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 把所有数放进 HashSet，便于 O(1) 查询
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }

        int longest = 0;
        for (int x : set) {
            // 只在「序列起点」x-1 不在集合里时才开始往后数
            if (!set.contains(x - 1)) {
                int y = x + 1;
                while (set.contains(y)) {
                    y++;
                }
                // y 是第一个不在集合里的数，长度 = y - x
                longest = Math.max(longest, y - x);
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        assert sol.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}) == 4;
        // 示例 2
        assert sol.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}) == 9;
        // 示例 3
        assert sol.longestConsecutive(new int[]{1, 0, 1, 2}) == 3;
        // 空数组
        assert sol.longestConsecutive(new int[]{}) == 0;
        // 单元素
        assert sol.longestConsecutive(new int[]{5}) == 1;
        // 全相同
        assert sol.longestConsecutive(new int[]{2, 2, 2}) == 1;
        // 负数
        assert sol.longestConsecutive(new int[]{-2, -1, 0, 1, 2}) == 5;
        // 完全不连续
        assert sol.longestConsecutive(new int[]{10, 30, 20}) == 1;

        System.out.println("All tests passed.");
    }
}
