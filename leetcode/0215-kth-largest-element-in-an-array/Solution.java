import java.util.Random;

/**
 * LeetCode 215. Kth Largest Element in an Array
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * 解法：快选（Quickselect），思路与 solution.py 完全一致。
 * 随机化 pivot + 原地 partition，平均 O(n)、空间 O(1)。
 *
 * 复杂度：
 *   时间 平均 O(n)，最坏 O(n^2)；空间 O(1)。
 */
public class Solution {

    private final Random rand = new Random();

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        // 第 k 大等价于升序下标 n-k
        int target = n - k;
        return quickselect(nums, 0, n - 1, target);
    }

    private int quickselect(int[] nums, int left, int right, int target) {
        // 随机选 pivot，避免退化
        int pivotIdx = left + rand.nextInt(right - left + 1);
        swap(nums, pivotIdx, right);
        int pivot = nums[right];

        // partition：< pivot 的丢左边
        int store = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, store, i);
                store++;
            }
        }
        // 把 pivot 放到最终位置 store
        swap(nums, store, right);

        if (store == target) {
            return nums[store];
        } else if (store < target) {
            return quickselect(nums, store + 1, right, target);
        } else {
            return quickselect(nums, left, store - 1, target);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 官方样例 1
        check(s.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2) == 5);
        // 含重复
        check(s.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4) == 4);
        // 单元素
        check(s.findKthLargest(new int[]{1}, 1) == 1);
        // 降序取最大
        check(s.findKthLargest(new int[]{7, 6, 5, 4, 3, 2, 1}, 1) == 7);
        // 降序取最小
        check(s.findKthLargest(new int[]{7, 6, 5, 4, 3, 2, 1}, 7) == 1);
        // 全相等
        check(s.findKthLargest(new int[]{2, 2, 2, 2}, 3) == 2);

        System.out.println("所有测试通过 ✅");
    }

    private static void check(boolean cond) {
        if (!cond) throw new AssertionError("测试失败");
    }
}