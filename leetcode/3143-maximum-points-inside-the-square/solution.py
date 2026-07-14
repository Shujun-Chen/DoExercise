"""
LeetCode 3143. Maximum Points Inside the Square
难度：Medium

题目：给定 2D 数组 points 与字符串 s，points[i] 是坐标，s[i] 是对应 tag。
"有效正方形"以原点为中心、边平行于坐标轴，且不包含两个 tag 相同的点。
求能放进某个有效正方形中的点的最大数量。
"""
from typing import List


class Solution:
    def maxPointsInsideSquare(self, points: List[List[int]], s: str) -> int:
        # 对每个点，计算 r = max(|x|, |y|)——"切比雪夫距离"。
        # 边长为 L 的正方形（中心在原点、边平行于轴）覆盖
        # 所有满足 max(|x|, |y|) <= L/2 的点。
        # 所以点被某正方形包含 <=> L/2 >= r <=> L >= 2r。
        # 把 (r, tag) 配对后按 r 升序排序：r 越小的点越"靠近"原点，
        # 总能先被任何正方形覆盖。
        items = sorted(
            (max(abs(x), abs(y)), tag) for (x, y), tag in zip(points, s)
        )

        # 关键观察：r 相同的若干点，要么都进入正方形，要么都不进——
        # 我们只能调节 L/2 这一道"边界"，落在 r 上的一组点无法只取一部分。
        # 因此遍历过程中，按 r 分组：组内无重复 tag 且与已纳入 tag 集合
        # 无冲突时，整体纳入并累加点数；否则停止。
        seen: set = set()   # 已被纳入正方形的 tag
        count = 0
        i = 0
        n = len(items)
        while i < n:
            r_i = items[i][0]
            j = i
            group_tags: List[str] = []
            # 收集所有 r == r_i 的 tag（同一组）
            while j < n and items[j][0] == r_i:
                group_tags.append(items[j][1])
                j += 1
            # 组内不能有重复 tag（否则这一组本身就违反"无相同 tag"约束）
            if len(set(group_tags)) != len(group_tags):
                break
            # 与已纳入的 tag 不能冲突
            if any(t in seen for t in group_tags):
                break
            # 整体纳入这一组
            seen.update(group_tags)
            count += len(group_tags)
            i = j
        return count


if __name__ == "__main__":
    sol = Solution()

    # 用例 1
    assert sol.maxPointsInsideSquare(
        [[2, 2], [-1, -2], [-4, 4], [-3, 1], [3, -3]], "abdca"
    ) == 2
    # 用例 2
    assert sol.maxPointsInsideSquare(
        [[1, 1], [-2, -2], [-2, 2]], "abb"
    ) == 1
    # 用例 3
    assert sol.maxPointsInsideSquare(
        [[1, 1], [-1, -1], [2, -2]], "ccd"
    ) == 0

    # 额外：所有 r 不同且 tag 全互异 -> 全部纳入
    assert sol.maxPointsInsideSquare(
        [[1, 0], [0, 2], [-3, 0]], "xyz"
    ) == 3

    # 额外：两个点 r 相同、tag 互异、与已纳入 tag 也不冲突 -> 一起纳入
    assert sol.maxPointsInsideSquare(
        [[1, 1], [-1, 1], [0, 0]], "abc"
    ) == 3

    print("All tests passed.")
