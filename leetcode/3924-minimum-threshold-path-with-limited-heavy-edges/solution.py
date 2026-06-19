from typing import List
from collections import deque


class Solution:
    def minThreshold(
        self,
        n: int,
        edges: List[List[int]],
        source: int,
        target: int,
        k: int,
    ) -> int:
        """
        返回最小的 threshold，使得存在一条从 source 到 target 的路径，
        其中 heavy 边（weight > threshold）的数量 ≤ k。
        如果不存在这样的 threshold，返回 -1。
        """
        # 如果起点即终点，不需要经过任何边，threshold 为 0
        if source == target:
            return 0

        # 建图
        adj = [[] for _ in range(n)]
        max_weight = 0
        for u, v, w in edges:
            adj[u].append((v, w))
            adj[v].append((u, w))
            max_weight = max(max_weight, w)

        # 0-1 BFS：检查给定 threshold 下是否能走到 target
        def reachable(threshold: int) -> bool:
            """0-1 BFS 判断是否存在 ≤ k 条 heavy 边的路径"""
            INF = 10**9
            dist = [INF] * n
            dist[source] = 0
            dq = deque([source])

            while dq:
                u = dq.popleft()
                d = dist[u]
                if d > k:
                    continue  # 已经超了 heavy 限制，剪枝
                if u == target:
                    return True
                for v, w in adj[u]:
                    heavy = 1 if w > threshold else 0  # heavy 边权重为 1
                    nd = d + heavy
                    if nd < dist[v] and nd <= k:
                        dist[v] = nd
                        if heavy == 0:
                            dq.appendleft(v)  # light 边优先
                        else:
                            dq.append(v)

            return dist[target] <= k

        # 第一步：检查是否任何路径都存在
        if not reachable(max_weight):
            return -1

        # 第二步：二分查找最小的 threshold
        lo, hi = 0, max_weight
        while lo < hi:
            mid = (lo + hi) // 2
            if reachable(mid):
                hi = mid
            else:
                lo = mid + 1

        return lo


if __name__ == "__main__":
    sol = Solution()

    # Example 1
    n = 6
    edges = [[0, 1, 5], [1, 2, 3], [3, 4, 4], [4, 5, 1], [1, 4, 2]]
    source, target, k = 0, 3, 1
    print(sol.minThreshold(n, edges, source, target, k))  # 期望输出: 4

    # Example 2
    n = 6
    edges = [[0, 1, 3], [1, 2, 4], [3, 4, 5], [4, 5, 6]]
    source, target, k = 0, 4, 1
    print(sol.minThreshold(n, edges, source, target, k))  # 期望输出: -1

    # Example 3: source == target
    n = 4
    edges = [[0, 1, 2], [1, 2, 2], [2, 3, 2], [3, 0, 2]]
    source, target, k = 0, 0, 0
    print(sol.minThreshold(n, edges, source, target, k))  # 期望输出: 0

    # 自定义测试：一条简单路径，k 足够大
    n = 3
    edges = [[0, 1, 10], [1, 2, 20]]
    source, target, k = 0, 2, 2
    print(sol.minThreshold(n, edges, source, target, k))  # 期望输出: 0（因为 k=2 可以走 2 条 heavy 边）
