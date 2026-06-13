class Solution {
    /**
     * 判断 PAC-MAN 能否逃脱所有鬼的追捕。
     *
     * 核心思路：在曼哈顿距离下，鬼和 PAC-MAN 的最优策略都是沿直线移动。
     * 如果 PAC-MAN 到终点的曼哈顿距离严格小于所有鬼到终点的曼哈顿距离，
     * 则 PAC-MAN 一定能先到达终点。
     *
     * 时间复杂度: O(n)，n 为鬼的数量
     * 空间复杂度: O(1)
     */
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        // PAC-MAN 从 (0,0) 到 target 的曼哈顿距离
        int myDist = Math.abs(target[0]) + Math.abs(target[1]);

        // 检查每个鬼到 target 的曼哈顿距离
        for (int[] ghost : ghosts) {
            int ghostDist = Math.abs(target[0] - ghost[0]) + Math.abs(target[1] - ghost[1]);
            // 如果某个鬼能在 PAC-MAN 到达前（或同时）到达终点，则无法逃脱
            if (ghostDist <= myDist) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 测试用例 1
        assert s.escapeGhosts(new int[][]{{1, 0}, {0, 3}}, new int[]{0, 1}) == true;

        // 测试用例 2
        assert s.escapeGhosts(new int[][]{{1, 0}}, new int[]{2, 0}) == false;

        // 测试用例 3
        assert s.escapeGhosts(new int[][]{{2, 0}}, new int[]{1, 0}) == false;

        System.out.println("所有测试用例通过！");
    }
}
