class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        long ans = 0;  // 使用 long 防止溢出

        // 步骤 1：统计全 1 子串（0 个 0 的情况）
        long onesRun = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                onesRun++;
            } else {
                ans += onesRun * (onesRun + 1) / 2;
                onesRun = 0;
            }
        }
        ans += onesRun * (onesRun + 1) / 2;

        // 记录所有 0 的位置
        int[] zeros = new int[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                zeros[m++] = i;
            }
        }
        if (m == 0) {
            return (int) ans;
        }

        // 在有效子串中，0 的数量最多为 sqrt(n)
        int maxZeros = (int) Math.sqrt(n) + 1;

        // 步骤 2：统计包含至少一个 0 的有效子串
        for (int firstIdx = 0; firstIdx < m; firstIdx++) {
            int firstZero = zeros[firstIdx];
            // 子串的左边界：上一个 0 的下一个位置
            int leftStart = (firstIdx > 0) ? zeros[firstIdx - 1] + 1 : 0;

            // 枚举子串中 0 的个数（从 1 到 maxZeros）
            int limit = Math.min(firstIdx + maxZeros, m);
            for (int lastIdx = firstIdx; lastIdx < limit; lastIdx++) {
                int lastZero = zeros[lastIdx];
                int zeroCnt = lastIdx - firstIdx + 1;
                int nextZero = (lastIdx + 1 < m) ? zeros[lastIdx + 1] : n;

                // 条件：1 的数量 >= zeroCnt²
                // end >= l + zeroCnt² + zeroCnt - 1
                long K = (long) zeroCnt * zeroCnt + zeroCnt - 1;

                // 阈值：当 l 足够大时，最小结束位置由 l + K 决定
                long thresholdL = lastZero - K;

                if (thresholdL >= firstZero) {
                    // 所有 l 都满足 l + K >= lastZero
                    // 需要 l + K < nextZero
                    int lEnd = Math.min(firstZero, (int) (nextZero - K - 1));
                    if (leftStart <= lEnd) {
                        long cnt = lEnd - leftStart + 1;
                        // Σ(nextZero - (l + K)) = cnt * (nextZero - K) - Σl
                        long sumL = (long) (leftStart + lEnd) * cnt / 2;
                        ans += cnt * (nextZero - K) - sumL;
                    }
                } else {
                    // 第一部分：l 较小，minEnd = lastZero
                    int part1End = Math.min(firstZero, (int) (thresholdL - 1));
                    if (leftStart <= part1End) {
                        long cnt1 = part1End - leftStart + 1;
                        long validEnd = nextZero - lastZero;
                        ans += cnt1 * Math.max(0, validEnd);
                    }

                    // 第二部分：l 较大，minEnd = l + K
                    int part2Start = Math.max(leftStart, (int) thresholdL);
                    int part2End = Math.min(firstZero, (int) (nextZero - K - 1));
                    if (part2Start <= part2End) {
                        long cnt2 = part2End - part2Start + 1;
                        long sumL2 = (long) (part2Start + part2End) * cnt2 / 2;
                        ans += cnt2 * (nextZero - K) - sumL2;
                    }
                }
            }
        }

        return (int) ans;
    }

    // 时间复杂度: O(n * sqrt(n))
    // 空间复杂度: O(n) — 存储 0 的位置

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        String s1 = "00011";
        System.out.println("Input: s = \"" + s1 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s1) + " (Expected: 5)");
        System.out.println();

        // 测试用例 2
        String s2 = "101101";
        System.out.println("Input: s = \"" + s2 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s2) + " (Expected: 16)");
        System.out.println();

        // 测试用例 3：全 1
        String s3 = "1111";
        System.out.println("Input: s = \"" + s3 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s3) + " (Expected: 10)");
        System.out.println();

        // 测试用例 4：全 0
        String s4 = "000";
        System.out.println("Input: s = \"" + s4 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s4) + " (Expected: 0)");
        System.out.println();

        // 测试用例 5：混合
        String s5 = "01";
        System.out.println("Input: s = \"" + s5 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s5) + " (Expected: 2)");
        System.out.println();

        // 测试用例 6
        String s6 = "1001";
        System.out.println("Input: s = \"" + s6 + "\"");
        System.out.println("Output: " + sol.numberOfSubstrings(s6) + " (Expected: 4)");
    }
}
