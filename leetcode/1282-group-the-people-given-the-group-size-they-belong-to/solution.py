"""
1282. Group the People Given the Group Size They Belong To
Medium

题目：给定 n 个人，每个人有一个组大小 groupSizes[i]，表示该人必须在大小为 groupSizes[i] 的组中。
要求：返回所有组的列表，使得每个人恰好出现在一个大小正确的组中。

解题思路：
1. 使用字典按组大小分类收集人员
2. 当某个组大小的人员数量达到该大小时，形成一个组
3. 重复此过程直到所有人分配完毕

时间复杂度：O(n) - 遍历数组一次
空间复杂度：O(n) - 字典存储分组信息
"""

from typing import List


class Solution:
    def groupThePeople(self, groupSizes: List[int]) -> List[List[int]]:
        # 字典：key = 组大小，value = 当前收集的人员列表
        size_to_people = {}
        result = []
        
        for person_id, group_size in enumerate(groupSizes):
            # 将人员加入对应大小的桶中
            if group_size not in size_to_people:
                size_to_people[group_size] = []
            size_to_people[group_size].append(person_id)
            
            # 当桶满时，形成一个组并重置桶
            if len(size_to_people[group_size]) == group_size:
                result.append(size_to_people[group_size])
                size_to_people[group_size] = []
        
        return result


if __name__ == "__main__":
    # 测试用例
    solution = Solution()
    
    # 测试 1: [3,3,3,3,3,1,3] -> [[5],[0,1,2],[3,4,6]]
    groupSizes1 = [3, 3, 3, 3, 3, 1, 3]
    result1 = solution.groupThePeople(groupSizes1)
    print(f"Input:  {groupSizes1}")
    print(f"Output: {result1}")
    print()
    
    # 测试 2: [2,1,3,3,3,2] -> [[1],[0,5],[2,3,4]]
    groupSizes2 = [2, 1, 3, 3, 3, 2]
    result2 = solution.groupThePeople(groupSizes2)
    print(f"Input:  {groupSizes2}")
    print(f"Output: {result2}")
    print()
    
    # 测试 3: [1,1] -> [[0],[1]]
    groupSizes3 = [1, 1]
    result3 = solution.groupThePeople(groupSizes3)
    print(f"Input:  {groupSizes3}")
    print(f"Output: {result3}")
