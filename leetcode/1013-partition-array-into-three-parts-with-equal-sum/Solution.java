/**
 * 1013. Partition Array Into Three Parts With Equal Sum
 * 判断能否将数组分成三个和相等的非空连续子数组。
 *
 * 思路：
 * 1. 计算数组总和 totalSum
 * 2. 若 totalSum % 3 != 0，则无法平分，返回 false
 * 3. target = totalSum / 3 为每个部分的目标和
 * 4. 遍历数组累加部分和，每当部分和等于 target 时计数 +1 并重置
 * 5. 若找到至少 3 个部分则返回 true
 *
 * 时间复杂度：O(n) —— 一次遍历
 * 空间复杂度：O(1) —— 只用常数变量
 */
class Solution {
    public boolean canThreePartsEqualSum(int[] arr) {
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }

        // 若总和不能被 3 整除，无法平分
        if (totalSum % 3 != 0) {
            return false;
        }

        int target = totalSum / 3;
        int partSum = 0;   // 当前部分和
        int count = 0;     // 已找到的和为 target 的部分数

        for (int num : arr) {
            partSum += num;
            if (partSum == target) {
                count++;
                partSum = 0;
                // 找到 3 个部分即可提前返回
                if (count == 3) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 示例 1
        int[] arr1 = {0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1};
        assert s.canThreePartsEqualSum(arr1) == true : "示例 1 应返回 true";

        // 示例 2
        int[] arr2 = {0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1};
        assert s.canThreePartsEqualSum(arr2) == false : "示例 2 应返回 false";

        // 示例 3
        int[] arr3 = {3, 3, 6, 5, -2, 2, 5, 1, -9, 4};
        assert s.canThreePartsEqualSum(arr3) == true : "示例 3 应返回 true";

        // 边界：三个相等元素
        int[] arr4 = {1, 1, 1};
        assert s.canThreePartsEqualSum(arr4) == true : "三个相等元素应返回 true";

        // 边界：全零数组
        int[] arr5 = {0, 0, 0, 0, 0};
        assert s.canThreePartsEqualSum(arr5) == true : "全零数组应返回 true";

        System.out.println("所有测试用例通过！");
    }
}
