"""
1200. Minimum Absolute Difference
https://leetcode.com/problems/minimum-absolute-difference/

时间复杂度：O(N log N) — 排序占主导
空间复杂度：O(N) — 结果列表
"""

from typing import List


class Solution:
    def minimumAbsDifference(self, arr: List[int]) -> List[List[int]]:
        # 先排序，这样最小绝对差一定出现在相邻元素之间
        arr.sort()

        # 第一遍扫描：找到最小绝对差
        min_diff = float('inf')
        for i in range(1, len(arr)):
            diff = arr[i] - arr[i - 1]
            if diff < min_diff:
                min_diff = diff

        # 第二遍扫描：收集所有差值等于 min_diff 的相邻对
        result = []
        for i in range(1, len(arr)):
            if arr[i] - arr[i - 1] == min_diff:
                result.append([arr[i - 1], arr[i]])

        return result


if __name__ == "__main__":
    s = Solution()
    # 示例测试
    assert s.minimumAbsDifference([4, 2, 1, 3]) == [[1, 2], [2, 3], [3, 4]]
    assert s.minimumAbsDifference([1, 3, 6, 10, 15]) == [[1, 3]]
    assert s.minimumAbsDifference([3, 8, -10, 23, 19, -4, -14, 27]) == [[-14, -10], [19, 23], [23, 27]]
    # 边界测试
    assert s.minimumAbsDifference([1, 5]) == [[1, 5]]
    print("所有测试用例通过")
