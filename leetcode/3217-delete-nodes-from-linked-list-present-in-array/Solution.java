/**
 * 3217. Delete Nodes From Linked List Present in Array
 * Difficulty: Medium
 * Tags: Array, Hash Table, Linked List
 *
 * 思路：使用哈希集合存储 nums 中的所有值，然后遍历链表，
 * 跳过所有值在集合中的节点。使用虚拟头节点简化边界处理。
 *
 * 时间复杂度：O(n + m)，其中 n 是链表长度，m 是 nums 数组长度
 * 空间复杂度：O(m)，哈希集合存储 nums 的值
 */

import java.util.HashSet;
import java.util.Set;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        // 将 nums 转换为集合，便于 O(1) 查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        // 使用虚拟头节点，方便处理头节点被删除的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // prev 指向最后一个确认保留的节点
        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {
            if (numSet.contains(curr.val)) {
                // 当前节点需要删除，跳过它
                prev.next = curr.next;
            } else {
                // 当前节点保留，移动 prev
                prev = curr;
            }
            curr = curr.next;
        }

        return dummy.next;
    }

    // 辅助方法：从数组构建链表
    private static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : arr) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    // 辅助方法：将链表转为数组便于比较
    private static int[] toArray(ListNode head) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    // 测试用例
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 测试用例 1: nums = [1,2,3], head = [1,2,3,4,5] -> [4,5]
        ListNode head1 = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = sol.modifiedList(new int[]{1, 2, 3}, head1);
        int[] arr1 = toArray(result1);
        assert arr1.length == 2 && arr1[0] == 4 && arr1[1] == 5
            : "Test 1 failed";
        System.out.println("Test 1 passed: [1,2,3,4,5] -> [4,5]");

        // 测试用例 2: nums = [1], head = [1,2,1,2,1,2] -> [2,2,2]
        ListNode head2 = buildList(new int[]{1, 2, 1, 2, 1, 2});
        ListNode result2 = sol.modifiedList(new int[]{1}, head2);
        int[] arr2 = toArray(result2);
        assert arr2.length == 3 && arr2[0] == 2 && arr2[1] == 2 && arr2[2] == 2
            : "Test 2 failed";
        System.out.println("Test 2 passed: [1,2,1,2,1,2] -> [2,2,2]");

        // 测试用例 3: nums = [5], head = [1,2,3,4] -> [1,2,3,4]
        ListNode head3 = buildList(new int[]{1, 2, 3, 4});
        ListNode result3 = sol.modifiedList(new int[]{5}, head3);
        int[] arr3 = toArray(result3);
        assert arr3.length == 4 && arr3[0] == 1 && arr3[1] == 2 && arr3[2] == 3 && arr3[3] == 4
            : "Test 3 failed";
        System.out.println("Test 3 passed: [1,2,3,4] -> [1,2,3,4]");

        System.out.println("\n所有测试通过！");
    }
}
