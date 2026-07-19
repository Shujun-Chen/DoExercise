public class Solution {
    /**
     * 计算 x 的各位数字之和，并判断 x 是否能被该和整除。
     *
     * 时间复杂度：O(log x)，需要遍历 x 的每一位数字。
     * 空间复杂度：O(1)，只使用常数个变量。
     */
    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int original = x;
        int digitSum = 0;

        // 逐位取出最低位并累加到数字和中
        while (x > 0) {
            digitSum += x % 10;
            x /= 10;
        }

        // 能被数字和整除时，x 是哈沙德数
        return original % digitSum == 0 ? digitSum : -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] testCases = {
            {18, 9},
            {23, -1},
            {1, 1},
            {21, 3},
            {100, 1},
            {99, -1}
        };

        for (int[] testCase : testCases) {
            int x = testCase[0];
            int expected = testCase[1];
            int actual = solution.sumOfTheDigitsOfHarshadNumber(x);
            assert actual == expected
                : "x=" + x + ": expected " + expected + ", got " + actual;
        }

        System.out.println("All tests passed!");
    }
}
