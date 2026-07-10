import java.util.Arrays;

class Solution {
    public int partitionDisjoint(int[] nums) {
        // leftMax: 左部分当前的最大值
        // candidateMax: 用于扩展 left 时赋给 leftMax 的暂存最大值
        // partition: left 的长度,初始为 1(至少包含 nums[0])
        int leftMax = nums[0];
        int candidateMax = nums[0];
        int partition = 1;

        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            if (x < leftMax) {
                // 当前元素必须属于 left,否则会出现在 right 中,
                // 但小于 leftMax 的元素在 right 里违反 left<=right 的约束
                partition = i + 1;
                leftMax = candidateMax;
            } else if (x > candidateMax) {
                // 记录新的候选最大值,为后续扩展做准备
                candidateMax = x;
            }
        }
        return partition;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // Example 1: [5,0,3,8,6] -> 3
        System.out.println(s.partitionDisjoint(new int[]{5, 0, 3, 8, 6})); // 3

        // Example 2: [1,1,1,0,6,12] -> 4
        System.out.println(s.partitionDisjoint(new int[]{1, 1, 1, 0, 6, 12})); // 4

        // 单调递增 -> 1
        System.out.println(s.partitionDisjoint(new int[]{1, 2, 3, 4, 5})); // 1

        // 单调递减 -> 4
        System.out.println(s.partitionDisjoint(new int[]{5, 4, 3, 2, 1})); // 4

        // 全相同 -> 1
        System.out.println(s.partitionDisjoint(new int[]{2, 2, 2, 2})); // 1

        // 时间复杂度:O(n) —— 一次遍历
        // 空间复杂度:O(1) —— 仅使用常数额外变量
    }
}
