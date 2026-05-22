# 599. Minimum Index Sum of Two Lists

**难度：** Easy

## 题目描述

给定两个字符串数组 `list1` 和 `list2`，找出所有**索引和最小**的**共同字符串**。

**共同字符串**：同时出现在 `list1` 和 `list2` 中的字符串。  
**最小索引和**：若某共同字符串在 `list1[i]` 和 `list2[j]` 中出现，则其索引和为 `i + j`，在所有共同字符串中取最小值。

返回所有满足最小索引和的共同字符串，顺序不限。

**示例 1：**
```
输入：list1 = ["Shogun","Tapioca Express","Burger King","KFC"]
     list2 = ["Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"]
输出：["Shogun"]
```

**示例 2：**
```
输入：list1 = ["Shogun","Tapioca Express","Burger King","KFC"]
     list2 = ["KFC","Shogun","Burger King"]
输出：["Shogun"]
解释：共同字符串中最小的索引和为 "Shogun" 的 0+1=1。
```

**示例 3：**
```
输入：list1 = ["happy","sad","good"]
     list2 = ["sad","happy","good"]
输出：["sad","happy"]
解释："happy" 索引和 = 0+1=1, "sad" 索引和 = 1+0=1, "good" 索引和 = 2+2=4。
```

## 解题思路

1. **哈希表映射**：遍历 `list1`，将其每个字符串及其索引存入哈希表。
2. **遍历查找**：遍历 `list2`，对每个元素检查是否在哈希表中。
3. **维护最小值**：若遇到共同字符串，计算 `索引和 = map[s] + j`：
   - 若小于当前最小值 → 重置结果列表，更新最小值
   - 若等于当前最小值 → 追加到结果列表
4. 最终返回结果列表。

由于题目保证至少有一个共同字符串，无需处理空结果的情况。

## 复杂度分析

- **时间复杂度：** O(n + m)，其中 n 和 m 分别为 `list1` 和 `list2` 的长度。一次遍历建立哈希表，一次遍历查找共同字符串。
- **空间复杂度：** O(n)，哈希表需要存储 `list1` 中所有字符串的索引。

## Python 解法要点

- 使用 `dict` 建立字符串到索引的映射
- 用 `float('inf')` 初始化最小值
- 结果以列表形式返回

## Java 解法要点

- 使用 `HashMap<String, Integer>` 建立映射
- 用 `Integer.MAX_VALUE` 初始化最小值
- 结果先用 `ArrayList` 收集，最后通过 `toArray(new String[0])` 转换
- 需要 `import java.util.*`
