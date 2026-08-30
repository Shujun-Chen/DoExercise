# 39. Combination Sum

**难度：** Medium

## 题目描述

给定一个无重复元素的整数数组 `candidates` 和一个目标整数 `target`,找出
`candidates` 中所有可以使数字和为 `target` 的 **不同组合**。同一个数字可以被
**无限制重复选取**。组合可以按任意顺序返回,但组合之间的顺序无关。

示例:
- `candidates = [2,3,6,7]`, `target = 7` → `[[2,2,3],[7]]`
- `candidates = [2,3,5]`, `target = 8` → `[[2,2,2,2],[2,3,3],[3,5]]`
- `candidates = [2]`, `target = 1` → `[]`

数据范围:题目保证合法组合数 < 150,`1 ≤ candidates[i] ≤ 200`。

## 解题思路

本质是「从一组数里挑若干个,凑出目标和,顺序无关」的搜索问题。
**回溯 + 排序剪枝** 是这题的经典模板。

### 第一步:排序

对 `candidates` 升序排序。这一步有两个目的:
1. **剪枝时单调**:从小到大遍历,一旦发现当前候选 `c > remain`,后面所有候选
   都更大,可以直接 `break`,不用再逐个判断。
2. **避免重复组合**(配合下一步的 `start` 索引):保证每个组合里的数是
   **非递减** 的。

### 第二步:DFS 回溯

定义 `dfs(start, remain)`:
- `start`:本轮搜索从 `candidates[start]` 开始(确保组合内顺序非递减,避免
  `[2,3]` 和 `[3,2]` 这种重复);
- `remain`:还差多少凑出 `target`。

退出条件:
- `remain == 0`:找到一个合法组合,把当前路径拷贝一份加入结果;
- `remain < candidates[start]`:因为数组已排序,后续候选只会更大,直接返回。

递归主体(`for i in range(start, len(candidates))`):
- `c = candidates[i]`,若 `c > remain` 则 `break`(剪枝);
- 做选择:`path.append(c)`;
- 递归:`dfs(i, remain - c)`(**注意传 `i` 不是 `i+1`**,因为允许重复使用同一个数);
- 撤销选择:`path.pop()`,继续尝试其他分支。

### 关键点:为什么传 `i` 而不是 `i+1`

允许重复选取 → 选完 `candidates[i]` 后,下一轮仍可以从 `i` 开始挑,而不是 `i+1`。
如果传 `i+1`,就退化成「每个数只能用一次」的版本(那是 40 题 Combination Sum II)。

### 为什么排序能避免重复组合

因为每次递归都从 `start` 开始挑,且 `candidates` 升序,所以一个组合在「选择序列」
里一定是升序的。`[2,2,3]` 只可能出现这一次,不会出现 `2,3,2`、`3,2,2` 等重复枚举。

## 复杂度分析

- **时间复杂度:** 受解的数量主导。设 `N = len(candidates)`, `T = target`,
  `M = min(candidates)`,最坏 `O(N^(T/M))`(每个位置有 N 种选择)。排序额外
  `O(N log N)`。题目保证解 < 150,实测极快。
- **空间复杂度:** `O(T / M)`(递归栈 + 当前路径长度)。

## Python 解法要点

- `path.copy()` 而不是 `path`,因为 `path` 是同一份引用,后续 pop 会污染结果。
- 排序后用 `if c > remain: break`,不要用 `continue` —— 后面只会更大。
- 空数组或者 target=0 的边界:target=0 时一进 `dfs` 就 `remain == 0`,会返回
  `[[]]`(一个空组合),这是符合题意的(零个元素之和为 0)。
- 题目保证 `candidates[i] > 0`,所以不需要担心负数导致的死循环。

## Java 解法要点

- `Arrays.sort(candidates)` 对基本类型数组原地排序。
- `res.add(new ArrayList<>(path))`:必须深拷贝,否则后续 `path.remove` 会污染已
  加入的结果。`new ArrayList<>(path)` 就是路径的拷贝构造。
- 用 `assert` 做单元测试时,**每个测试要 `new Solution()`**,因为 `res` 和
  `path` 是实例字段;`assert` 需要运行时加 `-ea`(示例代码里也是这么做的)。
- 撤销选择用 `path.remove(path.size() - 1)`,等价于栈的 pop。
- 排序 + `start` 索引的写法比「用 HashSet 去重」的写法快得多,后者常数开销很大。

## 拓展:与 Combination Sum II 的区别

| 题目 | 重复使用? | 候选数组 | 去重点 |
|------|----------|---------|--------|
| 39 本题 | 是 | 无重复元素 | 排序 + `start` 索引天然去重 |
| 40 II | 否 | 有重复元素 | 必须 `if i > start && candidates[i] == candidates[i-1]: continue` |

简单记忆:39 题传 `i`,40 题传 `i+1`,前者允许复用、后者不允许。
