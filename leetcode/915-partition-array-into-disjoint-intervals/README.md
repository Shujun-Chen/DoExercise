# 915. Partition Array into Disjoint Intervals

**难度：** Medium

## 题目描述

给定一个整数数组 `nums`,将其划分为两个**连续的**子数组 `left` 和 `right`,要求满足:

1. `left` 中的所有元素均小于等于 `right` 中的所有元素。
2. `left` 和 `right` 均非空。
3. `left` 的长度尽可能小。

返回划分后 `left` 的长度。题目保证划分一定存在。

**示例 1:**
```
输入:nums = [5,0,3,8,6]
输出:3
解释:left = [5,0,3], right = [8,6]
```

**示例 2:**
```
输入:nums = [1,1,1,0,6,12]
输出:4
解释:left = [1,1,1,0], right = [6,12]
```

**约束:**
- `2 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^6`

## 解题思路

这道题要求的是「最短合法 left」。

我们先有一个朴素思路:维护 `left_max`(已经确定的 left 部分的最大值)与 `partition`(left 的右端点+1,即 right 的起点),从左往右扫描。
- 若当前元素 `x >= left_max`,那它放进 right 是合法的(因为 right 的最小值只要 ≥ left_max 即可,x 不会破坏 right 内部关系)。
- 但如果 `x < left_max`,那 `x` 必须放进 left,否则 `x` 出现在 right 会违反 `left<=right` 的全局约束。此时 partition 需要扩充。

这里有一个关键细节:**right 部分内部的元素不受 left_max 约束**——只要 right 整体最小值 ≥ left_max 即可。所以我们还需要一个 `candidate_max` 来暂存 right 中扫过的最大值。一旦因为遇到必须归入 left 的元素而扩展 left 时,`left_max` 要用此时 right 中出现过的最大值来更新(因为这些值在扩展后会变成 left 的新最大值)。

具体步骤:
1. `left_max = candidate_max = nums[0]`,`partition = 1`。
2. 从 i=1 开始遍历:
   - 若 `nums[i] < left_max`:必须把当前位置并入 left,`partition = i + 1`,并把 `left_max` 更新为 `candidate_max`(吸纳此前 right 区间的最大值)。
   - 否则(`nums[i] >= left_max`):更新 `candidate_max = max(candidate_max, nums[i])`,暂存 right 中出现的最大值。
3. 最终 `partition` 即为 left 的长度。

整个过程一次遍历、O(1) 额外空间,非常高效。

## 复杂度分析

- **时间复杂度:** O(n) —— 只需一次遍历数组。
- **空间复杂度:** O(1) —— 仅维护若干常数级变量。

## Python 解法要点

- 使用 Python 内置 `List` 即可,无需额外数据结构。
- `if x < left_max` 中必须**严格小于**时才扩展;等于的情况属于合法放进 right。
- `elif x > candidate_max` 只在新元素更大时更新,保证 `candidate_max` 始终是 partition 之后位置上的最大值,扩展时无需再遍历 right 部分。
- 写成 `if x < left_max:` 后接 `elif`,可以让 `else` 部分(等于的情况)无需任何操作,逻辑自然跳过。

## Java 解法要点

- Java 解法与 Python 几乎一致,只是类型为 `int[]` 和 `int`。
- `partition` 直接保存 left 的长度,避免每次用 `i - leftEndIndex + 1` 的额外换算。
- 由于比较是整数,可以避免浮点 / 装箱问题,直接 `>` `<` 比较即可。
- 程序入口 `main` 中覆盖了多个边界用例:正序、逆序、全相等,以验证扩展 / 不扩展两种分支。
