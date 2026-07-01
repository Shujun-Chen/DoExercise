import java.util.*;

/**
 * LeetCode 736. Parse Lisp Expression
 * 
 * 解析并计算 Lisp 表达式，支持 let、add、mult 三种操作及变量作用域。
 */
public class Solution {
    // 全局索引指针（用于递归下降解析）
    private int idx;
    private String s;
    // 作用域栈
    private final List<Map<String, Integer>> scopes = new ArrayList<>();
    
    public int evaluate(String expression) {
        s = expression;
        idx = 0;
        scopes.clear();
        scopes.add(new HashMap<>()); // 全局作用域
        return parseExpr();
    }
    
    /**
     * 递归下降解析当前表达式并返回整数值
     */
    private int parseExpr() {
        skipWhitespace();
        
        // 情况1：整数（以数字或负号开头）
        if (s.charAt(idx) == '-' || Character.isDigit(s.charAt(idx))) {
            return parseInt();
        }
        
        // 情况2：变量引用（以字母开头）
        if (Character.isLetter(s.charAt(idx))) {
            return lookupVar();
        }
        
        // 情况3：复合表达式 (let ...) / (add ...) / (mult ...)
        // 跳过 '('
        idx++; // '('
        skipWhitespace();
        
        // 读取关键字
        String keyword = readToken();
        skipWhitespace();
        
        int result;
        switch (keyword) {
            case "add": {
                int e1 = parseExpr();
                skipWhitespace();
                int e2 = parseExpr();
                skipWhitespace();
                result = e1 + e2;
                break;
            }
            case "mult": {
                int e1 = parseExpr();
                skipWhitespace();
                int e2 = parseExpr();
                skipWhitespace();
                result = e1 * e2;
                break;
            }
            case "let": {
                // 新建作用域
                scopes.add(new HashMap<>());
                // 读取变量-值配对
                while (true) {
                    skipWhitespace();
                    // 如果下一个字符是 '('，说明这已经是最后的表达式
                    int savedIdx = idx;
                    String token = readToken();
                    skipWhitespace();
                    
                    if (s.charAt(idx) == ')') {
                        // token 是最终表达式
                        // 先还原 idx 重新解析
                        idx = savedIdx;
                        int val = parseExpr();
                        scopes.remove(scopes.size() - 1);
                        skipWhitespace();
                        result = val;
                        break;
                    }
                    
                    // token 是变量名，下一个是值
                    String varName = token;
                    int val = parseExpr();
                    scopes.get(scopes.size() - 1).put(varName, val);
                }
                break;
            }
            default:
                result = 0;
        }
        
        // 跳过 ')'
        idx++; // ')'
        return result;
    }
    
    /**
     * 解析整数字面量
     */
    private int parseInt() {
        int sign = 1;
        if (s.charAt(idx) == '-') {
            sign = -1;
            idx++;
        }
        int num = 0;
        while (idx < s.length() && Character.isDigit(s.charAt(idx))) {
            num = num * 10 + (s.charAt(idx) - '0');
            idx++;
        }
        return sign * num;
    }
    
    /**
     * 查找变量值（从内到外遍历作用域）
     */
    private int lookupVar() {
        String var = readToken();
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Integer> scope = scopes.get(i);
            if (scope.containsKey(var)) {
                return scope.get(var);
            }
        }
        return 0; // 按题目保证不会走到这里
    }
    
    /**
     * 读取一个 token（关键字、变量名或数字），不处理括号
     */
    private String readToken() {
        int start = idx;
        while (idx < s.length() && s.charAt(idx) != ' ' && s.charAt(idx) != '(' 
               && s.charAt(idx) != ')' && s.charAt(idx) != '-') {
            idx++;
        }
        return s.substring(start, idx);
    }
    
    /**
     * 跳过空白字符
     */
    private void skipWhitespace() {
        while (idx < s.length() && s.charAt(idx) == ' ') {
            idx++;
        }
    }
    
    // ========== 测试 ==========
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 测试用例1：嵌套 let 和变量作用域
        String expr1 = "(let x 2 (mult x (let x 3 y 4 (add x y))))";
        int res1 = sol.evaluate(expr1);
        System.out.println("Test 1: " + expr1);
        System.out.println("Output: " + res1 + ", Expected: 14");
        assert res1 == 14 : "Expected 14 but got " + res1;
        
        // 测试用例2：同名变量覆盖
        String expr2 = "(let x 3 x 2 x)";
        int res2 = sol.evaluate(expr2);
        System.out.println("\nTest 2: " + expr2);
        System.out.println("Output: " + res2 + ", Expected: 2");
        assert res2 == 2 : "Expected 2 but got " + res2;
        
        // 测试用例3：多个变量 + 复杂表达式
        String expr3 = "(let x 1 y 2 x (add x y) (add x y))";
        int res3 = sol.evaluate(expr3);
        System.out.println("\nTest 3: " + expr3);
        System.out.println("Output: " + res3 + ", Expected: 5");
        assert res3 == 5 : "Expected 5 but got " + res3;
        
        // 测试用例4：纯 add
        String expr4 = "(add 1 2)";
        int res4 = sol.evaluate(expr4);
        System.out.println("\nTest 4: " + expr4);
        System.out.println("Output: " + res4 + ", Expected: 3");
        assert res4 == 3 : "Expected 3 but got " + res4;
        
        // 测试用例5：纯 mult
        String expr5 = "(mult 3 7)";
        int res5 = sol.evaluate(expr5);
        System.out.println("\nTest 5: " + expr5);
        System.out.println("Output: " + res5 + ", Expected: 21");
        assert res5 == 21 : "Expected 21 but got " + res5;
        
        // 测试用例6：负整数
        String expr6 = "-5";
        int res6 = sol.evaluate(expr6);
        System.out.println("\nTest 6: " + expr6);
        System.out.println("Output: " + res6 + ", Expected: -5");
        assert res6 == -5 : "Expected -5 but got " + res6;
        
        // 测试用例7：let 中引用前面绑定的变量
        String expr7 = "(let x 1 y x y)";
        int res7 = sol.evaluate(expr7);
        System.out.println("\nTest 7: " + expr7);
        System.out.println("Output: " + res7 + ", Expected: 1");
        assert res7 == 1 : "Expected 1 but got " + res7;
        
        System.out.println("\n✅ 所有测试用例通过！");
    }
}
