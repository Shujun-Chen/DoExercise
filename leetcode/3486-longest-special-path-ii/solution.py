from typing import List
from collections import defaultdict


class Solution:
    def longestSpecialPath(self, edges: List[List[int]], nums: List[int]) -> List[int]:
        """
        最长特殊路径 II
        特殊路径：从祖先到后代的向下路径，所有值互不相同，但允许恰好一个值出现两次。
        返回 [最长特殊路径长度, 所有最长路径中节点数的最小值]。

        思路：DFS + 滑动窗口 + 前缀和
        - 维护当前从根到当前节点的路径
        - excess = sum(max(0, count[v]-1))，表示路径中重复值的超出量
        - 当 excess > 1 时，从窗口左端收缩直到 excess <= 1
        - 时间 O(n)，空间 O(n)
        """
        # 构建邻接表
        n = len(nums)
        adj = [[] for _ in range(n)]
        for u, v, w in edges:
            adj[u].append((v, w))
            adj[v].append((u, w))

        # DFS 状态
        path_vals = []       # 当前路径上的节点值
        path_dist = []       # 从根到路径上各节点的累计距离
        count = defaultdict(int)  # 值在当前窗口中出现的次数

        best_len = -1         # 最长特殊路径长度
        best_nodes = n + 1    # 最长特殊路径的最小节点数
        excess = 0            # sum(max(0, count[v]-1))
        start = 0             # 有效窗口的起始索引

        def dfs(u: int, parent: int, cum_dist: int) -> None:
            nonlocal excess, best_len, best_nodes, start

            # 保存当前状态以便回溯恢复
            removed = []  # 收缩过程中移除的 (值, 移除前计数)

            # 将当前节点加入路径
            val = nums[u]
            path_vals.append(val)
            path_dist.append(cum_dist)
            idx = len(path_vals) - 1

            old_count = count[val]
            count[val] += 1
            if old_count >= 1:
                excess += 1

            # 收缩窗口：当 excess > 1 时，从窗口前端移除节点
            while excess > 1:
                front_val = path_vals[start]
                removed.append((front_val, count[front_val]))
                count[front_val] -= 1
                if count[front_val] >= 1:
                    excess -= 1
                start += 1

            # 计算当前有效路径的长度和节点数
            curr_len = path_dist[idx] - path_dist[start]
            curr_nodes = idx - start + 1

            # 更新全局最优
            if curr_len > best_len:
                best_len = curr_len
                best_nodes = curr_nodes
            elif curr_len == best_len:
                best_nodes = min(best_nodes, curr_nodes)

            # 遍历子节点
            for v, w in adj[u]:
                if v != parent:
                    dfs(v, u, cum_dist + w)

            # 回溯：恢复收缩前的状态
            for front_val, old_count_val in reversed(removed):
                start -= 1
                count[front_val] = old_count_val

            # 移除当前节点
            path_vals.pop()
            path_dist.pop()
            count[val] -= 1

        dfs(0, -1, 0)
        return [best_len, best_nodes]


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    edges1 = [[0, 1, 1], [1, 2, 3], [1, 3, 1], [2, 4, 6],
              [4, 7, 2], [3, 5, 2], [3, 6, 5], [6, 8, 3]]
    nums1 = [1, 1, 0, 3, 1, 2, 1, 1, 0]
    result1 = sol.longestSpecialPath(edges1, nums1)
    print(f"示例 1: {result1}")  # 期望: [9, 3]

    # 示例 2
    edges2 = [[1, 0, 3], [0, 2, 4], [0, 3, 5]]
    nums2 = [1, 1, 0, 2]
    result2 = sol.longestSpecialPath(edges2, nums2)
    print(f"示例 2: {result2}")  # 期望: [5, 2]
