import java.util.TreeSet;

class Solution {
    /**
     * 使用 TreeSet 维护距离当前位置至少 x 的元素集合。
     * 对于每个位置 i，在 TreeSet 中查找与 nums[i] 最接近的值。
     *
     * 时间复杂度: O(n log n)
     * 空间复杂度: O(n)
     */
    public int minAbsoluteDifference(int[] nums, int x) {
        if (x == 0) return 0;

        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        TreeSet<Integer> set = new TreeSet<>();

        for (int i = x; i < n; i++) {
            // 将距离当前下标恰好 x 的元素加入有序集合
            set.add(nums[i - x]);
            // 查找 floor（小于等于 nums[i] 的最大值）
            Integer floor = set.floor(nums[i]);
            // 查找 ceiling（大于等于 nums[i] 的最小值）
            Integer ceil = set.ceiling(nums[i]);
            if (floor != null) {
                ans = Math.min(ans, Math.abs(nums[i] - floor));
            }
            if (ceil != null) {
                ans = Math.min(ans, Math.abs(nums[i] - ceil));
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 测试用例 1
        assert s.minAbsoluteDifference(new int[]{4, 3, 2, 4}, 2) == 0 : "测试1失败";
        // 测试用例 2
        assert s.minAbsoluteDifference(new int[]{5, 3, 2, 10, 15}, 1) == 1 : "测试2失败";
        // 测试用例 3
        assert s.minAbsoluteDifference(new int[]{1, 2, 3, 4}, 3) == 3 : "测试3失败";
        System.out.println("所有测试用例通过！");
    }
}
