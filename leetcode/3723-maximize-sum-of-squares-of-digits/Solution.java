/**
 * 3723. Maximize Sum of Squares of Digits
 * https://leetcode.com/problems/maximize-sum-of-squares-of-digits/
 *
 * 时间复杂度：O(num) — 构造长度为 num 的结果字符串
 * 空间复杂度：O(num) — 存储结果字符串
 */
class Solution {
    /**
     * 返回长度为 num、各位数字之和为 digitSum 且平方和最大的最大整数。
     * 若不存在则返回空字符串。
     */
    public String maxScoreString(int num, int digitSum) {
        // 最大可能数字之和为 num * 9，超过则无解
        if (digitSum > num * 9L) {
            return "";
        }

        // 贪心：尽可能多地使用数字 9，因为 9²=81 带来的平方收益最大
        int count9 = digitSum / 9;
        int remainder = digitSum % 9;

        // 构造结果：9 放在最前面，余数紧跟，其余补 0
        StringBuilder sb = new StringBuilder();
        sb.append("9".repeat(count9));
        if (remainder > 0) {
            sb.append(remainder);
        }
        int zeros = num - count9 - (remainder > 0 ? 1 : 0);
        if (zeros > 0) {
            sb.append("0".repeat(zeros));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 示例测试
        assert s.maxScoreString(2, 3).equals("30") : "示例 1 失败";
        assert s.maxScoreString(2, 17).equals("98") : "示例 2 失败";
        assert s.maxScoreString(1, 10).equals("") : "示例 3 失败";
        // 边界测试
        assert s.maxScoreString(1, 9).equals("9") : "边界 1 失败";
        assert s.maxScoreString(3, 18).equals("990") : "边界 2 失败";
        assert s.maxScoreString(3, 27).equals("999") : "边界 3 失败";
        assert s.maxScoreString(4, 35).equals("9998") : "边界 4 失败";
        System.out.println("所有测试用例通过");
    }
}
