import java.util.*;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    /**
     * 删除二叉树中所有值为 target 的叶子节点
     * 使用后序遍历，先处理子节点再处理当前节点
     *
     * 时间复杂度：O(n)，每个节点访问一次
     * 空间复杂度：O(h)，递归栈深度为树的高度
     */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }

        // 后序遍历：先递归处理左右子树
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);

        // 如果当前节点变成了叶子节点且值等于 target，删除它
        if (root.left == null && root.right == null && root.val == target) {
            return null;
        }

        return root;
    }

    /**
     * 从层序列表构建二叉树
     */
    public static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }

    /**
     * 将二叉树转为层序列表
     */
    public static List<Integer> treeToList(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }
        // 去除末尾的 null
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1: [1,2,3,2,null,2,4], target=2 -> [1,null,3,null,4]
        TreeNode root1 = buildTree(new Integer[]{1, 2, 3, 2, null, 2, 4});
        TreeNode result1 = sol.removeLeafNodes(root1, 2);
        System.out.println("Test 1: " + treeToList(result1));

        // 测试用例 2: [1,3,3,3,2], target=3 -> [1,3,null,null,2]
        TreeNode root2 = buildTree(new Integer[]{1, 3, 3, 3, 2});
        TreeNode result2 = sol.removeLeafNodes(root2, 3);
        System.out.println("Test 2: " + treeToList(result2));

        // 测试用例 3: [1,2,null,2,null,2], target=2 -> [1]
        TreeNode root3 = buildTree(new Integer[]{1, 2, null, 2, null, 2});
        TreeNode result3 = sol.removeLeafNodes(root3, 2);
        System.out.println("Test 3: " + treeToList(result3));
    }
}
