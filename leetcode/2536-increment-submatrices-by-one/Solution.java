import java.util.Arrays;

/**
 * LeetCode 2536. Increment Submatrices by One
 * 难度：Medium
 *
 * 解题思路（二维差分数组）：
 * 朴素做法对每个查询遍历子矩阵是 O(n²*q)。这里用二维差分数组优化到 O(n² + q)：
 *   1. 创建 (n+1) x (n+1) 的差分数组 diff。
 *   2. 对每个查询 [r1, c1, r2, c2]，在差分数组上四个点打标记:
 *        diff[r1][c1]     += 1
 *        diff[r1][c2+1]   -= 1   // 注意边界：c2+1 < n+1
 *        diff[r2+1][c1]   -= 1   // 注意边界：r2+1 < n+1
 *        diff[r2+1][c2+1] += 1   // 注意边界
 *   3. 二维前缀和还原：对每一行先做一次前缀和，再对每一列做一次前缀和。
 *
 * 时间复杂度：O(n² + q)
 * 空间复杂度：O(n²)
 */
public class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 创建 (n+1) x (n+1) 的差分数组
        // 多出一行一列的目的：当 r2+1 == n 或 c2+1 == n 时不会越界
        int[][] diff = new int[n + 1][n + 1];

        // 对每个查询在差分数组上"打标记"
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            diff[r1][c1] += 1;
            diff[r1][c2 + 1] -= 1;       // 左闭右开区间标记上界
            diff[r2 + 1][c1] -= 1;       // 左闭右开区间标记下界
            diff[r2 + 1][c2 + 1] += 1;   // 右下角的补偿项
        }

        // 先做行方向的前缀和（即对每一行从左到右累加）
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                diff[i][j] += diff[i][j - 1];
            }
        }

        // 再做列方向的前缀和（即对每一列从上到下累加）
        for (int j = 0; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                diff[i][j] += diff[i - 1][j];
            }
        }

        // 取前 n x n 部分即为最终矩阵
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = diff[i][j];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1
        int n1 = 3;
        int[][] q1 = {{1, 1, 2, 2}, {0, 0, 1, 1}};
        int[][] r1 = sol.rangeAddQueries(n1, q1);
        int[][] e1 = {{1, 1, 0}, {1, 2, 1}, {0, 1, 1}};
        System.out.println("Test 1: " + Arrays.deepEquals(r1, e1) + ", result=" + Arrays.deepToString(r1));

        // 测试用例 2
        int n2 = 2;
        int[][] q2 = {{0, 0, 1, 1}};
        int[][] r2 = sol.rangeAddQueries(n2, q2);
        int[][] e2 = {{1, 1}, {1, 1}};
        System.out.println("Test 2: " + Arrays.deepEquals(r2, e2) + ", result=" + Arrays.deepToString(r2));

        // 测试用例 3：整个矩阵被增量两次
        int n3 = 3;
        int[][] q3 = {{0, 0, 2, 2}, {0, 0, 2, 2}};
        int[][] r3 = sol.rangeAddQueries(n3, q3);
        int[][] e3 = {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};
        System.out.println("Test 3: " + Arrays.deepEquals(r3, e3) + ", result=" + Arrays.deepToString(r3));

        // 测试用例 4：n=1 单点查询
        int n4 = 1;
        int[][] q4 = {{0, 0, 0, 0}};
        int[][] r4 = sol.rangeAddQueries(n4, q4);
        int[][] e4 = {{1}};
        System.out.println("Test 4: " + Arrays.deepEquals(r4, e4) + ", result=" + Arrays.deepToString(r4));
    }
}
