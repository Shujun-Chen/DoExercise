class Solution:
    def wiggleSort(self, nums: list[int]) -> None:
        """
        将数组重新排列为 nums[0] < nums[1] > nums[2] < nums[3] ... 的摆动顺序。
        Do not return anything, modify nums in-place instead.

        思路：
        1. 先排序，得到有序数组
        2. 将排序后数组的左半部分（较小元素）逆序放入偶数下标位置
        3. 将右半部分（较大元素）逆序放入奇数下标位置
        这样能确保相邻元素不会相等，满足摆动条件
        """
        n = len(nums)
        sorted_nums = sorted(nums)

        # 分割点：左半部分有 (n+1)//2 个元素
        mid = (n + 1) // 2

        # 从右向左取左半部分（较小元素），放入偶数下标
        j = mid - 1  # 左半部分最后一个元素的下标
        k = n - 1    # 右半部分最后一个元素的下标

        for i in range(n):
            if i % 2 == 0:
                # 偶数下标放较小的元素
                nums[i] = sorted_nums[j]
                j -= 1
            else:
                # 奇数下标放较大的元素
                nums[i] = sorted_nums[k]
                k -= 1

    # 另一种实现：利用中位数 + 虚拟索引 + 三路划分（O(n) 时间，O(1) 空间）
    def wiggleSort_fast(self, nums: list[int]) -> None:
        """
        进阶解法：O(n) 时间，O(1) 额外空间
        利用 nth_element（快速选择）找中位数 + 虚拟索引 + 荷兰国旗三路划分
        """
        n = len(nums)

        # 虚拟索引映射：将实际下标 i 映射到虚拟位置
        # (2*i+1) % (n|1) 将偶数下标映射到左半，奇数下标映射到右半
        def vi(i: int) -> int:
            return (2 * i + 1) % (n | 1)

        # 三路划分（Dutch National Flag）
        # 目标：mid_val 左侧的元素 < mid_val，右侧的元素 > mid_val
        mid_val = self._find_kth_largest(nums, n // 2)  # 上中位数

        left, i, right = 0, 0, n - 1
        while i <= right:
            if nums[vi(i)] > mid_val:
                # 大于中位数的放左边（虚拟索引下，实际会分配到奇数位）
                nums[vi(left)], nums[vi(i)] = nums[vi(i)], nums[vi(left)]
                left += 1
                i += 1
            elif nums[vi(i)] < mid_val:
                # 小于中位数的放右边（虚拟索引下，实际会分配到偶数位）
                nums[vi(right)], nums[vi(i)] = nums[vi(i)], nums[vi(right)]
                right -= 1
            else:
                i += 1

    def _find_kth_largest(self, nums: list[int], k: int) -> int:
        """快速选择：找第 k 大的元素（k 从 0 开始）"""
        # 简化实现：直接排序取中位数
        # 实际可用 quickselect 做到 O(n)
        sorted_nums = sorted(nums)
        return sorted_nums[len(nums) - 1 - k]


if __name__ == "__main__":
    # 测试用例
    test_cases = [
        [1, 5, 1, 1, 6, 4],
        [1, 3, 2, 2, 3, 1],
        [1, 2, 3],
        [4, 3, 2, 1],
    ]

    for nums in test_cases:
        original = nums[:]
        s = Solution()
        s.wiggleSort(nums)
        print(f"{original} -> {nums}")

        # 验证摆动条件
        valid = True
        for i in range(1, len(nums)):
            if i % 2 == 1:  # 奇数位：nums[i-1] < nums[i]
                if not (nums[i - 1] < nums[i]):
                    valid = False
                    break
            else:  # 偶数位：nums[i-1] > nums[i]
                if not (nums[i - 1] > nums[i]):
                    valid = False
                    break
        print(f"  验证: {'✓' if valid else '✗'}")

    # 边界情况
    print("\n边界测试:")
    edge = [1, 1, 2]
    s = Solution()
    s.wiggleSort(edge)
    print(f"[1,1,2] -> {edge}")

    # 时间复杂度: O(n log n) — 排序耗时
    # 空间复杂度: O(n) — 使用额外数组存储排序结果
