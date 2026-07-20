import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class Solution {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        if (n == 0) {
            return 0;
        }

        int[][] islandId = new int[n][n];
        int[] area = new int[n * n + 2];
        int curId = 2;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && islandId[i][j] == 0) {
                    int size = dfs(grid, islandId, i, j, curId, dr, dc);
                    area[curId] = size;
                    curId++;
                }
            }
        }

        if (curId == 2) {
            return 1;
        }

        int maxArea = 0;
        for (int k = 2; k < curId; k++) {
            maxArea = Math.max(maxArea, area[k]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set seen = new HashSet();
                    int cur = 1;
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dr[d];
                        int nj = j + dc[d];
                        if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                            int nid = islandId[ni][nj];
                            if (nid > 1 && seen.add(nid)) {
                                cur += area[nid];
                            }
                        }
                    }
                    maxArea = Math.max(maxArea, cur);
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int[][] islandId, int r, int c, int mark, int[] dr, int[] dc) {
        int n = grid.length;
        Stack stack = new Stack();
        stack.push(new int[]{r, c});
        islandId[r][c] = mark;
        int size = 0;
        while (!stack.isEmpty()) {
            int[] p = (int[]) stack.pop();
            size++;
            for (int d = 0; d < 4; d++) {
                int nr = p[0] + dr[d];
                int nc = p[1] + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && grid[nr][nc] == 1 && islandId[nr][nc] == 0) {
                    islandId[nr][nc] = mark;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
        return size;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] g1 = {{1, 0}, {0, 1}};
        System.out.println(sol.largestIsland(g1));

        int[][] g2 = {{1, 1}, {1, 0}};
        System.out.println(sol.largestIsland(g2));

        int[][] g3 = {{1, 1}, {1, 1}};
        System.out.println(sol.largestIsland(g3));

        int[][] g4 = {{0, 0}, {0, 0}};
        System.out.println(sol.largestIsland(g4));

        int[][] g5 = {
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1},
                {0, 0, 0, 1, 1},
        };
        System.out.println(sol.largestIsland(g5)); // 期望 6: 翻转 (1,2) 合并岛屿 4 与单点 (2,2)
    }
}
