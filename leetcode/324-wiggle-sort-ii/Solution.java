import java.util.Arrays;

class Solution {
    /**
     * 将数组重新排列为 nums[0] < nums[1] > nums[2] < nums[3] ... 的摆动顺序。
     *
     * 思路：
     * 1. 先排序得到有序数组
     * 2. 将排序后数组的左半部分（较小元素）逆序放入偶数下标位置
     * 3. 将右半部分（较大元素）逆序放入奇数下标位置
     * 这样能确保相邻元素不会相等，满足摆动条件
     */
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // 左半部分有 (n+1)/2 个元素
        int mid = (n + 1) / 2;

        // 从右向左取左半部分（较小元素），放入偶数下标
        int j = mid - 1; // 左半部分最后一个元素的下标
        int k = n - 1;   // 右半部分最后一个元素的下标

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                // 偶数下标放较小的元素
                nums[i] = sorted[j--];
            } else {
                // 奇数下标放较大的元素
                nums[i] = sorted[k--];
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 测试用例
        int[][] testCases = {
            {1, 5, 1, 1, 6, 4},
            {1, 3, 2, 2, 3, 1},
            {1, 2, 3},
            {4, 3, 2, 1}
        };

        for (int[] nums : testCases) {
            int[] original = nums.clone();
            solution.wiggleSort(nums);
            System.out.print(Arrays.toString(original) + " -> " + Arrays.toString(nums));

            // 验证摆动条件
            boolean valid = true;
            for (int i = 1; i < nums.length; i++) {
                if (i % 2 == 1) { // 奇数位：nums[i-1] < nums[i]
                    if (!(nums[i - 1] < nums[i])) {
                        valid = false;
                        break;
                    }
                } else { // 偶数位：nums[i-1] > nums[i]
                    if (!(nums[i - 1] > nums[i])) {
                        valid = false;
                        break;
                    }
                }
            }
            System.out.println("  " + (valid ? "✓" : "✗"));
        }

        // 边界情况
        int[] edge = {1, 1, 2};
        solution.wiggleSort(edge);
        System.out.println("\n边界测试:");
        System.out.println("[1,1,2] -> " + Arrays.toString(edge));

        // 时间复杂度: O(n log n) — 排序耗时
        // 空间复杂度: O(n) — 使用额外数组存储排序结果
    }
}
