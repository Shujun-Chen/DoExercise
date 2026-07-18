public class Solution {
    private static final int MOD = 1_000_000_007;

    public int numberOfWays(int n, int x) {
        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int base = 1; ; base++) {
            int power = integerPowerUpTo(base, x, n);
            if (power > n) {
                break;
            }

            // 倒序更新，确保每个正整数的 x 次幂最多被选择一次。
            for (int total = n; total >= power; total--) {
                dp[total] = (dp[total] + dp[total - power]) % MOD;
            }
        }

        return (int) dp[n];
    }

    /**
     * 计算 base^exponent；一旦结果超过 limit 就提前返回 limit + 1，避免溢出。
     */
    private int integerPowerUpTo(int base, int exponent, int limit) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
            if (result > limit) {
                return limit + 1;
            }
        }
        return (int) result;
    }

    // 时间复杂度：O(n * n^(1/x))
    // 空间复杂度：O(n)
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] testCases = {
            {10, 2, 1},
            {4, 1, 2},
            {1, 1, 1},
            {100, 2, 3},
            {160, 3, 1},
            {300, 1, 872_471_266}
        };

        for (int[] testCase : testCases) {
            int n = testCase[0];
            int x = testCase[1];
            int expected = testCase[2];
            int actual = solution.numberOfWays(n, x);
            if (actual != expected) {
                throw new AssertionError(
                    "n=" + n + ", x=" + x
                        + ": expected " + expected + ", got " + actual
                );
            }
        }

        System.out.println("All tests passed!");
    }
}
