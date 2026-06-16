/**
 * 2315. Count Asterisks
 *
 * 给你一个字符串 s，其中每两个连续的竖线 '|' 被分组为一对。
 * 返回不在竖线对之间的 '*' 的数量。
 *
 * 解题思路：
 * 遍历字符串，用 bars 变量记录竖线数量。
 * bars % 2 == 0 时在竖线对外部，计数 '*'；否则在内部，跳过。
 *
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */
class Solution {
    public int countAsterisks(String s) {
        int ans = 0;
        int bars = 0;  // 记录已遇到的竖线数量

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '|') {
                bars++;  // 竖线数量 +1，切换内外状态
            } else if (c == '*' && bars % 2 == 0) {
                ans++;  // 在竖线对外部，计数
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例1
        assert sol.countAsterisks("l|*e*et|c**o|*de|") == 2 : "示例1 failed";

        // 示例2
        assert sol.countAsterisks("iamprogrammer") == 0 : "示例2 failed";

        // 示例3
        assert sol.countAsterisks("yo|uar|e**|b|e***au|tifu|l") == 5 : "示例3 failed";

        // 自定义测试: 只有竖线对内部有 *
        assert sol.countAsterisks("*|*|*") == 2 : "自定义1 failed";

        // 自定义测试: 空字符串
        assert sol.countAsterisks("") == 0 : "空字符串 failed";

        // 自定义测试: 只有 *
        assert sol.countAsterisks("***") == 3 : "全星号 failed";

        System.out.println("所有测试用例通过！");
    }
}
