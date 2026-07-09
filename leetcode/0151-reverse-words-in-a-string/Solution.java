/**
 * 反转字符串中的单词顺序。
 *
 * 思路（就地算法，满足进阶要求 O(1) 额外空间）：
 * 1. 反转整个字符串
 * 2. 反转每个单词
 * 3. 清理多余空格（去除首尾空格，将连续多个空格缩减为一个）
 *
 * 时间复杂度：O(n)，其中 n 为字符串长度，每个字符被访问常数次
 * 空间复杂度：O(1)，除结果字符串外只使用字符数组（Java 字符串不可变，需转 char[]）
 */
class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;

        // Step 1: 反转整个字符串
        reverse(chars, 0, n - 1);

        // Step 2: 反转每个单词
        reverseEachWord(chars, n);

        // Step 3: 清理多余空格，去除首尾空格和连续空格
        return cleanSpaces(chars, n);
    }

    /** 反转字符数组 [left, right] 区间 */
    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
            left++;
            right--;
        }
    }

    /** 逐个反转每个单词（单词间由空格分隔） */
    private void reverseEachWord(char[] chars, int n) {
        int i = 0;
        while (i < n) {
            // 跳过空格
            while (i < n && chars[i] == ' ') i++;
            int start = i;
            // 找到单词结尾
            while (i < n && chars[i] != ' ') i++;
            int end = i - 1;
            // 反转当前单词
            if (start <= end) {
                reverse(chars, start, end);
            }
        }
    }

    /** 清理多余空格：移除首尾空格，将连续多个空格缩减为一个 */
    private String cleanSpaces(char[] chars, int n) {
        int i = 0; // 写入指针
        int j = 0; // 读取指针

        while (j < n) {
            // 跳过连续空格
            while (j < n && chars[j] == ' ') j++;
            // 复制单词
            while (j < n && chars[j] != ' ') {
                chars[i++] = chars[j++];
            }
            // 跳过单词间的连续空格
            while (j < n && chars[j] == ' ') j++;
            // 如果后面还有单词，插入一个空格
            if (j < n) {
                chars[i++] = ' ';
            }
        }

        return new String(chars, 0, i);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例
        String[][] testCases = {
            {"the sky is blue", "blue is sky the"},
            {"  hello world  ", "world hello"},
            {"a good   example", "example good a"},
            {"  hello   world  ", "world hello"},
            {"single", "single"},
            {"", ""},
            {"   ", ""},
        };

        boolean allPassed = true;
        for (String[] tc : testCases) {
            String input = tc[0];
            String expected = tc[1];
            String result = sol.reverseWords(input);
            boolean pass = result.equals(expected);
            if (!pass) allPassed = false;
            System.out.println((pass ? "✅" : "❌") + " s=\"" + input
                + "\" -> \"" + result + "\" (expected=\"" + expected + "\")");
        }

        System.out.println(allPassed ? "\n✅ All tests passed!" : "\n❌ Some tests failed!");
    }
}
