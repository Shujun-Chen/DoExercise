# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
from typing import Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Solution:
    def removeLeafNodes(self, root: Optional[TreeNode], target: int) -> Optional[TreeNode]:
        # 时间复杂度：O(n)，每个节点访问一次
        # 空间复杂度：O(h)，递归栈深度为树的高度
        if not root:
            return None

        # 后序遍历：先处理左右子树
        root.left = self.removeLeafNodes(root.left, target)
        root.right = self.removeLeafNodes(root.right, target)

        # 如果当前节点变成了叶子且值等于 target，删除它
        if not root.left and not root.right and root.val == target:
            return None

        return root


def build_tree_from_list(values, index=0):
    """从层序列表构建二叉树"""
    if index >= len(values) or values[index] is None:
        return None
    node = TreeNode(values[index])
    node.left = build_tree_from_list(values, 2 * index + 1)
    node.right = build_tree_from_list(values, 2 * index + 2)
    return node


def tree_to_list(root):
    """将二叉树转为层序列表"""
    if not root:
        return []
    result = []
    queue = [root]
    while queue:
        node = queue.pop(0)
        if node:
            result.append(node.val)
            queue.append(node.left)
            queue.append(node.right)
        else:
            result.append(None)
    # 去除末尾的 None
    while result and result[-1] is None:
        result.pop()
    return result


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1: [1,2,3,2,null,2,4], target=2 -> [1,null,3,null,4]
    root = build_tree_from_list([1, 2, 3, 2, None, 2, 4])
    result = sol.removeLeafNodes(root, 2)
    print("Test 1:", tree_to_list(result))  # [1, None, 3, None, 4]

    # 测试用例 2: [1,3,3,3,2], target=3 -> [1,3,null,null,2]
    root = build_tree_from_list([1, 3, 3, 3, 2])
    result = sol.removeLeafNodes(root, 3)
    print("Test 2:", tree_to_list(result))  # [1, 3, None, None, 2]

    # 测试用例 3: [1,2,null,2,null,2], target=2 -> [1]
    root = build_tree_from_list([1, 2, None, 2, None, 2])
    result = sol.removeLeafNodes(root, 2)
    print("Test 3:", tree_to_list(result))  # [1]
