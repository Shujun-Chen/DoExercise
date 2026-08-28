"""
LeetCode 221. Maximal Square
难度：Medium

题目描述：
给定一个由 '0' 和 '1' 组成的二维矩阵，找出只包含 '1' 的最大正方形，返回其面积。

示例：
输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
输出：4
解释：右下角 2x2 的正方形全是 1

解题思路（二维动态规划）：
设 dp[i][j] 为以 matrix[i][j] 为右下角的最大全 1 正方形的边长。

状态转移：
- 若 matrix[i][j] == '0'，dp[i][j] = 0
- 若 matrix[i][j] == '1'，dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
  （要扩展成正方形，左、上、左上三个方向都得支持）

答案 = max(dp)^2。

空间优化：只保留上一行 dp 值 + 左上角的 dp 值（用变量 pre 暂存）。
"""

from typing import List


class Solution:
    def maximalSquare(self, matrix: List[List[str]]) -> int:
        if not matrix or not matrix[0]:
            return 0

        m, n = len(matrix), len(matrix[0])
        # dp[j] 表示上一行（或当前行在处理左邻居时）的 dp 值
        dp = [0] * (n + 1)  # 多开一列免去边界判断
        max_side = 0
        pre = 0  # 用于暂存左上角 dp[i-1][j-1]

        for i in range(1, m + 1):
            pre = 0  # 每行开始时左上角重置
            for j in range(1, n + 1):
                # 关键：保存当前 dp[j]，下一轮就变成"左上角"
                cur = dp[j]
                if matrix[i - 1][j - 1] == '1':
                    # 由左、上、左上三个方向的最小值 + 1
                    dp[j] = min(dp[j], dp[j - 1], pre) + 1
                    if dp[j] > max_side:
                        max_side = dp[j]
                else:
                    dp[j] = 0
                pre = cur  # 当前值就是下一轮的"左上角"

        return max_side * max_side


if __name__ == "__main__":
    s = Solution()

    # 用例 1：题面示例
    m1 = [
        ["1", "0", "1", "0", "0"],
        ["1", "0", "1", "1", "1"],
        ["1", "1", "1", "1", "1"],
        ["1", "0", "0", "1", "0"],
    ]
    assert s.maximalSquare(m1) == 4
    print("用例 1 通过：", s.maximalSquare(m1))

    # 用例 2：全 0
    m2 = [["0"]]
    assert s.maximalSquare(m2) == 0
    print("用例 2 通过")

    # 用例 3：全 1
    m3 = [["1", "1"], ["1", "1"]]
    assert s.maximalSquare(m3) == 4
    print("用例 3 通过")

    # 用例 4：单行 1,1,0,1,1 -> 最大 1x1，面积为 1
    m4 = [["1", "1", "0", "1", "1"]]
    assert s.maximalSquare(m4) == 1
    print("用例 4 通过")

    # 用例 5：大正方形 3x3 全 1
    m5 = [["1"] * 3 for _ in range(3)]
    assert s.maximalSquare(m5) == 9
    print("用例 5 通过")

    print("\n所有用例通过 ✓")
