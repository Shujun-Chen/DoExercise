from typing import List


class Solution:
    """
    LeetCode 1835. Find XOR Sum of All Pairs Bitwise AND

    关键恒等式：
        XOR_{i,j}(arr1[i] AND arr2[j]) = XOR(arr1) AND XOR(arr2)

    推导要点：
        (a AND b) XOR (a AND c) = a AND (b XOR c)
        因此对固定 a = arr1[i]，arr1[i] AND arr2[0] ^ arr1[i] AND arr2[1] ^ ...
        = arr1[i] AND (arr2[0] XOR arr2[1] XOR ...)
        再对所有 i 异或，相当于对 arr1XorSum 做 AND 提取得最终结果。
    """

    def getXORSum(self, arr1: List[int], arr2: List[int]) -> int:
        # 时间复杂度：O(n + m)，空间复杂度：O(1)
        # 先把两个数组各自全部异或，再做一次按位与即可
        xor1 = 0
        for x in arr1:
            xor1 ^= x
        xor2 = 0
        for x in arr2:
            xor2 ^= x
        return xor1 & xor2


if __name__ == "__main__":
    sol = Solution()

    # 官方示例 1
    assert sol.getXORSum([1, 2, 3], [6, 5]) == 0
    # 官方示例 2
    assert sol.getXORSum([12], [4]) == 4
    # 边界：两数组都为 [0]
    assert sol.getXORSum([0], [0]) == 0
    # 性质：交换参数顺序结果不变（AND 满足对称性）
    assert sol.getXORSum([7, 8, 9], [1, 2, 3]) == sol.getXORSum([1, 2, 3], [7, 8, 9])
    # 性质：arr2 全 0 时结果必为 0（任何数 AND 0 = 0）
    assert sol.getXORSum([5, 10, 15], [0, 0, 0]) == 0
    # 性质：所有元素相同 —— arr1=[1]*5(异或=1) AND arr2=[1]*4(异或=0) = 0
    assert sol.getXORSum([1] * 5, [1] * 4) == 0

    print("All tests passed!")