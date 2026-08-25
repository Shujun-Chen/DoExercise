package leetcode;

/**
 * LeetCode 5. Longest Palindromic Substring
 * https://leetcode.com/problems/longest-palindromic-substring/
 *
 * 难度: Medium
 * 标签: String, Dynamic Programming
 *
 * 题目描述:
 *     给定一个字符串 s,返回 s 中最长的回文子串。
 *
 * 约束:
 *     - 1 <= s.length <= 1000
 *     - s 仅由数字和英文字母组成
 *
 * 示例:
 *     Input: s = "babad"
 *     Output: "bab"  (或 "aba" 也是合法答案)
 *
 *     Input: s = "cbbd"
 *     Output: "bb"
 *
 * 思路:
 *     中心扩展法(Expand Around Center):
 *     回文串的对称轴可以是某个字符(奇数长度),也可以是某两个字符之间(偶数长度)。
 *     一共有 2n-1 个潜在中心。对每个中心向两边扩展,记录最长回文。
 *
 * 时间复杂度: O(n^2) —— 每个中心最多扩展 O(n),共 2n-1 个中心
 * 空间复杂度: O(1)   —— 只用常数额外空间(不计返回值)
 */
public class Solution {

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) {
            // 0 或 1 字符本身就是回文
            return s;
        }

        // 记录最优解的起止下标(含左不含右)
        int bestStart = 0;
        int bestEnd = 1;

        // 枚举所有潜在中心
        for (int i = 0; i < n; i++) {
            // 奇数长度回文:中心是单个字符 s[i]
            int[] odd = expand(s, i, i);
            if (odd[1] - odd[0] > bestEnd - bestStart) {
                bestStart = odd[0];
                bestEnd = odd[1];
            }
            // 偶数长度回文:中心是 s[i] 与 s[i+1] 之间
            int[] even = expand(s, i, i + 1);
            if (even[1] - even[0] > bestEnd - bestStart) {
                bestStart = even[0];
                bestEnd = even[1];
            }
        }

        return s.substring(bestStart, bestEnd);
    }

    /**
     * 以 (left, right) 为初始中心向外扩展,返回最长回文的 [start, end)。
     * 循环结束时 left/right 已越界,真实边界是 [left+1, right)。
     */
    private int[] expand(String s, int left, int right) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return new int[]{left + 1, right};
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        assert sol.longestPalindrome("babad").equals("bab")
                || sol.longestPalindrome("babad").equals("aba");
        assert sol.longestPalindrome("cbbd").equals("bb");

        assert sol.longestPalindrome("a").equals("a");
        assert sol.longestPalindrome("aa").equals("aa");
        assert sol.longestPalindrome("ab").equals("a")
                || sol.longestPalindrome("ab").equals("b");

        assert sol.longestPalindrome("aaaa").equals("aaaa");
        assert sol.longestPalindrome("racecar").equals("racecar");
        assert sol.longestPalindrome("abccba").equals("abccba");
        String nested = sol.longestPalindrome("abacdfgdcaba");
        assert nested.equals("aba") || nested.equals("aca") : nested;

        System.out.println("All tests passed.");
    }
}