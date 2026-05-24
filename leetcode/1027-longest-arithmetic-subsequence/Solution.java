import java.util.*;

/**
 * 1027. Longest Arithmetic Subsequence
 * 
 * 求最长等差子序列的长度。
 * 动态规划：用 HashMap 数组记录每个位置、每种公差对应的最长子序列长度。
 */
class Solution {
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        // dp[i] = {diff -> 以 i 结尾、公差为 diff 的最长等差子序列长度}
        Map<Integer, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
        }
        int ans = 2; // 最短等差子序列长度为 2

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                // 以 j 结尾的该公差子序列长度（至少为 1，即 j 自身）
                int length = dp[j].getOrDefault(diff, 1) + 1;
                // 取较大值更新 dp[i]
                dp[i].put(diff, Math.max(dp[i].getOrDefault(diff, 0), length));
                // 更新全局答案
                ans = Math.max(ans, length);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.longestArithSeqLength(new int[]{3, 6, 9, 12}));          // 4
        System.out.println(sol.longestArithSeqLength(new int[]{9, 4, 7, 2, 10}));      // 3
        System.out.println(sol.longestArithSeqLength(new int[]{20, 1, 15, 3, 10, 5, 8})); // 4
        System.out.println(sol.longestArithSeqLength(new int[]{1, 2, 3, 4}));           // 4
        System.out.println(sol.longestArithSeqLength(new int[]{1, 3, 5, 7, 9}));        // 5
        System.out.println(sol.longestArithSeqLength(new int[]{1, 1, 1, 1}));           // 4
    }
}

// 时间复杂度：O(n²)，n 为数组长度
// 空间复杂度：O(n²)，最坏情况下每个位置存储 O(n) 个不同公差
