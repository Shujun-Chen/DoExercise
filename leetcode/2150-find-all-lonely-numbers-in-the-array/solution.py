from typing import List
from collections import Counter


class Solution:
    def findLonely(self, nums: List[int]) -> List[int]:
        """
        找出数组中所有「孤独」的数字。
        一个数字 x 是孤独的，当且仅当：
          1. x 在数组中出现恰好一次
          2. x-1 和 x+1 都不在数组中

        :param nums: 整数数组
        :return: 所有孤独数字的列表
        """
        # 统计每个数字的出现次数
        freq = Counter(nums)
        result = []

        for x, cnt in freq.items():
            # 条件1：恰好出现一次
            # 条件2：相邻数字 x-1 和 x+1 都不在数组中
            if cnt == 1 and (x - 1) not in freq and (x + 1) not in freq:
                result.append(x)

        return result


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    nums1 = [10, 6, 5, 8]
    print(f"Input: {nums1}")
    print(f"Output: {sol.findLonely(nums1)}")  # 预期: [10, 8]

    # 示例 2
    nums2 = [1, 3, 5, 3]
    print(f"Input: {nums2}")
    print(f"Output: {sol.findLonely(nums2)}")  # 预期: [1, 5]

    # 额外测试：空结果
    nums3 = [1, 2, 3]
    print(f"Input: {nums3}")
    print(f"Output: {sol.findLonely(nums3)}")  # 预期: []

    # 额外测试：全部孤独
    nums4 = [0, 2, 4, 6]
    print(f"Input: {nums4}")
    print(f"Output: {sol.findLonely(nums4)}")  # 预期: [0, 2, 4, 6]

    # 时间: O(n) — 一次频率统计 + 一次遍历
    # 空间: O(n) — 哈希表存储频率
