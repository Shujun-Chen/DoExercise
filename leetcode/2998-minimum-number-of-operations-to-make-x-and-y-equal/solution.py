"""
LeetCode 2998. Minimum Number of Operations to Make X and Y Equal
难度: Medium

给定两个正整数 x 和 y，每次操作可以：
1. 如果 x 是 11 的倍数，将 x 除以 11
2. 如果 x 是 5 的倍数，将 x 除以 5
3. 将 x 减 1
4. 将 x 加 1

求使 x 和 y 相等所需的最少操作次数。
"""

from functools import lru_cache

INF = 10 ** 9


class Solution:
    def minimumOperationsToMakeEqual(self, x: int, y: int) -> int:
        """
        使用记忆化搜索（DP）求解。
        
        核心思路：
        - 如果 y >= x，只能通过递增到达，答案为 y - x
        - 如果 y < x，我们有两种策略：
          1. 直接递减：x - y 次操作
          2. 先调整 x 到 5 或 11 的倍数，然后做除法，再递归处理
        - 为了处理"先递增到倍数再除法"的情况，需要同时考虑向上和向下调整
        """
        
        # 剪枝：如果 y >= x，只能递增
        if y >= x:
            return y - x
        
        # 上界：超过 x + (x - y) 的数不可能最优，
        # 因为直接递减只需要 x - y 次操作
        upper = x + (x - y)
        
        @lru_cache(None)
        def dfs(val: int) -> int:
            """返回从 val 到 y 的最少操作次数"""
            # 基本情形
            if val <= y:
                return y - val
            
            # 如果已经超过上界，直接返回一个很大的数（此路不通）
            if val > upper:
                return INF
            
            # 策略1：直接递减到 y
            ans = val - y
            
            # 策略2：利用除以 11
            # 2a: 递减到最近的 11 的倍数，然后除以 11
            r = val % 11
            if r > 0:
                # 向下调整（递减 r 次到 val - r，然后除以 11）
                if val - r > 0:
                    ans = min(ans, r + 1 + dfs((val - r) // 11))
                # 向上调整（递增 11-r 次到 val + (11-r)，然后除以 11）
                ans = min(ans, (11 - r) + 1 + dfs((val + (11 - r)) // 11))
            else:
                # val 已经是 11 的倍数，直接除以 11
                ans = min(ans, 1 + dfs(val // 11))
            
            # 策略3：利用除以 5
            r = val % 5
            if r > 0:
                # 向下调整
                if val - r > 0:
                    ans = min(ans, r + 1 + dfs((val - r) // 5))
                # 向上调整
                ans = min(ans, (5 - r) + 1 + dfs((val + (5 - r)) // 5))
            else:
                # val 已经是 5 的倍数，直接除以 5
                ans = min(ans, 1 + dfs(val // 5))
            
            return ans
        
        return dfs(x)


if __name__ == "__main__":
    s = Solution()
    
    # 测试用例
    test_cases = [
        (26, 1, 3),
        (54, 2, 4),
        (25, 30, 5),
        (1, 1, 0),
        (10, 1, 2),  # 10 -> 11 (+1), 11 -> 1 (/11)
        (100, 1, 4),  # 100 -> 20 (/5), 20 -> 4 (/5), 4 -> 5 (+1), 5 -> 1 (/5)
        (7, 2, 4),    # 7 -> 10 (+3), 10 -> 2 (/5)
        (13, 1, 3),   # 13 -> 11 (-2), 11 -> 1 (/11)
    ]
    
    for x, y, expected in test_cases:
        result = s.minimumOperationsToMakeEqual(x, y)
        status = "✓" if result == expected else "✗"
        print(f"{status} x={x}, y={y} => {result} (expected {expected})")

# 复杂度分析:
# 时间复杂度: O(U) 其中 U = x + (x - y) 是搜索上界。每个状态最多访问一次。
# 空间复杂度: O(U) 用于记忆化递归的缓存。
