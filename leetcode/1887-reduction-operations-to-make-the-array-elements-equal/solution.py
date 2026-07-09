from typing import List


class Solution:
    """
    LeetCode 1887. Reduction Operations to Make the Array Elements Equal

    思路:
        每次操作把"当前最大的元素"减到"次大的元素"。换言之,排序后,每出现一个
        比前一个严格更小的值,前面所有更大值的元素都要被"压"过这一层——每压
        一次算一次操作,等价于:从大到小遍历,每跨过一档不同的值,当前累计
        已经处理过的元素个数都加到答案里(因为这些元素都会经历这次"压档")。

    步骤:
        1. 对数组排序(升序)。
        2. 从大到小遍历,用一个变量 unique_smaller_so_far 记录"到目前为止
           出现了多少个比当前值更小的不同值"。
        3. 每次 nums[i] != nums[i+1] 时,说明从 i+1 往后所有元素(共
           (i+1) 个)都已经被压过这一层,把这个 (i+1) 加到答案中。
        4. 实际上更简单的写法:从大到小遍历时,只要相邻两个元素不同,就把
           "右侧(更小)区间的大小"累加到答案。可以用去重后统计每个值
           的出现次数,然后按降序遍历:每换一档值,答案加上"该档右侧
           累计的元素个数"。

    复杂度:
        - 时间: O(n log n)   (主要是排序)
        - 空间: O(1) 额外 (排序原地,只维护若干计数变量)
    """

    def reductionOperations(self, nums: List[int]) -> int:
        # 1) 计数每个值出现多少次
        from collections import Counter
        cnt = Counter(nums)
        # 2) 按值从大到小排序
        values = sorted(cnt.keys(), reverse=True)
        # 3) 遍历:右侧累计元素数 = 已处理过(也就是比当前值更大的)元素总数
        ops = 0
        processed = 0  # 已经被"压过档"的元素数(也就是当前值右侧累计)
        for i, v in enumerate(values):
            if i == 0:
                processed = cnt[v]
                continue
            # v 比前一个值小一档,前面所有元素都得被压到这一档
            ops += processed
            processed += cnt[v]
        return ops


if __name__ == "__main__":
    # LeetCode 官方样例
    sol = Solution()
    assert sol.reductionOperations([5, 1, 3]) == 3, "样例1失败"
    assert sol.reductionOperations([1, 1, 1]) == 0, "样例2失败"
    assert sol.reductionOperations([1, 1, 2, 2, 3]) == 4, "样例3失败"
    print("所有官方样例通过 ✅")

    # 额外测试
    assert sol.reductionOperations([2, 1]) == 1        # 2 -> 1
    assert sol.reductionOperations([4, 3, 2, 1]) == 6  # 4→3→2→1
    assert sol.reductionOperations([5, 5, 5, 5]) == 0
    print("额外测试通过 ✅")
