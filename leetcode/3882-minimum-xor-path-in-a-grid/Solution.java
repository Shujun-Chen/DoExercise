public class Solution {
    // 从左上角出发，每步只能向右或向下，走到右下角。
    // 路径代价 = 路径上所有格子值的 XOR，求最小可能代价。
    //
    // 关键观察：grid[i][j] <= 1023，任意前缀 XOR 结果始终落在 [0, 1023]。
    // 因此对每个格子维护"从起点到该格所有可能 XOR 值"的布尔状态即可。
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        final int RANGE = 1024; // 0..1023 共 1024 个可能取值

        // dp[j] 表示"当前处理行"中第 j 列格子可达到的 XOR 值集合（布尔标记）。
        // 按列滚动：处理到 (i, j) 时 dp[j] 仍是上一行第 j 列（上方来源），
        // dp[j-1] 已更新为当前行第 j-1 列（左方来源）。
        boolean[][] dp = new boolean[n][RANGE];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                boolean[] cur = new boolean[RANGE];
                if (i == 0 && j == 0) {
                    // 起点：只有它自身
                    cur[v] = true;
                } else {
                    if (i > 0) {
                        // 来自上方：dp[j] 保存上一行第 j 列的集合
                        for (int x = 0; x < RANGE; x++) {
                            if (dp[j][x]) cur[x ^ v] = true;
                        }
                    }
                    if (j > 0) {
                        // 来自左方：dp[j-1] 已更新为当前行第 j-1 列的集合
                        for (int x = 0; x < RANGE; x++) {
                            if (dp[j - 1][x]) cur[x ^ v] = true;
                        }
                    }
                }
                dp[j] = cur;
            }
        }

        // 终点所有可达 XOR 值中的最小值
        for (int x = 0; x < RANGE; x++) {
            if (dp[n - 1][x]) return x;
        }
        return -1; // 理论上不会到达
    }

    // 时间复杂度：O(m * n * 1024)
    //   每个格子扫描一遍 1024 的布尔数组做转移；m * n <= 1000，可接受。
    // 空间复杂度：O(n * 1024)
    //   只保留一行(按列滚动)的布尔状态。

    public static void main(String[] args) {
        Solution s = new Solution();

        // 示例 1：单格边界
        assert1(s.minCost(new int[][]{{1}}) == 1);

        // 示例 2：2x2 网格，最小路径 2^3^4 = 5
        assert1(s.minCost(new int[][]{{2, 1}, {3, 4}}) == 5);

        // 示例 3：3x3 网格，终点可达 XOR 最小为 1
        assert1(s.minCost(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}) == 1);

        // 附加边界：2x3 全 3，四个 3 异或为 0
        assert1(s.minCost(new int[][]{{3, 3, 3}, {3, 3, 3}}) == 0);

        System.out.println("All tests passed!");
    }

    // 简单断言辅助，避免依赖 -ea 开关
    private static void assert1(boolean cond) {
        if (!cond) throw new AssertionError("Test failed");
    }
}
