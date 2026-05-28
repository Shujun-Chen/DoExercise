from typing import List


class Solution:
    def divideString(self, s: str, k: int, fill: str) -> List[str]:
        """
        将字符串 s 按每 k 个字符一组分割，最后一组不足 k 个字符时用 fill 填充。

        思路：
            以步长 k 遍历字符串，每次取出 k 个字符（或到最后剩余部分），
            如果该组字符数不足 k，则在末尾补 fill 字符直到长度达到 k。

        :param s: 输入字符串
        :param k: 每组字符数
        :param fill: 填充字符
        :return: 分组结果字符串列表
        """
        result = []
        n = len(s)

        for i in range(0, n, k):
            # 取当前段
            part = s[i:i + k]
            # 如果当前段长度不足 k，用 fill 填充
            if len(part) < k:
                part += fill * (k - len(part))
            result.append(part)

        return result


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    assert sol.divideString("abcdefghi", 3, "x") == ["abc", "def", "ghi"]
    # 示例 2
    assert sol.divideString("abcdefghij", 3, "x") == ["abc", "def", "ghi", "jxx"]
    # 边界测试：字符串长度恰好为 k
    assert sol.divideString("abc", 3, "x") == ["abc"]
    # 边界测试：字符串长度小于 k
    assert sol.divideString("ab", 3, "z") == ["abz"]
    # 边界测试：k = 1
    assert sol.divideString("abc", 1, "x") == ["a", "b", "c"]
    # 边界测试：字符串空串（约束保证长度 >= 1）
    assert sol.divideString("a", 2, "y") == ["ay"]

    print("所有测试用例通过！")

# 时间复杂度：O(n)，其中 n = len(s)，遍历字符串一次
# 空间复杂度：O(n / k) 用于存储结果列表
