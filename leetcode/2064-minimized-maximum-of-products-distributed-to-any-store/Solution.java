class Solution {
    /**
     * 二分查找最小的最大产品分配数 x。
     *
     * 对于给定的 x，检查每个产品类型需要多少个店铺：
     * - 如果某个产品有 q 件，则需要 ceil(q / x) 个店铺
     * - 如果总店铺数 <= n，则 x 可行
     *
     * 二分查找范围：[1, max(quantities)]
     */
    public int minimizedMaximum(int n, int[] quantities) {
        // 找到 quantities 的最大值作为二分上界
        int maxQ = 0;
        for (int q : quantities) {
            maxQ = Math.max(maxQ, q);
        }

        int lo = 1, hi = maxQ;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canDistribute(n, quantities, mid)) {
                hi = mid;  // 尝试更小的 x
            } else {
                lo = mid + 1;  // 需要更大的 x
            }
        }
        return lo;
    }

    /**
     * 检查当每个店铺最多分配 x 件产品时，是否能用 n 个店铺分配完所有产品。
     */
    private boolean canDistribute(int n, int[] quantities, int x) {
        int storesNeeded = 0;
        for (int q : quantities) {
            // 向上取整：ceil(q / x)
            storesNeeded += (q + x - 1) / x;
            // 提前剪枝
            if (storesNeeded > n) {
                return false;
            }
        }
        return storesNeeded <= n;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 示例 1
        int n1 = 6;
        int[] q1 = {11, 6};
        int r1 = s.minimizedMaximum(n1, q1);
        System.out.println("n=" + n1 + ", quantities=[11,6] → " + r1 + " (expected: 3)");
        assert r1 == 3 : "Example 1 failed";

        // 示例 2
        int n2 = 7;
        int[] q2 = {15, 10, 10};
        int r2 = s.minimizedMaximum(n2, q2);
        System.out.println("n=" + n2 + ", quantities=[15,10,10] → " + r2 + " (expected: 5)");
        assert r2 == 5 : "Example 2 failed";

        // 示例 3
        int n3 = 1;
        int[] q3 = {100000};
        int r3 = s.minimizedMaximum(n3, q3);
        System.out.println("n=" + n3 + ", quantities=[100000] → " + r3 + " (expected: 100000)");
        assert r3 == 100000 : "Example 3 failed";

        System.out.println("所有测试通过！");
    }
}

// 时间复杂度：O(m * log M)，其中 m = len(quantities), M = max(quantities)
// 空间复杂度：O(1)
