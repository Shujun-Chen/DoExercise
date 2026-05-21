# 3010. Divide an Array Into Subarrays With Minimum Cost I

**难度：** Easy

## 题目描述

给定一个长度为 `n` 的整数数组 `nums`。

一个数组的**成本**等于它的第一个元素的值。例如，`[1,2,3]` 的成本是 `1`，而 `[3,4,1]` 的成本是 `3`。

你需要将 `nums` 分成 **3 个互不相交的连续非空子数组**。

返回这些子数组成本之和的**最小值**。

## 解题思路

1. 将数组分割成 3 个非空连续子数组，需要选择两个分割点 i 和 j，其中 0 < i < j < n。
2. 子数组 1 = nums[0..i-1]，成本 = nums[0]
3. 子数组 2 = nums[i..j-1]，成本 = nums[i]
4. 子数组 3 = nums[j..n-1]，成本 = nums[j]
5. 总成本 = nums[0] + nums[i] + nums[j]
6. 由于 nums[0] 固定，只需从 nums[1:] 中选出最小的两个不同位置的元素相加即可。

## 复杂度分析

- **时间复杂度：** O(n log n) — 排序开销
- **空间复杂度：** O(n) — 排序所需额外空间

## Python 解法要点

- 调用 `sorted(nums[1:])` 对剩余元素排序
- 取前两个最小元素与 `nums[0]` 相加即得结果
- 利用 Python 的简洁语法一行完成核心逻辑

## Java 解法要点

- 使用 `Arrays.copyOfRange(nums, 1, n)` 复制子数组
- 使用 `Arrays.sort()` 排序
- 注意 `import java.util.Arrays`
