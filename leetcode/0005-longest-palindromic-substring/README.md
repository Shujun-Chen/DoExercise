# 5. Longest Palindromic Substring

**难度:** Medium

**标签:** String, Dynamic Programming

## 题目描述

给定一个字符串 `s`,返回 `s` 中最长的回文子串。

**约束:**
- `1 <= s.length <= 1000`
- `s` 仅由数字和英文字母组成

**示例 1:**
```
Input:  s = "babad"
Output: "bab"
```
注:`"aba"` 同样是合法答案。

**示例 2:**
```
Input:  s = "cbbd"
Output: "bb"
```

## 解题思路

用**中心扩展法**(Expand Around Center)。

回文串的对称轴可以是某一个字符(奇数长度),也可以是某两个相邻字符之间(偶数长度)。一条长度为 n 的字符串一共有 `2n - 1` 个潜在对称轴。对每个轴向两边试探扩展,直到越界或两端字符不等。

具体步骤:

1. 用 `best_start`、`best_end` 维护当前最优回文的起止下标(`[start, end)`)。
2. 遍历每个位置 `i`,分别以 `s[i]` 为中心(奇数)与 `s[i..i+1]` 之间为中心(偶数)做扩展。
3. 扩展函数 `expand(left, right)`:只要 `left >= 0`、`right < n` 且两端字符相等,就继续外扩;循环结束时 `[left+1, right)` 就是该中心能找到的最长回文。
4. 每次拿到新回文与当前最优比较长度,更长就更新 `best_start/best_end`。
5. 最后切片 `s[best_start:best_end]` 返回。

为什么是对称的?回文从中心往外,只要两端仍匹配就仍是回文;一遇不匹配或越界就停。枚举所有中心等价于枚举了所有可能的回文(奇偶都覆盖到)。

## 复杂度分析

- **时间复杂度:** `O(n²)` —— `2n - 1` 个中心,每个中心最多扩展 `O(n)`。
- **空间复杂度:** `O(1)` —— 只用常数额外变量(不计返回的子串)。

> 另解:Manacher 算法可降到 `O(n)`,但本题 `n <= 1000` 用不到;动态规划 `O(n²)` 时间、`O(n²)` 空间,在这里不划算。

## Python 解法要点

- 用闭包 `expand` 复用逻辑,但让它**返回** `(start, end)` 而不是去修改外层的 `best_start/best_end`,避免 `nonlocal` 噪音。
- 切片用 Python 的 `s[best_start:best_end]`,半开区间写起来很顺手。
- 边界:`n < 2` 直接返回原串,避免下面 `i+1` 在空串上越界。
- 测试用 `assert`,允许回文解不唯一的情况用 `in (...)` 或 `(s == a or s == b)` 处理。

## Java 解法要点

- `expand` 返回 `int[]`,Java 不支持 `tuple`,用数组最轻量。
- 主循环里两次调用 `expand` 后用 `if (len > bestLen) update` 更新最优,逻辑跟 Python 一致。
- 本地环境没装 JRE,没法跑 `main`;提交前在 LeetCode 编译器里测过通过。