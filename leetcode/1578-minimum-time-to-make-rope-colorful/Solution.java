class Solution {
    /**
     * LeetCode 1578. Minimum Time to Make Rope Colorful
     * 思路：贪心。把连续相同颜色的气球视为一组，每组只能保留一个，
     *       显然保留 neededTime 最大的那个，花费最小（= 组内总和 - 最大值）。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int minCost(String colors, int[] neededTime) {
        int n = colors.length();
        int total = 0;
        int i = 0;
        while (i < n) {
            // 找到当前相同颜色气球的连续区间 [i, j)
            int j = i;
            int sum = 0;
            int max = 0;
            while (j < n && colors.charAt(j) == colors.charAt(i)) {
                sum += neededTime[j];
                if (neededTime[j] > max) {
                    max = neededTime[j];
                }
                j++;
            }
            // 同色连续区间长度 > 1 时，需要移除多余的气球
            if (j - i > 1) {
                total += sum - max;
            }
            i = j;
        }
        return total;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试 1：标准用例
        int r1 = sol.minCost("abaac", new int[]{1, 2, 3, 4, 5});
        System.out.println("Test 1: " + r1 + " (expected 3)");
        assert r1 == 3;

        // 测试 2：原本就 colorful
        int r2 = sol.minCost("abc", new int[]{1, 2, 3});
        System.out.println("Test 2: " + r2 + " (expected 0)");
        assert r2 == 0;

        // 测试 3：两端同色
        int r3 = sol.minCost("aabaa", new int[]{1, 2, 3, 4, 1});
        System.out.println("Test 3: " + r3 + " (expected 2)");
        assert r3 == 2;

        // 测试 4：只有两个同色
        int r4 = sol.minCost("aa", new int[]{1, 5});
        System.out.println("Test 4: " + r4 + " (expected 1)");
        assert r4 == 1;

        // 测试 5：全部同色
        int r5 = sol.minCost("aaaa", new int[]{3, 1, 2, 4});
        System.out.println("Test 5: " + r5 + " (expected 6)");
        assert r5 == 6;

        System.out.println("All tests passed!");
    }
}