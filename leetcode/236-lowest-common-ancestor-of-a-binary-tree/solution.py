# Definition for a binary tree node.
class TreeNode:
    """二叉树节点定义（LeetCode 平台已内置，这里仅作本地测试使用）。"""
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        """
        LeetCode 236. Lowest Common Ancestor of a Binary Tree

        给定一棵二叉树（无指向父节点的指针），找到两个指定节点 p 和 q 的最近公共祖先（LCA）。
        最近公共祖先定义：p 和 q 都可以是自身的后代（含自身），LCA 是这两个后代中深度最大的节点。

        思路：后序遍历（自底向上）。
        1. 递归终止条件：当前节点为空 → 返回 None；当前节点恰好等于 p 或 q → 返回当前节点。
        2. 递归处理左右子树，分别得到 left 和 right 的查找结果。
        3. 根据 left/right 的组合判断：
           - left 与 right 都非空 → p 与 q 分别在左右子树，当前节点就是 LCA。
           - 仅 left 非空  → p 与 q 都在左子树，LCA 在 left 内部。
           - 仅 right 非空 → p 与 q 都在右子树，LCA 在 right 内部。
           - 都为空        → 当前子树不含 p/q，返回 None。

        复杂度：
        - 时间复杂度：O(n)，每个节点最多访问一次。
        - 空间复杂度：O(h)，递归栈深度取决于树高 h，最坏 O(n)。
        """
        # 终止条件：空节点 / 命中 p / 命中 q
        if root is None or root is p or root is q:
            return root

        # 在左右子树中分别寻找 p 和 q
        left = self.lowestCommonAncestor(root.left, p, q)
        right = self.lowestCommonAncestor(root.right, p, q)

        # 左右都非空：当前节点就是最近公共祖先
        if left is not None and right is not None:
            return root

        # 只在一侧找到，把那一侧的结果向上传递
        return left if left is not None else right


# ---------- 本地测试辅助 ----------
def build_tree(values):
    """
    根据层序数组（None 表示空位）构造二叉树，便于本地测试。
    用 BFS + 队列保证空位 NULL 之后的孩子不会被错挂到前面。
    """
    from collections import deque
    if not values or values[0] is None:
        return None
    root = TreeNode(values[0])
    queue = deque([root])
    i = 1
    while queue and i < len(values):
        node = queue.popleft()
        # 左孩子
        if i < len(values):
            v = values[i]
            i += 1
            if v is not None:
                node.left = TreeNode(v)
                queue.append(node.left)
        # 右孩子
        if i < len(values):
            v = values[i]
            i += 1
            if v is not None:
                node.right = TreeNode(v)
                queue.append(node.right)
    return root


def find_node(root, val):
    """按值查找节点，用于构造测试参数。"""
    if root is None:
        return None
    if root.val == val:
        return root
    return find_node(root.left, val) or find_node(root.right, val)


if __name__ == "__main__":
    sol = Solution()

    # 官方示例 1：root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=1 -> 3
    root1 = build_tree([3, 5, 1, 6, 2, 0, 8, None, None, 7, 4])
    p1, q1 = find_node(root1, 5), find_node(root1, 1)
    assert sol.lowestCommonAncestor(root1, p1, q1).val == 3, "ex1 failed"

    # 官方示例 2：root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=4 -> 5
    q2 = find_node(root1, 4)
    assert sol.lowestCommonAncestor(root1, p1, q2).val == 5, "ex2 failed"

    # 额外：p 是 q 的祖先（p=5, q=7）-> 5
    q_extra = find_node(root1, 7)
    assert sol.lowestCommonAncestor(root1, p1, q_extra).val == 5, "ancestor case failed"

    # 单节点树：root=[1], p=1, q=1 -> 1
    root2 = build_tree([1])
    p2 = q2_node = root2
    assert sol.lowestCommonAncestor(root2, p2, q2_node).val == 1, "single node failed"

    # 左偏链：root=[1,2,3,...]，p=左下，q=右下 -> 根
    root3 = build_tree([1, 2, None, 3, None, 4])
    p3, q3 = find_node(root3, 4), find_node(root3, 1)
    # build_tree 上面的写法并不能构造左偏链，这里改写为手工构造
    root3 = TreeNode(1)
    root3.left = TreeNode(2)
    root3.left.left = TreeNode(3)
    root3.left.left.left = TreeNode(4)
    p3, q3 = root3.left.left.left, root3
    assert sol.lowestCommonAncestor(root3, p3, q3).val == 1, "left chain failed"

    print("All tests passed!")