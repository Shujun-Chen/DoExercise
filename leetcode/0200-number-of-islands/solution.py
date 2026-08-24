import sys
from typing import List

# LeetCode 约束 m,n <= 300，最深线性岛屿 300，默认递归深度 1000 已足够。
# 这里留点保险，避免极端退化输入触发栈溢出。
sys.setrecursionlimit(10000)


class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        """
        岛屿数量。

        思路（DFS + 原地标记）：
        1. 遍历整个网格，每遇到一个 '1'，岛屿计数 +1。
        2. 从这个 '1' 出发做深度优先搜索（DFS），
           把与之相连（上下左右）的所有 '1' 都标记为 '0'，
           避免之后被重复计数。
        3. 标记动作原地修改 grid，不占用额外 visited 数组，
           空间复杂度降为 O(1) 辅助空间（递归栈不计）。
        4. 遍历方向：上、下、左、右 4 个方向。

        时间复杂度：O(m * n)，每个格子最多被访问 2 次
                    （一次作为起点 + 一次作为被淹没点）。
        空间复杂度：O(m * n) 最坏情况（整张图都是陆地），
                    来自 DFS 递归栈深度；辅助空间 O(1)。
        """
        if not grid or not grid[0]:
            return 0

        m, n = len(grid), len(grid[0])
        count = 0

        def dfs(i: int, j: int) -> None:
            # 越界或已经是水，直接返回
            if i < 0 or i >= m or j < 0 or j >= n or grid[i][j] != "1":
                return
            # 原地标记为水，避免重复访问
            grid[i][j] = "0"
            # 四个方向继续淹没
            dfs(i + 1, j)
            dfs(i - 1, j)
            dfs(i, j + 1)
            dfs(i, j - 1)

        for i in range(m):
            for j in range(n):
                if grid[i][j] == "1":
                    count += 1
                    dfs(i, j)

        return count


if __name__ == "__main__":
    solution = Solution()

    # 测试用例 1：标准示例
    grid1 = [
        ["1", "1", "1", "1", "0"],
        ["1", "1", "0", "1", "0"],
        ["1", "1", "0", "0", "0"],
        ["0", "0", "0", "0", "0"],
    ]
    assert solution.numIslands(grid1) == 1, f"Test 1 failed: {solution.numIslands([row[:] for row in grid1])}"
    print("Test 1 passed: 标准 4x5 网格 => 1")

    # 测试用例 2：3 个独立岛屿
    grid2 = [
        ["1", "1", "0", "0", "0"],
        ["1", "1", "0", "0", "0"],
        ["0", "0", "1", "0", "0"],
        ["0", "0", "0", "1", "1"],
    ]
    assert solution.numIslands(grid2) == 3, f"Test 2 failed"
    print("Test 2 passed: 3 个独立岛屿 => 3")

    # 测试用例 3：全水
    grid3 = [
        ["0", "0", "0"],
        ["0", "0", "0"],
    ]
    assert solution.numIslands(grid3) == 0, f"Test 3 failed"
    print("Test 3 passed: 全水网格 => 0")

    # 测试用例 4：全陆地
    grid4 = [
        ["1", "1"],
        ["1", "1"],
    ]
    assert solution.numIslands(grid4) == 1, f"Test 4 failed"
    print("Test 4 passed: 全陆地网格 => 1")

    # 测试用例 5：单格子
    grid5 = [["1"]]
    assert solution.numIslands(grid5) == 1, f"Test 5 failed"
    print("Test 5 passed: 单格陆地 => 1")

    grid6 = [["0"]]
    assert solution.numIslands(grid6) == 0, f"Test 6 failed"
    print("Test 6 passed: 单格水 => 0")

    # 测试用例 7：对角线相连（不算相连）
    grid7 = [
        ["1", "0", "1"],
        ["0", "1", "0"],
        ["1", "0", "1"],
    ]
    assert solution.numIslands(grid7) == 5, f"Test 7 failed"
    print("Test 7 passed: 对角线相连（不算相连）=> 5")

    # 测试用例 8：长条形岛屿
    grid8 = [
        ["1", "0", "0", "0", "0", "0", "1"],
        ["0", "1", "0", "0", "0", "1", "0"],
        ["0", "0", "1", "0", "1", "0", "0"],
        ["0", "0", "0", "1", "0", "0", "0"],
    ]
    assert solution.numIslands(grid8) == 7, f"Test 8 failed"
    print("Test 8 passed: 散布岛屿 => 7")

    # 测试用例 9：空网格
    assert solution.numIslands([]) == 0
    assert solution.numIslands([[]]) == 0
    print("Test 9 passed: 空网格 => 0")

    print("\n所有测试用例通过！")