class Solution:
    def reverseWords(self, s: str) -> str:
        """
        反转字符串中的单词顺序。

        思路：
        1. 使用 split() 分割字符串，自动处理连续空格和首尾空格
        2. 反转单词列表
        3. 用单个空格重新连接

        时间复杂度：O(n)，其中 n 为字符串长度
        空间复杂度：O(n)，用于存储分割后的单词列表
        """
        # split() 默认按空白字符分割，自动去除多余空格和首尾空格
        words = s.split()
        # 反转单词列表并用单个空格连接
        return ' '.join(reversed(words))


if __name__ == "__main__":
    sol = Solution()

    # 测试用例
    test_cases = [
        ("the sky is blue", "blue is sky the"),
        ("  hello world  ", "world hello"),
        ("a good   example", "example good a"),
        ("  hello   world  ", "world hello"),
        ("single", "single"),
        ("", ""),
        ("   ", ""),
    ]

    for s, expected in test_cases:
        result = sol.reverseWords(s)
        status = "✅" if result == expected else "❌"
        print(f'{status} s={s!r} -> {result!r} (expected={expected!r})')
