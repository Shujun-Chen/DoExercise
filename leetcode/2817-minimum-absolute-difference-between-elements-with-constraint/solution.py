from sortedcontainers import SortedList
from bisect import bisect_left

class Solution:
    def minAbsoluteDifference(self, nums: list[int], x: int) -> int:
        """
        使用有序列表维护距离当前位置至少 x 的元素集合。
        对于每个位置 i，在有序列表中查找与 nums[i] 最接近的值。

        时间复杂度: O(n log n) — 每次插入和查找都是 O(log n)
        空间复杂度: O(n) — 有序列表最多存储 n 个元素
        """
        if x == 0:
            return 0

        n = len(nums)
        sorted_list = SortedList()
        ans = 10**9  # nums[i] 最大值为 10^9，差值不会超过此值

        for i in range(x, n):
            # 将距离当前下标恰好 x 的元素加入有序集合
            sorted_list.add(nums[i - x])
            # 二分查找 nums[i] 在有序列表中的插入位置
            pos = bisect_left(sorted_list, nums[i])
            # 检查插入位置左右两边的值，取最小差值
            if pos < len(sorted_list):
                ans = min(ans, abs(nums[i] - sorted_list[pos]))
            if pos > 0:
                ans = min(ans, abs(nums[i] - sorted_list[pos - 1]))

        return ans


if __name__ == "__main__":
    s = Solution()
    # 测试用例 1
    assert s.minAbsoluteDifference([4, 3, 2, 4], 2) == 0
    # 测试用例 2
    assert s.minAbsoluteDifference([5, 3, 2, 10, 15], 1) == 1
    # 测试用例 3
    assert s.minAbsoluteDifference([1, 2, 3, 4], 3) == 3
    print("所有测试用例通过！")
