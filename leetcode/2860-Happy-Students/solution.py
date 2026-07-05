from typing import List

class Solution:
    def countWays(self, nums: List[int]) -> int:
        """
        统计让所有学生都开心的选组方式数量。
        
        思路：
        对 nums 排序后，设选中学生数为 k。
        若选中学生 i，需满足 nums[i] < k（选中的人数严格大于 nums[i]）
        若未选中学生 j，需满足 nums[j] > k（选中的人数严格小于 nums[j]）
        
        因此排序后，检查每个可能的 k (0..n)：
        - k 个选中的学生是前 k 个（nums 最小）
        - 前 k 个都满足 nums[i] < k
        - 后 n-k 个都满足 nums[j] > k
        """
        nums.sort()
        n = len(nums)
        ans = 0

        for k in range(n + 1):
            # 选中的学生（前 k 个）：必须都满足 nums[i] < k
            if k > 0 and nums[k - 1] >= k:
                continue
            # 未选中的学生（后 n-k 个）：必须都满足 nums[j] > k
            if k < n and nums[k] <= k:
                continue
            ans += 1

        return ans


if __name__ == "__main__":
    sol = Solution()
    
    # 示例 1
    assert sol.countWays([1, 1]) == 2
    
    # 示例 2
    assert sol.countWays([6, 0, 3, 3, 6, 7, 2, 7]) == 3
    
    # 边界测试
    assert sol.countWays([0]) == 1  # k=1: 0<1 ✓
    assert sol.countWays([5]) == 1  # k=0: 5>0 ✓
    assert sol.countWays([0, 1]) == 1  # k=2: 1<2 ✓
    
    print("所有测试用例通过！")
