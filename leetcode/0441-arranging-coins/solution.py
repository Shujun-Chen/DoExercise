class Solution:
    def arrangeCoins(self, n: int) -> int:
        """
        :type n: int
        :rtype: int

        思路：第 k 行需要 k 枚硬币，k 行共需 k*(k+1)/2 枚硬币。
        求满足 k*(k+1)/2 <= n 的最大 k。
        即解二次方程 k^2 + k - 2n <= 0 的最大整数根 k = (-1 + sqrt(1+8n)) / 2。
        直接用公式计算，注意 n 可能很大（2^31 - 1），用 sqrt 后取 int。
        """
        # 利用求根公式直接计算；int() 自动向下取整
        # 时间复杂度 O(1)，空间复杂度 O(1)
        import math
        return int((math.sqrt(1 + 8 * n) - 1) // 2)


if __name__ == "__main__":
    sol = Solution()
    # 测试用例
    assert sol.arrangeCoins(5) == 2, f"expect 2, got {sol.arrangeCoins(5)}"
    assert sol.arrangeCoins(8) == 3, f"expect 3, got {sol.arrangeCoins(8)}"
    assert sol.arrangeCoins(1) == 1
    assert sol.arrangeCoins(0) == 0
    assert sol.arrangeCoins(6) == 3  # 1+2+3 = 6，刚好三行完整
    assert sol.arrangeCoins(1804289383) == 60070  # 大数测试
    print("All tests passed!")