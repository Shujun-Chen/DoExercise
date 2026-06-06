class Solution {
    /**
     * 博弈 DP：从后往前计算每个位置的最优分差。
     *
     * dp[i] 表示从位置 i 开始，当前玩家能获得的最大分差
     * （当前玩家得分 - 对手得分）。
     *
     * 转移方程：
     *   dp[i] = max( sum(stoneValue[i:i+k]) - dp[i+k] ), k = 1, 2, 3
     *
     * 最终 dp[0] > 0 → Alice 赢，< 0 → Bob 赢，== 0 → 平局。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        // 滚动数组：dp1=dp[i+1], dp2=dp[i+2], dp3=dp[i+3]
        int dp1 = 0, dp2 = 0, dp3 = 0;

        for (int i = n - 1; i >= 0; i--) {
            int take = 0;
            int best = Integer.MIN_VALUE;
            // 尝试取 1、2、3 堆
            for (int k = 1; k <= 3 && i + k - 1 < n; k++) {
                take += stoneValue[i + k - 1];
                // 对手从 i+k 开始的分差
                int opp = (k == 1) ? dp1 : (k == 2) ? dp2 : dp3;
                best = Math.max(best, take - opp);
            }
            // 滚动更新
            dp3 = dp2;
            dp2 = dp1;
            dp1 = best;
        }

        if (dp1 > 0) return "Alice";
        else if (dp1 < 0) return "Bob";
        else return "Tie";
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 测试用例 1
        assert s.stoneGameIII(new int[]{1, 2, 3, 7}).equals("Bob");
        // 测试用例 2
        assert s.stoneGameIII(new int[]{1, 2, 3, -9}).equals("Alice");
        // 测试用例 3
        assert s.stoneGameIII(new int[]{1, 2, 3, 6}).equals("Tie");
        // 额外：单个元素
        assert s.stoneGameIII(new int[]{5}).equals("Alice");
        assert s.stoneGameIII(new int[]{-5}).equals("Bob");
        System.out.println("All tests passed!");
    }
}
