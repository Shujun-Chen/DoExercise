from typing import List

class Solution:
    def isRectangleOverlap(self, rec1: List[int], rec2: List[int]) -> bool:
        """
        判断两个轴对齐矩形是否重叠（交集面积为正）。
        
        两个矩形不重叠当且仅当一个矩形完全在另一个的左侧、右侧、上方或下方。
        检查两个矩形在 x 轴和 y 轴上的投影是否有重叠区间即可。
        """
        x1, y1, x2, y2 = rec1
        x3, y3, x4, y4 = rec2

        # x 轴投影重叠：两个矩形的 x 区间有交集
        overlap_x = max(x1, x3) < min(x2, x4)
        # y 轴投影重叠：两个矩形的 y 区间有交集
        overlap_y = max(y1, y3) < min(y2, y4)

        # 两个方向都有重叠 → 矩形相交
        return overlap_x and overlap_y


if __name__ == "__main__":
    sol = Solution()

    # Example 1: 重叠
    assert sol.isRectangleOverlap([0, 0, 2, 2], [1, 1, 3, 3]) == True

    # Example 2: 边缘接触，不重叠
    assert sol.isRectangleOverlap([0, 0, 1, 1], [1, 0, 2, 1]) == False

    # Example 3: 不相邻，不重叠
    assert sol.isRectangleOverlap([0, 0, 1, 1], [2, 2, 3, 3]) == False

    # 一个矩形完全包含另一个
    assert sol.isRectangleOverlap([0, 0, 5, 5], [1, 1, 3, 3]) == True

    # 上下排列，不重叠
    assert sol.isRectangleOverlap([0, 0, 2, 2], [0, 3, 2, 5]) == False

    print("所有测试用例通过！")

# 时间复杂度：O(1) — 只做常数次比较
# 空间复杂度：O(1) — 只使用固定数量的变量
