"""
803. Bricks Falling When Hit
难度: Hard

解法: 逆序并查集 (Reverse Union-Find)
思路: 逆序处理击打，逐步恢复砖块，用并查集维护与"天花板"的连通性。

时间复杂度: O(m * n * α(m * n))，其中 α 是反阿克曼函数
空间复杂度: O(m * n)
"""
from typing import List


class UnionFind:
    """带权并查集，size[i] 记录以 i 为根的连通分量大小"""
    def __init__(self, n: int):
        self.parent = list(range(n))
        self.size = [1] * n

    def find(self, x: int) -> int:
        """路径压缩查找根节点"""
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x: int, y: int):
        """按大小合并两个集合"""
        rx, ry = self.find(x), self.find(y)
        if rx == ry:
            return
        # 小树合并到大树
        if self.size[rx] < self.size[ry]:
            rx, ry = ry, rx
        self.parent[ry] = rx
        self.size[rx] += self.size[ry]

    def get_size(self, x: int) -> int:
        """返回 x 所在连通分量的大小"""
        return self.size[self.find(x)]


class Solution:
    def hitBricks(self, grid: List[List[int]], hits: List[List[int]]) -> List[int]:
        m, n = len(grid), len(grid[0])
        # 虚拟节点 index = m * n，代表天花板（第 0 行的上方）
        top = m * n
        uf = UnionFind(m * n + 1)

        # 1. 复制 grid，标记所有被击打的位置为 0
        g = [row[:] for row in grid]
        for r, c in hits:
            g[r][c] = 0

        # 2. 对剩余砖块建立并查集连通关系
        for r in range(m):
            for c in range(n):
                if g[r][c] == 1:
                    idx = r * n + c
                    # 与天花板连通
                    if r == 0:
                        uf.union(idx, top)
                    # 与上方砖块连通
                    if r > 0 and g[r - 1][c] == 1:
                        uf.union(idx, (r - 1) * n + c)
                    # 与左方砖块连通
                    if c > 0 and g[r][c - 1] == 1:
                        uf.union(idx, r * n + c - 1)

        # 3. 逆序恢复砖块，计算每次恢复后新增的稳定砖块数
        directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]
        result = [0] * len(hits)

        for i in range(len(hits) - 1, -1, -1):
            r, c = hits[i]
            # 如果原 grid 此处就没有砖块，跳过
            if grid[r][c] == 0:
                continue

            idx = r * n + c
            # 记录恢复前天花板连通分量的大小
            before = uf.get_size(top)

            # 恢复此砖块
            g[r][c] = 1

            # 如果在第 0 行，连通天花板
            if r == 0:
                uf.union(idx, top)

            # 与四个方向的邻居连通
            for dr, dc in directions:
                nr, nc = r + dr, c + dc
                if 0 <= nr < m and 0 <= nc < n and g[nr][nc] == 1:
                    uf.union(idx, nr * n + nc)

            # 计算恢复后天花板连通分量的大小变化
            after = uf.get_size(top)
            # 新增的稳定砖块数（减去自身 1）
            result[i] = max(0, after - before - 1)

        return result


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    grid1 = [[1, 0, 0, 0], [1, 1, 1, 0]]
    hits1 = [[1, 0]]
    print("Test 1:", sol.hitBricks([row[:] for row in grid1], hits1))  # [2]

    # 测试用例 2
    grid2 = [[1, 0, 0, 0], [1, 1, 0, 0]]
    hits2 = [[1, 1], [1, 0]]
    print("Test 2:", sol.hitBricks([row[:] for row in grid2], hits2))  # [0, 0]

    # 测试用例 3: 单个砖块
    grid3 = [[1], [1]]
    hits3 = [[1, 0]]
    print("Test 3:", sol.hitBricks([row[:] for row in grid3], hits3))  # [0]

    # 测试用例 4: 击打空位
    grid4 = [[1, 1], [0, 0]]
    hits4 = [[1, 0]]
    print("Test 4:", sol.hitBricks([row[:] for row in grid4], hits4))  # [0]

    print("All tests passed!")
