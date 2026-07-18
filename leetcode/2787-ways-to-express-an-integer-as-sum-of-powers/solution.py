class Solution:
    MOD = 1_000_000_007

    def numberOfWays(self, n: int, x: int) -> int:
        """计算用互不相同的正整数 x 次幂凑成 n 的方案数。

        时间复杂度：O(n * n^(1/x))
        空间复杂度：O(n)
        """
        dp = [0] * (n + 1)
        dp[0] = 1

        base = 1
        while (power := base**x) <= n:
            # 倒序更新保证当前幂值最多使用一次，符合“正整数互不相同”的限制。
            for total in range(n, power - 1, -1):
                dp[total] = (dp[total] + dp[total - power]) % self.MOD
            base += 1

        return dp[n]


if __name__ == "__main__":
    solution = Solution()
    test_cases = [
        (10, 2, 1),
        (4, 1, 2),
        (1, 1, 1),
        (100, 2, 3),
        (160, 3, 1),
        (300, 1, 872_471_266),
    ]

    for n, x, expected in test_cases:
        actual = solution.numberOfWays(n, x)
        assert actual == expected, (
            f"n={n}, x={x}: expected {expected}, got {actual}"
        )

    print("All tests passed!")
