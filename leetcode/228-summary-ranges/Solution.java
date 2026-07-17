import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    /**
     * 汇总排序且不重复的数组中的连续区间。
     *
     * 时间复杂度：O(n)，其中 n 为 nums 的长度。
     * 空间复杂度：O(n)，不计返回结果时为 O(1)。
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        int start = 0;

        for (int i = 1; i <= nums.length; i++) {
            // 到达数组末尾，或当前数字不再与前一个数字连续时，结束当前区间。
            if (i == nums.length || (long) nums[i] != (long) nums[i - 1] + 1) {
                if (start == i - 1) {
                    ranges.add(String.valueOf(nums[start]));
                } else {
                    ranges.add(nums[start] + "->" + nums[i - 1]);
                }
                start = i;
            }
        }

        return ranges;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        assert solution.summaryRanges(new int[]{}).equals(Arrays.asList());
        assert solution.summaryRanges(new int[]{0, 1, 2, 4, 5, 7})
                .equals(Arrays.asList("0->2", "4->5", "7"));
        assert solution.summaryRanges(new int[]{0, 2, 3, 4, 6, 8, 9})
                .equals(Arrays.asList("0", "2->4", "6", "8->9"));
        assert solution.summaryRanges(new int[]{-3, -2, -1, 2})
                .equals(Arrays.asList("-3->-1", "2"));

        System.out.println("All tests passed!");
    }
}
