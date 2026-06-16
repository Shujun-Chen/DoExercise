"""
2315. Count Asterisks

给你一个字符串 s，其中每两个连续的竖线 '|' 被分组为一对。
换言之，第 1 个和第 2 个 '|' 组成一对，第 3 个和第 4 个 '|' 组成一对，以此类推。

请你返回不在竖线对之间（即不在任何一对 '|' 内部）的 '*' 的数量。

注意：每个 '|' 只属于恰好一对。

示例 1:
输入: s = "l|*e*et|c**o|*de|"
输出: 2
解释: 不在竖线对之间的 '*' 是第 1 个和第 9 个 '*'。

示例 2:
输入: s = "iamprogrammer"
输出: 0
解释: 字符串中没有竖线，所以所有 '*' 都计数。但这里没有 '*'。

示例 3:
输入: s = "yo|uar|e**|b|e***au|tifu|l"
输出: 5
解释: 需要计数的 '*' 是第 1、2、3、4、5 个 '*'。

解题思路:
遍历字符串，用 bars 变量记录已经遇到的竖线数量。
当 bars 为偶数时，说明当前不在竖线对内部，此时遇到的 '*' 应当计数。
当 bars 为奇数时，说明当前在竖线对内部，遇到的 '*' 不计数。

时间复杂度: O(n)
空间复杂度: O(1)
"""


class Solution:
    def countAsterisks(self, s: str) -> int:
        """计算不在竖线对之间的 '*' 的数量"""
        ans = 0
        bars = 0  # 记录已遇到的竖线数量
        for c in s:
            if c == '|':
                bars += 1  # 竖线数量 +1，切换内外状态
            elif c == '*' and bars % 2 == 0:
                ans += 1  # 在竖线对外部，计数
        return ans


if __name__ == "__main__":
    sol = Solution()

    # 示例1
    assert sol.countAsterisks("l|*e*et|c**o|*de|") == 2, "示例1 failed"

    # 示例2
    assert sol.countAsterisks("iamprogrammer") == 0, "示例2 failed"

    # 示例3
    assert sol.countAsterisks("yo|uar|e**|b|e***au|tifu|l") == 5, "示例3 failed"

    # 自定义测试: 只有竖线对内部有 *
    assert sol.countAsterisks("*|*|*") == 2, "自定义1 failed"  # 第1个和第3个*都在外部

    # 自定义测试: 空字符串
    assert sol.countAsterisks("") == 0, "空字符串 failed"

    # 自定义测试: 只有 *
    assert sol.countAsterisks("***") == 3, "全星号 failed"

    print("所有测试用例通过！")
