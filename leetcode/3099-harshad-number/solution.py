class Solution:
    def sumOfTheDigitsOfHarshadNumber(self, x: int) -> int:
        """
        计算 x 的各位数字之和，并判断 x 是否能被该和整除。

        时间复杂度：O(log x)，需要遍历 x 的每一位数字。
        空间复杂度：O(1)，只使用常数个变量。
        """
        original = x
        digit_sum = 0

        # 逐位取出最低位并累加到数字和中
        while x > 0:
            digit_sum += x % 10
            x //= 10

        # 能被数字和整除时，x 是哈沙德数
        return digit_sum if original % digit_sum == 0 else -1


if __name__ == "__main__":
    solution = Solution()
    test_cases = [
        (18, 9),
        (23, -1),
        (1, 1),
        (21, 3),
        (100, 1),
        (99, -1),
    ]

    for value, expected in test_cases:
        actual = solution.sumOfTheDigitsOfHarshadNumber(value)
        assert actual == expected, (
            f"x={value}: expected {expected}, got {actual}"
        )

    print("All tests passed!")
