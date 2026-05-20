"""
354. Russian Doll Envelopes
https://leetcode.com/problems/russian-doll-envelopes/

核心思路：转化为最长递增子序列（LIS）问题。
1. 按宽度升序排列，宽度相同时按高度降序排列（避免同宽度嵌套）
2. 对高度序列求 LIS，使用耐心排序（二分查找）优化到 O(n log n)

时间复杂度：O(n log n)
空间复杂度：O(n)
"""

from typing import List
import bisect


class Solution:
    def maxEnvelopes(self, envelopes: List[List[int]]) -> int:
        # 按宽度升序，宽度相同则高度降序
        # 高度降序确保同宽度的信封不会互相嵌套
        envelopes.sort(key=lambda x: (x[0], -x[1]))

        # 提取所有高度
        heights = [h for _, h in envelopes]

        # 耐心排序求 LIS 长度
        # tails[i] 表示长度为 i+1 的递增子序列的最小末尾值
        tails = []
        for h in heights:
            # 在 tails 中二分查找第一个 >= h 的位置
            idx = bisect.bisect_left(tails, h)
            if idx == len(tails):
                tails.append(h)  # h 比所有 tails 值都大，扩展 LIS
            else:
                tails[idx] = h  # 用更小的值替换，为后续留空间

        return len(tails)


if __name__ == "__main__":
    # 测试用例
    sol = Solution()

    # 示例 1
    envelopes1 = [[5, 4], [6, 4], [6, 7], [2, 3]]
    assert sol.maxEnvelopes(envelopes1) == 3, f"Expected 3, got {sol.maxEnvelopes(envelopes1)}"

    # 示例 2
    envelopes2 = [[1, 1], [1, 1], [1, 1]]
    assert sol.maxEnvelopes(envelopes2) == 1, f"Expected 1, got {sol.maxEnvelopes(envelopes2)}"

    # 边界：单个信封
    assert sol.maxEnvelopes([[5, 4]]) == 1

    # 边界：严格递增
    envelopes3 = [[1, 2], [2, 3], [3, 4], [4, 5]]
    assert sol.maxEnvelopes(envelopes3) == 4

    # 同宽不同高
    envelopes4 = [[2, 3], [2, 4], [2, 1]]
    assert sol.maxEnvelopes(envelopes4) == 1

    print("所有测试用例通过！")
