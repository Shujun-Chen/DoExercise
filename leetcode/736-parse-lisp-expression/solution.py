class Solution:
    def evaluate(self, expression: str) -> int:
        """
        解析并计算 Lisp 表达式。
        支持 let、add、mult 三种表达式及变量作用域。
        """
        # 作用域栈，每个元素为一个 dict（变量名 -> 值）
        scopes = [{}]

        def parse(s: str) -> int:
            """递归解析表达式并返回整数值"""
            s = s.strip()

            # 情况1：整数（可能带负号）
            if s[0] == '-' or s[0].isdigit():
                return int(s)

            # 情况2：变量引用（以字母开头，不是括号）
            if s[0].isalpha():
                # 从内到外查找变量
                for scope in reversed(scopes):
                    if s in scope:
                        return scope[s]
                # 按题目保证，不会走到这里
                return 0

            # 情况3：复合表达式 (let/add/mult ...)
            # 去掉外层括号
            s = s[1:-1].strip()

            # 按括号平衡拆分成 token
            tokens = []
            i = 0
            while i < len(s):
                # 跳过空白
                while i < len(s) and s[i] == ' ':
                    i += 1
                if i >= len(s):
                    break

                if s[i] == '(':
                    # 找到匹配的右括号
                    bal = 1
                    j = i + 1
                    while bal > 0 and j < len(s):
                        if s[j] == '(':
                            bal += 1
                        elif s[j] == ')':
                            bal -= 1
                        j += 1
                    tokens.append(s[i:j])
                    i = j
                else:
                    # 普通 token（变量名、关键字或数字）
                    j = i
                    while j < len(s) and s[j] != ' ':
                        j += 1
                    tokens.append(s[i:j])
                    i = j

            if not tokens:
                return 0

            keyword = tokens[0]

            if keyword == 'add':
                # (add e1 e2) -> e1 + e2
                return parse(tokens[1]) + parse(tokens[2])

            if keyword == 'mult':
                # (mult e1 e2) -> e1 * e2
                return parse(tokens[1]) * parse(tokens[2])

            if keyword == 'let':
                # (let v1 e1 v2 e2 ... expr)
                # 新建一层作用域
                scopes.append({})
                # 处理变量绑定对 (v1, e1), (v2, e2), ...
                for k in range(1, len(tokens) - 1, 2):
                    var = tokens[k]
                    val = parse(tokens[k + 1])
                    scopes[-1][var] = val
                # 最后一个 token 是返回值表达式
                result = parse(tokens[-1])
                scopes.pop()
                return result

            return 0

        return parse(expression)


if __name__ == "__main__":
    sol = Solution()

    # 测试用例1：嵌套 let 和变量作用域
    expr1 = "(let x 2 (mult x (let x 3 y 4 (add x y))))"
    res1 = sol.evaluate(expr1)
    print(f"Test 1: {expr1}")
    print(f"Output: {res1}, Expected: 14")
    assert res1 == 14, f"Expected 14 but got {res1}"

    # 测试用例2：同名变量覆盖
    expr2 = "(let x 3 x 2 x)"
    res2 = sol.evaluate(expr2)
    print(f"\nTest 2: {expr2}")
    print(f"Output: {res2}, Expected: 2")
    assert res2 == 2, f"Expected 2 but got {res2}"

    # 测试用例3：多个变量 + 复杂表达式
    expr3 = "(let x 1 y 2 x (add x y) (add x y))"
    res3 = sol.evaluate(expr3)
    print(f"\nTest 3: {expr3}")
    print(f"Output: {res3}, Expected: 5")
    assert res3 == 5, f"Expected 5 but got {res3}"

    # 测试用例4：纯 add
    expr4 = "(add 1 2)"
    res4 = sol.evaluate(expr4)
    print(f"\nTest 4: {expr4}")
    print(f"Output: {res4}, Expected: 3")
    assert res4 == 3, f"Expected 3 but got {res4}"

    # 测试用例5：纯 mult
    expr5 = "(mult 3 7)"
    res5 = sol.evaluate(expr5)
    print(f"\nTest 5: {expr5}")
    print(f"Output: {res5}, Expected: 21")
    assert res5 == 21, f"Expected 21 but got {res5}"

    # 测试用例6：负整数
    expr6 = "-5"
    res6 = sol.evaluate(expr6)
    print(f"\nTest 6: {expr6}")
    print(f"Output: {res6}, Expected: -5")
    assert res6 == -5, f"Expected -5 but got {res6}"

    # 测试用例7：let 中引用前面绑定的变量
    expr7 = "(let x 1 y x y)"
    res7 = sol.evaluate(expr7)
    print(f"\nTest 7: {expr7}")
    print(f"Output: {res7}, Expected: 1")
    assert res7 == 1, f"Expected 1 but got {res7}"

    print("\n✅ 所有测试用例通过！")
