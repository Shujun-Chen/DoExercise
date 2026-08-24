class Solution {
    /**
     * 岛屿数量。
     *
     * 思路（DFS + 原地标记）：
     * 1. 遍历整个网格，每遇到一个 '1'，岛屿计数 +1。
     * 2. 从这个 '1' 出发做深度优先搜索（DFS），
     *    把与之相连（上下左右）的所有 '1' 都标记为 '0'，
     *    避免重复计数。
     * 3. 标记动作原地修改 grid，节省 visited 数组空间。
     *
     * 时间复杂度：O(m * n)，每个格子最多被访问 2 次。
     * 空间复杂度：O(m * n) 最坏情况，来自 DFS 递归栈深度；辅助空间 O(1)。
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j, m, n);
                }
            }
        }

        return count;
    }

    private void dfs(char[][] grid, int i, int j, int m, int n) {
        // 越界或已经是水，直接返回
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') {
            return;
        }
        // 原地标记为水，避免重复访问
        grid[i][j] = '0';
        // 四个方向继续淹没
        dfs(grid, i + 1, j, m, n);
        dfs(grid, i - 1, j, m, n);
        dfs(grid, i, j + 1, m, n);
        dfs(grid, i, j - 1, m, n);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 测试用例 1：标准示例
        char[][] grid1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        int result1 = solution.numIslands(copyGrid(grid1));
        assert result1 == 1 : "Test 1 failed: " + result1;
        System.out.println("Test 1 passed: 标准 4x5 网格 => 1");

        // 测试用例 2：3 个独立岛屿
        char[][] grid2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        int result2 = solution.numIslands(copyGrid(grid2));
        assert result2 == 3 : "Test 2 failed: " + result2;
        System.out.println("Test 2 passed: 3 个独立岛屿 => 3");

        // 测试用例 3：全水
        char[][] grid3 = {
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        int result3 = solution.numIslands(copyGrid(grid3));
        assert result3 == 0 : "Test 3 failed: " + result3;
        System.out.println("Test 3 passed: 全水网格 => 0");

        // 测试用例 4：全陆地
        char[][] grid4 = {
            {'1', '1'},
            {'1', '1'}
        };
        int result4 = solution.numIslands(copyGrid(grid4));
        assert result4 == 1 : "Test 4 failed: " + result4;
        System.out.println("Test 4 passed: 全陆地网格 => 1");

        // 测试用例 5：单格子
        char[][] grid5 = {{'1'}};
        int result5 = solution.numIslands(copyGrid(grid5));
        assert result5 == 1 : "Test 5 failed: " + result5;
        System.out.println("Test 5 passed: 单格陆地 => 1");

        char[][] grid6 = {{'0'}};
        int result6 = solution.numIslands(copyGrid(grid6));
        assert result6 == 0 : "Test 6 failed: " + result6;
        System.out.println("Test 6 passed: 单格水 => 0");

        // 测试用例 7：对角线相连（不算相连）
        char[][] grid7 = {
            {'1', '0', '1'},
            {'0', '1', '0'},
            {'1', '0', '1'}
        };
        int result7 = solution.numIslands(copyGrid(grid7));
        assert result7 == 5 : "Test 7 failed: " + result7;
        System.out.println("Test 7 passed: 对角线相连（不算相连）=> 5");

        // 测试用例 8：长条形岛屿
        char[][] grid8 = {
            {'1', '0', '0', '0', '0', '0', '1'},
            {'0', '1', '0', '0', '0', '1', '0'},
            {'0', '0', '1', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '0', '0', '0'}
        };
        int result8 = solution.numIslands(copyGrid(grid8));
        assert result8 == 7 : "Test 8 failed: " + result8;
        System.out.println("Test 8 passed: 散布岛屿 => 7");

        // 测试用例 9：空网格
        int result9a = solution.numIslands(new char[][]{});
        assert result9a == 0 : "Test 9a failed";
        int result9b = solution.numIslands(new char[][]{{}});
        assert result9b == 0 : "Test 9b failed";
        System.out.println("Test 9 passed: 空网格 => 0");

        System.out.println("\n所有测试用例通过！");
    }

    private static char[][] copyGrid(char[][] grid) {
        if (grid == null) return null;
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
}