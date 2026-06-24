class Solution:
    def findMissingElements(self, nums: list[int]) -> list[int]:
        """
        找出给定范围内缺失的所有整数。

        题目保证数组中的整数是唯一的，且原范围的最小值和最大值仍然在数组中。
        返回按升序排列的缺失整数列表。

        :param nums: 包含唯一整数的数组
        :return: 缺失的整数列表（升序）
        """
        # 求出数组中的最小值和最大值，即原始范围的边界
        min_val = min(nums)
        max_val = max(nums)

        # 将数组转为集合，实现 O(1) 的查找
        num_set = set(nums)

        # 遍历 [min_val, max_val] 范围内的所有整数
        # 如果不在集合中，说明缺失，加入结果列表
        result = []
        for i in range(min_val, max_val + 1):
            if i not in num_set:
                result.append(i)

        return result
        # 由于遍历本身就是从小到大，结果天然有序，无需额外排序


if __name__ == "__main__":
    # 测试用例
    sol = Solution()

    # 示例 1
    nums1 = [1, 4, 2, 5]
    print(f"输入: nums = {nums1}")
    print(f"输出: {sol.findMissingElements(nums1)}")  # 预期: [3]

    # 示例 2
    nums2 = [7, 8, 6, 9]
    print(f"输入: nums = {nums2}")
    print(f"输出: {sol.findMissingElements(nums2)}")  # 预期: []

    # 示例 3
    nums3 = [5, 1]
    print(f"输入: nums = {nums3}")
    print(f"输出: {sol.findMissingElements(nums3)}")  # 预期: [2, 3, 4]

    # 边界测试：最小长度 2
    nums4 = [10, 12]
    print(f"输入: nums = {nums4}")
    print(f"输出: {sol.findMissingElements(nums4)}")  # 预期: [11]

    # 边界测试：连续无缺失
    nums5 = [3, 2, 1, 4]
    print(f"输入: nums = {nums5}")
    print(f"输出: {sol.findMissingElements(nums5)}")  # 预期: []

# 复杂度分析
# 时间复杂度: O(n) — 求 min/max 和建集合各需 O(n)，遍历范围最多 O(n)（最坏情况范围长度 ≤ n）
# 空间复杂度: O(n) — 哈希集合存储 nums
