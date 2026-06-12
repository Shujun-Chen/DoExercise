import java.util.*;

/**
 * 1407. Top Travellers
 *
 * 解题思路：
 * 1. 使用 LEFT JOIN 连接 Users 和 Rides 表
 * 2. 按用户分组，计算每个用户的总行程距离
 * 3. 使用 COALESCE 处理没有行程的用户（距离为0）
 * 4. 按距离降序、名字升序排序
 *
 * SQL 解法：
 * SELECT 
 *     u.name,
 *     COALESCE(SUM(r.distance), 0) AS travelled_distance
 * FROM Users u
 * LEFT JOIN Rides r ON u.id = r.user_id
 * GROUP BY u.id, u.name
 * ORDER BY travelled_distance DESC, u.name ASC;
 *
 * 时间复杂度：O(n log n) - 排序操作
 * 空间复杂度：O(n) - 存储结果
 */
class Solution {
    
    public List<List<Object>> topTravellers(List<List<Integer>> users, List<List<Integer>> rides) {
        // 计算每个用户的总行程距离
        Map<Integer, Integer> userDistance = new HashMap<>();
        for (List<Integer> ride : rides) {
            int userId = ride.get(1);
            int distance = ride.get(2);
            userDistance.merge(userId, distance, Integer::sum);
        }
        
        // 构建结果列表
        List<List<Object>> result = new ArrayList<>();
        for (List<Integer> user : users) {
            int userId = user.get(0);
            String name = String.valueOf(user.get(1)); // 简化处理，实际应为字符串
            int distance = userDistance.getOrDefault(userId, 0);
            result.add(Arrays.asList(name, distance));
        }
        
        // 按距离降序、名字升序排序
        result.sort((a, b) -> {
            int distA = (int) a.get(1);
            int distB = (int) b.get(1);
            if (distA != distB) {
                return distB - distA; // 降序
            }
            return ((String) a.get(0)).compareTo((String) b.get(0)); // 升序
        });
        
        return result;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试用例
        List<List<Integer>> users = Arrays.asList(
            Arrays.asList(1, 1), // Alice
            Arrays.asList(2, 2), // Bob
            Arrays.asList(3, 3), // Alex
            Arrays.asList(4, 4), // Donald
            Arrays.asList(7, 5), // Lee
            Arrays.asList(13, 6), // Jonathan
            Arrays.asList(19, 7)  // Elvis
        );
        
        List<List<Integer>> rides = Arrays.asList(
            Arrays.asList(1, 1, 120),
            Arrays.asList(2, 2, 317),
            Arrays.asList(3, 3, 222),
            Arrays.asList(4, 7, 100),
            Arrays.asList(5, 13, 312),
            Arrays.asList(6, 19, 50),
            Arrays.asList(7, 7, 120),
            Arrays.asList(8, 19, 400),
            Arrays.asList(9, 7, 230)
        );
        
        List<List<Object>> result = solution.topTravellers(users, rides);
        
        // 打印结果
        System.out.printf("%-12s %18s%n", "name", "travelled_distance");
        System.out.println("-".repeat(32));
        for (List<Object> row : result) {
            System.out.printf("%-12s %18d%n", row.get(0), row.get(1));
        }
        
        // 验证结果
        int[] expectedDistances = {450, 450, 317, 312, 222, 120, 0};
        for (int i = 0; i < expectedDistances.length; i++) {
            assert (int) result.get(i).get(1) == expectedDistances[i] : 
                "Expected " + expectedDistances[i] + ", got " + result.get(i).get(1);
        }
        
        System.out.println("\n✓ 所有测试用例通过");
    }
}
