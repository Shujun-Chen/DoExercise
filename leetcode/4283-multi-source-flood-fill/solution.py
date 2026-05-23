"""
4283. Multi Source Flood Fill
Medium | BFS, Multi-source BFS, Grid

You are given n (rows) and m (columns) of a grid, and a list of sources.
Each source [r, c, color] initially colors cell (r, c) with the given color.
Every time step, each colored cell spreads its color to adjacent uncolored
cells (up, down, left, right). All spreads happen simultaneously.
If multiple colors reach the same cell at the same time, the cell takes the
maximum color value. Continue until no more cells can be colored.
"""

from collections import deque
from typing import List


class Solution:
    def colorGrid(self, n: int, m: int, sources: List[List[int]]) -> List[List[int]]:
        """
        多源 BFS 解法。
        初始化网格，将所有 source 入队，然后按层 BFS 模拟颜色扩散。
        当同一时刻有多个颜色到达同一个格子时，取颜色值最大的那个。
        """
        # 初始化网格，0 表示未染色
        grid = [[0] * m for _ in range(n)]
        # 记录每个格子的"时间步"（距离最近 source 的距离）
        dist = [[-1] * m for _ in range(n)]

        q = deque()

        # 将所有 source 入队，作为多源 BFS 的起点
        for r, c, color in sources:
            grid[r][c] = color
            dist[r][c] = 0
            q.append((r, c))

        # 四个方向的偏移量：上、下、左、右
        dirs = [(-1, 0), (1, 0), (0, -1), (0, 1)]

        # BFS 逐层扩散
        while q:
            r, c = q.popleft()
            cur_color = grid[r][c]
            cur_dist = dist[r][c]

            for dr, dc in dirs:
                nr, nc = r + dr, c + dc
                # 边界检查
                if nr < 0 or nr >= n or nc < 0 or nc >= m:
                    continue

                # 如果该格子从未被访问过
                if dist[nr][nc] == -1:
                    dist[nr][nc] = cur_dist + 1
                    grid[nr][nc] = cur_color
                    q.append((nr, nc))
                # 如果该格子在"同一时间步"被另一颜色到达，取最大值
                elif dist[nr][nc] == cur_dist + 1 and cur_color > grid[nr][nc]:
                    grid[nr][nc] = cur_color
                    # 注意：不需要再次入队，因为该格子已在前一次访问时入队

        return grid


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    n, m = 3, 3
    sources = [[0, 0, 1], [2, 2, 2]]
    expected = [[1, 1, 2], [1, 2, 2], [2, 2, 2]]
    result = sol.colorGrid(n, m, sources)
    assert result == expected, f"Example 1 failed: {result}"
    print("示例 1 通过:", result)

    # 示例 2
    n, m = 3, 3
    sources = [[0, 1, 3], [1, 1, 5]]
    expected = [[3, 3, 3], [5, 5, 5], [5, 5, 5]]
    result = sol.colorGrid(n, m, sources)
    assert result == expected, f"Example 2 failed: {result}"
    print("示例 2 通过:", result)

    # 示例 3
    n, m = 2, 2
    sources = [[1, 1, 5]]
    expected = [[5, 5], [5, 5]]
    result = sol.colorGrid(n, m, sources)
    assert result == expected, f"Example 3 failed: {result}"
    print("示例 3 通过:", result)

    print("所有测试用例通过！")

# 时间复杂度: O(n * m) — 每个格子最多入队并处理一次
# 空间复杂度: O(n * m) — 网格、dist数组和队列
