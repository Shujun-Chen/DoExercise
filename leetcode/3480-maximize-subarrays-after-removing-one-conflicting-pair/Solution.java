import java.util.*;

/**
 * 3480. Maximize Subarrays After Removing One Conflicting Pair
 * 
 * 计算删除一个冲突对后，最多能得到多少个不包含任何冲突对的子数组。
 * 
 * 思路：
 * 1. 确保每对 (a, b) 满足 a < b。
 * 2. 从右向左扫描，维护当前活动冲突对的最小 b 值和次小 b 值。
 * 3. 对每个起始位置 i，有效子数组的右端点必须 < 最小 b 值。
 * 4. 统计每个冲突对作为"瓶颈"时对删除收益的贡献。
 * 5. 总有效子数组数 = 原始总数 + 最佳删除的额外收益。
 * 
 * 时间复杂度: O(n + m)
 * 空间复杂度: O(n + m)
 */
class Solution {
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        int m = conflictingPairs.length;
        
        // 1. 标准化冲突对：确保 a < b
        int[] aArr = new int[m];
        int[] bArr = new int[m];
        for (int i = 0; i < m; i++) {
            int x = conflictingPairs[i][0];
            int y = conflictingPairs[i][1];
            aArr[i] = Math.min(x, y);
            bArr[i] = Math.max(x, y);
        }
        
        // 2. 按 a 值分组：使用邻接表
        List<int[]>[] byA = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            byA[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            byA[aArr[i]].add(new int[]{bArr[i], i});
        }
        
        long total = 0;            // 原始有效子数组总数
        final long INF = n + 5L;
        long minB = INF;           // 当前最小 b
        int minIdx = -1;           // 当前最小 b 对应的冲突对索引
        long secondMinB = INF;     // 当前次小 b
        long[] contrib = new long[m]; // 每个冲突对删除后的收益
        
        // 3. 从右向左扫描位置 i = n, n-1, ..., 1
        for (int i = n; i >= 1; i--) {
            // 添加所有 a == i 的冲突对到活动集合
            for (int[] pair : byA[i]) {
                long b = pair[0];
                int idx = pair[1];
                if (b < minB) {
                    secondMinB = minB;
                    minB = b;
                    minIdx = idx;
                } else if (b < secondMinB) {
                    secondMinB = b;
                }
            }
            
            if (minB == INF) {
                // 没有活动冲突对，所有子数组都有效
                total += n + 1L - i;
            } else {
                // 以 i 为起点的有效子数组数 = minB - i
                total += minB - i;
                // 删除瓶颈冲突对的额外收益
                if (secondMinB == INF) {
                    // 没有其他冲突对，删除后所有子数组都有效
                    contrib[minIdx] += (n + 1L) - minB;
                } else {
                    // 次小 b 成为新瓶颈
                    contrib[minIdx] += secondMinB - minB;
                }
            }
        }
        
        // 4. 取最大收益
        long bestGain = 0;
        for (long c : contrib) {
            bestGain = Math.max(bestGain, c);
        }
        return total + bestGain;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 示例 1
        int n = 4;
        int[][] pairs = {{2, 3}, {1, 4}};
        long result = sol.maxSubarrays(n, pairs);
        System.out.println("示例 1: n=" + n + ", pairs=" + Arrays.deepToString(pairs) + " => " + result + " (期望: 9)");
        assert result == 9 : "示例 1 失败: " + result + " != 9";
        
        // 示例 2
        n = 5;
        pairs = new int[][]{{1, 2}, {2, 5}, {3, 5}};
        result = sol.maxSubarrays(n, pairs);
        System.out.println("示例 2: n=" + n + ", pairs=" + Arrays.deepToString(pairs) + " => " + result + " (期望: 12)");
        assert result == 12 : "示例 2 失败: " + result + " != 12";
        
        // 边界：无冲突对
        n = 3;
        pairs = new int[][]{};
        result = sol.maxSubarrays(n, pairs);
        long expected = 3L * 4 / 2;
        System.out.println("边界 1 (无冲突): n=" + n + " => " + result + " (期望: " + expected + ")");
        assert result == expected : "边界 1 失败: " + result + " != " + expected;
        
        // 边界：单个冲突对
        n = 5;
        pairs = new int[][]{{2, 4}};
        result = sol.maxSubarrays(n, pairs);
        System.out.println("边界 2 (单冲突): n=" + n + ", pairs=" + Arrays.deepToString(pairs) + " => " + result + " (期望: 15)");
        assert result == 15 : "边界 2 失败: " + result + " != 15";
        
        System.out.println("\n全部测试通过!");
    }
}
