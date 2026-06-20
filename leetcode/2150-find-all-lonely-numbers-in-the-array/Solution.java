import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2150. Find All Lonely Numbers in the Array (Medium)
 *
 * 找出数组中所有「孤独」的数字。
 * 一个数字 x 是孤独的，当且仅当：
 *   1. x 在数组中出现恰好一次
 *   2. x-1 和 x+1 都不在数组中
 */
class Solution {
    public List<Integer> findLonely(int[] nums) {
        // 统计每个数字的出现次数
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        // 遍历哈希表，筛选孤独数字
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int x = entry.getKey();
            int cnt = entry.getValue();
            // 条件1：恰好出现一次
            // 条件2：相邻数字 x-1 和 x+1 都不在数组中
            if (cnt == 1 && !freq.containsKey(x - 1) && !freq.containsKey(x + 1)) {
                result.add(x);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        int[] nums1 = {10, 6, 5, 8};
        System.out.println("Input: " + java.util.Arrays.toString(nums1));
        System.out.println("Output: " + sol.findLonely(nums1)); // 预期: [10, 8]

        // 示例 2
        int[] nums2 = {1, 3, 5, 3};
        System.out.println("Input: " + java.util.Arrays.toString(nums2));
        System.out.println("Output: " + sol.findLonely(nums2)); // 预期: [1, 5]

        // 额外测试：空结果
        int[] nums3 = {1, 2, 3};
        System.out.println("Input: " + java.util.Arrays.toString(nums3));
        System.out.println("Output: " + sol.findLonely(nums3)); // 预期: []

        // 额外测试：全部孤独
        int[] nums4 = {0, 2, 4, 6};
        System.out.println("Input: " + java.util.Arrays.toString(nums4));
        System.out.println("Output: " + sol.findLonely(nums4)); // 预期: [0, 2, 4, 6]

        // 时间: O(n) — 一次频率统计 + 一次遍历
        // 空间: O(n) — 哈希表存储频率
    }
}
