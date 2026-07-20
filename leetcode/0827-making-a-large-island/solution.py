from typing import List


class Solution:
    def largestIsland(self, grid: List[List[int]]) -> int:
        """
        思路：
        1) 先用 DFS 把所有为 1 的格子染色，每个岛屿分配一个独立 id（从 2 开始，
           避开 0/1），并记录每个岛屿的面积 area[id]。
        2) 再遍历每个为 0 的格子，看上下左右四个邻居，把不同 id 的岛屿面积加起来
           再加 1（即翻转该 0），求最大值。
        3) 边界情况：全 1 时直接返回 n*n；全 0 时翻转任意一个 0 都得到 1。
        """

        n = len(grid)
        if n == 0:
            return 0

        area = {}  # 岛屿 id -> 面积
        island_id = [[0] * n for _ in range(n)]
        cur_id = 2  # 从 2 开始，避免和 0/1 冲突

        # 四个方向
        dirs = [(-1, 0), (1, 0), (0, -1), (0, 1)]

        def dfs(r: int, c: int, mark: int) -> int:
            """返回本次访问到的岛屿面积，并把所有属于该岛屿的格子标记为 mark。"""
            stack = [(r, c)]
            island_id[r][c] = mark
            size = 0
            while stack:
                x, y = stack.pop()
                size += 1
                for dx, dy in dirs:
                    nx, ny = x + dx, y + dy
                    if 0 <= nx < n and 0 <= ny < n and grid[nx][ny] == 1 and island_id[nx][ny] == 0:
                        island_id[nx][ny] = mark
                        stack.append((nx, ny))
            return size

        # 第一遍：给所有岛屿染色并记录面积
        for i in range(n):
            for j in range(n):
                if grid[i][j] == 1 and island_id[i][j] == 0:
                    sz = dfs(i, j, cur_id)
                    area[cur_id] = sz
                    cur_id += 1

        # 全是 1 的情况：直接返回总面积
        if not area:
            return 1  # 网格全是 0，翻转一格得到 1

        max_area = max(area.values())  # 不翻转任何 0 时的最大岛屿

        # 第二遍：尝试翻转每个 0
        for i in range(n):
            for j in range(n):
                if grid[i][j] == 0:
                    seen = set()
                    cur = 1  # 翻转后的这个格子本身
                    for dx, dy in dirs:
                        ni, nj = i + dx, j + dy
                        if 0 <= ni < n and 0 <= nj < n:
                            nid = island_id[ni][nj]
                            if nid > 1 and nid not in seen:
                                seen.add(nid)
                                cur += area[nid]
                    max_area = max(max_area, cur)

        return max_area


if __name__ == "__main__":
    sol = Solution()

    # 测试 1: 示例 1 -> 3
    g1 = [[1, 0], [0, 1]]
    print(sol.largestIsland(g1))  # 3

    # 测试 2: 示例 2 -> 4
    g2 = [[1, 1], [1, 0]]
    print(sol.largestIsland(g2))  # 4

    # 测试 3: 示例 3 -> 4（全 1）
    g3 = [[1, 1], [1, 1]]
    print(sol.largestIsland(g3))  # 4

    # 测试 4: 全 0 -> 1
    g4 = [[0, 0], [0, 0]]
    print(sol.largestIsland(g4))  # 1

    # 测试 5: 较大网格
    g5 = [
        [1, 1, 0, 0, 0],
        [1, 1, 0, 0, 0],
        [0, 0, 1, 0, 1],
        [0, 0, 0, 1, 1],
    ]
    print(sol.largestIsland(g5))  # 期望 6: 翻转 (1,2) 合并岛屿 4(左上) 与单点 (2,2)
