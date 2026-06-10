import java.util.*;

class Solution {
    /**
     * 计算建造所有房间的不同顺序数。
     *
     * 思路：
     * - 给定的 prevRoom 数组描述了一棵有根树（根为 0）
     * - 每个节点必须在其父节点之后建造
     * - 答案为 n! / (∏ subtree_size[v])，对所有节点 v 求子树大小的乘积
     * - 该公式的组合学意义：在 n! 种排列中，每个节点的子树大小决定了
     *   该节点与其子孙之间不能自由排列的部分
     *
     * 时间复杂度：O(n)，DFS 遍历 + 模逆运算
     * 空间复杂度：O(n)，存储树结构和子树大小
     */
    public int waysToBuildRooms(int[] prevRoom) {
        final long MOD = (long) 1e9 + 7;
        int n = prevRoom.length;

        // 构建邻接表表示树
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            children.get(prevRoom[i]).add(i);
        }

        // DFS 计算每个节点的子树大小
        long[] subtree = new long[n];
        Arrays.fill(subtree, 1);
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0}); // {节点, 下一个子节点索引}
        while (!stack.isEmpty()) {
            int[] top = stack.peek();
            int node = top[0];
            int idx = top[1];
            if (idx < children.get(node).size()) {
                top[1]++;
                stack.push(new int[]{children.get(node).get(idx), 0});
            } else {
                stack.pop();
                for (int child : children.get(node)) {
                    subtree[node] = (subtree[node] + subtree[child]) % MOD;
                }
            }
        }

        // 计算 n! mod MOD
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i % MOD;
        }

        // 计算所有子树大小的乘积
        long prod = 1;
        for (long s : subtree) {
            prod = prod * s % MOD;
        }

        // 使用费马小定理计算模逆元：a^(-1) ≡ a^(p-2) (mod p)
        long invProd = modPow(prod, MOD - 2, MOD);

        return (int) (fact * invProd % MOD);
    }

    // 快速幂取模
    private long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = result * base % mod;
            }
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1：线性链 0 -> 1 -> 2，只有一种建造顺序
        assert sol.waysToBuildRooms(new int[]{-1, 0, 1}) == 1;
        System.out.println("示例 1 通过");

        // 示例 2：0 -> {1, 2}，1 -> 3，2 -> 4，共 6 种顺序
        assert sol.waysToBuildRooms(new int[]{-1, 0, 0, 1, 2}) == 6;
        System.out.println("示例 2 通过");

        // 额外测试：两节点，只有一种顺序
        assert sol.waysToBuildRooms(new int[]{-1, 0}) == 1;
        System.out.println("额外测试 1 通过");

        System.out.println("所有测试通过！");
    }
}
