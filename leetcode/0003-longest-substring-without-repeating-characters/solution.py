"""
LeetCode 3. Longest Substring Without Repeating Characters
难度: Medium

题目描述:
给定一个字符串 s，找出其中不含重复字符的最长子串的长度。

示例:
输入: s = "abcabcbb"
输出: 3
解释: 无重复字符的最长子串是 "abc"，长度为 3。

输入: s = "bbbbb"
输出: 1

输入: s = "pwwkew"
输出: 3
解释: 无重复字符的最长子串是 "wke"，长度为 3。
"""


class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        """
        滑动窗口 + 哈希表 经典解法

        思路:
        维护一个左闭右开的窗口 [left, right)，保证窗口内字符不重复。
        - right 向右扩展，把新字符加入窗口
        - 如果新字符已在窗口内，则把 left 跳到「重复字符上一次出现位置的下一位」
        - 每次扩展后更新最大长度
        """
        # last_seen[char] = 该字符最近一次出现的索引
        last_seen = {}
        left = 0          # 窗口左边界
        max_len = 0       # 记录最大窗口长度

        for right, ch in enumerate(s):
            # 如果字符在窗口内出现过，left 跳到上次出现位置的下一位
            # 注意：取 max 是为了避免 left 往回跳（如果重复字符在窗口外）
            if ch in last_seen and last_seen[ch] >= left:
                left = last_seen[ch] + 1
            # 更新字符最近出现位置
            last_seen[ch] = right
            # 当前窗口长度 = right - left + 1 = right - left + 1（因为 right 是索引）
            max_len = max(max_len, right - left + 1)

        return max_len


# 时间复杂度: O(n)，每个字符最多访问两次（right 遍历 + left 最多跳一次）
# 空间复杂度: O(min(n, 字符集大小))，哈希表最多存 O(min(n, 256))


if __name__ == "__main__":
    sol = Solution()

    # 测试用例
    test_cases = [
        ("abcabcbb", 3),
        ("bbbbb", 1),
        ("pwwkew", 3),
        ("", 0),
        (" ", 1),
        ("au", 2),
        ("dvdf", 3),
    ]

    for s, expected in test_cases:
        result = sol.lengthOfLongestSubstring(s)
        status = "✓" if result == expected else "✗"
        print(f"{status} input={s!r:15} expected={expected}, got={result}")
