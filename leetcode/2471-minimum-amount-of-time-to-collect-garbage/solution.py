from typing import List


class Solution:
    def garbageCollection(self, garbage: List[str], travel: List[int]) -> int:
        """
        计算收集所有垃圾的最短时间。

        思路：
        三种垃圾（M/P/G）由三辆独立的卡车收集，每辆卡车从 house 0 出发，
        只能按顺序前进。因为三辆车可以同时工作（但同一时刻只能有一辆车行驶
        或收集），所以总时间 = 三辆车的各自时间之和。
        
        对每种垃圾类型：
        - 收集时间 = 该类型在所有房子的总数量
        - 行驶时间 = 从 house 0 到包含该类型的最远房子的路程之和
        """
        # 记录每种垃圾的最后出现位置和总数量
        last_pos = {'M': 0, 'P': 0, 'G': 0}
        total_count = {'M': 0, 'P': 0, 'G': 0}

        for i, g in enumerate(garbage):
            for c in g:
                total_count[c] += 1
                last_pos[c] = i

        total_minutes = 0
        for c in ('M', 'P', 'G'):
            # 收集时间
            total_minutes += total_count[c]
            # 行驶时间：从 house 0 到 last_pos[c] 的路程
            if last_pos[c] > 0:
                total_minutes += sum(travel[:last_pos[c]])

        return total_minutes

    # 时间复杂度: O(N * K)，其中 N = len(garbage), K = 每个字符串平均长度（最多10）
    # 空间复杂度: O(1)


if __name__ == "__main__":
    sol = Solution()

    # 示例 1
    garbage1 = ["G", "P", "GP", "GG"]
    travel1 = [2, 4, 3]
    print(sol.garbageCollection(garbage1, travel1))  # 预期: 21

    # 示例 2
    garbage2 = ["MMM", "PGM", "GP"]
    travel2 = [3, 10]
    print(sol.garbageCollection(garbage2, travel2))  # 预期: 37
