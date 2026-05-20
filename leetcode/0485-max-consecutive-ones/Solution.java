/**
 * 485. Max Consecutive Ones
 * https://leetcode.com/problems/max-consecutive-ones/
 * 难度：Easy
 *
 * 时间复杂度：O(n)，遍历数组一次
 * 空间复杂度：O(1)，只使用两个计数器变量
 */

class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        // curCount: 当前连续 1 的个数
        // maxCount: 历史最大连续 1 的个数
        int maxCount = 0;
        int curCount = 0;

        for (int num : nums) {
            if (num == 1) {
                // 遇到 1，增加当前计数并更新最大值
                curCount++;
                maxCount = Math.max(maxCount, curCount);
            } else {
                // 遇到 0，重置当前计数
                curCount = 0;
            }
        }

        return maxCount;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 测试用例
        assert s.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}) == 3;
        assert s.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1}) == 2;
        assert s.findMaxConsecutiveOnes(new int[]{0}) == 0;
        assert s.findMaxConsecutiveOnes(new int[]{1}) == 1;
        assert s.findMaxConsecutiveOnes(new int[]{0, 0, 0}) == 0;
        assert s.findMaxConsecutiveOnes(new int[]{1, 1, 1}) == 3;

        System.out.println("All tests passed!");
    }
}
