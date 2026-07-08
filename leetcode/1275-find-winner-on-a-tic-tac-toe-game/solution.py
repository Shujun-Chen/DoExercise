"""
LeetCode 1275. Find Winner on a Tic Tac Toe Game

难度: Easy
标签: Array, Hash Table, Matrix, Simulation

题目描述:
A 和 B 在 3x3 的棋盘上玩井字棋,A 先手放 'X',B 放 'O'。
给定一个 moves 数组,moves[i] = [row, col] 表示第 i 步落在哪个格子。
返回最终结果:
- "A"  表示 A 赢
- "B"  表示 B 赢
- "Draw"  表示平局
- "Pending"  表示还有步可走

思路:
模拟每一步落子,然后在每一步之后判断是否有玩家连成三子。
判断胜利的方式:对每行、每列、两条对角线分别检查是否被同一玩家占满。
简化:不必维护整个 3x3 棋盘,只需要 9 个格子的最终归属即可。
但更直观的做法是直接维护一个 3x3 数组,在第 i 步后判断 (i 必须是奇数 → A 下完,
偶数 → B 下完)。如果任何一行/列/对角线被同一字符占满,立即返回赢家。
最后根据走子总数判断是 "Draw"(9 步)还是 "Pending"。

时间复杂度: O(n) — n ≤ 9,每次最多检查 8 条线
空间复杂度: O(1) — 固定 3x3 棋盘
"""

from typing import List


class Solution:
    def tictactoe(self, moves: List[List[int]]) -> str:
        # 3x3 棋盘,初始为空格
        grid = [[" "] * 3 for _ in range(3)]

        def check_winner(ch: str) -> bool:
            """检查是否有玩家用 ch 占满了某行/列/对角线。"""
            # 三行
            for r in range(3):
                if all(grid[r][c] == ch for c in range(3)):
                    return True
            # 三列
            for c in range(3):
                if all(grid[r][c] == ch for r in range(3)):
                    return True
            # 两条对角线
            if all(grid[i][i] == ch for i in range(3)):
                return True
            if all(grid[i][2 - i] == ch for i in range(3)):
                return True
            return False

        # 偶数下标 → A(0,2,4...),奇数下标 → B(1,3,5...)
        for i, (r, c) in enumerate(moves):
            player = "A" if i % 2 == 0 else "B"
            grid[r][c] = "X" if player == "A" else "O"
            # 在下完一步之后立刻判断,这样可以处理提前结束的棋局
            if check_winner("X" if player == "A" else "O"):
                return player

        # 没分出胜负
        return "Draw" if len(moves) == 9 else "Pending"


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1: A 赢
    moves1 = [[0, 0], [2, 0], [1, 1], [2, 1], [2, 2]]
    assert sol.tictactoe(moves1) == "A", f"Test 1 failed: {sol.tictactoe(moves1)}"
    print("Test 1 passed: A wins")

    # 测试用例 2: B 赢
    moves2 = [[0, 0], [1, 1], [0, 1], [0, 2], [1, 0], [2, 0]]
    assert sol.tictactoe(moves2) == "B", f"Test 2 failed: {sol.tictactoe(moves2)}"
    print("Test 2 passed: B wins")

    # 测试用例 3: 平局
    moves3 = [[0, 0], [1, 1], [2, 0], [1, 0], [1, 2], [2, 1], [0, 1], [0, 2], [2, 2]]
    assert sol.tictactoe(moves3) == "Draw", f"Test 3 failed: {sol.tictactoe(moves3)}"
    print("Test 3 passed: Draw")

    # 测试用例 4: 棋局未结束
    moves4 = [[0, 0], [1, 1]]
    assert sol.tictactoe(moves4) == "Pending", f"Test 4 failed: {sol.tictactoe(moves4)}"
    print("Test 4 passed: Pending")

    # 测试用例 5: A 在第 3 步就赢(对角线)
    moves5 = [[0, 0], [0, 1], [1, 1], [1, 0], [2, 2]]
    assert sol.tictactoe(moves5) == "A", f"Test 5 failed: {sol.tictactoe(moves5)}"
    print("Test 5 passed: A diagonal win")

    print("\nAll tests passed!")
