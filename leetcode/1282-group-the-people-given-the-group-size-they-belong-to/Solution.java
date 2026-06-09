import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1282. Group the People Given the Group Size They Belong To
 * Medium
 *
 * 题目：给定 n 个人，每个人有一个组大小 groupSizes[i]，表示该人必须在大小为 groupSizes[i] 的组中。
 * 要求：返回所有组的列表，使得每个人恰好出现在一个大小正确的组中。
 *
 * 解题思路：
 * 1. 使用 HashMap 按组大小分类收集人员
 * 2. 当某个组大小的人员数量达到该大小时，形成一个组
 * 3. 重复此过程直到所有人分配完毕
 *
 * 时间复杂度：O(n) - 遍历数组一次
 * 空间复杂度：O(n) - HashMap 存储分组信息
 */
class Solution {
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        // HashMap：key = 组大小，value = 当前收集的人员列表
        Map<Integer, List<Integer>> sizeToPeople = new HashMap<>();
        List<List<Integer>> result = new ArrayList<>();
        
        for (int personId = 0; personId < groupSizes.length; personId++) {
            int groupSize = groupSizes[personId];
            
            // 获取或创建该组大小的人员列表
            sizeToPeople.putIfAbsent(groupSize, new ArrayList<>());
            List<Integer> people = sizeToPeople.get(groupSize);
            people.add(personId);
            
            // 当桶满时，形成一个组并重置桶
            if (people.size() == groupSize) {
                result.add(people);
                sizeToPeople.put(groupSize, new ArrayList<>());
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // 测试 1: [3,3,3,3,3,1,3] -> [[5],[0,1,2],[3,4,6]]
        int[] groupSizes1 = {3, 3, 3, 3, 3, 1, 3};
        List<List<Integer>> result1 = solution.groupThePeople(groupSizes1);
        System.out.println("Input:  [3,3,3,3,3,1,3]");
        System.out.println("Output: " + result1);
        System.out.println();
        
        // 测试 2: [2,1,3,3,3,2] -> [[1],[0,5],[2,3,4]]
        int[] groupSizes2 = {2, 1, 3, 3, 3, 2};
        List<List<Integer>> result2 = solution.groupThePeople(groupSizes2);
        System.out.println("Input:  [2,1,3,3,3,2]");
        System.out.println("Output: " + result2);
        System.out.println();
        
        // 测试 3: [1,1] -> [[0],[1]]
        int[] groupSizes3 = {1, 1};
        List<List<Integer>> result3 = solution.groupThePeople(groupSizes3);
        System.out.println("Input:  [1,1]");
        System.out.println("Output: " + result3);
    }
}
