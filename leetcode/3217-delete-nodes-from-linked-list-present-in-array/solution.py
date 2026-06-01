"""
3217. Delete Nodes From Linked List Present in Array
Difficulty: Medium
Tags: Array, Hash Table, Linked List

思路：使用哈希集合存储 nums 中的所有值，然后遍历链表，
跳过所有值在集合中的节点。使用虚拟头节点简化边界处理。

时间复杂度：O(n + m)，其中 n 是链表长度，m 是 nums 数组长度
空间复杂度：O(m)，哈希集合存储 nums 的值
"""

from typing import Optional, List


# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def modifiedList(self, nums: List[int], head: Optional[ListNode]) -> Optional[ListNode]:
        # 将 nums 转换为集合，便于 O(1) 查找
        num_set = set(nums)

        # 使用虚拟头节点，方便处理头节点被删除的情况
        dummy = ListNode(0)
        dummy.next = head

        # prev 指向最后一个确认保留的节点
        prev = dummy
        curr = head

        while curr:
            if curr.val in num_set:
                # 当前节点需要删除，跳过它
                prev.next = curr.next
            else:
                # 当前节点保留，移动 prev
                prev = curr
            curr = curr.next

        return dummy.next


# 测试用例
if __name__ == "__main__":
    def build_list(arr):
        """从数组构建链表"""
        dummy = ListNode(0)
        curr = dummy
        for val in arr:
            curr.next = ListNode(val)
            curr = curr.next
        return dummy.next

    def to_list(head):
        """将链表转为数组便于比较"""
        result = []
        while head:
            result.append(head.val)
            head = head.next
        return result

    sol = Solution()

    # 测试用例 1: nums = [1,2,3], head = [1,2,3,4,5] -> [4,5]
    head = build_list([1, 2, 3, 4, 5])
    result = sol.modifiedList([1, 2, 3], head)
    assert to_list(result) == [4, 5], f"Test 1 failed: {to_list(result)}"
    print("Test 1 passed: [1,2,3,4,5] -> [4,5]")

    # 测试用例 2: nums = [1], head = [1,2,1,2,1,2] -> [2,2,2]
    head = build_list([1, 2, 1, 2, 1, 2])
    result = sol.modifiedList([1], head)
    assert to_list(result) == [2, 2, 2], f"Test 2 failed: {to_list(result)}"
    print("Test 2 passed: [1,2,1,2,1,2] -> [2,2,2]")

    # 测试用例 3: nums = [5], head = [1,2,3,4] -> [1,2,3,4]
    head = build_list([1, 2, 3, 4])
    result = sol.modifiedList([5], head)
    assert to_list(result) == [1, 2, 3, 4], f"Test 3 failed: {to_list(result)}"
    print("Test 3 passed: [1,2,3,4] -> [1,2,3,4]")

    print("\n所有测试通过！")
