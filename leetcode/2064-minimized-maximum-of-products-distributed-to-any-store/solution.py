class Solution:
    def minimizedMaximum(self, n: int, quantities: list[int]) -> int:
        """
        二分查找最小的最大产品分配数 x。
        
        对于给定的 x，检查每个产品类型需要多少个店铺：
        - 如果某个产品有 q 件，则需要 ceil(q / x) 个店铺
        - 如果总店铺数 <= n，则 x 可行
        
        二分查找范围：[1, max(quantities)]
        """
        # 判断给定的 x 是否可行
        def can_distribute(x: int) -> bool:
            stores_needed = 0
            for q in quantities:
                # 向上取整：ceil(q / x)
                stores_needed += (q + x - 1) // x
                # 提前剪枝：如果已经超过 n，直接返回 False
                if stores_needed > n:
                    return False
            return stores_needed <= n

        lo, hi = 1, max(quantities)
        while lo < hi:
            mid = (lo + hi) // 2
            if can_distribute(mid):
                hi = mid  # 尝试更小的 x
            else:
                lo = mid + 1  # 需要更大的 x
        return lo


if __name__ == "__main__":
    s = Solution()

    # 示例 1
    n = 6
    quantities = [11, 6]
    result = s.minimizedMaximum(n, quantities)
    print(f"n={n}, quantities={quantities} → {result} (expected: 3)")
    assert result == 3

    # 示例 2
    n = 7
    quantities = [15, 10, 10]
    result = s.minimizedMaximum(n, quantities)
    print(f"n={n}, quantities={quantities} → {result} (expected: 5)")
    assert result == 5

    # 示例 3
    n = 1
    quantities = [100000]
    result = s.minimizedMaximum(n, quantities)
    print(f"n={n}, quantities={quantities} → {result} (expected: 100000)")
    assert result == 100000

    # 额外测试：边界情况
    n = 2
    quantities = [5, 7]
    result = s.minimizedMaximum(n, quantities)
    print(f"n={n}, quantities={quantities} → {result}")

    print("所有测试通过！")

# 时间复杂度：O(m * log M)，其中 m = len(quantities), M = max(quantities)
# 空间复杂度：O(1)
