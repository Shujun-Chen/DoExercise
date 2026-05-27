from typing import List


class Solution:
    def canThreePartsEqualSum(self, arr: List[int]) -> bool:
        """
        判断能否将数组分成三个和相等的非空连续子数组。

        思路：
        1. 计算数组总和 total_sum
        2. 如果 total_sum 不能被 3 整除，则无法平分
        3. target = total_sum // 3 是每个部分的目标和
        4. 遍历数组，累加部分和，每当部分和等于 target 时，计数 +1 并重置累加器
        5. 如果找到至少 3 个和为 target 的部分则返回 True

        时间复杂度：O(n) —— 只需遍历一次数组
        空间复杂度：O(1) —— 仅用常数变量
        """
        total_sum = sum(arr)

        # 如果总和不能被 3 整除，直接返回 False
        if total_sum % 3 != 0:
            return False

        target = total_sum // 3
        part_sum = 0  # 当前部分和
        count = 0    # 已找到的和为 target 的部分数

        for num in arr:
            part_sum += num
            if part_sum == target:
                count += 1
                part_sum = 0

        # 至少找到 3 个部分，多余的部分和为 0 可以合并到最后一个部分中
        return count >= 3


if __name__ == "__main__":
    s = Solution()

    # 示例 1
    arr1 = [0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1]
    assert s.canThreePartsEqualSum(arr1) == True, "示例 1 应返回 True"

    # 示例 2
    arr2 = [0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1]
    assert s.canThreePartsEqualSum(arr2) == False, "示例 2 应返回 False"

    # 示例 3
    arr3 = [3, 3, 6, 5, -2, 2, 5, 1, -9, 4]
    assert s.canThreePartsEqualSum(arr3) == True, "示例 3 应返回 True"

    # 边界：数组只有 3 个元素
    arr4 = [1, 1, 1]
    assert s.canThreePartsEqualSum(arr4) == True, "三个相等元素应返回 True"

    # 边界：总和为 0 的情况
    arr5 = [0, 0, 0, 0, 0]
    assert s.canThreePartsEqualSum(arr5) == True, "全零数组应返回 True"

    print("所有测试用例通过！")
