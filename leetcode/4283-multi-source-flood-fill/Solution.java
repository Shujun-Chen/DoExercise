import java.util.*;

/**
 * 4283. Multi Source Flood Fill
 * Medium | BFS, Multi-source BFS, Grid
 *
 * 给定一个 n x m 的网格和一些初始染色源 sources。
 * 每个时间步，所有已染色的格子同时向四个方向（上、下、左、右）的未染色格子扩散颜色。
 * 如果同一时间步有多个颜色到达同一个格子，取颜色值最大的那个。
 * 当没有格子可染色时结束，返回最终网格。
 */
class Solution {
    /**
     * 使用多源 BFS 模拟颜色扩散。
     * 利用队列按层遍历，dist 数组记录每个格子的到达时间，
     * 当同一时间多个颜色到达同一格时取最大值。
     */
    public int[][] colorGrid(int n, int m, int[][] sources) {
        // 初始化网格，0 表示未染色
        int[][] grid = new int[n][m];
        // 记录每个格子的时间步（距离最近 source 的步数）
        int[][] dist = new int[n][m];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        // 多源 BFS 队列
        Queue<int[]> queue = new LinkedList<>();

        // 将所有 source 加入队列作为 BFS 起点
        for (int[] src : sources) {
            int r = src[0], c = src[1], color = src[2];
            grid[r][c] = color;
            dist[r][c] = 0;
            queue.offer(new int[]{r, c});
        }

        // 四个方向：上、下、左、右
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // BFS 逐层扩散
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0], c = cell[1];
            int curColor = grid[r][c];
            int curDist = dist[r][c];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // 边界检查
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                    continue;
                }

                // 如果该格子从未被访问过
                if (dist[nr][nc] == -1) {
                    dist[nr][nc] = curDist + 1;
                    grid[nr][nc] = curColor;
                    queue.offer(new int[]{nr, nc});
                }
                // 如果同一时间步被另一颜色到达，取最大值
                else if (dist[nr][nc] == curDist + 1 && curColor > grid[nr][nc]) {
                    grid[nr][nc] = curColor;
                    // 不需要再次入队，已在前一次入队
                }
            }
        }

        return grid;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        int n = 3, m = 3;
        int[][] sources1 = {{0, 0, 1}, {2, 2, 2}};
        int[][] expected1 = {{1, 1, 2}, {1, 2, 2}, {2, 2, 2}};
        int[][] result1 = sol.colorGrid(n, m, sources1);
        System.out.println("示例 1: " + Arrays.deepToString(result1));
        assert Arrays.deepEquals(result1, expected1) : "示例 1 失败";

        // 示例 2
        int[][] sources2 = {{0, 1, 3}, {1, 1, 5}};
        int[][] expected2 = {{3, 3, 3}, {5, 5, 5}, {5, 5, 5}};
        int[][] result2 = sol.colorGrid(n, m, sources2);
        System.out.println("示例 2: " + Arrays.deepToString(result2));
        assert Arrays.deepEquals(result2, expected2) : "示例 2 失败";

        // 示例 3
        n = 2; m = 2;
        int[][] sources3 = {{1, 1, 5}};
        int[][] expected3 = {{5, 5}, {5, 5}};
        int[][] result3 = sol.colorGrid(n, m, sources3);
        System.out.println("示例 3: " + Arrays.deepToString(result3));
        assert Arrays.deepEquals(result3, expected3) : "示例 3 失败";

        System.out.println("所有测试用例通过！");
    }
}

// 时间复杂度: O(n * m) — 每个格子最多入队并处理一次
// 空间复杂度: O(n * m) — grid、dist 数组和队列
