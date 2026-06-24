import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    /**
     * 找出给定范围内缺失的所有整数。
     * 题目保证数组中的整数是唯一的，且原范围的最小值和最大值仍然在数组中。
     *
     * @param nums 包含唯一整数的数组
     * @return 缺失的整数列表（升序）
     */
    public List<Integer> findMissingElements(int[] nums) {
        // 求出数组中的最小值和最大值
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int num : nums) {
            minVal = Math.min(minVal, num);
            maxVal = Math.max(maxVal, num);
        }

        // 将数组转为哈希集合，实现 O(1) 查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        // 遍历 [minVal, maxVal] 范围内的所有整数
        // 不在集合中的即为缺失值
        List<Integer> result = new ArrayList<>();
        for (int i = minVal; i <= maxVal; i++) {
            if (!numSet.contains(i)) {
                result.add(i);
            }
        }

        return result;
        // 遍历是升序的，结果天然有序
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        int[] nums1 = {1, 4, 2, 5};
        System.out.println("输入: nums = [1,4,2,5]");
        System.out.println("输出: " + sol.findMissingElements(nums1));  // 预期: [3]

        // 示例 2
        int[] nums2 = {7, 8, 6, 9};
        System.out.println("输入: nums = [7,8,6,9]");
        System.out.println("输出: " + sol.findMissingElements(nums2));  // 预期: []

        // 示例 3
        int[] nums3 = {5, 1};
        System.out.println("输入: nums = [5,1]");
        System.out.println("输出: " + sol.findMissingElements(nums3));  // 预期: [2, 3, 4]

        // 边界测试
        int[] nums4 = {10, 12};
        System.out.println("输入: nums = [10,12]");
        System.out.println("输出: " + sol.findMissingElements(nums4));  // 预期: [11]

        // 连续无缺失
        int[] nums5 = {3, 2, 1, 4};
        System.out.println("输入: nums = [3,2,1,4]");
        System.out.println("输出: " + sol.findMissingElements(nums5));  // 预期: []
    }
}

// 复杂度分析
// 时间复杂度: O(n) — 求 min/max 和建集合各需 O(n)，遍历范围最多 O(n)
// 空间复杂度: O(n) — 哈希集合存储 nums
