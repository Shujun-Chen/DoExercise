import java.util.Arrays;

class Solution {
    public int minimumCost(int[] nums) {
        // 将数组分成3个连续非空子数组，每个子数组成本 = 它的第一个元素
        // 总成本 = nums[0] + nums[i] + nums[j]，0 < i < j < n
        // 在 nums[1:] 中选最小的两个元素即可
        int n = nums.length;
        int[] rest = Arrays.copyOfRange(nums, 1, n);
        Arrays.sort(rest);
        return nums[0] + rest[0] + rest[1];
        // 时间复杂度：O(n log n) — 排序开销
        // 空间复杂度：O(n) — 复制数组
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int[] nums1 = {1, 2, 3, 12};
        System.out.println("nums=" + Arrays.toString(nums1) + ", result=" + sol.minimumCost(nums1) + " (expected=6)");

        // 测试用例 2
        int[] nums2 = {5, 4, 3};
        System.out.println("nums=" + Arrays.toString(nums2) + ", result=" + sol.minimumCost(nums2) + " (expected=12)");

        // 测试用例 3
        int[] nums3 = {10, 3, 1, 1};
        System.out.println("nums=" + Arrays.toString(nums3) + ", result=" + sol.minimumCost(nums3) + " (expected=12)");

        // 测试用例 4
        int[] nums4 = {1, 10, 5, 2};
        System.out.println("nums=" + Arrays.toString(nums4) + ", result=" + sol.minimumCost(nums4) + " (expected=8)");
    }
}
