"""
LeetCode 39. Combination Sum
https://leetcode.com/problems/combination-sum/

题目描述:
给定一个无重复元素的整数数组 candidates 和一个目标整数 target,
找出 candidates 中可以使数字和为目标数 target 的所有不同组合。
同一个数字可以被无限制重复选取。组合内元素的顺序无关。
"""
from typing import List


class Solution:
    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        """
        解法:回溯 + 剪枝
        核心思路:对 candidates 排序后,做「选 / 不选」式的 DFS。
        排序的目的:在循环里一旦发现当前累计和 + 候选 > target,
        就可以直接 break,后面的候选更大,更不可能满足。
        """
        # 排序便于剪枝(后面有详细解释)
        candidates.sort()
        res: List[List[int]] = []
        path: List[int] = []

        def dfs(start: int, remain: int) -> None:
            # start: 本次搜索从 candidates[start] 开始选,避免重复组合
            # remain: 还差多少凑出 target
            if remain == 0:
                # 找到一个合法组合,拷贝一份加入结果
                res.append(path.copy())
                return
            # 关键剪枝:remain < candidates[start] 时,再往后只会更大,直接返回
            # (因为数组已排序)
            if remain < candidates[start]:
                return
            for i in range(start, len(candidates)):
                c = candidates[i]
                # 剪枝:加入 c 后超过 target,后面的更大,直接结束本层循环
                if c > remain:
                    break
                # 做选择:把 c 加入路径
                path.append(c)
                # 注意:递归时仍传 i(不是 i+1),允许重复使用同一个数
                dfs(i, remain - c)
                # 撤销选择:回溯到上一层继续尝试其他分支
                path.pop()

        dfs(0, target)
        return res


if __name__ == "__main__":
    sol = Solution()

    # 测试 1:官方例 1
    # candidates = [2,3,6,7], target = 7
    # 期望:[[2,2,3],[7]]
    out1 = sol.combinationSum([2, 3, 6, 7], 7)
    assert sorted(out1) == sorted([[2, 2, 3], [7]]), f"test1 failed: {out1}"
    print(f"test1 passed: {out1}")

    # 测试 2:官方例 2
    # candidates = [2,3,5], target = 8
    # 期望:[[2,2,2,2],[2,3,3],[3,5]]
    out2 = sol.combinationSum([2, 3, 5], 8)
    assert sorted(out2) == sorted([[2, 2, 2, 2], [2, 3, 3], [3, 5]]), f"test2 failed: {out2}"
    print(f"test2 passed: {out2}")

    # 测试 3:官方例 3(target 小于最小候选)
    # candidates = [2], target = 1
    # 期望:[]
    out3 = sol.combinationSum([2], 1)
    assert out3 == [], f"test3 failed: {out3}"
    print(f"test3 passed: {out3}")

    # 测试 4:target = 0
    # 任何 candidates,空组合都满足(和为 0)
    out4 = sol.combinationSum([2, 3, 5], 0)
    assert out4 == [[]], f"test4 failed: {out4}"
    print(f"test4 passed: {out4}")

    # 测试 5:candidates 含较大数,部分组合需要多次重复
    # candidates = [3,4,5], target = 16
    # 期望(无序比较):[[3,3,3,3,4],[3,3,5,5],[4,4,4,4],[3,4,4,5]]
    out5 = sol.combinationSum([3, 4, 5], 16)
    expected5 = sorted([
        [3, 3, 3, 3, 4],
        [3, 3, 5, 5],
        [4, 4, 4, 4],
        [3, 4, 4, 5],
    ])
    assert sorted(out5) == expected5, f"test5 failed: {out5}"
    print(f"test5 passed: {out5}")

    # 测试 6:无重复元素的同一组合不应该被多次收录
    # 比如 candidates=[2,3],target=5 → 只有 [2,3],不应出现两次
    out6 = sol.combinationSum([2, 3], 5)
    assert sorted(out6) == [[2, 3]], f"test6 failed: {out6}"
    print(f"test6 passed: {out6}")

    print("\n所有测试通过 ✓")


# 复杂度分析
# 时间复杂度:O(N^(T/M) + N),其中 N = len(candidates),T = target,
#   M = min(candidates)。最坏情况下每个数都选 M,递归深度 T/M;
#   排序 O(N log N)。
#   题目保证解的总数 < 150,所以实测很快。
# 空间复杂度:O(T/M) — 递归栈和路径长度,最坏 target / min(candidate)。
