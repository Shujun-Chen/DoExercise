"""
LeetCode 215. Kth Largest Element in an Array
https://leetcode.com/problems/kth-largest-element-in-an-array/

题目：
    给定整数数组 nums 和整数 k，返回数组中第 k 大的元素（按排序顺序，
    注意不要求元素互不相同）。要求在线性时间内求解。

思路（快选 Quickselect）：
    1. 目标是把问题转化为"找下标为 n-k 的元素"——排序后第 k 大
       正好对应升序第 n-k 位（0-indexed）。
    2. 仿照快排的 partition：以 nums[right] 为 pivot，把 < pivot 的
       扔左边、> pivot 的扔右边，最终 pivot 落在下标 p。
    3. 比较 p 与目标下标 target：
         - p == target → 找到了，直接返回
         - p < target  → 第 k 大在右半边，继续往右找
         - p > target  → 在左半边，继续往左找
    4. 平均 O(n)，最坏 O(n^2)（每次 pivot 都选到极值），工程上
       可加随机化 pivot 把最坏概率压到忽略不计。这里我保留单边
       随机（只对 right 索引随机），代码更短。

复杂度：
    时间 平均 O(n)，最坏 O(n^2)；空间 O(1)（原地 partition）。
"""
import random
from typing import List


class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        n = len(nums)
        # 第 k 大等价于升序后下标 n-k（0-indexed）
        target = n - k

        def quickselect(left: int, right: int) -> int:
            # 随机选 pivot，避免最坏情况（已排序数组）
            pivot_idx = random.randint(left, right)
            nums[pivot_idx], nums[right] = nums[right], nums[pivot_idx]
            pivot = nums[right]

            # partition：把 < pivot 的推到左边
            store = left
            for i in range(left, right):
                if nums[i] < pivot:
                    nums[store], nums[i] = nums[i], nums[store]
                    store += 1
            # 把 pivot 放到最终位置 store
            nums[store], nums[right] = nums[right], nums[store]

            if store == target:
                return nums[store]
            elif store < target:
                # 第 k 大在右半边
                return quickselect(store + 1, right)
            else:
                # 在左半边
                return quickselect(left, store - 1)

        return quickselect(0, n - 1)


if __name__ == "__main__":
    s = Solution()

    # 官方样例 1
    assert s.findKthLargest([3, 2, 1, 5, 6, 4], 2) == 5
    # 官方样例 2（含重复，验证"不要求 distinct"）
    assert s.findKthLargest([3, 2, 3, 1, 2, 4, 5, 5, 6], 4) == 4
    # 边界：只有一个元素
    assert s.findKthLargest([1], 1) == 1
    # 边界：k=1 取最大
    assert s.findKthLargest([7, 6, 5, 4, 3, 2, 1], 1) == 7
    # 边界：k=n 取最小
    assert s.findKthLargest([7, 6, 5, 4, 3, 2, 1], 7) == 1
    # 全相等
    assert s.findKthLargest([2, 2, 2, 2], 3) == 2

    print("所有测试通过 ✅")