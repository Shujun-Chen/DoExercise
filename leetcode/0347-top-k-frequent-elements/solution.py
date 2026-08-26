from typing import List
from collections import Counter


class Solution:
    """
    LeetCode 347 - Top K Frequent Elements

    思路：桶排序 (Bucket Sort)
    1. 先用 Counter 统计每个数字出现的次数
    2. 以「出现频率」作为桶下标，把数字放到对应桶里
       - 频率范围是 [0, n]，所以桶数组长度 = n + 1
       - 每个桶里装的是所有频率等于该下标的数字
    3. 从高频桶往低频桶遍历，累计收集到 k 个数字就返回

    为什么能用桶排序？
       频率的取值有限（0 到 n），最多 n+1 个桶
       每个元素只被放进一个桶里一次，所以总体是 O(n)

    时间复杂度：O(n)，其中 n = len(nums)
    空间复杂度：O(n)
    """

    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        # 1. 统计每个数字的频率
        count = Counter(nums)

        n = len(nums)
        # 2. 建立桶：frequency_buckets[i] 装着所有频率为 i 的数字
        # 长度为 n+1 是因为频率最大就是 n（所有元素都相同）
        frequency_buckets: List[List[int]] = [[] for _ in range(n + 1)]
        for num, freq in count.items():
            frequency_buckets[freq].append(num)

        # 3. 从高到低遍历桶，收集结果
        result: List[int] = []
        # 从最大可能频率 n 开始往前找，先装满高频桶的数字
        for freq in range(n, 0, -1):
            for num in frequency_buckets[freq]:
                result.append(num)
                if len(result) == k:
                    return result

        # 题目保证一定有答案（k ≤ 不同数字个数），走到这行只是兜底
        return result


# ---------------- 测试 ----------------
if __name__ == "__main__":
    sol = Solution()

    # 用例 1：基础
    nums1 = [1, 1, 1, 2, 2, 3]
    k1 = 2
    # 期望 [1, 2]（顺序任意）
    out1 = sorted(sol.topKFrequent(nums1, k1))
    assert out1 == [1, 2], f"case1 failed: {out1}"
    print(f"用例1 通过：nums={nums1}, k={k1} -> {out1}")

    # 用例 2：只有一个不同数字
    nums2 = [1]
    k2 = 1
    out2 = sol.topKFrequent(nums2, k2)
    assert out2 == [1], f"case2 failed: {out2}"
    print(f"用例2 通过：nums={nums2}, k={k2} -> {out2}")

    # 用例 3：所有数字频率相同（k == 不同数字数量）
    nums3 = [4, 4, 4, 5, 5, 5, 6]
    k3 = 3
    out3 = sorted(sol.topKFrequent(nums3, k3))
    assert out3 == [4, 5, 6], f"case3 failed: {out3}"
    print(f"用例3 通过：nums={nums3}, k={k3} -> {out3}")

    # 用例 4：有大量重复
    nums4 = [1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5]
    k4 = 3
    out4 = sorted(sol.topKFrequent(nums4, k4))
    assert out4 == [3, 4, 5], f"case4 failed: {out4}"
    print(f"用例4 通过：nums={nums4}, k={k4} -> {out4}")

    # 用例 5：负数也能处理
    nums5 = [-1, -1, -2, -2, -2, 3]
    k5 = 2
    out5 = sorted(sol.topKFrequent(nums5, k5))
    assert out5 == [-2, -1], f"case5 failed: {out5}"
    print(f"用例5 通过：nums={nums5}, k={k5} -> {out5}")

    print("\n全部用例通过 ✅")
