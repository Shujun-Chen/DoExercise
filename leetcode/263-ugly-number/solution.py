class Solution:
    def isUgly(self, n: int) -> bool:
        """
        判断一个数是否为丑数
        
        丑数：只包含质因数 2、3、5 的正整数
        
        思路：不断除以 2、3、5，如果最终结果为 1 则是丑数
        
        时间复杂度：O(log n) — 每次除法至少将 n 减半
        空间复杂度：O(1)
        """
        # 丑数定义为正整数，0 和负数不是丑数
        if n <= 0:
            return False
        
        # 不断除去质因数 2、3、5
        for factor in [2, 3, 5]:
            while n % factor == 0:
                n //= factor
        
        # 如果剩余值为 1，说明只包含 2、3、5 的因子
        return n == 1


if __name__ == "__main__":
    s = Solution()
    
    # 测试用例 1
    assert s.isUgly(6) == True, "Test 1 failed"
    
    # 测试用例 2
    assert s.isUgly(1) == True, "Test 2 failed"
    
    # 测试用例 3
    assert s.isUgly(14) == False, "Test 3 failed"
    
    # 额外测试
    assert s.isUgly(0) == False, "Test 4 failed"
    assert s.isUgly(-1) == False, "Test 5 failed"
    assert s.isUgly(8) == True, "Test 6 failed"
    assert s.isUgly(7) == False, "Test 7 failed"
    assert s.isUgly(30) == True, "Test 8 failed"
    
    print("所有测试用例通过！")
