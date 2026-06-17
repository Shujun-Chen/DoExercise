// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    /**
     * 每 k 个节点一组翻转链表。
     * 使用迭代方法，O(1) 额外空间。
     *
     * 思路：
     * 1. 用 dummy 节点简化边界处理
     * 2. 遍历链表，每 k 个节点一组进行翻转
     * 3. 翻转后将反转后的子链表接回原链表
     * 4. 不足 k 个的节点保持原有顺序
     *
     * 时间复杂度：O(n)，其中 n 是链表长度
     * 空间复杂度：O(1)
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        // 创建 dummy 节点，简化头部处理
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // prevGroup 指向当前待翻转组的前一个节点
        ListNode prevGroup = dummy;

        while (true) {
            // 检查是否有足够的 k 个节点
            ListNode groupStart = prevGroup.next;
            ListNode end = prevGroup;

            for (int i = 0; i < k; i++) {
                end = end.next;
                if (end == null) {
                    // 不足 k 个节点，直接返回结果
                    return dummy.next;
                }
            }

            // 记录下一组的起始节点
            ListNode nextGroup = end.next;

            // 翻转当前 k 个节点
            // 标准链表翻转：将每个节点逐个插入到 prevGroup 之后
            ListNode prev = nextGroup;  // 翻转后的尾节点指向下一组
            ListNode curr = groupStart;

            while (curr != nextGroup) {
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            // 将翻转后的子链表接回主链表
            prevGroup.next = end;
            // prevGroup 移动到下一组的前一个位置（即当前组的起始节点）
            prevGroup = groupStart;
        }
    }

    // 辅助方法：从数组创建链表
    private ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    // 辅助方法：将链表转为数组
    private int[] listToArray(ListNode head) {
        java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 测试用例 1：head = [1,2,3,4,5], k = 2 => [2,1,4,3,5]
        ListNode head1 = solution.createList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = solution.reverseKGroup(head1, 2);
        int[] expected1 = {2, 1, 4, 3, 5};
        assert java.util.Arrays.equals(solution.listToArray(result1), expected1) : "Test 1 failed";
        System.out.println("Test 1 passed: [1,2,3,4,5], k=2 => " + java.util.Arrays.toString(solution.listToArray(result1)));

        // 测试用例 2：head = [1,2,3,4,5], k = 3 => [3,2,1,4,5]
        ListNode head2 = solution.createList(new int[]{1, 2, 3, 4, 5});
        ListNode result2 = solution.reverseKGroup(head2, 3);
        int[] expected2 = {3, 2, 1, 4, 5};
        assert java.util.Arrays.equals(solution.listToArray(result2), expected2) : "Test 2 failed";
        System.out.println("Test 2 passed: [1,2,3,4,5], k=3 => " + java.util.Arrays.toString(solution.listToArray(result2)));

        // 测试用例 3：单节点，k=1
        ListNode head3 = solution.createList(new int[]{1});
        ListNode result3 = solution.reverseKGroup(head3, 1);
        int[] expected3 = {1};
        assert java.util.Arrays.equals(solution.listToArray(result3), expected3) : "Test 3 failed";
        System.out.println("Test 3 passed: [1], k=1 => " + java.util.Arrays.toString(solution.listToArray(result3)));

        // 测试用例 4：k 等于链表长度
        ListNode head4 = solution.createList(new int[]{1, 2, 3});
        ListNode result4 = solution.reverseKGroup(head4, 3);
        int[] expected4 = {3, 2, 1};
        assert java.util.Arrays.equals(solution.listToArray(result4), expected4) : "Test 4 failed";
        System.out.println("Test 4 passed: [1,2,3], k=3 => " + java.util.Arrays.toString(solution.listToArray(result4)));

        // 测试用例 5：k 等于链表长度
        ListNode head5 = solution.createList(new int[]{1, 2});
        ListNode result5 = solution.reverseKGroup(head5, 2);
        int[] expected5 = {2, 1};
        assert java.util.Arrays.equals(solution.listToArray(result5), expected5) : "Test 5 failed";
        System.out.println("Test 5 passed: [1,2], k=2 => " + java.util.Arrays.toString(solution.listToArray(result5)));

        // 测试用例 6：长链表
        ListNode head6 = solution.createList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode result6 = solution.reverseKGroup(head6, 2);
        int[] expected6 = {2, 1, 4, 3, 6, 5};
        assert java.util.Arrays.equals(solution.listToArray(result6), expected6) : "Test 6 failed";
        System.out.println("Test 6 passed: [1,2,3,4,5,6], k=2 => " + java.util.Arrays.toString(solution.listToArray(result6)));

        System.out.println("\n所有测试用例通过！");
    }
}
