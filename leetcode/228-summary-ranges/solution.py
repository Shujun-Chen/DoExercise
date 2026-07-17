from typing import List


class Solution:
    def summaryRanges(self, nums: List[int]) -> List[str]:
        """汇总排序且不重复的数组中的连续区间。

        时间复杂度：O(n)，其中 n 为 nums 的长度。
        空间复杂度：O(n)，不计返回结果时为 O(1)。
        """
        ranges = []
        start = 0

        for i in range(1, len(nums) + 1):
            # 到达数组末尾，或当前数字不再与前一个数字连续时，结束当前区间。
            if i == len(nums) or nums[i] != nums[i - 1] + 1:
                if start == i - 1:
                    ranges.append(str(nums[start]))
                else:
                    ranges.append(f"{nums[start]}->{nums[i - 1]}")
                start = i

        return ranges


if __name__ == "__main__":
    solution = Solution()
    test_cases = [
        ([], []),
        ([0, 1, 2, 4, 5, 7], ["0->2", "4->5", "7"]),
        ([0, 2, 3, 4, 6, 8, 9], ["0", "2->4", "6", "8->9"]),
        ([-3, -2, -1, 2], ["-3->-1", "2"]),
    ]

    for nums, expected in test_cases:
        assert solution.summaryRanges(nums) == expected

    print("All tests passed!")
