from typing import List


class Solution:
    def longestArithSeqLength(self, nums: List[int]) -> int:
        """
        求最长等差子序列的长度。

        思路：动态规划。
        对于每个位置 i，用一个字典记录以 i 结尾、公差为 diff 的最长等差子序列长度。
        遍历所有 j < i，计算 diff = nums[i] - nums[j]，
        则 dp[i][diff] = dp[j].get(diff, 1) + 1，表示在 j 的基础上延长一个元素。
        """
        n = len(nums)
        # dp[i] = {diff: 最长长度}
        dp = [{} for _ in range(n)]
        ans = 2  # 最短的等差子序列长度为 2

        for i in range(n):
            for j in range(i):
                diff = nums[i] - nums[j]
                # 以 j 结尾的该公差子序列长度（至少为 1，即 j 自身）
                length = dp[j].get(diff, 1) + 1
                # 更新以 i 结尾的该公差子序列长度
                if length > dp[i].get(diff, 0):
                    dp[i][diff] = length
                # 更新全局答案
                if length > ans:
                    ans = length

        return ans

# 时间复杂度：O(n²)，n 为数组长度
# 空间复杂度：O(n²)，最坏情况下每个位置存储 O(n) 个不同公差


if __name__ == "__main__":
    sol = Solution()
    print(sol.longestArithSeqLength([3, 6, 9, 12]))           # 4
    print(sol.longestArithSeqLength([9, 4, 7, 2, 10]))       # 3
    print(sol.longestArithSeqLength([20, 1, 15, 3, 10, 5, 8]))  # 4
    print(sol.longestArithSeqLength([1, 2, 3, 4]))            # 4
    print(sol.longestArithSeqLength([1, 3, 5, 7, 9]))         # 5
    print(sol.longestArithSeqLength([1, 1, 1, 1]))            # 4
