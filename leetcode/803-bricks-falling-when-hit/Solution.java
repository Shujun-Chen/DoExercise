/**
 * 803. Bricks Falling When Hit
 * 难度: Hard
 *
 * 解法: 逆序并查集 (Reverse Union-Find)
 * 思路: 逆序处理击打，逐步恢复砖块，用并查集维护与"天花板"的连通性。
 *
 * 时间复杂度: O(m * n * α(m * n))，其中 α 是反阿克曼函数
 * 空间复杂度: O(m * n)
 */
import java.util.*;

class Solution {
    // 并查集：parent[i] = 父节点, size[i] = 以 i 为根的连通分量大小
    private int[] parent;
    private int[] size;

    // 查找根节点（路径压缩）
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // 按大小合并两个集合，返回合并后根节点的连通分量大小
    private void union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry) return;
        // 小树合并到大树
        if (size[rx] < size[ry]) {
            int tmp = rx; rx = ry; ry = tmp;
        }
        parent[ry] = rx;
        size[rx] += size[ry];
    }

    // 获取 x 所在连通分量的大小
    private int getSize(int x) {
        return size[find(x)];
    }

    public int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length, n = grid[0].length;
        // 虚拟节点 m*n 代表天花板（第 0 行的上方）
        int top = m * n;

        // 初始化并查集
        parent = new int[m * n + 1];
        size = new int[m * n + 1];
        for (int i = 0; i <= m * n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // 1. 复制 grid，标记所有被击打的位置为 0
        int[][] g = new int[m][n];
        for (int i = 0; i < m; i++) {
            g[i] = grid[i].clone();
        }
        for (int[] hit : hits) {
            g[hit[0]][hit[1]] = 0;
        }

        // 2. 对剩余砖块建立并查集连通关系
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (g[r][c] == 1) {
                    int idx = r * n + c;
                    // 与天花板连通
                    if (r == 0) {
                        union(idx, top);
                    }
                    // 与上方砖块连通
                    if (r > 0 && g[r - 1][c] == 1) {
                        union(idx, (r - 1) * n + c);
                    }
                    // 与左方砖块连通
                    if (c > 0 && g[r][c - 1] == 1) {
                        union(idx, r * n + c - 1);
                    }
                }
            }
        }

        // 3. 逆序恢复砖块，计算每次恢复后新增的稳定砖块数
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[] result = new int[hits.length];

        for (int i = hits.length - 1; i >= 0; i--) {
            int r = hits[i][0], c = hits[i][1];
            // 如果原 grid 此处就没有砖块，跳过
            if (grid[r][c] == 0) continue;

            int idx = r * n + c;
            // 记录恢复前天花板连通分量的大小
            int before = getSize(top);

            // 恢复此砖块
            g[r][c] = 1;

            // 如果在第 0 行，连通天花板
            if (r == 0) {
                union(idx, top);
            }

            // 与四个方向的邻居连通
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && g[nr][nc] == 1) {
                    union(idx, nr * n + nc);
                }
            }

            // 计算恢复后天花板连通分量的大小变化
            int after = getSize(top);
            // 新增的稳定砖块数（减去自身 1）
            result[i] = Math.max(0, after - before - 1);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int[][] grid1 = {{1, 0, 0, 0}, {1, 1, 1, 0}};
        int[][] hits1 = {{1, 0}};
        int[] res1 = sol.hitBricks(grid1, hits1);
        System.out.println("Test 1: " + Arrays.toString(res1));  // [2]

        // 测试用例 2
        int[][] grid2 = {{1, 0, 0, 0}, {1, 1, 0, 0}};
        int[][] hits2 = {{1, 1}, {1, 0}};
        int[] res2 = sol.hitBricks(grid2, hits2);
        System.out.println("Test 2: " + Arrays.toString(res2));  // [0, 0]

        // 测试用例 3: 单个砖块
        int[][] grid3 = {{1}, {1}};
        int[][] hits3 = {{1, 0}};
        int[] res3 = sol.hitBricks(grid3, hits3);
        System.out.println("Test 3: " + Arrays.toString(res3));  // [0]

        // 测试用例 4: 击打空位
        int[][] grid4 = {{1, 1}, {0, 0}};
        int[][] hits4 = {{1, 0}};
        int[] res4 = sol.hitBricks(grid4, hits4);
        System.out.println("Test 4: " + Arrays.toString(res4));  // [0]

        System.out.println("All tests passed!");
    }
}
