class Solution:
    def minCost(self, colors: str, neededTime: list[int]) -> int:
        """
        LeetCode 1578. Minimum Time to Make Rope Colorful
        思路：贪心。扫描 colors，当相邻两个气球颜色相同时，
        只能保留 neededTime 更大的那个，移除较小的一个（累加较小的时间）。
        时间复杂度：O(n)
        空间复杂度：O(1)
        """
        n = len(colors)
        total = 0  # 总花费时间
        i = 0
        while i < n:
            # 找到当前相同颜色气球的连续区间 [i, j]
            j = i
            cur_sum = 0
            cur_max = 0
            while j < n and colors[j] == colors[i]:
                # 累加这段区间所有 neededTime，并记录最大值
                cur_sum += neededTime[j]
                cur_max = max(cur_max, neededTime[j])
                j += 1
            # 在这段连续相同颜色的气球里，保留最大时间的，移除其余
            # 因此要花掉的总时间 = 区间总和 - 最大值
            if j - i > 1:
                total += cur_sum - cur_max
            i = j
        return total


if __name__ == "__main__":
    sol = Solution()
    # 测试用例 1
    assert sol.minCost("abaac", [1, 2, 3, 4, 5]) == 3
    # 测试用例 2
    assert sol.minCost("abc", [1, 2, 3]) == 0
    # 测试用例 3
    assert sol.minCost("aabaa", [1, 2, 3, 4, 1]) == 2
    # 边界：只有两个同色
    assert sol.minCost("aa", [1, 5]) == 1
    # 边界：全部同色
    assert sol.minCost("aaaa", [3, 1, 2, 4]) == 1 + 2 + 3  # = 6，保留 4
    print("All tests passed!")