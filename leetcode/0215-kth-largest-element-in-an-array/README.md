# 215. Kth Largest Element in an Array

**难度：** Medium

## 题目描述
给定整数数组 `nums` 和整数 `k`，返回数组中**第 k 大的元素**。注意按排序顺序，不要求 distinct（即允许重复）。要求在线性时间内求解（实际上题目只要求"比 O(n log n) 排序更好"，并不是严格平均 O(n)）。

## 解题思路

把"第 k 大"换算成"升序后下标 n−k"，就能用快排派生的**快选（Quickselect）**算法：

1. 选定一个 pivot，把数组原地 partition 成左（< pivot）、中（= pivot）、右（> pivot）三段。
2. pivot 落到的下标 `p`：
   - `p == target` → 直接返回 `nums[p]`
   - `p < target` → 第 k 大在右半边，递归右半
   - `p > target` → 在左半边，递归左半
3. 每次 partition 把搜索范围砍掉一段，平均递归深度 log n，总比较次数 O(n)，所以平均 O(n)。最坏 O(n²)（每次 pivot 选到极值）。加**随机化 pivot** 把最坏概率压到忽略不计。

### 为什么不在 Python 里直接用 sort？
`nums.sort()` 是 O(n log n)，够用但没体现"算法功底"。快选才是 LeetCode 想看的解法之一，且能讲清楚"为什么快排可以用来找第 k 大"。

### 易错点
- **目标下标是 `n - k`**，不是 `k`。k=1（最大）→ target=n-1；k=n（最小）→ target=0。
- **partition 后要立即把 pivot 换回 store 位**，否则 store 处的值不是 pivot，递归时逻辑会错。
- Java 端 `package` 声明如果与目录不一致，运行时会 `NoClassDefFoundError`。这里题号目录与 `package leetcode` 不匹配，所以去掉了 package 声明。

## 复杂度分析
- **时间复杂度：** 平均 O(n)，最坏 O(n²)（已用随机化兜底）。
- **空间复杂度：** O(1)，原地 partition。

## Python 解法要点
- 用 `random.randint(left, right)` 选 pivot，写法比 `randint(left, right+1)` 不带 +1 更直观（标准库语义就是 [a, b]）。
- 用闭包 `quickselect(left, right)` 递归，省掉把 target 当参数传来传去。
- `store` 表示"已经放好 < pivot 元素的右边界"，循环结束后 `store` 就是 pivot 的最终位置。

## Java 解法要点
- `left + rand.nextInt(right - left + 1)` 等价于 Python 的 `randint(left, right)`。
- Java 没有元组赋值，必须写 `swap` 方法。
- `nextInt(int)` 接受正整数（0 到 bound-1），所以传 `right - left + 1`，不要传 0（会抛 `IllegalArgumentException`）。
- 递归签名要带 `target`，因为 Java 不像 Python 闭包可以捕获外层变量。