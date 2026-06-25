import java.util.List;

class Solution {
    /**
     * 找出最小的有效分割下标 i，使得左右两个子数组拥有相同的支配元素。
     * 支配元素定义：出现次数超过子数组长度一半的元素。
     * 题目保证整个数组有且只有一个支配元素。
     */
    public int minimumIndex(List<Integer> nums) {
        int n = nums.size();

        // ---------- Step 1: Boyer-Moore 投票算法找出全局支配元素 ----------
        int candidate = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        // ---------- Step 2: 统计支配元素在整个数组中的出现次数 ----------
        int totalFreq = 0;
        for (int num : nums) {
            if (num == candidate) {
                totalFreq++;
            }
        }

        // ---------- Step 3: 遍历分割点，检查左右子数组是否都满足支配条件 ----------
        int leftFreq = 0;
        for (int i = 0; i < n - 1; i++) {
            if (nums.get(i) == candidate) {
                leftFreq++;
            }
            int rightFreq = totalFreq - leftFreq;
            int leftLen = i + 1;
            int rightLen = n - i - 1;

            if (leftFreq * 2 > leftLen && rightFreq * 2 > rightLen) {
                return i;
            }
        }

        return -1;
    }

    // 时间复杂度: O(n) — 两次线性扫描
    // 空间复杂度: O(1) — 仅使用常数级额外空间

    public static void main(String[] args) {
        Solution s = new Solution();

        // 测试用例 1
        List<Integer> nums1 = List.of(1, 2, 2, 2);
        int res1 = s.minimumIndex(nums1);
        assert res1 == 2 : "Expected 2, got " + res1;
        System.out.println("✅ Test 1 passed: nums=" + nums1 + " -> " + res1);

        // 测试用例 2
        List<Integer> nums2 = List.of(2, 1, 3, 1, 1, 1, 7, 1, 2, 1);
        int res2 = s.minimumIndex(nums2);
        assert res2 == 4 : "Expected 4, got " + res2;
        System.out.println("✅ Test 2 passed: nums=" + nums2 + " -> " + res2);

        // 测试用例 3
        List<Integer> nums3 = List.of(3, 3, 3, 3, 7, 2, 2);
        int res3 = s.minimumIndex(nums3);
        assert res3 == -1 : "Expected -1, got " + res3;
        System.out.println("✅ Test 3 passed: nums=" + nums3 + " -> " + res3);

        // 额外测试：只有两个元素且支配元素相同
        List<Integer> nums4 = List.of(1, 1);
        int res4 = s.minimumIndex(nums4);
        assert res4 == 0 : "Expected 0, got " + res4;
        System.out.println("✅ Test 4 passed: nums=" + nums4 + " -> " + res4);

        System.out.println("\n🎉 所有测试通过！");
    }
}
