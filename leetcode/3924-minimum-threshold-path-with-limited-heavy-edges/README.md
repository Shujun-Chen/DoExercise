# 3924. Minimum Threshold Path With Limited Heavy Edges

**难度：** Hard

## 题目描述

给定一个无向带权图，`n` 个节点标号 0 到 n-1，边用 `edges[i] = [u_i, v_i, w_i]` 表示。

定义 threshold：
- **light 边**：权重 ≤ threshold
- **heavy 边**：权重 > threshold

一条从 `source` 到 `target` 的路径是 **valid** 的，当且仅当它包含 **至多** `k` 条 heavy 边。

求满足存在至少一条 valid 路径的 **最小 threshold**。如果不存在这样的路径，返回 -1。

## 解题思路

这是一道「二分答案 + 图论可达性」的经典组合题。

### 核心观察
- threshold 越大，light 边越多，heavy 边越少，路径越容易满足约束
- threshold 越小，heavy 边越多，越难满足约束
- 答案具有单调性：若 threshold = T 可行，则所有 ≥ T 的 threshold 也都可行

### 算法步骤

1. **特判**：如果 source == target，直接返回 0
2. **建图**：构建邻接表，同时记录最大边权
3. **连通性检查**：以 max_weight 为 threshold 运行 0-1 BFS（所有边都是 light），如果仍不可达，返回 -1
4. **二分查找**：在 [0, max_weight] 内二分最小的可行 threshold
   - 每次 check(mid) 用 0-1 BFS 统计从 source 到 target 所需的最少 heavy 边数量
   - 如果 ≤ k，则 mid 可行，缩小右边界
   - 否则扩大左边界

### 0-1 BFS 细节
- heavy 边的代价为 1，light 边的代价为 0
- 使用双端队列（deque）：light 边 push_front，heavy 边 push_back
- 时间复杂度 O(V + E) 每轮，优于 Dijkstra 的 O(E log V)

## 复杂度分析

- **时间复杂度：** O((V + E) * log W)，其中 W = max edge weight，二分 log W 轮，每轮 0-1 BFS O(V + E)
- **空间复杂度：** O(V + E)，邻接表和距离数组

## Python 解法要点

- 用 `deque` 实现 0-1 BFS
- `dist` 数组记录到每个节点所需的**最少 heavy 边数**
- 剪枝：如果 dist[u] > k 直接跳过
- 主函数先用 `reachable(max_weight)` 判断连通性

## Java 解法要点

- 用 `ArrayDeque` 模拟 0-1 BFS
- `int[]` 数组比 `List` 更省内存，适合密集小图
- 注意 `Integer.MAX_VALUE` 初始化距离
- `reachable()` 返回 `boolean`，提前 return true 优化
