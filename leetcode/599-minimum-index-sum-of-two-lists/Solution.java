import java.util.*;

class Solution {
    public String[] findRestaurant(String[] list1, String[] list2) {
        // 将 list1 中的字符串及其索引存入哈希表
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            indexMap.put(list1[i], i);
        }

        List<String> result = new ArrayList<>();
        int minSum = Integer.MAX_VALUE;

        // 遍历 list2，查找共同字符串并计算索引和
        for (int j = 0; j < list2.length; j++) {
            String s = list2[j];
            if (indexMap.containsKey(s)) {
                int total = indexMap.get(s) + j;
                // 发现更小的索引和 → 重置结果
                if (total < minSum) {
                    minSum = total;
                    result.clear();
                    result.add(s);
                }
                // 相同的最小索引和 → 追加到结果
                else if (total == minSum) {
                    result.add(s);
                }
            }
        }

        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        String[] list1 = {"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list2 = {"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};
        String[] res1 = sol.findRestaurant(list1, list2);
        assert res1.length == 1 && res1[0].equals("Shogun") : "Test 1 failed";

        // 测试用例 2
        String[] list3 = {"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list4 = {"KFC", "Shogun", "Burger King"};
        String[] res2 = sol.findRestaurant(list3, list4);
        assert res2.length == 1 && res2[0].equals("Shogun") : "Test 2 failed";

        // 测试用例 3：多个最小索引和
        String[] list5 = {"happy", "sad", "good"};
        String[] list6 = {"sad", "happy", "good"};
        String[] res3 = sol.findRestaurant(list5, list6);
        Set<String> expected = new HashSet<>(Arrays.asList("sad", "happy"));
        Set<String> actual = new HashSet<>(Arrays.asList(res3));
        assert expected.equals(actual) : "Test 3 failed";

        System.out.println("All test cases passed!");
    }
}

// 时间复杂度：O(n + m)，其中 n 和 m 分别为 list1 和 list2 的长度
// 空间复杂度：O(n)，哈希表存储 list1 中所有字符串及其索引
