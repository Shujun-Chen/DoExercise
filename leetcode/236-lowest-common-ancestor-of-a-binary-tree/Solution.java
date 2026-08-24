// LeetCode 平台已内置 TreeNode 定义；本地运行需保留此处定义。
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    /**
     * LeetCode 236. Lowest Common Ancestor of a Binary Tree
     *
     * 给定二叉树根节点 root 和两个目标节点 p、q，返回它们的最近公共祖先（LCA）。
     * 注意：p 和 q 可以是自身的后代。
     *
     * 思路：后序遍历。
     * 1. 空节点 / 当前节点等于 p 或 q → 直接返回当前节点。
     * 2. 递归处理左右子树，得到 left 和 right。
     * 3. 若 left 与 right 都非空，说明 p 与 q 分别位于左右子树，当前节点就是 LCA；
     *    否则把非空那一侧的结果向上返回。
     *
     * 复杂度：
     * - 时间复杂度：O(n)，每个节点最多访问一次。
     * - 空间复杂度：O(h)，递归栈深度取决于树高，最坏 O(n)。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 命中空节点 / p / q 时直接返回
        if (root == null || root == p || root == q) {
            return root;
        }

        // 在左右子树中寻找
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 左右都非空 → 当前节点就是 LCA
        if (left != null && right != null) {
            return root;
        }

        // 否则把非空一侧的结果向上传递
        return left != null ? left : right;
    }

    // ---------- 本地测试辅助 ----------
    private static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        // 用数组当环形队列，逻辑与 BFS 一致：依次把左右孩子挂到当前节点上
        TreeNode[] nodes = new TreeNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = values[i] == null ? null : new TreeNode(values[i]);
        }
        int head = 0, tail = 1; // 队列区间为 [head, tail)
        while (head < tail && tail < values.length) {
            TreeNode cur = nodes[head++];
            if (cur == null) continue;
            // 左孩子
            if (tail < values.length) {
                cur.left = nodes[tail++];
            }
            // 右孩子
            if (tail < values.length) {
                cur.right = nodes[tail++];
            }
        }
        return nodes[0];
    }

    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        TreeNode left = findNode(root.left, val);
        return left != null ? left : findNode(root.right, val);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 官方示例 1：root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=1 -> 3
        TreeNode root1 = buildTree(new Integer[]{
            3, 5, 1, 6, 2, 0, 8, null, null, 7, 4
        });
        TreeNode p1 = findNode(root1, 5);
        TreeNode q1 = findNode(root1, 1);
        assert sol.lowestCommonAncestor(root1, p1, q1).val == 3 : "ex1 failed";

        // 官方示例 2：p=5, q=4 -> 5
        TreeNode q2 = findNode(root1, 4);
        assert sol.lowestCommonAncestor(root1, p1, q2).val == 5 : "ex2 failed";

        // 祖先关系：p=5, q=7 -> 5
        TreeNode qExtra = findNode(root1, 7);
        assert sol.lowestCommonAncestor(root1, p1, qExtra).val == 5 : "ancestor case failed";

        // 单节点
        TreeNode root2 = buildTree(new Integer[]{1});
        assert sol.lowestCommonAncestor(root2, root2, root2).val == 1 : "single node failed";

        // 左偏链：1 -> 2 -> 3 -> 4，p=4, q=1 -> 1
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        root3.left.left.left = new TreeNode(4);
        assert sol.lowestCommonAncestor(root3, root3.left.left.left, root3).val == 1
            : "left chain failed";

        System.out.println("All tests passed!");
    }
}