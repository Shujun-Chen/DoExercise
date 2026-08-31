# 3. Longest Substring Without Repeating Characters

**难度：** Medium

## 题目描述

给定一个字符串 `s`，找出其中**不含重复字符**的最长子串的长度。

示例：
- 输入: `s = "abcabcbb"` → 输出 `3`（"abc"）
- 输入: `s = "bbbbb"` → 输出 `1`
- 输入: `s = "pwwkew"` → 输出 `3`（"wke"）

## 解题思路

**滑动窗口 + 哈希表** 是这道题的标准解法。

核心思想：维护一个左闭右开窗口 `[left, right)`，保证窗口内字符不重复。`right` 一路向右扩展，当新加入的字符与窗口内已有字符重复时，把 `left` 跳到重复字符上一次出现位置的下一位，使窗口重新合法。

具体步骤：
1. 用 `last_seen[char]` 记录字符最近一次出现的索引。
2. 遍历 `right` 从 0 到 n-1：
   - 如果 `s[right]` 已在窗口内（`last_seen[ch] >= left`），把 `left` 推到 `last_seen[ch] + 1`。
   - 取 `max(left, last_seen[ch] + 1)` 是为了避免 `left` 往回跳（如果重复字符其实在窗口外了）。
3. 更新 `last_seen[ch] = right`。
4. 每次循环更新 `max_len = max(max_len, right - left + 1)`。

**关键点**：`left` 的更新要取 `max(left, last_seen[ch] + 1)`，否则会出现窗口回退的 bug，比如 `"abba"` 这种 case。

## 复杂度分析

- **时间复杂度：** O(n) — 每个字符被 `right` 访问一次，`left` 至多前进 n 次。
- **空间复杂度：** O(min(n, |Σ|)) — 哈希表大小，Σ 是字符集（ASCII 128 / 扩展字符集更大）。

## Python 解法要点

- 用字典 `last_seen = {}` 记录字符最近出现的索引。
- `enumerate(s)` 同时拿索引和字符，比手动维护 `right` 计数器更 Pythonic。
- `if ch in last_seen and last_seen[ch] >= left` 双重判断保证 `left` 单调不减。
- 测试覆盖了空串、纯重复、典型滑动窗口 case（`dvdf`）等边界。

## Java 解法要点

- 用 `int[128]` 数组代替哈希表，**O(1) 空间**（固定大小），对 ASCII 输入更快。
- 数组存的是「下一个合法位置」即 `right + 1`，所以比较时用 `lastSeen[ch] > left` 即可。
- 边界条件：空串 `""` 直接返回 0（`for` 循环不执行，`max_len` 保持初始 0）。
- `main` 方法里用 `String[][]` 写测试用例，比 JUnit 轻量，适合题解。
