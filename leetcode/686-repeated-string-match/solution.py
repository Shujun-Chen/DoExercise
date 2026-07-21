class Solution:
    def repeatedStringMatch(self, a: str, b: str) -> int:
        """
        LeetCode 686. Repeated String Match

        给定字符串 a 和 b，将 a 重复若干次后，使 b 成为其子串。返回最少重复次数；
        若不可能，返回 -1。

        思路：
        1. 若 b 中含有 a 中没有的字符，则无论如何重复 a 都不可能包含 b，直接返回 -1。
        2. 否则 b 一定出现在 a 重复 k 次构成的字符串中。
           设 m = len(a), n = len(b)。考虑 a^k 长度从 m 增长到至少 n + m - 1
           （多复制一份保证跨边界的情况也能被覆盖），k 的最小值是 ceil(n/m) 或
           ceil(n/m)+1。直接枚举 k from 1 to n/m + 2 即可。
        3. 用 str.find 检查子串，若找到则返回当前 k。

        复杂度：
        - 时间复杂度：O((n/m + 2) * (n + m)) = O(n + m)，因每次拼接和查找线性相关。
        - 空间复杂度：O(n + m)，主要来自拼接后的临时字符串。
        """
        # 若 b 中含有 a 中没有的字符，直接返回 -1
        if set(b) - set(a):
            return -1

        m, n = len(a), len(b)
        # 最多需要重复的次数：n/m 上取整，再多 1 次覆盖跨边界情形
        repeat_times = n // m + 2
        # 不断拼接 a 并在每次拼接后检查 b 是否出现
        repeated = ""
        for k in range(1, repeat_times + 1):
            repeated += a
            if b in repeated:
                return k
        # 理论上若 set 校验通过，应在前 repeat_times 次内找到；兜底返回 -1
        return -1


if __name__ == "__main__":
    sol = Solution()

    # 官方示例 1：a="abcd", b="cdabcdab" -> 3
    assert sol.repeatedStringMatch("abcd", "cdabcdab") == 3
    # 官方示例 2：a="a", b="aa" -> 2
    assert sol.repeatedStringMatch("a", "aa") == 2

    # 额外边界：b 长度为 1 且就是 a 中的字符 -> 1
    assert sol.repeatedStringMatch("abc", "a") == 1
    # 跨边界：a="abc", b="cab" -> 2 ("abcabc" 包含 "cab")
    assert sol.repeatedStringMatch("abc", "cab") == 2
    # 不可能的字符：b 中含 a 中没有的字符 -> -1
    assert sol.repeatedStringMatch("abc", "d") == -1
    # a 与 b 长度相同：b 是 a 的旋转 -> 2
    assert sol.repeatedStringMatch("abc", "cab") == 2
    # a 与 b 完全相等 -> 1
    assert sol.repeatedStringMatch("abcd", "abcd") == 1

    print("All tests passed!")