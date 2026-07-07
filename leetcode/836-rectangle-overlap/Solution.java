class Solution {
    /**
     * 判断两个轴对齐矩形是否重叠（交集面积为正）。
     * 两个矩形不重叠当且仅当一个矩形完全在另一个的左侧、右侧、上方或下方。
     * 检查两个矩形在 x 轴和 y 轴上的投影是否有重叠区间即可。
     */
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        int x1 = rec1[0], y1 = rec1[1], x2 = rec1[2], y2 = rec1[3];
        int x3 = rec2[0], y3 = rec2[1], x4 = rec2[2], y4 = rec2[3];

        // x 轴投影重叠：两个矩形的 x 区间有交集
        boolean overlapX = Math.max(x1, x3) < Math.min(x2, x4);
        // y 轴投影重叠：两个矩形的 y 区间有交集
        boolean overlapY = Math.max(y1, y3) < Math.min(y2, y4);

        // 两个方向都有重叠 → 矩形相交
        return overlapX && overlapY;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1: 重叠
        assert sol.isRectangleOverlap(new int[]{0, 0, 2, 2}, new int[]{1, 1, 3, 3}) == true : "Example 1 failed";

        // Example 2: 边缘接触，不重叠
        assert sol.isRectangleOverlap(new int[]{0, 0, 1, 1}, new int[]{1, 0, 2, 1}) == false : "Example 2 failed";

        // Example 3: 不相邻，不重叠
        assert sol.isRectangleOverlap(new int[]{0, 0, 1, 1}, new int[]{2, 2, 3, 3}) == false : "Example 3 failed";

        // 一个矩形完全包含另一个
        assert sol.isRectangleOverlap(new int[]{0, 0, 5, 5}, new int[]{1, 1, 3, 3}) == true : "Containment failed";

        // 上下排列，不重叠
        assert sol.isRectangleOverlap(new int[]{0, 0, 2, 2}, new int[]{0, 3, 2, 5}) == false : "Vertical non-overlap failed";

        System.out.println("所有测试用例通过！");
    }
}

// 时间复杂度：O(1) — 只做常数次比较
// 空间复杂度：O(1) — 只使用固定数量的变量
