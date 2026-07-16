class Solution:
    def minCost(self, grid: list[list[int]]) -> int:
        # 从左上角 (0,0) 出发，每步只能向右或向下，走到右下角 (m-1,n-1)。
        # 路径代价 = 路径上所有格子值的 XOR，求最小可能代价。
        #
        # 关键观察：grid[i][j] <= 1023，任意前缀 XOR 结果始终落在 [0, 1023]。
        # 因此可以对每个格子维护"从起点到该格所有可能的 XOR 值集合"，
        # 逐格从上方和左方转移即可。
        m, n = len(grid), len(grid[0])

        # dp[j] 表示"当前正在处理的行"中第 j 列格子可达到的 XOR 值集合。
        # 采用按列滚动：处理到 (i, j) 时，dp[j] 仍保存上一行第 j 列的结果（上方来源），
        # 而 dp[j-1] 已被更新为当前行的结果（左方来源）。
        dp = [set() for _ in range(n)]

        for i in range(m):
            for j in range(n):
                v = grid[i][j]
                cur = set()
                if i == 0 and j == 0:
                    # 起点：只有它自身
                    cur.add(v)
                else:
                    if i > 0:
                        # 来自上方：dp[j] 此刻保存的是上一行第 j 列的集合
                        for x in dp[j]:
                            cur.add(x ^ v)
                    if j > 0:
                        # 来自左方：dp[j-1] 已更新为当前行第 j-1 列的集合
                        for x in dp[j - 1]:
                            cur.add(x ^ v)
                dp[j] = cur

        # 终点所有可达 XOR 值中的最小值
        return min(dp[n - 1])

    # 时间复杂度：O(m * n * 1024)
    #   每个格子的集合大小至多 1024，转移时遍历上/左集合。
    #   由于 m * n <= 1000，总量约 1000 * 1024，可接受。
    # 空间复杂度：O(n * 1024)
    #   只保留一行(按列滚动)的集合，每个集合至多 1024 个元素。


if __name__ == "__main__":
    s = Solution()

    # 示例 1：单格边界，答案即该格子本身
    assert s.minCost([[1]]) == 1

    # 示例 2：2x2 网格
    # 路径 右->下: 2^1^4 = 7
    # 路径 下->右: 2^3^4 = 5
    # 最小为 5
    assert s.minCost([[2, 1], [3, 4]]) == 5

    # 示例 3：3x3 网格 [[1,2,3],[4,5,6],[7,8,9]]
    # 终点可达 XOR 集合为 {1,3,7,9,15}，最小为 1
    assert s.minCost([[1, 2, 3], [4, 5, 6], [7, 8, 9]]) == 1

    # 附加边界：2x3 网格全为 3，任意路径经过 4 个格子，四个 3 异或为 0
    assert s.minCost([[3, 3, 3], [3, 3, 3]]) == 0

    print("All tests passed!")
