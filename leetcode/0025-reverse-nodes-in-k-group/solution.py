from typing import Optional


# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def reverseKGroup(self, head: Optional[ListNode], k: int) -> Optional[ListNode]:
        """
        每 k 个节点一组翻转链表。
        使用迭代方法，O(1) 额外空间。

        思路：
        1. 用 dummy 节点简化边界处理
        2. 遍历链表，每 k 个节点一组进行翻转
        3. 翻转后将反转后的子链表接回原链表
        4. 不足 k 个的节点保持原有顺序
        """
        if not head or k == 1:
            return head

        # 创建 dummy 节点，简化头部处理
        dummy = ListNode(0)
        dummy.next = head

        # prevGroup 指向当前待翻转组的前一个节点
        prevGroup = dummy

        while True:
            # 检查是否有足够的 k 个节点
            groupStart = prevGroup.next
            end = prevGroup
            for _ in range(k):
                end = end.next
                if not end:
                    # 不足 k 个节点，直接返回结果
                    return dummy.next

            # 记录下一组的起始节点
            nextGroup = end.next

            # 翻转当前 k 个节点
            # 标准链表翻转：将每个节点逐个插入到 prevGroup 之后
            prev = nextGroup  # 翻转后的尾节点指向下一组
            curr = groupStart
            while curr != nextGroup:
                temp = curr.next
                curr.next = prev
                prev = curr
                curr = temp

            # 将翻转后的子链表接回主链表
            prevGroup.next = end
            # prevGroup 移动到下一组的前一个位置（即当前组的起始节点）
            prevGroup = groupStart

        return dummy.next


# 辅助函数：从列表创建链表
def create_linked_list(arr):
    if not arr:
        return None
    head = ListNode(arr[0])
    curr = head
    for val in arr[1:]:
        curr.next = ListNode(val)
        curr = curr.next
    return head


# 辅助函数：将链表转为列表
def linked_list_to_list(head):
    result = []
    curr = head
    while curr:
        result.append(curr.val)
        curr = curr.next
    return result


if __name__ == "__main__":
    solution = Solution()

    # 测试用例 1：head = [1,2,3,4,5], k = 2 => [2,1,4,3,5]
    head1 = create_linked_list([1, 2, 3, 4, 5])
    result1 = solution.reverseKGroup(head1, 2)
    assert linked_list_to_list(result1) == [2, 1, 4, 3, 5], f"Test 1 failed: {linked_list_to_list(result1)}"
    print("Test 1 passed: [1,2,3,4,5], k=2 =>", linked_list_to_list(result1))

    # 测试用例 2：head = [1,2,3,4,5], k = 3 => [3,2,1,4,5]
    head2 = create_linked_list([1, 2, 3, 4, 5])
    result2 = solution.reverseKGroup(head2, 3)
    assert linked_list_to_list(result2) == [3, 2, 1, 4, 5], f"Test 2 failed: {linked_list_to_list(result2)}"
    print("Test 2 passed: [1,2,3,4,5], k=3 =>", linked_list_to_list(result2))

    # 测试用例 3：单节点，k=1
    head3 = create_linked_list([1])
    result3 = solution.reverseKGroup(head3, 1)
    assert linked_list_to_list(result3) == [1], f"Test 3 failed: {linked_list_to_list(result3)}"
    print("Test 3 passed: [1], k=1 =>", linked_list_to_list(result3))

    # 测试用例 4：k 等于链表长度
    head4 = create_linked_list([1, 2, 3])
    result4 = solution.reverseKGroup(head4, 3)
    assert linked_list_to_list(result4) == [3, 2, 1], f"Test 4 failed: {linked_list_to_list(result4)}"
    print("Test 4 passed: [1,2,3], k=3 =>", linked_list_to_list(result4))

    # 测试用例 5：k 大于链表长度的特殊情况（k <= n 由约束保证，但测试边界）
    head5 = create_linked_list([1, 2])
    result5 = solution.reverseKGroup(head5, 2)
    assert linked_list_to_list(result5) == [2, 1], f"Test 5 failed: {linked_list_to_list(result5)}"
    print("Test 5 passed: [1,2], k=2 =>", linked_list_to_list(result5))

    # 测试用例 6：长链表
    head6 = create_linked_list([1, 2, 3, 4, 5, 6])
    result6 = solution.reverseKGroup(head6, 2)
    assert linked_list_to_list(result6) == [2, 1, 4, 3, 6, 5], f"Test 6 failed: {linked_list_to_list(result6)}"
    print("Test 6 passed: [1,2,3,4,5,6], k=2 =>", linked_list_to_list(result6))

    print("\n所有测试用例通过！")
