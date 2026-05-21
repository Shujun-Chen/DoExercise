"""
3723. Maximize Sum of Squares of Digits
https://leetcode.com/problems/maximize-sum-of-squares-of-digits/

时间复杂度：O(num) — 构造长度为 num 的结果字符串
空间复杂度：O(num) — 存储结果字符串
"""


class Solution:
    def maxScoreString(self, num: int, digitSum: int) -> str:
        """
        返回长度为 num、各位数字之和为 digitSum 且平方和最大的最大整数。
        若不存在则返回空字符串。
        """
        # 最大可能数字之和为 num * 9，超过则无解
        if digitSum > num * 9:
            return ""

        # 贪心：尽可能多地使用数字 9，因为 9²=81 带来的平方收益最大
        count_9 = digitSum // 9
        remainder = digitSum % 9

        # 构造结果：9 放在最前面，余数紧跟，其余补 0
        result = []
        result.append("9" * count_9)
        if remainder > 0:
            result.append(str(remainder))
        zeros = num - count_9 - (1 if remainder > 0 else 0)
        if zeros > 0:
            result.append("0" * zeros)

        return "".join(result)


if __name__ == "__main__":
    s = Solution()
    # 示例测试
    assert s.maxScoreString(2, 3) == "30"
    assert s.maxScoreString(2, 17) == "98"
    assert s.maxScoreString(1, 10) == ""
    # 边界测试
    assert s.maxScoreString(1, 9) == "9"
    assert s.maxScoreString(3, 0) == "000"  # sum=0 时全为 0
    assert s.maxScoreString(3, 18) == "990"
    assert s.maxScoreString(3, 27) == "999"
    assert s.maxScoreString(4, 35) == "9998"
    print("所有测试用例通过")
