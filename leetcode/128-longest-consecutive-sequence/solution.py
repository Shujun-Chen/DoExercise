"""
LeetCode 128. Longest Consecutive Sequence
https://leetcode.com/problems/longest-consecutive-sequence/

难度：Medium

给定一个未排序的整数数组 nums，找出数字连续的最长序列（元素顺序不重要，
序列值连续即可）的长度。要求算法在 O(n) 时间复杂度内完成。

思路：
    朴素做法是排序后扫一遍，但排序是 O(n log n)，不达标。
    要做到 O(n)，关键思路是「只在序列的起点开始往后数」：
      - 先把数组全部丢进 set，便于 O(1) 判断某个数是否存在；
      - 遍历集合中的每个 x，若 x-1 不在集合里，说明 x 是某个连续序列的起点；
      - 从 x 出发不断判断 x+1, x+2, ... 是否在集合里，数到断掉为止；
      - 用当前序列长度更新答案。
    因为每个数最多被访问两次（一次作为起点判断、一次作为别人的后继），
    所以总复杂度是 O(n)。

复杂度：
    时间：O(n)
    空间：O(n)（哈希集合）
"""

from typing import List


class Solution:
    def longestConsecutive(self, nums: List[int]) -> int:
        if not nums:
            return 0

        s = set(nums)  # O(n) 建集合，便于 O(1) 查询
        longest = 0

        for x in s:
            # 只从「序列起点」开始枚举：x-1 不在集合里时 x 才是起点
            if x - 1 not in s:
                y = x + 1
                while y in s:
                    y += 1
                # 此时 y 是第一个不在集合里的数，长度 = y - x
                longest = max(longest, y - x)

        return longest


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    assert sol.longestConsecutive([100, 4, 200, 1, 3, 2]) == 4
    # 示例 2
    assert sol.longestConsecutive([0, 3, 7, 2, 5, 8, 4, 6, 0, 1]) == 9
    # 示例 3
    assert sol.longestConsecutive([1, 0, 1, 2]) == 3
    # 空数组
    assert sol.longestConsecutive([]) == 0
    # 单元素
    assert sol.longestConsecutive([5]) == 1
    # 全相同
    assert sol.longestConsecutive([2, 2, 2]) == 1
    # 负数
    assert sol.longestConsecutive([-2, -1, 0, 1, 2]) == 5
    # 完全不连续
    assert sol.longestConsecutive([10, 30, 20]) == 1

    print("All tests passed.")
