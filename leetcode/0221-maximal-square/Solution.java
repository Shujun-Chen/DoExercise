package leetcode;

/**
 * LeetCode 221. Maximal Square
 * 难度：Medium
 *
 * 题目描述：
 * 给定一个由 '0' 和 '1' 组成的二维矩阵，找出只包含 '1' 的最大正方形，返回其面积。
 *
 * 解题思路（二维动态规划 + 空间优化）：
 * 设 dp[i][j] 为以 matrix[i][j] 为右下角的最大全 1 正方形边长。
 * 状态转移：
 *   matrix[i][j] == '0' -> dp[i][j] = 0
 *   matrix[i][j] == '1' -> dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
 *
 * 空间优化：滚动一行 dp，用 pre 暂存左上角值。
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(n)
 */
public class Solution {

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        // dp[j] 代表上一行（或当前行的左邻居）的 dp 值
        int[] dp = new int[n + 1];
        int maxSide = 0;
        int pre = 0; // 暂存左上角的 dp[i-1][j-1]

        for (int i = 1; i <= m; i++) {
            pre = 0;
            for (int j = 1; j <= n; j++) {
                int cur = dp[j]; // 保留当前 dp[j]，下一轮它就是"左上角"
                if (matrix[i - 1][j - 1] == '1') {
                    // 由左、上、左上三个方向取最小再加 1
                    dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), pre) + 1;
                    if (dp[j] > maxSide) {
                        maxSide = dp[j];
                    }
                } else {
                    dp[j] = 0;
                }
                pre = cur; // 当前值变成下一轮的左上角
            }
        }

        return maxSide * maxSide;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 用例 1：题面示例
        char[][] m1 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        check(s.maximalSquare(m1), 4, 1);

        // 用例 2：单格 0
        char[][] m2 = {{'0'}};
        check(s.maximalSquare(m2), 0, 2);

        // 用例 3：2x2 全 1
        char[][] m3 = {
            {'1', '1'},
            {'1', '1'}
        };
        check(s.maximalSquare(m3), 4, 3);

        // 用例 4：单行 1,1,0,1,1 -> 最大 1
        char[][] m4 = {{'1', '1', '0', '1', '1'}};
        check(s.maximalSquare(m4), 1, 4);

        // 用例 5：3x3 全 1
        char[][] m5 = {
            {'1', '1', '1'},
            {'1', '1', '1'},
            {'1', '1', '1'}
        };
        check(s.maximalSquare(m5), 9, 5);

        System.out.println("所有用例通过 ✓");
    }

    private static void check(int actual, int expected, int idx) {
        if (actual != expected) {
            throw new AssertionError("用例 " + idx + " 失败，期望 " + expected + " 实得 " + actual);
        }
        System.out.println("用例 " + idx + " 通过： " + actual);
    }
}
