import java.util.*;

/**
 * LeetCode 347 - Top K Frequent Elements
 *
 * 思路：桶排序（与 Python 解法思路一致）
 * 1. 先用 HashMap 统计每个数字的频率
 * 2. 用 List<List<Integer>> 桶数组，桶下标 = 出现频率
 *    桶里装所有频率等于该下标的数字
 * 3. 从高频桶往低频桶遍历，收集够 k 个就返回
 *
 * 时间复杂度：O(n)，n = nums.length
 * 空间复杂度：O(n)
 */
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 统计频率
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        // 2. 建桶：频率 i 对应的桶里放所有频率为 i 的数字
        int n = nums.length;
        List<List<Integer>> bucket = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            bucket.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            bucket.get(freq).add(num);
        }

        // 3. 从高频桶往低频桶遍历，收集 k 个数字返回
        List<Integer> result = new ArrayList<>();
        for (int freq = n; freq > 0 && result.size() < k; freq--) {
            for (int num : bucket.get(freq)) {
                result.add(num);
                if (result.size() == k) {
                    break;
                }
            }
        }

        // 把 List<Integer> 转成 int[]
        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }

    // 简易辅助：把 int[] 转成排序后的 List，方便测试对比
    private static List<Integer> sorted(int[] arr) {
        Integer[] boxed = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            boxed[i] = arr[i];
        }
        Arrays.sort(boxed);
        return Arrays.asList(boxed);
    }

    private static boolean listsEqual(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 用例 1：基础
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int[] out1 = sol.topKFrequent(nums1, 2);
        List<Integer> expected1 = Arrays.asList(1, 2);
        assert listsEqual(sorted(out1), expected1) : "case1 failed: " + Arrays.toString(out1);
        System.out.println("用例1 通过：nums=[1,1,1,2,2,3], k=2 -> " + Arrays.toString(out1));

        // 用例 2：单元素
        int[] nums2 = {1};
        int[] out2 = sol.topKFrequent(nums2, 1);
        List<Integer> expected2 = Arrays.asList(1);
        assert listsEqual(sorted(out2), expected2) : "case2 failed: " + Arrays.toString(out2);
        System.out.println("用例2 通过：nums=[1], k=1 -> " + Arrays.toString(out2));

        // 用例 3：频率全相同
        int[] nums3 = {4, 4, 4, 5, 5, 5, 6};
        int[] out3 = sol.topKFrequent(nums3, 3);
        List<Integer> expected3 = Arrays.asList(4, 5, 6);
        assert listsEqual(sorted(out3), expected3) : "case3 failed: " + Arrays.toString(out3);
        System.out.println("用例3 通过：nums=[4,4,4,5,5,5,6], k=3 -> " + Arrays.toString(out3));

        // 用例 4：含负数
        int[] nums4 = {-1, -1, -2, -2, -2, 3};
        int[] out4 = sol.topKFrequent(nums4, 2);
        List<Integer> expected4 = Arrays.asList(-2, -1);
        assert listsEqual(sorted(out4), expected4) : "case4 failed: " + Arrays.toString(out4);
        System.out.println("用例4 通过：nums=[-1,-1,-2,-2,-2,3], k=2 -> " + Arrays.toString(out4));

        // 用例 5：大量重复
        int[] nums5 = {1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        int[] out5 = sol.topKFrequent(nums5, 3);
        List<Integer> expected5 = Arrays.asList(3, 4, 5);
        assert listsEqual(sorted(out5), expected5) : "case5 failed: " + Arrays.toString(out5);
        System.out.println("用例5 通过：nums=[...], k=3 -> " + Arrays.toString(out5));

        System.out.println("\n全部用例通过 ✅");
    }
}
