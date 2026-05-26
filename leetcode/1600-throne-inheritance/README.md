# 1600. Throne Inheritance

**难度：** Medium

## 题目描述

一个王国由国王、他的孩子、孙子等组成。每隔一段时间，家族中会有人去世或新生儿出生。

王国有一个明确的继承顺序，国王是第一位。定义递归函数 `Successor(x, curOrder)`，给定人员 `x` 和当前继承顺序 `curOrder`，返回 `x` 之后的下一个继承者。

实现 `ThroneInheritance` 类：
- `ThroneInheritance(string kingName)` 初始化对象，`kingName` 是国王的名字
- `void birth(string parentName, string childName)` 表示 `parentName` 生下了 `childName`
- `void death(string name)` 表示 `name` 去世了（去世不影响继承顺序，仅将其标记为已故）
- `string[] getInheritanceOrder()` 返回当前继承顺序列表（排除已故人员）

## 解题思路

这道题的继承规则本质上就是**树的深度优先搜索（DFS）前序遍历**：

1. **数据结构设计：**
   - 用哈希表 `children` 存储每个父节点到子节点列表的映射，子节点按出生顺序排列
   - 用集合 `dead` 记录已故人员

2. **核心操作：**
   - `birth(parentName, childName)`：在父节点的子节点列表中追加 childName，时间复杂度 O(1)
   - `death(name)`：将 name 添加到 dead 集合，时间复杂度 O(1)
   - `getInheritanceOrder()`：从国王开始 DFS 前序遍历，遍历时跳过 dead 集合中的人员，时间复杂度 O(N)

3. **关键观察：**
   - 去世不影响继承顺序结构，只是在输出时跳过该人。所以不需要从树中删除节点
   - 继承顺序就是二叉树/多叉树的前序遍历——先父节点，再从左到右遍历子节点

## 复杂度分析

- **时间复杂度：** `birth()` O(1)，`death()` O(1)，`getInheritanceOrder()` O(N)，其中 N 为家族成员总数
- **空间复杂度：** O(N)，用于存储家族树结构和已故人员集合

## Python 解法要点

- 使用 `dict` 存储父子关系，`set` 存储已故人员
- `getInheritanceOrder` 中用嵌套函数 `dfs` 实现前序遍历
- 注意为每个新节点初始化子节点列表，防止 KeyError

## Java 解法要点

- 使用 `HashMap<String, List<String>>` 和 `HashSet<String>`
- 使用 `putIfAbsent` 确保列表初始化
- `getOrDefault` 处理可能不存在的键
- `main` 方法中测试用例与 Python 一致
