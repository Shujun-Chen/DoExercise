class Solution {
    /**
     * 计算卡车能行驶的最大距离。
     *
     * 思路：模拟燃油消耗过程
     * - 每消耗 1 升主油箱燃油，行驶 10 km
     * - 每消耗 5 升主油箱燃油，若副油箱有油则转移 1 升到主油箱
     * - 持续消耗直到主油箱为空
     *
     * 时间复杂度：O(mainTank / 5) ≈ O(n)
     * 空间复杂度：O(1)
     */
    public int distanceTraveled(int mainTank, int additionalTank) {
        int distance = 0; // 总行驶距离（km）

        while (mainTank > 0) {
            // 本轮消耗的燃油量（最多 5 升或剩余油量）
            int consume = Math.min(5, mainTank);
            mainTank -= consume;
            distance += consume * 10; // 每升 10 km

            // 每消耗 5 升，检查副油箱是否可以补充
            if (consume == 5 && additionalTank > 0) {
                mainTank += 1;
                additionalTank -= 1;
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        assert sol.distanceTraveled(5, 10) == 60 : "测试 1 失败";

        // 测试用例 2
        assert sol.distanceTraveled(1, 2) == 10 : "测试 2 失败";

        // 额外测试：刚好消耗完整主油箱
        assert sol.distanceTraveled(10, 2) == 120 : "测试 3 失败";

        // 额外测试：副油箱为空
        assert sol.distanceTraveled(7, 0) == 70 : "测试 4 失败";

        System.out.println("所有测试用例通过！");
    }
}
