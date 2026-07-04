class Solution {
    private static final int MOD = 1_000_000_007;

    /**
     * 计算 powers 数组范围内元素的乘积，结果模 10^9+7。
     * powers 数组由 n 的二进制表示构成（最小的 2 的幂次集合，和为 n）。
     */
    public int[] productQueries(int n, int[][] queries) {
        // Step 1: 从 n 的二进制分解中提取 powers 数组
        // n <= 10^9 < 2^30，最多 30 个二进制位
        int[] powers = new int[30]; // 预分配足够空间
        int len = 0;
        int p = 1; // 当前 2 的幂次: 2^0, 2^1, 2^2, ...
        while (n > 0) {
            if ((n & 1) == 1) {   // 当前位为 1
                powers[len++] = p;
            }
            n >>= 1;              // 右移一位，检查下一个二进制位
            p <<= 1;              // 下一个幂次
        }

        // Step 2: 前缀积数组，prefix[i] = product(powers[0..i-1]) % MOD
        long[] prefix = new long[len + 1];
        prefix[0] = 1;
        for (int i = 0; i < len; i++) {
            prefix[i + 1] = (prefix[i] * powers[i]) % MOD;
        }

        // Step 3: 处理每个查询
        // product(left..right) = prefix[right+1] * modInverse(prefix[left]) % MOD
        int m = queries.length;
        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            // prefix[right+1] * modInverse(prefix[left]) % MOD
            long inv = modPow(prefix[left], MOD - 2);
            result[i] = (int)((prefix[right + 1] * inv) % MOD);
        }

        return result;
    }

    /**
     * 快速幂：计算 a^b % MOD
     */
    private long modPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int n1 = 15;
        int[][] queries1 = {{0, 1}, {2, 2}, {0, 3}};
        int[] out1 = sol.productQueries(n1, queries1);
        int[] expected1 = {2, 4, 64};
        System.out.println("Test 1: n=15");
        boolean pass1 = true;
        for (int i = 0; i < out1.length; i++) {
            System.out.print(out1[i] + " ");
            if (out1[i] != expected1[i]) pass1 = false;
        }
        System.out.println(pass1 ? "PASS" : "FAIL");

        // 测试用例 2
        int n2 = 2;
        int[][] queries2 = {{0, 0}};
        int[] out2 = sol.productQueries(n2, queries2);
        int[] expected2 = {2};
        System.out.println("Test 2: n=2");
        boolean pass2 = true;
        for (int i = 0; i < out2.length; i++) {
            System.out.print(out2[i] + " ");
            if (out2[i] != expected2[i]) pass2 = false;
        }
        System.out.println(pass2 ? "PASS" : "FAIL");

        // 测试用例 3: n=1 (二进制 1) → powers=[1]
        int n3 = 1;
        int[][] queries3 = {{0, 0}};
        int[] out3 = sol.productQueries(n3, queries3);
        int[] expected3 = {1};
        System.out.println("Test 3: n=1");
        boolean pass3 = true;
        for (int i = 0; i < out3.length; i++) {
            System.out.print(out3[i] + " ");
            if (out3[i] != expected3[i]) pass3 = false;
        }
        System.out.println(pass3 ? "PASS" : "FAIL");

        // 测试用例 4: n=29 (11101) → powers=[1,4,8,16], product=512
        int n4 = 29;
        int[][] queries4 = {{0, 3}};
        int[] out4 = sol.productQueries(n4, queries4);
        int[] expected4 = {512};
        System.out.println("Test 4: n=29");
        boolean pass4 = true;
        for (int i = 0; i < out4.length; i++) {
            System.out.print(out4[i] + " ");
            if (out4[i] != expected4[i]) pass4 = false;
        }
        System.out.println(pass4 ? "PASS" : "FAIL");
    }
}
