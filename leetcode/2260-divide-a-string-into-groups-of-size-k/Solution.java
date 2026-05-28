import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    /**
     * 将字符串 s 按每 k 个字符一组分割，最后一组不足 k 个字符时用 fill 填充。
     *
     * @param s    输入字符串
     * @param k    每组字符数
     * @param fill 填充字符
     * @return 分组结果字符串数组
     */
    public String[] divideString(String s, int k, char fill) {
        List<String> result = new ArrayList<>();
        int n = s.length();

        for (int i = 0; i < n; i += k) {
            // 取当前段，若超出长度则取到末尾
            int end = Math.min(i + k, n);
            String part = s.substring(i, end);
            // 如果当前段长度不足 k，用 fill 填充
            if (part.length() < k) {
                StringBuilder sb = new StringBuilder(part);
                for (int j = part.length(); j < k; j++) {
                    sb.append(fill);
                }
                part = sb.toString();
            }
            result.add(part);
        }

        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        String[] test1 = sol.divideString("abcdefghi", 3, 'x');
        assert Arrays.equals(test1, new String[]{"abc", "def", "ghi"}) :
            "Test 1 failed: " + Arrays.toString(test1);

        // 示例 2
        String[] test2 = sol.divideString("abcdefghij", 3, 'x');
        assert Arrays.equals(test2, new String[]{"abc", "def", "ghi", "jxx"}) :
            "Test 2 failed: " + Arrays.toString(test2);

        // 边界测试：字符串长度恰好为 k
        String[] test3 = sol.divideString("abc", 3, 'x');
        assert Arrays.equals(test3, new String[]{"abc"}) :
            "Test 3 failed: " + Arrays.toString(test3);

        // 边界测试：字符串长度小于 k
        String[] test4 = sol.divideString("ab", 3, 'z');
        assert Arrays.equals(test4, new String[]{"abz"}) :
            "Test 4 failed: " + Arrays.toString(test4);

        // 边界测试：k = 1
        String[] test5 = sol.divideString("abc", 1, 'x');
        assert Arrays.equals(test5, new String[]{"a", "b", "c"}) :
            "Test 5 failed: " + Arrays.toString(test5);

        System.out.println("所有测试用例通过！");
    }
}

// 时间复杂度：O(n)，其中 n = len(s)，遍历字符串一次
// 空间复杂度：O(n / k) 用于存储结果列表
