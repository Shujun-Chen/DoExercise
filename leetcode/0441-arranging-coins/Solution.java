class Solution {
    /**
     * 思路：第 k 行需要 k 枚硬币，k 行共需 k*(k+1)/2 枚硬币。
     * 求满足 k*(k+1)/2 <= n 的最大 k。
     * 解一元二次方程 k^2 + k - 2n = 0，取正根 k = (-1 + sqrt(1+8n)) / 2。
     * 用 (long) 防止 8*n 在 n = 2^31 - 1 时溢出 int。
     * 时间复杂度 O(1)，空间复杂度 O(1)。
     */
    public int arrangeCoins(int n) {
        // 转 long 避免乘法溢出
        long nn = (long) n;
        // 求根公式 + 强制转换为 long 再做除法，最后转回 int
        long k = (long) ((Math.sqrt(1 + 8.0 * nn) - 1) / 2);
        return (int) k;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        // 题目示例
        check(sol.arrangeCoins(5), 2, "n=5");
        check(sol.arrangeCoins(8), 3, "n=8");
        // 边界测试
        check(sol.arrangeCoins(1), 1, "n=1");
        check(sol.arrangeCoins(0), 0, "n=0");
        check(sol.arrangeCoins(6), 3, "n=6 (1+2+3=6 刚好三行)");
        // 大数测试 (n 接近 2^31 - 1)
        check(sol.arrangeCoins(1804289383), 60070, "large n");
        System.out.println("All tests passed!");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + " -> expect " + expected + ", got " + actual);
        }
    }
}