import java.util.Arrays;

class Solution {
    /**
     * 在 n×n 矩阵中，找出每个 3×3 子矩阵的最大值，
     * 生成 (n-2)×(n-2) 的结果矩阵。
     *
     * 思路：暴力遍历每个 3×3 窗口，取最大值。
     * 时间复杂度：O(n^2)，每个位置检查 9 个元素，共 (n-2)^2 个窗口
     * 空间复杂度：O(n^2)，输出矩阵大小
     */
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        // 结果矩阵大小为 (n-2) × (n-2)
        int[][] result = new int[n - 2][n - 2];

        // 遍历每个 3×3 窗口的左上角 (i, j)
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                // 找出以 (i, j) 为左上角的 3×3 子矩阵中的最大值
                int maxVal = 0;
                for (int di = 0; di < 3; di++) {
                    for (int dj = 0; dj < 3; dj++) {
                        maxVal = Math.max(maxVal, grid[i + di][j + dj]);
                    }
                }
                result[i][j] = maxVal;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int[][] grid1 = {{9, 9, 8, 1}, {5, 6, 2, 6}, {8, 2, 6, 4}, {6, 2, 2, 2}};
        int[][] ans1 = sol.largestLocal(grid1);
        int[][] expected1 = {{9, 9}, {8, 6}};
        assert Arrays.deepEquals(ans1, expected1) : "测试用例1失败: " + Arrays.deepToString(ans1);
        System.out.println("测试用例1通过: " + Arrays.deepToString(ans1));

        // 测试用例 2
        int[][] grid2 = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 2, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        int[][] ans2 = sol.largestLocal(grid2);
        int[][] expected2 = {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};
        assert Arrays.deepEquals(ans2, expected2) : "测试用例2失败: " + Arrays.deepToString(ans2);
        System.out.println("测试用例2通过: " + Arrays.deepToString(ans2));

        // 测试用例 3: 最小 n=3
        int[][] grid3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] ans3 = sol.largestLocal(grid3);
        int[][] expected3 = {{9}};
        assert Arrays.deepEquals(ans3, expected3) : "测试用例3失败: " + Arrays.deepToString(ans3);
        System.out.println("测试用例3通过: " + Arrays.deepToString(ans3));

        System.out.println("\n所有测试用例通过！");
    }
}
