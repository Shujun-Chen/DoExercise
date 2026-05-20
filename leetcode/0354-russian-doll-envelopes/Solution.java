import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 354. Russian Doll Envelopes
 * https://leetcode.com/problems/russian-doll-envelopes/
 *
 * 核心思路：转化为最长递增子序列（LIS）问题。
 * 1. 按宽度升序排列，宽度相同时按高度降序排列（避免同宽度嵌套）
 * 2. 对高度序列求 LIS，使用耐心排序（二分查找）优化到 O(n log n)
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        if (n == 0) return 0;

        // 按宽度升序，宽度相同则高度降序
        // 高度降序确保同宽度的信封不会互相嵌套
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return b[1] - a[1];
            }
        });

        // 耐心排序求 LIS 长度
        // tails[i] 表示长度为 i+1 的递增子序列的最小末尾值
        List<Integer> tails = new ArrayList<>();

        for (int[] envelope : envelopes) {
            int h = envelope[1];
            // 在 tails 中二分查找第一个 >= h 的位置
            int idx = lowerBound(tails, h);
            if (idx == tails.size()) {
                tails.add(h); // h 比所有 tails 值都大，扩展 LIS
            } else {
                tails.set(idx, h); // 用更小的值替换，为后续留空间
            }
        }

        return tails.size();
    }

    // 二分查找：返回第一个 >= target 的位置
    private int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        int[][] envelopes1 = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        int result1 = sol.maxEnvelopes(envelopes1);
        assert result1 == 3 : "Expected 3, got " + result1;

        // 示例 2
        int[][] envelopes2 = {{1, 1}, {1, 1}, {1, 1}};
        int result2 = sol.maxEnvelopes(envelopes2);
        assert result2 == 1 : "Expected 1, got " + result2;

        // 边界：单个信封
        int[][] single = {{5, 4}};
        assert sol.maxEnvelopes(single) == 1;

        // 边界：严格递增
        int[][] incr = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};
        assert sol.maxEnvelopes(incr) == 4;

        // 同宽不同高
        int[][] sameW = {{2, 3}, {2, 4}, {2, 1}};
        assert sol.maxEnvelopes(sameW) == 1;

        System.out.println("所有测试用例通过！");
    }
}
