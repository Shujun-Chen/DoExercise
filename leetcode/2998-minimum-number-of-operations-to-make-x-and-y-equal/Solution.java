import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 2998. Minimum Number of Operations to Make X and Y Equal
 * 难度: Medium
 *
 * 给定两个正整数 x 和 y，每次操作可以：
 * 1. 如果 x 是 11 的倍数，将 x 除以 11
 * 2. 如果 x 是 5 的倍数，将 x 除以 5
 * 3. 将 x 减 1
 * 4. 将 x 加 1
 *
 * 求使 x 和 y 相等所需的最少操作次数。
 */
class Solution {
    
    private Map<Integer, Integer> memo;
    private int y;
    private int upper;
    
    public int minimumOperationsToMakeEqual(int x, int y) {
        // 如果 y >= x，只能递增
        if (y >= x) {
            return y - x;
        }
        
        this.y = y;
        this.upper = x + (x - y); // 上界
        this.memo = new HashMap<>();
        
        return dfs(x);
    }
    
    /**
     * 记忆化搜索：返回从 val 到 y 的最少操作次数
     */
    private int dfs(int val) {
        // 基本情形
        if (val <= y) {
            return y - val;
        }
        
        // 超过上界，此路不通
        if (val > upper) {
            return Integer.MAX_VALUE / 2;
        }
        
        // 记忆化
        if (memo.containsKey(val)) {
            return memo.get(val);
        }
        
        // 策略1：直接递减到 y
        int ans = val - y;
        
        // 策略2：利用除以 11
        int r = val % 11;
        if (r > 0) {
            // 向下调整到最近的 11 的倍数
            if (val - r > 0) {
                ans = Math.min(ans, r + 1 + dfs((val - r) / 11));
            }
            // 向上调整到最近的 11 的倍数
            ans = Math.min(ans, (11 - r) + 1 + dfs((val + (11 - r)) / 11));
        } else {
            // 已经是 11 的倍数
            ans = Math.min(ans, 1 + dfs(val / 11));
        }
        
        // 策略3：利用除以 5
        r = val % 5;
        if (r > 0) {
            // 向下调整到最近的 5 的倍数
            if (val - r > 0) {
                ans = Math.min(ans, r + 1 + dfs((val - r) / 5));
            }
            // 向上调整到最近的 5 的倍数
            ans = Math.min(ans, (5 - r) + 1 + dfs((val + (5 - r)) / 5));
        } else {
            // 已经是 5 的倍数
            ans = Math.min(ans, 1 + dfs(val / 5));
        }
        
        memo.put(val, ans);
        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        
        // 测试用例
        int[][] testCases = {
            {26, 1, 3},
            {54, 2, 4},
            {25, 30, 5},
            {1, 1, 0},
            {10, 1, 2},
            {100, 1, 4},
            {7, 2, 4},
            {13, 1, 3}
        };
        
        for (int[] tc : testCases) {
            int x = tc[0], y = tc[1], expected = tc[2];
            int result = s.minimumOperationsToMakeEqual(x, y);
            String status = (result == expected) ? "✓" : "✗";
            System.out.println(status + " x=" + x + ", y=" + y + " => " + result + " (expected " + expected + ")");
        }
    }
}

/**
 * 复杂度分析:
 * 时间复杂度: O(U) 其中 U = x + (x - y) 是搜索上界
 * 空间复杂度: O(U) 用于记忆化缓存
 */
