class Solution:
    def numberOfSubstrings(self, s: str) -> int:
        """
        统计包含主导 1 的子串数量。
        定义：子串中 1 的数量 >= 0 的数量的平方，则称该子串有"主导 1"。

        思路：
        1. 先统计全 1 子串（0个0），这些总是满足条件
        2. 再统计包含至少一个 0 的子串：
           - 在有效子串中，0 的数量最多为 sqrt(n) 个（因为需要至少 zeros² 个 1）
           - 固定第一个 0 的位置，枚举后续最多 sqrt(n) 个 0
           - 对每组 0，计算满足条件的结束位置范围
        """
        n = len(s)

        # 步骤 1：统计全 1 子串（0 个 0 的情况）
        ans = 0
        ones_run = 0
        for ch in s:
            if ch == '1':
                ones_run += 1
            else:
                ans += ones_run * (ones_run + 1) // 2
                ones_run = 0
        ans += ones_run * (ones_run + 1) // 2

        # 记录所有 0 的位置
        zeros = [i for i, ch in enumerate(s) if ch == '0']
        m = len(zeros)
        if m == 0:
            return ans

        # 在有效子串中，0 的数量最多为 sqrt(n)
        max_zeros = int(n ** 0.5) + 1

        # 步骤 2：统计包含至少一个 0 的有效子串
        for first_zero_idx in range(m):
            first_zero_pos = zeros[first_zero_idx]  # 子串中的第一个 0
            # 子串的左边界：上一个 0 的下一个位置
            left_start = zeros[first_zero_idx - 1] + 1 if first_zero_idx > 0 else 0

            # 枚举子串中的 0 的数量（从 1 到 max_zeros）
            for last_zero_idx in range(first_zero_idx,
                                       min(first_zero_idx + max_zeros, m)):
                last_zero_pos = zeros[last_zero_idx]  # 子串中的最后一个 0
                zeros_cnt = last_zero_idx - first_zero_idx + 1  # 0 的个数
                next_zero = zeros[last_zero_idx + 1] if last_zero_idx + 1 < m else n

                # 条件：1 的数量 >= zeros_cnt²
                # 1 的数量 = (end - l + 1) - zeros_cnt
                # => end - l + 1 >= zeros_cnt² + zeros_cnt
                # => end >= l + zeros_cnt² + zeros_cnt - 1
                K = zeros_cnt * zeros_cnt + zeros_cnt - 1

                # 阈值：当 l 足够大时，最小结束位置由 l + K 决定
                # 当 l 较小时，最小结束位置受 last_zero_pos 限制
                threshold_l = last_zero_pos - K

                if threshold_l >= first_zero_pos:
                    # 所有 l 都满足 l + K >= last_zero_pos
                    # 结束位置的最小值 = l + K，需要 l + K < next_zero
                    l_end = min(first_zero_pos, next_zero - K - 1)
                    if left_start <= l_end:
                        cnt = l_end - left_start + 1
                        # Σ(next_zero - (l + K)) = Σ(next_zero - K - l)
                        # = cnt * (next_zero - K) - (left_start + l_end) * cnt / 2
                        ans += cnt * (next_zero - K) - \
                            (left_start + l_end) * cnt // 2
                else:
                    # 第一部分：l 较小，满足 l + K < last_zero_pos
                    # 结束位置的最小值 = last_zero_pos
                    part1_end = min(first_zero_pos, threshold_l - 1)
                    if left_start <= part1_end:
                        cnt1 = part1_end - left_start + 1
                        valid_end = max(0, next_zero - last_zero_pos)
                        ans += cnt1 * valid_end

                    # 第二部分：l 较大，满足 l + K >= last_zero_pos
                    part2_start = max(left_start, threshold_l)
                    part2_end = min(first_zero_pos, next_zero - K - 1)
                    if part2_start <= part2_end:
                        cnt2 = part2_end - part2_start + 1
                        ans += cnt2 * (next_zero - K) - \
                            (part2_start + part2_end) * cnt2 // 2

        return ans

    # 时间复杂度: O(n * sqrt(n))
    # 空间复杂度: O(n) - 存储 0 的位置


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    s1 = "00011"
    print(f"Input: s = \"{s1}\"")
    print(f"Output: {sol.numberOfSubstrings(s1)} (Expected: 5)")
    print()

    # 测试用例 2
    s2 = "101101"
    print(f"Input: s = \"{s2}\"")
    print(f"Output: {sol.numberOfSubstrings(s2)} (Expected: 16)")
    print()

    # 测试用例 3：全 1
    s3 = "1111"
    print(f"Input: s = \"{s3}\"")
    print(f"Output: {sol.numberOfSubstrings(s3)} (Expected: 10)")
    print()

    # 测试用例 4：全 0
    s4 = "000"
    print(f"Input: s = \"{s4}\"")
    print(f"Output: {sol.numberOfSubstrings(s4)} (Expected: 0)")
    print()

    # 测试用例 5：混合
    s5 = "01"
    print(f"Input: s = \"{s5}\"")
    print(f"Output: {sol.numberOfSubstrings(s5)} (Expected: 2)")
    print()

    # 测试用例 6：长字符串
    s6 = "1001"
    print(f"Input: s = \"{s6}\"")
    print(f"Output: {sol.numberOfSubstrings(s6)} (Expected: 4)")
