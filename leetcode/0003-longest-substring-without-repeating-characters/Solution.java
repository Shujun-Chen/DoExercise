package leetcode;

/**
 * LeetCode 3. Longest Substring Without Repeating Characters
 * 难度: Medium
 *
 * 题目描述:
 * 给定一个字符串 s，找出其中不含重复字符的最长子串的长度。
 *
 * 示例:
 * 输入: s = "abcabcbb"  → 输出 3 ("abc")
 * 输入: s = "bbbbb"     → 输出 1
 * 输入: s = "pwwkew"    → 输出 3 ("wke")
 */
public class Solution {

    /**
     * 滑动窗口 + 数组模拟哈希表（ASCII 字符集）
     *
     * 思路:
     * - 用 int[128] 记录每个字符最近一次出现的「下一个合法位置」
     * - left 表示窗口左边界（不含）
     * - 每轮遍历 right，把 left 更新为 max(left, lastSeen[ch])
     * - 当前窗口长度 = right - left + 1，更新 maxLen
     *
     * 时间复杂度: O(n)
     * 空间复杂度: O(1) （固定 128 大小的数组，与输入长度无关）
     */
    public int lengthOfLongestSubstring(String s) {
        // lastSeen[i] = 字符 i 下次可作为窗口左边界的位置
        int[] lastSeen = new int[128];
        // 初始化为 0：第 0 个字符之后的位置
        // 实际判断时用 lastSeen[ch] <= left 来判定「不在窗口内」
        // 由于 left 至少从 0 开始，把数组填成 0 即可
        // (用 0 也意味着"上次出现位置在索引 0 之前"，与 left=0 不冲突)

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // 如果字符在窗口内（最近出现位置 >= left），则把 left 推过去
            if (lastSeen[ch] > left) {
                left = lastSeen[ch];
            }
            // 当前字符的「下一个合法位置」= right + 1
            lastSeen[ch] = right + 1;
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        String[][] cases = {
            {"abcabcbb", "3"},
            {"bbbbb",    "1"},
            {"pwwkew",   "3"},
            {"",         "0"},
            {" ",        "1"},
            {"au",       "2"},
            {"dvdf",     "3"},
        };

        for (String[] c : cases) {
            String s = c[0];
            int expected = Integer.parseInt(c[1]);
            int got = sol.lengthOfLongestSubstring(s);
            String status = (got == expected) ? "✓" : "✗";
            System.out.println(status + " input=" + s + " expected=" + expected + ", got=" + got);
        }
    }
}
