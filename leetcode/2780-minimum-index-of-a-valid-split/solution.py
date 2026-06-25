from typing import List


class Solution:
    def minimumIndex(self, nums: List[int]) -> int:
        """
        找出最小的有效分割下标 i，使得左右两个子数组拥有相同的支配元素。

        支配元素定义：出现次数超过子数组长度一半的元素。
        题目保证整个数组有且只有一个支配元素。
        """
        n = len(nums)

        # ---------- Step 1: 用 Boyer-Moore 投票算法找出全局支配元素 ----------
        candidate = None
        count = 0
        for num in nums:
            if count == 0:
                candidate = num
            count += 1 if num == candidate else -1

        # ---------- Step 2: 统计支配元素在整个数组中的出现次数 ----------
        total_freq = sum(1 for num in nums if num == candidate)

        # ---------- Step 3: 遍历分割点，检查左右子数组是否都满足支配条件 ----------
        left_freq = 0
        for i in range(n - 1):  # i 最大到 n-2，保证右子数组非空
            if nums[i] == candidate:
                left_freq += 1
            right_freq = total_freq - left_freq

            left_len = i + 1
            right_len = n - i - 1

            # 支配元素在左右两个子数组中都超过一半
            if left_freq * 2 > left_len and right_freq * 2 > right_len:
                return i

        return -1

# 时间复杂度: O(n) — 两次线性扫描
# 空间复杂度: O(1) — 仅使用常数级额外空间


if __name__ == "__main__":
    s = Solution()

    # 测试用例 1
    nums1 = [1, 2, 2, 2]
    assert s.minimumIndex(nums1) == 2, f"Expected 2, got {s.minimumIndex(nums1)}"
    print(f"✅ Test 1 passed: nums={nums1} -> {s.minimumIndex(nums1)}")

    # 测试用例 2
    nums2 = [2, 1, 3, 1, 1, 1, 7, 1, 2, 1]
    assert s.minimumIndex(nums2) == 4, f"Expected 4, got {s.minimumIndex(nums2)}"
    print(f"✅ Test 2 passed: nums={nums2} -> {s.minimumIndex(nums2)}")

    # 测试用例 3
    nums3 = [3, 3, 3, 3, 7, 2, 2]
    assert s.minimumIndex(nums3) == -1, f"Expected -1, got {s.minimumIndex(nums3)}"
    print(f"✅ Test 3 passed: nums={nums3} -> {s.minimumIndex(nums3)}")

    # 额外测试：只有两个元素且支配元素相同
    nums4 = [1, 1]
    assert s.minimumIndex(nums4) == 0, f"Expected 0, got {s.minimumIndex(nums4)}"
    print(f"✅ Test 4 passed: nums={nums4} -> {s.minimumIndex(nums4)}")

    print("\n🎉 所有测试通过！")
