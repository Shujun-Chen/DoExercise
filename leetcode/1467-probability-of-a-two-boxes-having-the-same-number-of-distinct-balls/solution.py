"""
1467. Probability of a Two Boxes Having The Same Number of Distinct Balls

给定 k 种颜色的 2n 个球，balls[i] 表示颜色 i 的球的数量。
将所有球随机打乱后，前 n 个放入第一个盒子，后 n 个放入第二个盒子。
求两个盒子中不同颜色数量相等的概率。
"""
from typing import List


class Solution:
    def getProbability(self, balls: List[int]) -> float:
        """
        使用 DFS 枚举所有可能的分配方案，计算概率。
        
        思路：
        - 对于每种颜色 i，选择 x_i 个球放入盒子1（0 ≤ x_i ≤ balls[i]）
        - 需要满足 sum(x_i) = n
        - 对于每种合法分配，计算其出现的方式数：
          ways = n! * n! * prod(C(balls[i], x_i)) / (2n)!
        - 概率 = sum(有利分配的方式数) / 总方式数

        时间复杂度：O(k * (max(balls[i])+1)^k) — 在约束 k≤8, balls[i]≤6 下可行
        空间复杂度：O(k) — 递归栈深度
        """
        k = len(balls)
        total_balls = sum(balls)
        n = total_balls // 2

        # 预计算阶乘 (最大到 total_balls)
        fact = [1] * (total_balls + 1)
        for i in range(1, total_balls + 1):
            fact[i] = fact[i - 1] * i

        favorable = 0  # 有利分配的方式数（乘以分母后的值）

        def dfs(idx: int, sum1: int, distinct1: int, distinct2: int, wf: int) -> None:
            """
            idx: 当前处理到的颜色索引
            sum1: 盒子1中已分配的球数
            distinct1: 盒子1中已出现的颜色数
            distinct2: 盒子2中已出现的颜色数
            wf: 已处理颜色的 C(balls[i], x_i) 乘积
            """
            nonlocal favorable
            if sum1 > n:  # 剪枝：盒子1球数不能超过 n
                return
            if idx == k:
                if sum1 == n and distinct1 == distinct2:
                    # 方式数 = n! * n! * prod(C(balls[i], x_i))
                    favorable += fact[n] * fact[n] * wf
                return

            b = balls[idx]
            for x in range(b + 1):
                new_sum1 = sum1 + x
                if new_sum1 > n:
                    break  # 后续 x 只会更大
                new_distinct1 = distinct1 + (1 if x > 0 else 0)
                new_distinct2 = distinct2 + (1 if b - x > 0 else 0)
                # 计算 C(b, x)
                comb = fact[b] // (fact[x] * fact[b - x])
                dfs(idx + 1, new_sum1, new_distinct1, new_distinct2, wf * comb)

        dfs(0, 0, 0, 0, 1)

        total_ways = fact[total_balls]
        return favorable / total_ways


if __name__ == "__main__":
    sol = Solution()
    
    # 示例 1
    result = sol.getProbability([1, 1])
    print(f"balls=[1,1] → {result:.5f} (预期: 1.00000)")
    assert abs(result - 1.00000) < 1e-5
    
    # 示例 2
    result = sol.getProbability([2, 1, 1])
    print(f"balls=[2,1,1] → {result:.5f} (预期: 0.66667)")
    assert abs(result - 0.66667) < 1e-4
    
    # 示例 3
    result = sol.getProbability([1, 2, 1, 2])
    print(f"balls=[1,2,1,2] → {result:.5f} (预期: 0.60000)")
    assert abs(result - 0.60000) < 1e-4
    
    print("所有测试通过!")
