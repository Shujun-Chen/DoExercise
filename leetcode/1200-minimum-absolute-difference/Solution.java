import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1200. Minimum Absolute Difference
 * https://leetcode.com/problems/minimum-absolute-difference/
 *
 * 时间复杂度：O(N log N) — 排序占主导
 * 空间复杂度：O(N) — 结果列表
 */
class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        // 先排序，这样最小绝对差一定出现在相邻元素之间
        Arrays.sort(arr);

        // 第一遍扫描：找到最小绝对差
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int diff = arr[i] - arr[i - 1];
            if (diff < minDiff) {
                minDiff = diff;
            }
        }

        // 第二遍扫描：收集所有差值等于 minDiff 的相邻对
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == minDiff) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 示例测试
        System.out.println(s.minimumAbsDifference(new int[]{4, 2, 1, 3}));
        // 预期：[[1, 2], [2, 3], [3, 4]]

        System.out.println(s.minimumAbsDifference(new int[]{1, 3, 6, 10, 15}));
        // 预期：[[1, 3]]

        System.out.println(s.minimumAbsDifference(new int[]{3, 8, -10, 23, 19, -4, -14, 27}));
        // 预期：[[-14, -10], [19, 23], [23, 27]]

        // 边界测试
        System.out.println(s.minimumAbsDifference(new int[]{1, 5}));
        // 预期：[[1, 5]]
    }
}
