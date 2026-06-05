from typing import List

# 时间复杂度: O(n) — 遍历每个位置一次
# 空间复杂度: O(1) — 只用常数额外空间


class Solution:
    def numberOfAlternatingGroups(self, colors: List[int]) -> int:
        n = len(colors)
        count = 0
        # 遍历每个位置，检查其与左右邻居是否构成交替组
        for i in range(n):
            left = colors[(i - 1) % n]
            mid = colors[i]
            right = colors[(i + 1) % n]
            # 中间瓦片与左右两侧颜色不同即为交替组
            if mid != left and mid != right:
                count += 1
        return count


if __name__ == "__main__":
    s = Solution()
    # 示例 1: 全相同，无交替组
    print(s.numberOfAlternatingGroups([1, 1, 1]))  # 0
    # 示例 2: 有 3 个交替组
    print(s.numberOfAlternatingGroups([0, 1, 0, 0, 1]))  # 3
    # 边界: 最小长度，只有中间位置交替
    print(s.numberOfAlternatingGroups([0, 1, 0]))  # 1
    # 全交替
    print(s.numberOfAlternatingGroups([0, 1, 0, 1]))  # 4
