class Solution:
    def numTilePossibilities(self, tiles: str) -> int:
        """
        计算所有可能的非空序列数量。
        使用回溯法 + 频率统计，避免生成重复序列。
        """
        # 统计每个字母的出现次数 (A-Z)
        freq = [0] * 26
        for ch in tiles:
            freq[ord(ch) - ord('A')] += 1

        def backtrack() -> int:
            """DFS 回溯：选择当前可用的字母，计数后继续递归"""
            total = 0
            for i in range(26):
                if freq[i] > 0:
                    # 使用当前字母
                    freq[i] -= 1
                    # 每用掉一个字母就产生了一个新序列，+1 计数
                    total += 1 + backtrack()
                    # 回溯，恢复计数
                    freq[i] += 1
            return total

        return backtrack()


if __name__ == "__main__":
    sol = Solution()

    # 测试用例
    assert sol.numTilePossibilities("AAB") == 8, "Example 1 failed"
    assert sol.numTilePossibilities("AAABBC") == 188, "Example 2 failed"
    assert sol.numTilePossibilities("V") == 1, "Example 3 failed"

    print("所有测试用例通过！")

    # 复杂度分析
    # 时间复杂度：O(2^n) 或更精确 O(n!)，其中 n = tiles.length
    # 空间复杂度：O(n)，递归栈深度最多为 n
