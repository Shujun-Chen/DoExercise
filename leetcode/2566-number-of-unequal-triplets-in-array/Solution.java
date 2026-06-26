import java.util.*;

/**
 * 2566. Number of Unequal Triplets in Array
 * 难度：Easy
 *
 * 给你一个下标从 0 开始的正整数数组 nums。
 * 找出满足以下条件的三元组 (i, j, k) 的数目：
 * - 0 <= i < j < k < nums.length
 * - nums[i]、nums[j] 和 nums[k] 两两互不相同
 */
public class Solution {
    
    /**
     * 计数组合法（O(n) 时间）
     *
     * 思路：
     * 1. 统计每个数字的出现频率
     * 2. 总三元组数 = C(n, 3) = n*(n-1)*(n-2)/6
     * 3. 对每个出现次数 c 的值，减去包含重复数字的三元组：
     *    - 恰好两个重复：C(c, 2) * (n - c) = c*(c-1)/2 * (n-c)
     *    - 三个都重复：C(c, 3) = c*(c-1)*(c-2)/6
     * 4. 剩下的就是两两互不相同的三元组
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int unequalTriplets(int[] nums) {
        int n = nums.length;
        
        // 统计每个数字的频率
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // 总三元组数 C(n, 3)
        long total = (long) n * (n - 1) * (n - 2) / 6;
        
        // 减去包含重复数字的三元组
        for (int c : freq.values()) {
            if (c >= 2) {
                // 恰好两个重复：C(c, 2) * (n - c)
                total -= (long) c * (c - 1) / 2 * (n - c);
            }
            if (c >= 3) {
                // 三个都重复：C(c, 3)
                total -= (long) c * (c - 1) * (c - 2) / 6;
            }
        }
        
        return (int) total;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 测试用例 1
        int[] nums1 = {4, 4, 2, 4, 3};
        int result1 = sol.unequalTriplets(nums1);
        System.out.println("nums = [4,4,2,4,3] → " + result1 + " (期望: 3)");
        assert result1 == 3 : "测试用例 1 失败";
        
        // 测试用例 2
        int[] nums2 = {1, 1, 1, 1, 1};
        int result2 = sol.unequalTriplets(nums2);
        System.out.println("nums = [1,1,1,1,1] → " + result2 + " (期望: 0)");
        assert result2 == 0 : "测试用例 2 失败";
        
        // 测试用例 3：全部不同
        int[] nums3 = {1, 2, 3};
        int result3 = sol.unequalTriplets(nums3);
        System.out.println("nums = [1,2,3] → " + result3 + " (期望: 1)");
        assert result3 == 1 : "测试用例 3 失败";
        
        // 测试用例 4：混合
        int[] nums4 = {1, 1, 2, 2, 3};
        int result4 = sol.unequalTriplets(nums4);
        // C(5,3)=10 - C(2,2)*(5-2)*2 = 10 - 3 - 3 = 4
        System.out.println("nums = [1,1,2,2,3] → " + result4 + " (期望: 4)");
        assert result4 == 4 : "测试用例 4 失败";
        
        System.out.println("所有测试通过！");
    }
}
