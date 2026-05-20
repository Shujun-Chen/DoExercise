import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * 1389. Create Target Array in the Given Order
 * https://leetcode.com/problems/create-target-array-in-the-given-order/
 *
 * 时间复杂度：O(n^2) — 每次插入操作可能移动后续元素
 * 空间复杂度：O(n) — 结果列表
 */
class Solution {
    public int[] createTargetArray(int[] nums, int[] index) {
        // 使用 ArrayList 支持在任意位置插入
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // add(index, element) 将元素插入到指定位置，原位置及之后元素后移
            list.add(index[i], nums[i]);
        }
        // 转换为 int[] 返回
        int[] target = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            target[i] = list.get(i);
        }
        return target;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 示例测试
        System.out.println(Arrays.toString(
            s.createTargetArray(new int[]{0, 1, 2, 3, 4}, new int[]{0, 1, 2, 2, 1})));
        // 预期：[0, 4, 1, 3, 2]

        System.out.println(Arrays.toString(
            s.createTargetArray(new int[]{1, 2, 3, 4, 0}, new int[]{0, 1, 2, 3, 0})));
        // 预期：[0, 1, 2, 3, 4]

        System.out.println(Arrays.toString(
            s.createTargetArray(new int[]{1}, new int[]{0})));
        // 预期：[1]
    }
}
