class Solution:
    def productQueries(self, n: int, queries: list[list[int]]) -> list[int]:
        """
        计算 powers 数组范围内元素的乘积，结果模 10^9+7。

        powers 数组由 n 的二进制表示构成（最小的 2 的幂次集合，和为 n）。
        例如 n=15 (1111) → powers = [1, 2, 4, 8]。
        """
        MOD = 10 ** 9 + 7

        # Step 1: 从 n 的二进制分解中提取 powers 数组
        powers = []
        p = 1  # 当前 2 的幂次: 2^0, 2^1, 2^2, ...
        while n > 0:
            if n & 1:          # 当前位为 1，说明该幂次在二进制表示中
                powers.append(p)
            n >>= 1            # 右移一位，检查下一个二进制位
            p <<= 1            # 下一个幂次

        # Step 2: 前缀积数组，prefix[i] = product(powers[0..i-1]) % MOD
        size = len(powers)
        prefix = [1] * (size + 1)
        for i in range(size):
            prefix[i + 1] = (prefix[i] * powers[i]) % MOD

        # Step 3: 快速幂 + 费马小定理求逆元
        def mod_pow(a: int, b: int) -> int:
            """计算 a^b % MOD"""
            res = 1
            while b:
                if b & 1:
                    res = (res * a) % MOD
                a = (a * a) % MOD
                b >>= 1
            return res

        # Step 4: 对每个查询，用前缀积计算区间乘积
        # product(left..right) = prefix[right+1] / prefix[left]
        # 由于模运算，用 prefix[left] 的逆元代替除法
        result = []
        for left, right in queries:
            # prefix[right+1] * modInverse(prefix[left]) % MOD
            q = prefix[right + 1] * mod_pow(prefix[left], MOD - 2) % MOD
            result.append(q)

        return result


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    n1 = 15
    queries1 = [[0, 1], [2, 2], [0, 3]]
    out1 = sol.productQueries(n1, queries1)
    expected1 = [2, 4, 64]
    print(f"Test 1: n=15, queries={queries1}")
    print(f"  Output: {out1}")
    print(f"  Expected: {expected1}")
    print(f"  {'PASS' if out1 == expected1 else 'FAIL'}")

    # 测试用例 2
    n2 = 2
    queries2 = [[0, 0]]
    out2 = sol.productQueries(n2, queries2)
    expected2 = [2]
    print(f"Test 2: n=2, queries={queries2}")
    print(f"  Output: {out2}")
    print(f"  Expected: {expected2}")
    print(f"  {'PASS' if out2 == expected2 else 'FAIL'}")

    # 测试用例 3: n=1 (二进制 1) → powers=[1]
    n3 = 1
    queries3 = [[0, 0]]
    out3 = sol.productQueries(n3, queries3)
    expected3 = [1]
    print(f"Test 3: n=1, queries={queries3}")
    print(f"  Output: {out3}")
    print(f"  Expected: {expected3}")
    print(f"  {'PASS' if out3 == expected3 else 'FAIL'}")

    # 测试用例 4: n=29 (二进制 11101) → powers=[1,4,8,16]
    n4 = 29
    queries4 = [[0, 3]]
    out4 = sol.productQueries(n4, queries4)
    # powers=[1,4,8,16], product=1*4*8*16=512
    expected4 = [512]
    print(f"Test 4: n=29, queries={queries4}")
    print(f"  Output: {out4}")
    print(f"  Expected: {expected4}")
    print(f"  {'PASS' if out4 == expected4 else 'FAIL'}")
