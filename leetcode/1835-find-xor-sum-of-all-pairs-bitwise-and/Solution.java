public class Solution {
    /**
     * LeetCode 1835. Find XOR Sum of All Pairs Bitwise AND
     *
     * 关键恒等式：
     *   XOR_{i,j}(arr1[i] AND arr2[j]) = XOR(arr1) AND XOR(arr2)
     *
     * 推导要点：
     *   (a AND b) XOR (a AND c) = a AND (b XOR c)
     *   因此对固定的 a = arr1[i]，arr1[i] AND arr2[0] ^ arr1[i] AND arr2[1] ^ ...
     *   = arr1[i] AND (arr2[0] XOR arr2[1] XOR ...)
     *   再对所有 i 异或，最终等价于 (XOR(arr1)) AND (XOR(arr2))。
     *
     * 时间复杂度：O(n + m)
     * 空间复杂度：O(1)
     */
    public int getXORSum(int[] arr1, int[] arr2) {
        // 分别对两个数组做整体异或，再做一次按位与即可
        int xor1 = 0;
        for (int x : arr1) {
            xor1 ^= x;
        }
        int xor2 = 0;
        for (int x : arr2) {
            xor2 ^= x;
        }
        return xor1 & xor2;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 官方示例 1
        assert sol.getXORSum(new int[]{1, 2, 3}, new int[]{6, 5}) == 0;
        // 官方示例 2
        assert sol.getXORSum(new int[]{12}, new int[]{4}) == 4;
        // 边界：两个数组都为 [0]
        assert sol.getXORSum(new int[]{0}, new int[]{0}) == 0;
        // 性质：交换参数顺序结果不变（AND 满足对称性）
        assert sol.getXORSum(new int[]{7, 8, 9}, new int[]{1, 2, 3})
                == sol.getXORSum(new int[]{1, 2, 3}, new int[]{7, 8, 9});
        // 性质：arr2 全 0 时结果必为 0（任何数 AND 0 = 0）
        assert sol.getXORSum(new int[]{5, 10, 15}, new int[]{0, 0, 0}) == 0;
        // 性质：arr1=[1]*5(异或=1) AND arr2=[1]*4(异或=0) = 0
        assert sol.getXORSum(new int[]{1, 1, 1, 1, 1}, new int[]{1, 1, 1, 1}) == 0;

        System.out.println("All tests passed!");
    }
}