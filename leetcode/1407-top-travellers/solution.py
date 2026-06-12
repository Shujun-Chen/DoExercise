#!/usr/bin/env python3
"""
1407. Top Travellers

解题思路：
1. 使用 LEFT JOIN 连接 Users 和 Rides 表
2. 按用户分组，计算每个用户的总行程距离
3. 使用 COALESCE 处理没有行程的用户（距离为0）
4. 按距离降序、名字升序排序

SQL 解法：
SELECT 
    u.name,
    COALESCE(SUM(r.distance), 0) AS travelled_distance
FROM Users u
LEFT JOIN Rides r ON u.id = r.user_id
GROUP BY u.id, u.name
ORDER BY travelled_distance DESC, u.name ASC;

时间复杂度：O(n log n) - 排序操作
空间复杂度：O(n) - 存储结果
"""

from collections import defaultdict


def top_travellers(users, rides):
    """
    计算每个用户的总行程距离
    
    Args:
        users: List of [id, name] pairs
        rides: List of [id, user_id, distance] triples
    
    Returns:
        List of [name, travelled_distance] pairs, sorted by distance DESC, name ASC
    """
    # 计算每个用户的总行程距离
    user_distance = defaultdict(int)
    for _, user_id, distance in rides:
        user_distance[user_id] += distance
    
    # 构建结果
    result = []
    for user_id, name in users:
        result.append((name, user_distance.get(user_id, 0)))
    
    # 按距离降序、名字升序排序
    result.sort(key=lambda x: (-x[1], x[0]))
    
    return result


if __name__ == "__main__":
    # 测试用例
    users = [
        (1, 'Alice'),
        (2, 'Bob'),
        (3, 'Alex'),
        (4, 'Donald'),
        (7, 'Lee'),
        (13, 'Jonathan'),
        (19, 'Elvis')
    ]
    
    rides = [
        (1, 1, 120),
        (2, 2, 317),
        (3, 3, 222),
        (4, 7, 100),
        (5, 13, 312),
        (6, 19, 50),
        (7, 7, 120),
        (8, 19, 400),
        (9, 7, 230)
    ]
    
    result = top_travellers(users, rides)
    
    # 打印结果
    print(f"{'name':<12} {'travelled_distance':>18}")
    print("-" * 32)
    for name, dist in result:
        print(f"{name:<12} {dist:>18}")
    
    # 验证结果
    expected = [
        ('Elvis', 450),
        ('Lee', 450),
        ('Bob', 317),
        ('Jonathan', 312),
        ('Alex', 222),
        ('Alice', 120),
        ('Donald', 0)
    ]
    
    assert result == expected, f"Expected {expected}, got {result}"
    print("\n✓ 所有测试用例通过")
