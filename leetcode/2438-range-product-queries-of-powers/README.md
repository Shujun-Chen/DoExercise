# 2438. Range Product Queries of Powers

**难度：** Medium

## 题目描述

给定一个正整数 `n`，存在一个 **0-indexed** 的数组 `powers`，由**最少数量的 2 的幂**组成，且这些幂的和为 `n`。数组按非递减顺序排序，且形成该数组的方式唯一。

同时给定一个 **0-indexed** 的二维整数数组 `queries`，其中 `queries[i] = [left_i, right_i]`。每个查询需要计算 `powers[left_i] × powers[left_i+1] × ... × powers[right_i]` 的乘积。

返回一个与 `queries` 等长的数组 `answers`，其中 `answers[i]` 是第 i 个查询的答案。由于答案可能很大，每个结果应对 `10^9 + 7` 取模。

**示例 1：**
```
输入：n = 15, queries = [[0,1],[2,2],[0,3]]
输出：[2,4,64]
解释：
n=15 → powers = [1,2,4,8]
第1个查询：powers[0]×powers[1] = 1×2 = 2
第2个查询：powers[2] = 4
第3个查询：powers[0]×powers[1]×powers[2]×powers[3] = 1×2×4×8 = 64
```

**示例 2：**
```
输入：n = 2, queries = [[0,0]]
输出：[2]
解释：
n=2 → powers = [2]
```

## 解题思路

### 第一步：构造 powers 数组

任意正整数 `n` 的二进制表示唯一确定了一组 2 的幂次，这些幂次之和等于 `n`，且数量最少。这正是 `n` 的二进制分解。

例如：
- `n = 15 = 1111₂` → 二进制位 0,1,2,3 均为 1 → `powers = [2⁰, 2¹, 2², 2³] = [1, 2, 4, 8]`
- `n = 2 = 10₂` → 二进制位 1 为 1 → `powers = [2¹] = [2]`

实现方式：不断右移 `n`，同时左移幂次 `p`，当最低位为 1 时记录当前 `p`。

### 第二步：高效计算区间乘积

题目约束中，`queries` 最多可达 10⁵ 条，而 `powers` 数组长度最多约 30（因为 n ≤ 10⁹ < 2³⁰），直接暴力枚举每个查询也是可行的（3×10⁶ 次乘法）。

更优的方案是使用 **前缀积 + 模逆元**，实现 O(1) 查询：

1. 计算前缀积数组 `prefix[i] = powers[0]×...×powers[i-1] mod MOD`
2. 区间 `[left, right]` 的乘积 = `prefix[right+1] / prefix[left] mod MOD`
3. 由于 MOD = 10⁹+7 是质数，可由**费马小定理**用快速幂求逆元：`inv(x) = x^(MOD-2) mod MOD`

### 第三步：模运算

所有乘法在模 `10⁹+7` 下进行，使用 `long` 避免中间溢出。

## 复杂度分析

- **时间复杂度：** O(len(powers) + m·log(MOD))，其中 len(powers) ≤ 30 是二进制中 1 的位数，m 是 queries 数量，log(MOD) ≈ 30 为快速幂的复杂度
- **空间复杂度：** O(len(powers))，用于存储 powers 和前缀积数组

## Python 解法要点

- 用 `while n > 0` 循环分解二进制，同时用 `p <<= 1` 累进 2 的幂次
- 用前缀积 `prefix` 存储累积乘积
- 实现 `mod_pow(a, b)` 快速幂，通过费马小定理计算逆元
- 最终 `prefix[right+1] * mod_pow(prefix[left], MOD-2) % MOD`

## Java 解法要点

- `int[] powers` 预分配 30 的空间，用 `len` 记录实际长度
- 前缀积用 `long[]` 存储避免中间溢出
- 快速幂用 `long` 计算并取模
- 注意 `>>=` 和 `<<=` 与 Python 相同，但 `&` 位运算优先级低于 `==`，需要括号 `(n & 1) == 1`
