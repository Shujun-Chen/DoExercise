class Solution {
    /**
     * 计算数组中每个索引 i (1 <= i <= n-2) 的美丽值之和。
     *
     * 美丽值规则：
     * - 2: nums[i] 严格大于左侧所有元素，且严格小于右侧所有元素
     * - 1: 仅满足 nums[i-1] < nums[i] < nums[i+1]，不满足条件2
     * - 0: 以上均不满足
     */
    public int sumOfBeauties(int[] nums) {
        int n = nums.length;
        if (n < 3) return 0;

        // 预处理 leftMax[i] = nums[0..i-1] 的最大值
        int[] leftMax = new int[n];
        leftMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], nums[i - 1]);
        }

        // 预处理 rightMin[i] = nums[i+1..n-1] 的最小值
        int[] rightMin = new int[n];
        rightMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMin[i] = Math.min(rightMin[i + 1], nums[i + 1]);
        }

        int beautySum = 0;
        for (int i = 1; i < n - 1; i++) {
            if (leftMax[i] < nums[i] && nums[i] < rightMin[i]) {
                // 条件2：严格大于左侧所有，严格小于右侧所有
                beautySum += 2;
            } else if (nums[i - 1] < nums[i] && nums[i] < nums[i + 1]) {
                // 条件1：仅相邻元素满足递增
                beautySum += 1;
            }
        }

        return beautySum;
    }

    // 时间复杂度：O(n)，两次线性扫描
    // 空间复杂度：O(n)，两个辅助数组

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int[] nums1 = {1, 2, 3};
        int result1 = sol.sumOfBeauties(nums1);
        System.out.println("nums = [1,2,3] → " + result1 + " (expected: 2)");
        assert result1 == 2 : "Test 1 failed";

        // 测试用例 2
        int[] nums2 = {2, 4, 6, 4};
        int result2 = sol.sumOfBeauties(nums2);
        System.out.println("nums = [2,4,6,4] → " + result2 + " (expected: 1)");
        assert result2 == 1 : "Test 2 failed";

        // 测试用例 3
        int[] nums3 = {3, 2, 1};
        int result3 = sol.sumOfBeauties(nums3);
        System.out.println("nums = [3,2,1] → " + result3 + " (expected: 0)");
        assert result3 == 0 : "Test 3 failed";

        // 额外测试：全递增
        int[] nums4 = {1, 2, 3, 4, 5};
        int result4 = sol.sumOfBeauties(nums4);
        System.out.println("nums = [1,2,3,4,5] → " + result4);
        assert result4 == 6 : "Test 4 failed";

        System.out.println("所有测试通过！");
    }
}
