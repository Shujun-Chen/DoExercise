from typing import List


class Solution:
    def construct2DArray(self, original: List[int], m: int, n: int) -> List[List[int]]:
        """
        将一维数组转换为二维数组
        
        思路：
        1. 首先检查元素总数是否匹配 m * n，不匹配则返回空数组
        2. 每次取 n 个元素作为一行，构建二维数组
        
        时间复杂度：O(m * n) = O(len(original))
        空间复杂度：O(m * n) = O(len(original))（输出空间）
        """
        # 元素总数不匹配，无法构建
        if len(original) != m * n:
            return []
        
        # 按行切片构建二维数组
        result = []
        for i in range(m):
            result.append(original[i * n: (i + 1) * n])
        return result


if __name__ == "__main__":
    sol = Solution()
    
    # 测试用例 1
    original = [1, 2, 3, 4]
    m, n = 2, 2
    result = sol.construct2DArray(original, m, n)
    print(f"输入: original={original}, m={m}, n={n}")
    print(f"输出: {result}")
    assert result == [[1, 2], [3, 4]]
    
    # 测试用例 2
    original = [1, 2, 3]
    m, n = 1, 3
    result = sol.construct2DArray(original, m, n)
    print(f"输入: original={original}, m={m}, n={n}")
    print(f"输出: {result}")
    assert result == [[1, 2, 3]]
    
    # 测试用例 3
    original = [1, 2]
    m, n = 1, 1
    result = sol.construct2DArray(original, m, n)
    print(f"输入: original={original}, m={m}, n={n}")
    print(f"输出: {result}")
    assert result == []
    
    print("\n所有测试用例通过！")
