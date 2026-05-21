class Solution(object):
    def minimumCost(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        # 将数组分成3个连续非空子数组，每个子数组的成本 = 它的第一个元素
        # 总成本 = nums[0] + nums[i] + nums[j]，其中 0 < i < j < n
        # 要使总成本最小，只需在 nums[1:] 中选出最小的两个元素
        rest = sorted(nums[1:])
        return nums[0] + rest[0] + rest[1]
        # 时间复杂度：O(n log n) — 排序开销
        # 空间复杂度：O(n) — 排序使用的额外空间


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    nums = [1, 2, 3, 12]
    print(f"nums={nums}, result={sol.minimumCost(nums)} (expected=6)")

    # 测试用例 2
    nums = [5, 4, 3]
    print(f"nums={nums}, result={sol.minimumCost(nums)} (expected=12)")

    # 测试用例 3
    nums = [10, 3, 1, 1]
    print(f"nums={nums}, result={sol.minimumCost(nums)} (expected=12)")

    # 测试用例 4：额外测试
    nums = [1, 10, 5, 2]
    # nums[1:] = [10,5,2], 最小两个 = 2+5=7, 总 = 1+7=8
    print(f"nums={nums}, result={sol.minimumCost(nums)} (expected=8)")
