"""
485. Max Consecutive Ones
https://leetcode.com/problems/max-consecutive-ones/
难度：Easy

时间复杂度：O(n)，遍历数组一次
空间复杂度：O(1)，只使用两个计数器变量
"""

from typing import List


class Solution:
    def findMaxConsecutiveOnes(self, nums: List[int]) -> int:
        # cur_count: 当前连续 1 的个数
        # max_count: 历史最大连续 1 的个数
        max_count = 0
        cur_count = 0

        for num in nums:
            if num == 1:
                # 遇到 1，增加当前计数并更新最大值
                cur_count += 1
                max_count = max(max_count, cur_count)
            else:
                # 遇到 0，重置当前计数
                cur_count = 0

        return max_count


if __name__ == "__main__":
    s = Solution()
    # 测试用例
    assert s.findMaxConsecutiveOnes([1, 1, 0, 1, 1, 1]) == 3
    assert s.findMaxConsecutiveOnes([1, 0, 1, 1, 0, 1]) == 2
    assert s.findMaxConsecutiveOnes([0]) == 0
    assert s.findMaxConsecutiveOnes([1]) == 1
    assert s.findMaxConsecutiveOnes([0, 0, 0]) == 0
    assert s.findMaxConsecutiveOnes([1, 1, 1]) == 3
    print("All tests passed!")
