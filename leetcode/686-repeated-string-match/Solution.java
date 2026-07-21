import java.util.HashSet;
import java.util.Set;

class Solution {
    /**
     * LeetCode 686. Repeated String Match
     *
     * 将字符串 a 重复若干次，使 b 成为其子串，返回最少重复次数；不可能则返回 -1。
     *
     * 思路：
     * 1. 先用字符集合判断 b 中的字符是否都在 a 里，否则直接返回 -1。
     * 2. 枚举重复次数 k（1 .. n/m + 2），每次拼接 a 后用 indexOf 判断 b 是否出现，
     *    若出现则返回 k。
     *
     * 复杂度：
     * - 时间复杂度：O((n/m + 2) * (n + m))，等价 O(n + m)。
     * - 空间复杂度：O(n + m)，用于存储拼接后的字符串。
     */
    public int repeatedStringMatch(String a, String b) {
        // 把 a 的字符放入集合
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            set.add(a.charAt(i));
        }
        // b 中存在 a 没有的字符时不可能匹配
        for (int i = 0; i < b.length(); i++) {
            if (!set.contains(b.charAt(i))) {
                return -1;
            }
        }

        int m = a.length();
        int n = b.length();
        // n/m + 2 次足够覆盖跨边界的情形
        int repeatTimes = n / m + 2;

        StringBuilder sb = new StringBuilder();
        for (int k = 1; k <= repeatTimes; k++) {
            sb.append(a);
            // indexOf 返回首次出现的位置，-1 表示不存在
            if (sb.indexOf(b) != -1) {
                return k;
            }
        }
        // 兜底：理论上不会到达
        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 官方示例 1
        assert sol.repeatedStringMatch("abcd", "cdabcdab") == 3 : "ex1 failed";
        // 官方示例 2
        assert sol.repeatedStringMatch("a", "aa") == 2 : "ex2 failed";
        // 单字符匹配
        assert sol.repeatedStringMatch("abc", "a") == 1 : "single char failed";
        // 跨边界
        assert sol.repeatedStringMatch("abc", "cab") == 2 : "wrap failed";
        // 不可能的字符
        assert sol.repeatedStringMatch("abc", "d") == -1 : "impossible failed";
        // a 与 b 完全相等
        assert sol.repeatedStringMatch("abcd", "abcd") == 1 : "equal failed";

        System.out.println("All tests passed!");
    }
}