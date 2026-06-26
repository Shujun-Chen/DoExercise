"""
2566. Number of Unequal Triplets in Array
难度：Easy

给你一个下标从 0 开始的正整数数组 nums。
请你找出并统计满足以下条件的三元组 (i, j, k) 的数目：
- 0 <= i < j < k < nums.length
- nums[i]、nums[j] 和 nums[k] 两两互不相同

即：nums[i] != nums[j]、nums[i] != nums[k]、nums[j] != nums[k]
"""

from typing import List
from collections import Counter
from math import comb


class Solution:
    def unequalTriplets(self, nums: List[int]) -> int:
        """
        计数组合法（O(n) 时间）
        
        思路：
        1. 统计每个数字的出现频率
        2. 总三元组数 = C(n, 3)
        3. 对每个出现次数 c 的值，减去包含重复数字的三元组：
           - 恰好两个重复：C(c, 2) * (n - c)
           - 三个都重复：C(c, 3)
        4. 剩下的就是两两互不相同的三元组
        
        时间复杂度：O(n)
        空间复杂度：O(n)
        """
        n = len(nums)
        freq = Counter(nums)
        
        total = comb(n, 3)
        for c in freq.values():
            if c >= 2:
                # 选 2 个当前值 + 1 个其他值
                total -= comb(c, 2) * (n - c)
            if c >= 3:
                # 选 3 个当前值
                total -= comb(c, 3)
        
        return total


if __name__ == "__main__":
    sol = Solution()
    
    # 测试用例 1
    nums1 = [4, 4, 2, 4, 3]
    print(f"nums = {nums1} → {sol.unequalTriplets(nums1)} (期望: 3)")
    assert sol.unequalTriplets(nums1) == 3
    
    # 测试用例 2
    nums2 = [1, 1, 1, 1, 1]
    print(f"nums = {nums2} → {sol.unequalTriplets(nums2)} (期望: 0)")
    assert sol.unequalTriplets(nums2) == 0
    
    # 测试用例 3：全部不同
    nums3 = [1, 2, 3]
    print(f"nums = {nums3} → {sol.unequalTriplets(nums3)} (期望: 1)")
    assert sol.unequalTriplets(nums3) == 1
    
    # 测试用例 4：混合
    nums4 = [1, 1, 2, 2, 3]
    # C(5,3)=10 - 包含重复的
    # 值1: C(2,2)*(5-2)=3, 值2: C(2,2)*(5-2)=3
    # total = 10 - 3 - 3 = 4
    print(f"nums = {nums4} → {sol.unequalTriplets(nums4)} (期望: 4)")
    assert sol.unequalTriplets(nums4) == 4
    
    print("所有测试通过！")
