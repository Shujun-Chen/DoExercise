// 时间复杂度: O(n) — 遍历每个位置一次
// 空间复杂度: O(1) — 只用常数额外空间

class Solution {
    public int numberOfAlternatingGroups(int[] colors) {
        int n = colors.length;
        int count = 0;
        // 遍历每个位置，检查其与左右邻居是否构成交替组
        for (int i = 0; i < n; i++) {
            int left = colors[(i - 1 + n) % n];
            int mid = colors[i];
            int right = colors[(i + 1) % n];
            // 中间瓦片与左右两侧颜色不同即为交替组
            if (mid != left && mid != right) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 示例 1: 全相同，无交替组
        System.out.println(s.numberOfAlternatingGroups(new int[]{1, 1, 1})); // 0
        // 示例 2: 有 3 个交替组
        System.out.println(s.numberOfAlternatingGroups(new int[]{0, 1, 0, 0, 1})); // 3
        // 边界: 最小长度，只有中间位置交替
        System.out.println(s.numberOfAlternatingGroups(new int[]{0, 1, 0})); // 1
        // 全交替
        System.out.println(s.numberOfAlternatingGroups(new int[]{0, 1, 0, 1})); // 4
    }
}
