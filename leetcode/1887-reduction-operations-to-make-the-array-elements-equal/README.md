# 1887. Reduction Operations to Make the Array Elements Equal

**难度：** Medium

## 题目描述

给定一个整数数组 `nums`,目标是把所有元素都变成同一个值。一次操作按以下三步:

1. 找到 `nums` 中**最大**的值 `largest`,若有多个,取下标最小的 `i`。
2. 找到 `nums` 中**严格小于** `largest` 的**下一个最大**值 `nextLargest`。
3. 把 `nums[i]` 减为 `nextLargest`。

返回把数组所有元素变成相等所需的操作次数。

**约束：** `1 ≤ nums.length ≤ 5·10⁴`,`1 ≤ nums[i] ≤ 5·10⁴`

## 解题思路

关键观察:每次操作只动**当前最大**的元素,把它压到**次大值**。换句话说,排序后,只要"跨过一档"不同的值,前面所有更大值的元素都必然要"被压过这一档"——每压过 1 次算 1 次操作。

把数组按值统计出现次数,然后**按值降序**遍历:

- 设 `processed` 表示"目前已处理(也就是比当前值更大)的元素总数"。
- 第一次进入降序序列时,`processed` = 第一档的元素数。
- 每换一档(即 `values[i] != values[i+1]`),前面所有 `processed` 个元素都得被压到这一档 → 答案加上 `processed`,再把当前档的元素数累加到 `processed`。

示例 `[1,1,2,2,3]`:
- 计数 `{3:1, 2:2, 1:2}` → 降序值序列 `[3, 2, 1]`
- 第 1 档 `3`:`processed = 1`
- 第 2 档 `2`:`ops += 1 → 1`,`processed = 1 + 2 = 3`
- 第 3 档 `1`:`ops += 3 → 4`,`processed = 3 + 2 = 5`
- 答案 = **4** ✓

## 复杂度分析

- **时间复杂度：** O(n log n) (排序)
- **空间复杂度：** O(n) (HashMap 计数);若原地排序数组再 O(1) 额外空间也可

## Python 解法要点

- 用 `collections.Counter` 做计数。
- `sorted(cnt.keys(), reverse=True)` 拿到降序的 distinct 值序列。
- 单次遍历即得答案,逻辑非常清晰,无嵌套循环。

## Java 解法要点

- `Map<Integer, Integer>` 计数,`merge(v, 1, Integer::sum)` 一行搞定累加。
- `Integer[] values = cnt.keySet().toArray(new Integer[0])` 拿到键集合,再 `Arrays.sort(values, (a, b) -> b - a)` 降序。
- 注意 `Arrays.sort(int[])` 没有 lambda 重载,必须先转 `Integer[]`。
