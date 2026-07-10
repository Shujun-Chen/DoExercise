from typing import List


class Solution:
    def partitionDisjoint(self, nums: List[int]) -> int:
        """
        将数组分成左右两部分,要求:
        1. left 中所有元素 <= right 中所有元素
        2. left 尽可能短

        思路:
        设 left_max 为左部分当前的最大值,partition 为分割点
        candidate_max 记录从 partition 开始往后遍历过程中遇到的最大值

        遍历 nums:
          - 如果当前元素 < left_max,说明它必须属于 left(否则它会出现在 right 里,
            而 right 的所有元素必须 >= left_max,这会矛盾)。
            此时划分变更为当前位置+1,同时更新 left_max = candidate_max。
          - 否则,candidate_max 取较大值(为后续可能扩展做准备)。
        一次遍历即可确定 partition。
        """
        left_max = nums[0]      # 左部分当前的最大值
        candidate_max = nums[0] # 暂存的最大值,用于扩展时赋给 left_max
        partition = 1           # left 的长度(下一个元素归属右边的起点索引)

        for i in range(1, len(nums)):
            x = nums[i]
            if x < left_max:
                # 当前元素必须归入 left,扩展左部分,并同步更新最大值
                partition = i + 1
                left_max = candidate_max
            elif x > candidate_max:
                # 记录一个新的候选最大值,后续可能需要扩展
                candidate_max = x

        return partition


if __name__ == "__main__":
    sol = Solution()

    # Example 1: nums = [5,0,3,8,6] -> 3
    print(sol.partitionDisjoint([5, 0, 3, 8, 6]))  # 3

    # Example 2: nums = [1,1,1,0,6,12] -> 4
    print(sol.partitionDisjoint([1, 1, 1, 0, 6, 12]))  # 4

    # 单调递增:[1,2,3,4,5] -> 1
    print(sol.partitionDisjoint([1, 2, 3, 4, 5]))  # 1

    # 单调递减:[5,4,3,2,1] -> 4
    print(sol.partitionDisjoint([5, 4, 3, 2, 1]))  # 4

    # 全部相同:[2,2,2,2] -> 1
    print(sol.partitionDisjoint([2, 2, 2, 2]))  # 1

    # 时间复杂度:O(n),只需一次遍历
    # 空间复杂度:O(1)
