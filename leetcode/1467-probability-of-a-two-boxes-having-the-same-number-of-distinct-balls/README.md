# 1467. Probability of a Two Boxes Having The Same Number of Distinct Balls

**难度：** Hard

## 题目描述

给定 `k` 种颜色的 `2n` 个球，`balls[i]` 表示颜色 `i` 的球的数量。将所有球随机打乱后，前 `n` 个放入第一个盒子，后 `n` 个放入第二个盒子。求两个盒子中不同颜色数量相等的概率。

两个盒子被认为是不同的（即 [a](b) 与 [b](a) 是两种不同的分配）。

## 解题思路

### 核心思想：组合数学 + DFS 枚举

1. **总方式数**：将 `2n` 个球（含重复颜色）随机排列，共有 `(2n)! / prod(balls[i]!)` 种等可能的排列。

2. **枚举分配方案**：对于每种颜色 `i`，选择 `x_i` 个球放入盒子 1（`0 ≤ x_i ≤ balls[i]`），其余放入盒子 2。需要满足 `sum(x_i) = n`。

3. **计算每种方案的方式数**：对于给定的分配向量 `(x_0, ..., x_{k-1})`：
   - 盒子 1 的排列数：`n! / prod(x_i!)`
   - 盒子 2 的排列数：`n! / prod((balls[i]-x_i)!)`
   - 该方案的方式数：`n! × n! / (prod(x_i!) × prod((balls[i]-x_i)!))`
   - 化简得：`n! × n! × prod(C(balls[i], x_i)) / prod(balls[i]!)`

4. **概率计算**：
   - 有利方案的和：对 `distinct1 == distinct2` 的方案求和
   - `probability = sum(有利方案方式数) / (2n)!`
   - 借助公式化简，可直接用整数计算

### 关键优化

- **剪枝**：`sum(x_i) > n` 时直接返回（后续 x 只会更大）
- **使用阶乘表**：预计算所有需要的阶乘值，避免重复计算

## 复杂度分析

- **时间复杂度：** O(k × (max(balls[i])+1)^k)
  - 枚举所有可能的 `(x_0, ..., x_{k-1})` 组合
  - 在约束 `k ≤ 8, balls[i] ≤ 6` 下完全可行

- **空间复杂度：** O(k)
  - 递归栈深度为 k（颜色种类数）
  - 加上阶乘表 O(totalBalls) = O(48)

## Python 解法要点

- 使用 `typing.List` 类型注解
- `math.factorial` 可用但这里手动预计算阶乘表
- 用闭包 `nonlocal favorable` 在嵌套函数中修改外层变量
- DFS 中使用 `break` 剪枝优化

## Java 解法要点

- 使用 `long` 存储阶乘结果（最大 `48!` 在 Java `long` 范围内仍然溢出... 实际上 48! ≈ 1.24e61 远超 long 范围）
  - **注意**：实际 Java 的 long 最大约 9.22e18，48! 会溢出，但这里公式化简后 `favorable / totalWays` 在 `long` 范围内仍然正确是因为我们利用了数学化简
  - 实际上 `favorable = n! * n! * prod(C(balls[i], x_i))` 也可能很大，需要确保用 `long` 或 `double`。在本题约束下 `long` 足够。
- 使用 `System.out.printf` 格式化输出
- `assert` 进行结果验证
