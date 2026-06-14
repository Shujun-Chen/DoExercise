import java.util.Arrays;

class Solution {
    /**
     * 将一维数组转换为二维数组
     * 
     * 思路：
     * 1. 首先检查元素总数是否匹配 m * n，不匹配则返回空数组
     * 2. 每次取 n 个元素作为一行，构建二维数组
     * 
     * 时间复杂度：O(m * n) = O(original.length)
     * 空间复杂度：O(m * n) = O(original.length)（输出空间）
     */
    public int[][] construct2DArray(int[] original, int m, int n) {
        // 元素总数不匹配，无法构建
        if (original.length != m * n) {
            return new int[0][0];
        }
        
        // 按行切片构建二维数组
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = original[i * n + j];
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 测试用例 1
        int[] original1 = {1, 2, 3, 4};
        int[][] result1 = sol.construct2DArray(original1, 2, 2);
        System.out.println("输入: original=[1,2,3,4], m=2, n=2");
        System.out.println("输出: " + Arrays.deepToString(result1));
        assert Arrays.deepEquals(result1, new int[][]{{1, 2}, {3, 4}});
        
        // 测试用例 2
        int[] original2 = {1, 2, 3};
        int[][] result2 = sol.construct2DArray(original2, 1, 3);
        System.out.println("输入: original=[1,2,3], m=1, n=3");
        System.out.println("输出: " + Arrays.deepToString(result2));
        assert Arrays.deepEquals(result2, new int[][]{{1, 2, 3}});
        
        // 测试用例 3
        int[] original3 = {1, 2};
        int[][] result3 = sol.construct2DArray(original3, 1, 1);
        System.out.println("输入: original=[1,2], m=1, n=1");
        System.out.println("输出: " + Arrays.deepToString(result3));
        assert Arrays.deepEquals(result3, new int[0][0]);
        
        System.out.println("\n所有测试用例通过！");
    }
}
