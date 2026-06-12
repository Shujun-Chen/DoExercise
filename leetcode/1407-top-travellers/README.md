# 1407. Top Travellers

**难度：** Easy

## 题目描述

给定两个表：`Users` 和 `Rides`。

**Users 表：**
| Column Name | Type    |
|-------------|---------|
| id          | int     |
| name        | varchar |

`id` 是该表的唯一列。

**Rides 表：**
| Column Name | Type    |
|-------------|---------|
| id          | int     |
| user_id     | int     |
| distance    | int     |

`id` 是该表的唯一列。

编写一个解决方案来报告每个用户旅行的距离。

返回结果表按 `travelled_distance` 降序排序，如果两个或更多用户旅行的距离相同，则按他们的名字升序排序。

## 解题思路

### 方法一：SQL 解法（推荐）

```sql
SELECT 
    u.name,
    COALESCE(SUM(r.distance), 0) AS travelled_distance
FROM Users u
LEFT JOIN Rides r ON u.id = r.user_id
GROUP BY u.id, u.name
ORDER BY travelled_distance DESC, u.name ASC;
```

**关键点：**
1. 使用 `LEFT JOIN` 连接两个表，确保没有行程的用户也被包含
2. 使用 `COALESCE` 函数将 `NULL` 值转换为 0
3. 使用 `GROUP BY` 按用户分组
4. 使用 `ORDER BY` 按距离降序、名字升序排序

### 方法二：Python 解法

使用字典统计每个用户的总行程距离，然后排序。

### 方法三：Java 解法

使用 `HashMap` 统计距离，然后自定义排序。

## 复杂度分析

- **时间复杂度：** O(n log n) - 排序操作，其中 n 是用户数量
- **空间复杂度：** O(n) - 存储结果和中间数据

## Python 解法要点

1. 使用 `defaultdict` 统计每个用户的总行程距离
2. 遍历 `rides` 列表，累加每个用户的距离
3. 构建结果列表，包含用户名和总距离
4. 使用 `sort()` 方法按距离降序、名字升序排序

## Java 解法要点

1. 使用 `HashMap` 统计每个用户的总行程距离
2. 使用 `merge()` 方法简化累加操作
3. 构建结果列表，使用 `Arrays.asList()` 创建每行数据
4. 使用自定义 `Comparator` 实现多条件排序
