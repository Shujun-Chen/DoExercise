from typing import List
from collections import defaultdict

class Solution:
    def waysToBuildRooms(self, prevRoom: List[int]) -> int:
        """
        计算建造所有房间的不同顺序数。

        思路：
        - 给定的 prevRoom 数组描述了一棵有根树（根为 0）
        - 每个节点必须在其父节点之后建造
        - 答案为 n! / (∏ subtree_size[v])，对所有节点 v 求子树大小的乘积
        - 该公式的组合学意义：在 n! 种排列中，每个节点的子树大小决定了
          该节点与其子孙之间不能自由排列的部分

        时间复杂度：O(n)，DFS 遍历 + 模逆运算
        空间复杂度：O(n)，存储树结构和子树大小
        """
        MOD = 10**9 + 7
        n = len(prevRoom)

        # 构建邻接表表示树
        children = defaultdict(list)
        for i in range(1, n):
            children[prevRoom[i]].append(i)

        # DFS 计算每个节点的子树大小
        subtree = [1] * n
        stack = [(0, False)]
        while stack:
            node, visited = stack.pop()
            if visited:
                for child in children[node]:
                    subtree[node] += subtree[child]
            else:
                stack.append((node, True))
                for child in reversed(children[node]):
                    stack.append((child, False))

        # 计算 n! mod MOD
        fact = 1
        for i in range(1, n + 1):
            fact = fact * i % MOD

        # 计算所有子树大小的乘积
        prod = 1
        for s in subtree:
            prod = prod * s % MOD

        # 使用费马小定理计算模逆元：a^(-1) ≡ a^(p-2) (mod p)
        def modinv(x):
            return pow(x, MOD - 2, MOD)

        return fact * modinv(prod) % MOD


if __name__ == "__main__":
    sol = Solution()

    # 示例 1：线性链 0 -> 1 -> 2，只有一种建造顺序
    assert sol.waysToBuildRooms([-1, 0, 1]) == 1
    print("示例 1 通过")

    # 示例 2：0 -> {1, 2}，1 -> 3，2 -> 4，共 6 种顺序
    assert sol.waysToBuildRooms([-1, 0, 0, 1, 2]) == 6
    print("示例 2 通过")

    # 额外测试：两节点，只有一种顺序
    assert sol.waysToBuildRooms([-1, 0]) == 1
    print("额外测试 1 通过")

    print("所有测试通过！")
