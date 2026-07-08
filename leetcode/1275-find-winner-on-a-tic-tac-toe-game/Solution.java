import java.util.List;

/**
 * LeetCode 1275. Find Winner on a Tic Tac Toe Game
 *
 * 难度: Easy
 * 标签: Array, Hash Table, Matrix, Simulation
 *
 * 题目描述:
 * A 和 B 在 3x3 的棋盘上玩井字棋,A 先手放 'X',B 放 'O'。
 * 给定一个 moves 列表,moves[i] = [row, col] 表示第 i 步落在哪个格子。
 * 返回最终结果:
 *   - "A"      表示 A 赢
 *   - "B"      表示 B 赢
 *   - "Draw"   表示平局
 *   - "Pending" 表示还有步可走
 *
 * 思路:
 * 维护一个 3x3 的 char 数组模拟棋盘。遍历 moves:
 *   - 偶数下标 → A 落 'X',奇数下标 → B 落 'O'。
 *   - 每落一子立即判断当前玩家是否连成三子(行/列/对角线)。
 *   - 若有人赢直接返回其字母。
 *   - 否则看走子总数:9 步 → "Draw",否则 → "Pending"。
 *
 * 时间复杂度: O(n),n ≤ 9,每步检查 8 条线
 * 空间复杂度: O(1),固定 3x3 棋盘
 */
class Solution {

    public String tictactoe(int[][] moves) {
        // 3x3 棋盘,初始为空格
        char[][] grid = new char[3][3];

        // 判断 ch 是否连成三子
        for (int i = 0; i < moves.length; i++) {
            int r = moves[i][0];
            int c = moves[i][1];
            char ch = (i % 2 == 0) ? 'X' : 'O';
            grid[r][c] = ch;

            // 行
            boolean win = false;
            for (int rr = 0; rr < 3 && !win; rr++) {
                if (grid[rr][0] == ch && grid[rr][1] == ch && grid[rr][2] == ch) {
                    win = true;
                }
            }
            // 列
            for (int cc = 0; cc < 3 && !win; cc++) {
                if (grid[0][cc] == ch && grid[1][cc] == ch && grid[2][cc] == ch) {
                    win = true;
                }
            }
            // 主对角线
            if (!win && grid[0][0] == ch && grid[1][1] == ch && grid[2][2] == ch) {
                win = true;
            }
            // 副对角线
            if (!win && grid[0][2] == ch && grid[1][1] == ch && grid[2][0] == ch) {
                win = true;
            }

            if (win) {
                return (ch == 'X') ? "A" : "B";
            }
        }

        // 没分出胜负
        return moves.length == 9 ? "Draw" : "Pending";
    }

    /** 测试入口 */
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 用例 1: A 赢
        int[][] moves1 = {{0,0},{2,0},{1,1},{2,1},{2,2}};
        check(sol, moves1, "A", 1);

        // 用例 2: B 赢
        int[][] moves2 = {{0,0},{1,1},{0,1},{0,2},{1,0},{2,0}};
        check(sol, moves2, "B", 2);

        // 用例 3: 平局
        int[][] moves3 = {{0,0},{1,1},{2,0},{1,0},{1,2},{2,1},{0,1},{0,2},{2,2}};
        check(sol, moves3, "Draw", 3);

        // 用例 4: 棋局未结束
        int[][] moves4 = {{0,0},{1,1}};
        check(sol, moves4, "Pending", 4);

        // 用例 5: A 第 5 步对角线赢
        int[][] moves5 = {{0,0},{0,1},{1,1},{1,0},{2,2}};
        check(sol, moves5, "A", 5);

        System.out.println("All tests passed!");
    }

    private static void check(Solution sol, int[][] moves, String expected, int idx) {
        String got = sol.tictactoe(moves);
        if (!got.equals(expected)) {
            throw new RuntimeException("Test " + idx + " failed: expected " + expected + " got " + got);
        }
        System.out.println("Test " + idx + " passed: " + got);
    }
}
