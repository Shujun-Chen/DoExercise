from typing import List

class Solution:
    def largestLocal(self, grid: List[List[int]]) -> List[List[int]]:
        """
        在 n×n 矩阵中，找出每个 3×3 子矩阵的最大值，
        生成 (n-2)×(n-2) 的结果矩阵。

        思路：暴力遍历每个 3×3 窗口，取最大值。
        时间复杂度：O(n^2)，每个位置检查 9 个元素，共 (n-2)^2 个窗口
        空间复杂度：O(n^2)，输出矩阵大小
        """
        n = len(grid)
        # 结果矩阵大小为 (n-2) × (n-2)
        result = [[0] * (n - 2) for _ in range(n - 2)]

        # 遍历每个 3×3 窗口的左上角 (i, j)
        for i in range(n - 2):
            for j in range(n - 2):
                # 找出以 (i, j) 为左上角的 3×3 子矩阵中的最大值
                max_val = 0
                for di in range(3):
                    for dj in range(3):
                        max_val = max(max_val, grid[i + di][j + dj])
                result[i][j] = max_val

        return result


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    grid1 = [[9, 9, 8, 1], [5, 6, 2, 6], [8, 2, 6, 4], [6, 2, 2, 2]]
    ans1 = sol.largestLocal(grid1)
    assert ans1 == [[9, 9], [8, 6]], f"测试用例1失败: {ans1}"
    print(f"测试用例1通过: {ans1}")

    # 测试用例 2
    grid2 = [[1, 1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 2, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1, 1]]
    ans2 = sol.largestLocal(grid2)
    assert ans2 == [[2, 2, 2], [2, 2, 2], [2, 2, 2]], f"测试用例2失败: {ans2}"
    print(f"测试用例2通过: {ans2}")

    # 测试用例 3: 最小 n=3
    grid3 = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    ans3 = sol.largestLocal(grid3)
    assert ans3 == [[9]], f"测试用例3失败: {ans3}"
    print(f"测试用例3通过: {ans3}")

    print("\n所有测试用例通过！")
