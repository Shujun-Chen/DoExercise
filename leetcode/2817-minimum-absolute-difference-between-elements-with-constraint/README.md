# 2817. Minimum Absolute Difference Between Elements With Constraint

**难度：** Medium

## 题目描述

给定一个 0-indexed 整数数组 `nums` 和一个整数 `x`，找到数组中下标之差至少为 `x` 的两个元素之间的最小绝对差。即找到满足 `abs(i - j) >= x` 且 `abs(nums[i] - nums[j])` 最小的两个下标 `i` 和 `j`。

## 解题思路

### 方法：有序集合 + 二分查找

1. 遍历数组，对于每个位置 `i`（从 `x` 开始），将 `nums[i - x]` 插入有序集合中。
2. 有序集合中存储的是所有距离当前位置至少 `x` 个下标的元素。
3. 在有序集合中，利用二分查找（Python 的 `bisect` 或 Java 的 `TreeSet.floor/ceiling`）找到与 `nums[i]` 最接近的值。
4. 维护全局最小差值 `ans`。

**关键点：** 不需要暴力枚举所有满足距离条件的下标对，只需维护一个有序集合并利用二分即可在 `O(log n)` 时间内找到最近邻。

## 复杂度分析
- **时间复杂度：** O(n log n)，遍历数组 O(n)，每次有序集合操作 O(log n)
- **空间复杂度：** O(n)，有序集合最多存储 n 个元素

## Python 解法要点
- 使用 `sortedcontainers.SortedList` 维护有序序列
- `bisect_left` 查找插入位置，检查左右两侧的值

## Java 解法要点
- 使用 `TreeSet` 自带的 `floor()` 和 `ceiling()` 方法直接查找最近邻
- 注意处理返回值为 `null` 的情况
