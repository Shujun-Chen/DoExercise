"""
LeetCode 2536. Increment Submatrices by One
难度：Medium

题目描述：
给定一个 n x n 的矩阵 mat，初始全为 0。对每个查询 [row1, col1, row2, col2]，
将子矩阵 (row1, col1) 到 (row2, col2) 中所有元素加 1。返回处理完所有查询后的矩阵。

解题思路（二维差分数组）：
朴素做法是对每个查询遍历整个子矩阵，时间复杂度 O(n² * q)，最坏情况
n=500, q=10⁴ 时为 2.5*10⁹，会超时。
利用二维差分数组可以做到 O(n² + q)：
  1. 创建一个 (n+1) x (n+1) 的差分数组 diff，初始全 0。
  2. 对每个查询 [r1, c1, r2, c2]，执行四个"打标记"操作：
        diff[r1][c1]     += 1
        diff[r1][c2 + 1] -= 1   (若 c2 + 1 < n)
        diff[r2 + 1][c1] -= 1   (若 r2 + 1 < n)
        diff[r2 + 1][c2 + 1] += 1 (若 r2 + 1 < n 且 c2 + 1 < n)
  3. 对 diff 做二维前缀和还原，得到每个位置的最终值。

时间复杂度：O(n² + q)
空间复杂度：O(n²)
"""


class Solution:
    def rangeAddQueries(self, n: int, queries: list[list[int]]) -> list[list[int]]:
        # 创建 (n+1) x (n+1) 的差分数组，多出一行一列用于处理边界
        diff = [[0] * (n + 1) for _ in range(n + 1)]

        # 对每个查询在差分数组上"打标记"
        for r1, c1, r2, c2 in queries:
            diff[r1][c1] += 1
            diff[r1][c2 + 1] -= 1
            diff[r2 + 1][c1] -= 1
            diff[r2 + 1][c2 + 1] += 1

        # 二维前缀和还原：第一遍沿列方向累加，第二遍沿行方向累加
        # 先做行方向的前缀和
        for i in range(n + 1):
            for j in range(1, n + 1):
                diff[i][j] += diff[i][j - 1]

        # 再做列方向的前缀和（即把每一列向下累加）
        for j in range(n + 1):
            for i in range(1, n + 1):
                diff[i][j] += diff[i - 1][j]

        # 取前 n x n 部分即为答案
        return [row[:n] for row in diff[:n]]


if __name__ == "__main__":
    # 测试用例 1
    sol = Solution()
    result1 = sol.rangeAddQueries(3, [[1, 1, 2, 2], [0, 0, 1, 1]])
    expected1 = [[1, 1, 0], [1, 2, 1], [0, 1, 1]]
    print(f"Test 1: {result1 == expected1}, result={result1}")

    # 测试用例 2
    result2 = sol.rangeAddQueries(2, [[0, 0, 1, 1]])
    expected2 = [[1, 1], [1, 1]]
    print(f"Test 2: {result2 == expected2}, result={result2}")

    # 测试用例 3：没有查询（不合法，但测一下 n=1 的单点情况）
    result3 = sol.rangeAddQueries(1, [[0, 0, 0, 0]])
    expected3 = [[1]]
    print(f"Test 3: {result3 == expected3}, result={result3}")

    # 测试用例 4：覆盖整个矩阵
    result4 = sol.rangeAddQueries(3, [[0, 0, 2, 2], [0, 0, 2, 2]])
    expected4 = [[2, 2, 2], [2, 2, 2], [2, 2, 2]]
    print(f"Test 4: {result4 == expected4}, result={result4}")
