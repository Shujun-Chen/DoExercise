class Solution(object):
    def findRestaurant(self, list1, list2):
        """
        :type list1: List[str]
        :type list2: List[str]
        :rtype: List[str]
        """
        # 将 list1 中的字符串及其索引存入哈希表
        index_map = {}
        for i, s in enumerate(list1):
            index_map[s] = i

        result = []
        min_sum = float('inf')

        # 遍历 list2，查找共同字符串，计算索引和
        for j, s in enumerate(list2):
            if s in index_map:
                total = index_map[s] + j
                # 发现更小的索引和 → 重置结果
                if total < min_sum:
                    min_sum = total
                    result = [s]
                # 相同的最小索引和 → 追加到结果
                elif total == min_sum:
                    result.append(s)

        return result


if __name__ == "__main__":
    sol = Solution()

    # 测试用例 1
    list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]
    list2 = ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
    assert sol.findRestaurant(list1, list2) == ["Shogun"]

    # 测试用例 2
    list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]
    list2 = ["KFC", "Shogun", "Burger King"]
    assert sol.findRestaurant(list1, list2) == ["Shogun"]

    # 测试用例 3：多个最小索引和
    list1 = ["happy", "sad", "good"]
    list2 = ["sad", "happy", "good"]
    result = sol.findRestaurant(list1, list2)
    assert set(result) == {"sad", "happy"}

    print("All test cases passed!")

# 时间复杂度：O(n + m)，其中 n 和 m 分别为 list1 和 list2 的长度
# 空间复杂度：O(n)，哈希表存储 list1 中所有字符串及其索引
