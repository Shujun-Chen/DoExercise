import java.util.*;

/**
 * LeetCode 3143. Maximum Points Inside the Square
 * 难度：Medium
 *
 * 题目：给定 2D 数组 points 与字符串 s，points[i] 是坐标，s[i] 是对应 tag。
 * "有效正方形"以原点为中心、边平行于坐标轴，且不包含两个 tag 相同的点。
 * 求能放进某个有效正方形中的点的最大数量。
 *
 * 思路：边长为 L 的正方形覆盖所有满足 max(|x|,|y|) <= L/2 的点。
 * 定义 r = max(|x|,|y|)，把点按 r 升序遍历；r 相同的点必须整体纳入或全不纳入。
 * 组内无重复 tag 且与已纳入 tag 无冲突时纳入；否则停止，答案就是已纳入的点数。
 */
class Solution {

    public int maxPointsInsideSquare(int[][] points, String s) {
        int n = points.length;
        // 用下标数组排序，避免直接重排 int[][]
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;

        // 按 r = max(|x|, |y|) 升序排序
        Arrays.sort(idx, (a, b) -> {
            int ra = chebyshev(points[a]);
            int rb = chebyshev(points[b]);
            return Integer.compare(ra, rb);
        });

        // 已纳入的 tag 集合 + 当前累计点数
        Set<Character> seen = new HashSet<>();
        int count = 0;
        int i = 0;
        while (i < n) {
            int r = chebyshev(points[idx[i]]);
            int j = i;
            List<Character> group = new ArrayList<>();
            // 收集所有 r 相同的点
            while (j < n && chebyshev(points[idx[j]]) == r) {
                group.add(s.charAt(idx[j]));
                j++;
            }
            // 组内是否有重复 tag
            Set<Character> groupSet = new HashSet<>(group);
            if (groupSet.size() != group.size()) {
                break;
            }
            // 与已纳入 tag 是否冲突
            boolean conflict = false;
            for (char c : group) {
                if (seen.contains(c)) {
                    conflict = true;
                    break;
                }
            }
            if (conflict) {
                break;
            }
            // 整体纳入
            seen.addAll(group);
            count += group.size();
            i = j;
        }
        return count;
    }

    // 切比雪夫距离：max(|x|, |y|)
    private int chebyshev(int[] p) {
        return Math.max(Math.abs(p[0]), Math.abs(p[1]));
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 用例 1
        int[][] p1 = {{2, 2}, {-1, -2}, {-4, 4}, {-3, 1}, {3, -3}};
        assert sol.maxPointsInsideSquare(p1, "abdca") == 2 : "case 1";

        // 用例 2
        int[][] p2 = {{1, 1}, {-2, -2}, {-2, 2}};
        assert sol.maxPointsInsideSquare(p2, "abb") == 1 : "case 2";

        // 用例 3
        int[][] p3 = {{1, 1}, {-1, -1}, {2, -2}};
        assert sol.maxPointsInsideSquare(p3, "ccd") == 0 : "case 3";

        // 额外：所有 tag 互异 -> 全部纳入
        int[][] p4 = {{1, 0}, {0, 2}, {-3, 0}};
        assert sol.maxPointsInsideSquare(p4, "xyz") == 3 : "case 4";

        System.out.println("All tests passed.");
    }
}
