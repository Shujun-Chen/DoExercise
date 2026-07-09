import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {

    /**
     * LeetCode 1887. Reduction Operations to Make the Array Elements Equal
     *
     * 思路:
     *   每次操作把"当前最大元素"压到"次大值"。把数组排序后,每跨过一档
     *   不同的值,前面累计的(比当前值更大的)所有元素都得经历这次"压档",
     *   因此把"右侧已处理元素数"累加到答案中即可。
     *
     * 步骤:
     *   1) 用 HashMap 统计每个值出现次数,再按值降序排序。
     *   2) 遍历:变量 processed 表示"已经被压过档的元素数"(初始为第一档
     *      的元素个数);换档时,累加 processed 到 ops,然后把当前档
     *      的元素数加到 processed 上。
     *
     * 复杂度:
     *   - 时间: O(n log n)  (排序)
     *   - 空间: O(n)        (HashMap 计数)
     */
    public int reductionOperations(int[] nums) {
        // 1) 统计每个值的出现次数
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        // 2) 取出所有 distinct 值并按降序排序
        Integer[] values = cnt.keySet().toArray(new Integer[0]);
        Arrays.sort(values, (a, b) -> b - a);

        // 3) 遍历:换档时累加"右侧已处理元素数"
        int ops = 0;
        int processed = 0;
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                processed = cnt.get(values[i]);
                continue;
            }
            ops += processed;          // 前面所有元素都要被压到这一档
            processed += cnt.get(values[i]);
        }
        return ops;
    }

    /** LeetCode 给的方法签名,有些环境要求严格一致 */
    public static int solve(int[] nums) {
        return new Solution().reductionOperations(nums);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 官方样例
        int[][] tests = {
            {5, 1, 3},          // -> 3
            {1, 1, 1},          // -> 0
            {1, 1, 2, 2, 3},    // -> 4
            {2, 1},             // -> 1
            {4, 3, 2, 1},       // -> 6
            {5, 5, 5, 5},       // -> 0
        };
        int[] expected = {3, 0, 4, 1, 6, 0};

        for (int i = 0; i < tests.length; i++) {
            int got = sol.reductionOperations(tests[i]);
            boolean ok = (got == expected[i]);
            System.out.printf("样例 %d: nums=%s -> %d (expected %d) %s%n",
                    i + 1, Arrays.toString(tests[i]), got, expected[i],
                    ok ? "✅" : "❌");
            if (!ok) {
                System.err.println("测试失败!");
                System.exit(1);
            }
        }
        System.out.println("所有测试通过 ✅");
    }
}
