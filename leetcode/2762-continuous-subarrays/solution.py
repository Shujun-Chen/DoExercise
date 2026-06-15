from typing import List
from collections import deque


class Solution:
    def continuousSubarrays(self, nums: List[int]) -> int:
        """
        统计连续子数组的个数
        
        连续子数组定义：子数组内任意两个元素的差的绝对值 <= 2
        
        思路：滑动窗口 + 双单调队列
        - 用 max_deque 维护窗口内递减序列（队首是最大值）
        - 用 min_deque 维护窗口内递增序列（队首是最小值）
        - 当窗口 [left, right] 满足 max - min <= 2 时，
          以 right 结尾的连续子数组有 (right - left + 1) 个
        - 当条件不满足时，移动 left 指针
        
        时间复杂度：O(n) — 每个元素最多进出队列各一次
        空间复杂度：O(n) — 队列空间
        """
        n = len(nums)
        max_deque = deque()  # 递减队列，存储下标，队首是窗口最大值
        min_deque = deque()  # 递增队列，存储下标，队首是窗口最小值
        
        left = 0
        result = 0
        
        for right in range(n):
            # 维护递减队列：移除比当前元素小的元素
            while max_deque and nums[max_deque[-1]] <= nums[right]:
                max_deque.pop()
            max_deque.append(right)
            
            # 维护递增队列：移除比当前元素大的元素
            while min_deque and nums[min_deque[-1]] >= nums[right]:
                min_deque.pop()
            min_deque.append(right)
            
            # 收缩窗口：当 max - min > 2 时移动左指针
            while nums[max_deque[0]] - nums[min_deque[0]] > 2:
                left += 1
                # 移除已经离开窗口的下标
                if max_deque[0] < left:
                    max_deque.popleft()
                if min_deque[0] < left:
                    min_deque.popleft()
            
            # 以 right 结尾的连续子数组个数
            result += right - left + 1
        
        return result


if __name__ == "__main__":
    s = Solution()
    
    # 测试用例 1
    nums1 = [5, 4, 2, 4]
    assert s.continuousSubarrays(nums1) == 8, f"Expected 8, got {s.continuousSubarrays(nums1)}"
    
    # 测试用例 2
    nums2 = [1, 2, 3]
    assert s.continuousSubarrays(nums2) == 6, f"Expected 6, got {s.continuousSubarrays(nums2)}"
    
    # 额外测试：单个元素
    nums3 = [100]
    assert s.continuousSubarrays(nums3) == 1
    
    # 额外测试：所有元素相同
    nums4 = [3, 3, 3, 3]
    assert s.continuousSubarrays(nums4) == 10  # n*(n+1)/2 = 4*5/2 = 10
    
    # 额外测试：差值刚好为2，所有子数组都连续
    nums5 = [1, 3, 1, 3]
    assert s.continuousSubarrays(nums5) == 10  # 4 + 3 + 2 + 1 = 10
    
    # 额外测试：差值超过2，部分子数组不连续
    nums6 = [1, 5, 1]
    assert s.continuousSubarrays(nums6) == 3  # [1],[5],[1] only
    
    print("All test cases passed!")
