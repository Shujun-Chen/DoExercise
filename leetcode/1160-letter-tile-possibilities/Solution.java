import java.util.*;

public class Solution {
    /**
     * 计算所有可能的非空序列数量。
     * 使用回溯法 + 频率统计，避免生成重复序列。
     */
    public int numTilePossibilities(String tiles) {
        // 统计每个字母的出现次数 (A-Z)
        int[] freq = new int[26];
        for (char ch : tiles.toCharArray()) {
            freq[ch - 'A']++;
        }
        return backtrack(freq);
    }

    /**
     * DFS 回溯：选择当前可用的字母，计数后继续递归
     */
    private int backtrack(int[] freq) {
        int total = 0;
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                // 使用当前字母
                freq[i]--;
                // 每用掉一个字母就产生了一个新序列，+1 计数
                total += 1 + backtrack(freq);
                // 回溯，恢复计数
                freq[i]++;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例
        assert sol.numTilePossibilities("AAB") == 8 : "Example 1 failed";
        assert sol.numTilePossibilities("AAABBC") == 188 : "Example 2 failed";
        assert sol.numTilePossibilities("V") == 1 : "Example 3 failed";

        System.out.println("所有测试用例通过！");
    }

    // 复杂度分析
    // 时间复杂度：O(2^n) 或更精确 O(n!)，其中 n = tiles.length
    // 空间复杂度：O(n)，递归栈深度最多为 n
}
