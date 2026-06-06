from typing import List


class Solution:
    def stoneGameIII(self, stoneValue: List[int]) -> str:
        """
        博弈 DP：从后往前计算每个位置的最优分差。

        dp[i] 表示从位置 i 开始，当前玩家能获得的最大分差
        （当前玩家得分 - 对手得分）。

        转移方程：
            dp[i] = max( sum(stoneValue[i:i+k]) - dp[i+k] ), k = 1, 2, 3

        - 取 1 堆：stoneValue[i] - dp[i+1]
        - 取 2 堆：stoneValue[i] + stoneValue[i+1] - dp[i+2]
        - 取 3 堆：stoneValue[i] + stoneValue[i+1] + stoneValue[i+2] - dp[i+3]

        最终 dp[0] > 0 → Alice 赢，< 0 → Bob 赢，== 0 → 平局。

        时间复杂度：O(n)，每个位置只计算一次
        空间复杂度：O(1)，只用三个变量滚动
        """
        n = len(stoneValue)
        # 用三个变量代替 dp 数组，从后往前滚动
        # dp_i 对应 dp[i]，dp1=dp[i+1], dp2=dp[i+2], dp3=dp[i+3]
        dp = [0, 0, 0]  # dp1, dp2, dp3

        for i in range(n - 1, -1, -1):
            # 当前取的石头总和
            take = 0
            best = float('-inf')
            # 尝试取 1、2、3 堆
            for k in range(1, 4):
                if i + k - 1 < n:
                    take += stoneValue[i + k - 1]
                    # 对手从 i+k 开始，分差为 dp[k-1]（即 dp[i+k]）
                    best = max(best, take - dp[k - 1])
            # 滚动：dp3 丢弃，dp2→dp3, dp1→dp2, best→dp1
            dp = [best, dp[0], dp[1]]

        diff = dp[0]  # dp[0] 的值
        if diff > 0:
            return "Alice"
        elif diff < 0:
            return "Bob"
        else:
            return "Tie"


if __name__ == "__main__":
    s = Solution()
    # 测试用例 1
    assert s.stoneGameIII([1, 2, 3, 7]) == "Bob"
    # 测试用例 2
    assert s.stoneGameIII([1, 2, 3, -9]) == "Alice"
    # 测试用例 3
    assert s.stoneGameIII([1, 2, 3, 6]) == "Tie"
    # 额外：单个元素
    assert s.stoneGameIII([5]) == "Alice"
    assert s.stoneGameIII([-5]) == "Bob"
    print("All tests passed!")
