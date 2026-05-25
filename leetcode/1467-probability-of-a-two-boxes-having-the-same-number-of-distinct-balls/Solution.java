/**
 * 1467. Probability of a Two Boxes Having The Same Number of Distinct Balls
 *
 * 给定 k 种颜色的 2n 个球，balls[i] 表示颜色 i 的球的数量。
 * 将所有球随机打乱后，前 n 个放入第一个盒子，后 n 个放入第二个盒子。
 * 求两个盒子中不同颜色数量相等的概率。
 */
class Solution {
    private int n;
    private int k;
    private int[] balls;
    private double favorable;
    private double combC2n_n; // C(2n, n)，用于概率归一化

    /**
     * 使用 DFS 枚举所有可能的分配方案，计算概率。
     *
     * 思路：
     * - 对于每种颜色 i，选择 x_i 个球放入盒子1 (0 ≤ x_i ≤ balls[i])
     * - 需要满足 sum(x_i) = n
     * - 对于每种合法分配的概率贡献为：
     *   prob = prod(C(balls[i], x_i)) / C(2n, n)
     * - 总概率 = sum(有利分配的概率贡献)
     *
     * 用 double 计算避免 long 溢出 (48! ≈ 1.24e61 远超 long 范围)
     *
     * 时间复杂度：O(k * (max(balls[i])+1)^k)
     * 空间复杂度：O(k)
     */
    public double getProbability(int[] balls) {
        this.balls = balls;
        this.k = balls.length;
        int totalBalls = 0;
        for (int b : balls) {
            totalBalls += b;
        }
        this.n = totalBalls / 2;

        // 预计算 C 值所需阶乘 (int 足够，C(48,24) ≈ 3.2e13)
        long[] fact = new long[totalBalls + 1];
        fact[0] = 1;
        for (int i = 1; i <= totalBalls; i++) {
            fact[i] = fact[i - 1] * i;
        }

        // 预计算 C(b, x) 查找表
        long[][] C = new long[totalBalls + 1][totalBalls + 1];
        for (int i = 0; i <= totalBalls; i++) {
            for (int j = 0; j <= i; j++) {
                C[i][j] = fact[i] / (fact[j] * fact[i - j]);
            }
        }

        // C(2n, n) 作为 double
        combC2n_n = (double) C[totalBalls][n];

        favorable = 0.0;
        dfs(0, 0, 0, 0, 1.0, C);

        // favorable / C(2n, n) — favorable 是 sum(prod(C(bi, xi))) for favorable cases
        // 但我们已经通过 wf * 1.0 / C(2n, n) 累计到 favorable 中了
        return favorable;
    }

    /**
     * DFS 枚举所有分配方案，直接累加概率到 favorable
     *
     * @param idx       当前处理到的颜色索引
     * @param sum1      盒子1中已分配的球数
     * @param distinct1 盒子1中已出现的颜色数
     * @param distinct2 盒子2中已出现的颜色数
     * @param wf        prod(C(balls[i], x_i)) 作为 double，除以 C(2n,n) 后累加
     * @param C         C[n][k] 查找表
     */
    private void dfs(int idx, int sum1, int distinct1, int distinct2, double wf, long[][] C) {
        if (sum1 > n) {
            return; // 剪枝：盒子1球数不能超过 n
        }
        if (idx == k) {
            if (sum1 == n && distinct1 == distinct2) {
                // 概率贡献 = prod(C(balls[i], x_i)) / C(2n, n)
                favorable += wf / combC2n_n;
            }
            return;
        }

        int b = balls[idx];
        for (int x = 0; x <= b; x++) {
            int newSum1 = sum1 + x;
            if (newSum1 > n) {
                break; // 后续 x 只会更大，直接跳出
            }
            int newDistinct1 = distinct1 + (x > 0 ? 1 : 0);
            int newDistinct2 = distinct2 + (b - x > 0 ? 1 : 0);
            dfs(idx + 1, newSum1, newDistinct1, newDistinct2, wf * C[b][x], C);
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        double result;

        // 示例 1
        result = sol.getProbability(new int[]{1, 1});
        System.out.printf("balls=[1,1] → %.5f (预期: 1.00000)%n", result);
        assert Math.abs(result - 1.00000) < 1e-5 : "示例1失败";

        // 示例 2
        result = sol.getProbability(new int[]{2, 1, 1});
        System.out.printf("balls=[2,1,1] → %.5f (预期: 0.66667)%n", result);
        assert Math.abs(result - 0.66667) < 1e-4 : "示例2失败";

        // 示例 3
        result = sol.getProbability(new int[]{1, 2, 1, 2});
        System.out.printf("balls=[1,2,1,2] → %.5f (预期: 0.60000)%n", result);
        assert Math.abs(result - 0.60000) < 1e-4 : "示例3失败";

        System.out.println("所有测试通过!");
    }
}
