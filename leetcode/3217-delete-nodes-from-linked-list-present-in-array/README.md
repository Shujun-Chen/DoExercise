# 3217. Delete Nodes From Linked List Present in Array

**难度：** Medium

## 题目描述

给定一个整数数组 nums 和链表的头节点 head。返回修改后的链表头节点，删除链表中所有值存在于 nums 中的节点。

## 解题思路

1. **哈希集合预处理**：将 nums 数组中的所有值存入哈希集合，这样查找操作的时间复杂度为 O(1)
2. **虚拟头节点**：使用 dummy 节点作为链表的起始点，这样即使头节点被删除也能正常处理
3. **双指针遍历**：
   - `prev` 指向最后一个确认保留的节点
   - `curr` 遍历链表的每个节点
   - 如果 `curr.val` 在集合中，则删除该节点（`prev.next = curr.next`）
   - 如果不在集合中，则移动 `prev` 到 `curr`
4. **返回结果**：返回 `dummy.next`，即新的头节点

## 复杂度分析

- **时间复杂度：** O(n + m)，其中 n 是链表长度，m 是 nums 数组长度
  - 构建哈希集合需要 O(m)
  - 遍历链表需要 O(n)
- **空间复杂度：** O(m)，哈希集合存储 nums 中的 m 个元素

## Python 解法要点

- 使用 `set()` 构建哈希集合，`in` 操作时间复杂度为 O(1)
- 使用虚拟头节点 `dummy` 简化边界处理
- 单次遍历即可完成删除操作
- 类型注解使用 `Optional[ListNode]` 表示可能返回 None

## Java 解法要点

- 使用 `HashSet<Integer>` 存储 nums 的值
- 使用 `ListNode dummy = new ListNode(0)` 作为虚拟头节点
- 使用 `numSet.contains(curr.val)` 进行 O(1) 查找
- 流式 API `stream().mapToInt()` 用于将链表转为数组进行比较
