"""
1389. Create Target Array in the Given Order
https://leetcode.com/problems/create-target-array-in-the-given-order/

时间复杂度：O(n^2) — 每次插入操作可能移动后续元素
空间复杂度：O(n) — 结果数组
"""

from typing import List


class Solution:
    def createTargetArray(self, nums: List[int], index: List[int]) -> List[int]:
        # 按给定顺序依次插入到目标位置
        target = []
        for i in range(len(nums)):
            # list.insert(pos, val) 会将 val 插入到下标 pos 处，原位置及之后元素后移
            target.insert(index[i], nums[i])
        return target


if __name__ == "__main__":
    s = Solution()
    # 示例测试
    assert s.createTargetArray([0, 1, 2, 3, 4], [0, 1, 2, 2, 1]) == [0, 4, 1, 3, 2]
    assert s.createTargetArray([1, 2, 3, 4, 0], [0, 1, 2, 3, 0]) == [0, 1, 2, 3, 4]
    assert s.createTargetArray([1], [0]) == [1]
    print("所有测试用例通过")
