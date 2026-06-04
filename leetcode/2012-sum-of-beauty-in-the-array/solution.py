from typing import List

class Solution:
    def sumOfBeauties(self, nums: List[int]) -> int:
        """
        计算数组中每个索引 i (1 <= i <= n-2) 的美丽值之和。
        
        美丽值规则：
        - 2: nums[i] 严格大于左侧所有元素，且严格小于右侧所有元素
        - 1: 仅满足 nums[i-1] < nums[i] < nums[i+1]，不满足条件2
        - 0: 以上均不满足
        """
        n = len(nums)
        if n < 3:
            return 0

        # 预处理：left_max[i] = nums[0..i-1] 的最大值
        left_max = [0] * n
        left_max[0] = nums[0]
        for i in range(1, n):
            left_max[i] = max(left_max[i - 1], nums[i - 1])

        # 预处理：right_min[i] = nums[i+1..n-1] 的最小值
        right_min = [0] * n
        right_min[n - 1] = nums[n - 1]
        for i in range(n - 2, -1, -1):
            right_min[i] = min(right_min[i + 1], nums[i + 1])

        beauty_sum = 0
        for i in range(1, n - 1):
            if left_max[i] < nums[i] < right_min[i]:
                # 条件2：严格大于左侧所有，严格小于右侧所有
                beauty_sum += 2
            elif nums[i - 1] < nums[i] < nums[i + 1]:
                # 条件1：仅相邻元素满足递增
                beauty_sum += 1

        return beauty_sum

# 时间复杂度：O(n)，两次线性扫描
# 空间复杂度：O(n)，两个辅助数组


if __name__ == "__main__":
    sol = Solution()
    
    # 测试用例 1
    nums1 = [1, 2, 3]
    result1 = sol.sumOfBeauties(nums1)
    print(f"nums = {nums1} → {result1} (expected: 2)")
    assert result1 == 2, f"Test 1 failed: expected 2, got {result1}"
    
    # 测试用例 2
    nums2 = [2, 4, 6, 4]
    result2 = sol.sumOfBeauties(nums2)
    print(f"nums = {nums2} → {result2} (expected: 1)")
    assert result2 == 1, f"Test 2 failed: expected 1, got {result2}"
    
    # 测试用例 3
    nums3 = [3, 2, 1]
    result3 = sol.sumOfBeauties(nums3)
    print(f"nums = {nums3} → {result3} (expected: 0)")
    assert result3 == 0, f"Test 3 failed: expected 0, got {result3}"
    
    # 额外测试：全递增
    nums4 = [1, 2, 3, 4, 5]
    result4 = sol.sumOfBeauties(nums4)
    print(f"nums = {nums4} → {result4}")
    # 索引1,2,3都满足条件2，所以预期 2+2+2 = 6
    assert result4 == 6, f"Test 4 failed: expected 6, got {result4}"
    
    print("所有测试通过！")
