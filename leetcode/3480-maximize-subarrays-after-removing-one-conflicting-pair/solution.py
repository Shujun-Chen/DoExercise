from typing import List
from collections import defaultdict


class Solution:
    def maxSubarrays(self, n: int, conflictingPairs: List[List[int]]) -> int:
        """
        计算删除一个冲突对后，最多能得到多少个不包含任何冲突对的子数组。

        思路：
        1. 确保每对 (a, b) 满足 a < b。
        2. 从右向左扫描，维护当前活动冲突对的最小 b 值和次小 b 值。
        3. 对每个起始位置 i，有效子数组的右端点必须 < 最小 b 值。
        4. 统计每个冲突对作为"瓶颈"时对删除收益的贡献。
        5. 总有效子数组数 = 原始总数 + 最佳删除的额外收益。

        时间复杂度: O(n + m)，其中 m = len(conflictingPairs)
        空间复杂度: O(n + m)
        """
        # 1. 标准化冲突对：确保 a < b
        pairs = []
        for x, y in conflictingPairs:
            a, b = (x, y) if x < y else (y, x)
            pairs.append((a, b))

        m = len(pairs)
        # 按 a 值分组
        by_a = defaultdict(list)
        for idx, (a, b) in enumerate(pairs):
            by_a[a].append((b, idx))

        INF = n + 5
        min_b = INF          # 当前最小 b
        min_idx = -1         # 当前最小 b 对应的冲突对索引
        second_min_b = INF   # 当前次小 b
        total = 0            # 原始有效子数组总数
        contrib = [0] * m    # 每个冲突对删除后的收益

        # 2. 从右向左扫描位置 i = n, n-1, ..., 1
        for i in range(n, 0, -1):
            # 添加所有 a == i 的冲突对到活动集合
            if i in by_a:
                for b, idx in by_a[i]:
                    if b < min_b:
                        # 新最小值出现，原来的最小值降为次小
                        second_min_b = min_b
                        min_b = b
                        min_idx = idx
                    elif b < second_min_b:
                        second_min_b = b

            if min_b == INF:
                # 没有活动冲突对，所有子数组都有效
                total += n + 1 - i
            else:
                # 以 i 为起点的有效子数组数 = min_b - i
                total += min_b - i
                # 删除瓶颈冲突对的额外收益
                if second_min_b == INF:
                    # 没有其他冲突对，删除后所有子数组都有效
                    contrib[min_idx] += (n + 1) - min_b
                else:
                    # 次小 b 成为新瓶颈
                    contrib[min_idx] += second_min_b - min_b

        # 3. 取最大收益
        best_gain = max(contrib) if contrib else 0
        return total + best_gain


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    n = 4
    pairs = [[2, 3], [1, 4]]
    result = sol.maxSubarrays(n, pairs)
    print(f"示例 1: n={n}, pairs={pairs} => {result} (期望: 9)")
    assert result == 9, f"示例 1 失败: {result} != 9"

    # 示例 2
    n = 5
    pairs = [[1, 2], [2, 5], [3, 5]]
    result = sol.maxSubarrays(n, pairs)
    print(f"示例 2: n={n}, pairs={pairs} => {result} (期望: 12)")
    assert result == 12, f"示例 2 失败: {result} != 12"

    # 边界：无冲突对
    n = 3
    pairs = []
    result = sol.maxSubarrays(n, pairs)
    expected = 3 * 4 // 2  # 所有子数组
    print(f"边界 1 (无冲突): n={n} => {result} (期望: {expected})")
    assert result == expected, f"边界 1 失败: {result} != {expected}"

    # 边界：单个冲突对
    n = 5
    pairs = [[2, 4]]
    result = sol.maxSubarrays(n, pairs)
    # 原始：i=1,2 时 min_b=4 => 3+2=5, i=3,4,5 时无冲突 => 3+2+1=6, 总计=11
    # 删除后：全部有效 => 15
    print(f"边界 2 (单冲突): n={n}, pairs={pairs} => {result} (期望: 15)")
    assert result == 15, f"边界 2 失败: {result} != 15"

    print("\n全部测试通过!")
