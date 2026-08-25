"""
LeetCode 5. Longest Palindromic Substring
https://leetcode.com/problems/longest-palindromic-substring/

难度: Medium
标签: String, Dynamic Programming

题目描述:
    给定一个字符串 s,返回 s 中最长的回文子串。

约束:
    - 1 <= s.length <= 1000
    - s 仅由数字和英文字母组成

示例:
    Input: s = "babad"
    Output: "bab"  (或 "aba" 也是合法答案)

    Input: s = "cbbd"
    Output: "bb"

思路:
    中心扩展法(Expand Around Center):
    回文串的对称轴可以是某个字符(奇数长度),也可以是某两个字符之间(偶数长度)。
    一共有 2n-1 个潜在中心。对每个中心向两边扩展,记录最长回文。

    时间复杂度: O(n^2) —— 每个中心最多扩展 O(n),共 2n-1 个中心
    空间复杂度: O(1)   —— 只用常数额外空间(不计返回值)

    (另一种思路:Manacher 算法 O(n),但代码复杂,本题 n<=1000 不必要)
"""


class Solution:
    def longestPalindrome(self, s: str) -> str:
        """返回 s 中最长的回文子串。"""
        n = len(s)
        if n < 2:
            # 0 或 1 字符本身就是回文
            return s

        # 记录最优解的起止下标(含左不含右)
        best_start, best_end = 0, 1

        # 工具函数:以 (left, right) 为初始中心向外扩展,返回最长回文的 [start, end)
        def expand(left: int, right: int) -> tuple[int, int]:
            while left >= 0 and right < n and s[left] == s[right]:
                left -= 1
                right += 1
            # 循环结束时 left/right 已越界,真实边界是 [left+1, right)
            return left + 1, right

        # 枚举所有潜在中心
        for i in range(n):
            # 奇数长度回文:中心是单个字符 s[i]
            l, r = expand(i, i)
            if r - l > best_end - best_start:
                best_start, best_end = l, r
            # 偶数长度回文:中心是 s[i] 与 s[i+1] 之间
            l, r = expand(i, i + 1)
            if r - l > best_end - best_start:
                best_start, best_end = l, r

        return s[best_start:best_end]


if __name__ == "__main__":
    sol = Solution()

    # 官方示例
    assert sol.longestPalindrome("babad") in ("bab", "aba")
    assert sol.longestPalindrome("cbbd") == "bb"

    # 边界
    assert sol.longestPalindrome("a") == "a"
    assert sol.longestPalindrome("aa") == "aa"
    assert sol.longestPalindrome("ab") in ("a", "b")

    # 全部相同
    assert sol.longestPalindrome("aaaa") == "aaaa"

    # 无回文(全部不同)——返回任意单字符
    assert sol.longestPalindrome("abcdef") in set("abcdef")

    # 偶数长回文在中间
    assert sol.longestPalindrome("abccba") == "abccba"

    # 整串即回文
    assert sol.longestPalindrome("racecar") == "racecar"

    # 嵌套回文
    assert sol.longestPalindrome("abacdfgdcaba") in ("aba", "aca")

    print("All tests passed.")