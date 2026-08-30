import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 39. Combination Sum
 * https://leetcode.com/problems/combination-sum/
 *
 * 给定无重复元素的整数数组 candidates 和目标 target,
 * 找出所有可以使数字和为目标数 target 的不同组合。
 * 同一个数可以被无限制重复选取。
 */
public class Solution {

    // 结果集:每个内部 List 是一种合法组合
    private final List<List<Integer>> res = new ArrayList<>();
    // 当前正在构造的组合路径
    private final List<Integer> path = new ArrayList<>();
    // 排序后的候选数组(便于剪枝)
    private int[] candidates;

    /**
     * 主入口
     *
     * @param candidates 候选数组(无重复元素)
     * @param target     目标和
     * @return 所有合法组合
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 排序后,DFS 中只要发现 c > remain 就可以直接 break,提升效率
        Arrays.sort(candidates);
        this.candidates = candidates;
        dfs(0, target);
        return res;
    }

    /**
     * 深度优先搜索
     *
     * @param start  本轮搜索从 candidates[start] 开始(避免重复组合)
     * @param remain 还差多少凑出 target
     */
    private void dfs(int start, int remain) {
        // 终止条件 1:remain == 0,凑出 target,记录当前组合
        if (remain == 0) {
            res.add(new ArrayList<>(path)); // 必须拷贝,否则后续 path 变更会污染结果
            return;
        }
        // 终止条件 2:排序后 candidates[start] 已经 > remain,后续只会更大,直接返回
        // 这里 start 可能 == candidates.length,需要先判
        if (start >= candidates.length || remain < candidates[start]) {
            return;
        }
        // 关键循环:从 start 开始尝试每个候选
        for (int i = start; i < candidates.length; i++) {
            int c = candidates[i];
            // 剪枝:c 已经 > remain,后面的更大,直接结束循环
            if (c > remain) {
                break;
            }
            // 做出选择:把 c 加入当前路径
            path.add(c);
            // 递归:注意仍是 i(允许重复使用同一数字),不是 i + 1
            dfs(i, remain - c);
            // 撤销选择(回溯),继续尝试其他分支
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        // 注意:每个 case 必须新建 Solution,因为 res/path 是实例字段

        // 测试 1:官方例 1
        Solution sol1 = new Solution();
        int[] c1 = {2, 3, 6, 7};
        List<List<Integer>> out1 = sol1.combinationSum(c1, 7);
        // 期望:[[2,2,3],[7]]
        sortEach(out1);
        sortOuter(out1);
        assert "[[2, 2, 3], [7]]".equals(out1.toString()) : "test1 failed: " + out1;
        System.out.println("test1 passed: " + out1);

        // 测试 2:官方例 2
        Solution sol2 = new Solution();
        int[] c2 = {2, 3, 5};
        List<List<Integer>> out2 = sol2.combinationSum(c2, 8);
        sortEach(out2);
        sortOuter(out2);
        assert "[[2, 2, 2, 2], [2, 3, 3], [3, 5]]".equals(out2.toString())
                : "test2 failed: " + out2;
        System.out.println("test2 passed: " + out2);

        // 测试 3:target 小于最小候选
        Solution sol3 = new Solution();
        int[] c3 = {2};
        List<List<Integer>> out3 = sol3.combinationSum(c3, 1);
        assert "[]".equals(out3.toString()) : "test3 failed: " + out3;
        System.out.println("test3 passed: " + out3);

        // 测试 4:大目标 + 较大候选(组合较长)
        Solution sol4 = new Solution();
        int[] c4 = {3, 4, 5};
        List<List<Integer>> out4 = sol4.combinationSum(c4, 16);
        // 期望四种组合(无序比较)
        sortEach(out4);
        sortOuter(out4);
        String expected4 = "[[3, 3, 3, 3, 4], [3, 3, 5, 5], [3, 4, 4, 5], [4, 4, 4, 4]]";
        assert expected4.equals(out4.toString()) : "test4 failed: " + out4;
        System.out.println("test4 passed: " + out4);

        System.out.println("\n所有测试通过 ✓");
    }

    /** 对每个内部 List 排序,便于断言比较(组合内顺序无关) */
    private static void sortEach(List<List<Integer>> lists) {
        for (List<Integer> l : lists) {
            l.sort(Integer::compareTo);
        }
    }

    /** 对外层 List 排序(按 toString 比较),便于断言比较 */
    private static void sortOuter(List<List<Integer>> lists) {
        lists.sort((a, b) -> a.toString().compareTo(b.toString()));
    }
}

/*
 * 复杂度分析
 * 时间复杂度:最坏情况下回溯会枚举所有组合,
 *   递归深度最多 target / min(candidates),总状态数与解的数量级相关。
 *   排序 O(N log N);题目保证组合数 < 150,实测性能良好。
 * 空间复杂度:O(target / min(candidates)) — 递归栈 + path 长度。
 */
