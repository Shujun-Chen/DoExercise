from typing import List


class Solution:
    def escapeGhosts(self, ghosts: List[List[int]], target: List[int]) -> bool:
        """
        判断 PAC-MAN 能否逃脱所有鬼的追捕。

        核心思路：在曼哈顿距离下，鬼和 PAC-MAN 的最优策略都是沿直线移动。
        如果 PAC-MAN 到终点的曼哈顿距离严格小于所有鬼到终点的曼哈顿距离，
        则 PAC-MAN 一定能先到达终点。

        时间复杂度: O(n)，n 为鬼的数量
        空间复杂度: O(1)
        """
        # PAC-MAN 从 (0,0) 到 target 的曼哈顿距离
        my_dist = abs(target[0]) + abs(target[1])

        # 检查每个鬼到 target 的曼哈顿距离
        for gx, gy in ghosts:
            ghost_dist = abs(target[0] - gx) + abs(target[1] - gy)
            # 如果某个鬼能在 PAC-MAN 到达前（或同时）到达终点，则无法逃脱
            if ghost_dist <= my_dist:
                return False

        return True


if __name__ == "__main__":
    s = Solution()

    # 测试用例 1
    assert s.escapeGhosts([[1, 0], [0, 3]], [0, 1]) == True

    # 测试用例 2
    assert s.escapeGhosts([[1, 0]], [2, 0]) == False

    # 测试用例 3
    assert s.escapeGhosts([[2, 0]], [1, 0]) == False

    print("所有测试用例通过！")
