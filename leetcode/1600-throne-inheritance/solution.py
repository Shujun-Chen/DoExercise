from typing import List


class ThroneInheritance:
    """王位继承顺序类"""

    def __init__(self, kingName: str):
        """
        初始化
        :param kingName: 国王的名字
        """
        self.king = kingName
        # 用哈希表存储每个父节点的子节点列表（按出生顺序）
        self.children: dict = {kingName: []}
        # 用集合记录已故人员
        self.dead: set = set()

    def birth(self, parentName: str, childName: str) -> None:
        """
        parentName 的孩子 childName 出生
        :param parentName: 父节点名字
        :param childName: 孩子名字
        """
        if parentName not in self.children:
            self.children[parentName] = []
        self.children[parentName].append(childName)
        # 为 childName 初始化子节点列表
        if childName not in self.children:
            self.children[childName] = []

    def death(self, name: str) -> None:
        """
        标记 name 已故
        :param name: 人员名字
        """
        self.dead.add(name)

    def getInheritanceOrder(self) -> List[str]:
        """
        返回当前继承顺序（排除已故人员）
        继承顺序 = 前序遍历（DFS），先父节点再子节点
        :return: 继承顺序列表
        """
        result = []

        def dfs(name: str) -> None:
            """前序遍历家族树"""
            if name not in self.dead:
                result.append(name)
            for child in self.children.get(name, []):
                dfs(child)

        dfs(self.king)
        return result


# 测试用例
if __name__ == "__main__":
    t = ThroneInheritance("king")               # 继承顺序: king
    t.birth("king", "andy")                     # king > andy
    t.birth("king", "bob")                      # king > andy > bob
    t.birth("king", "catherine")                # king > andy > bob > catherine
    t.birth("andy", "matthew")                  # king > andy > matthew > bob > catherine
    t.birth("bob", "alex")                      # king > andy > matthew > bob > alex > catherine
    t.birth("bob", "asha")                      # king > andy > matthew > bob > alex > asha > catherine
    order1 = t.getInheritanceOrder()
    print("继承顺序:", order1)
    assert order1 == ["king", "andy", "matthew", "bob", "alex", "asha", "catherine"]

    t.death("bob")                              # bob 去世
    order2 = t.getInheritanceOrder()
    print("Bob 去世后:", order2)
    assert order2 == ["king", "andy", "matthew", "alex", "asha", "catherine"]

    print("所有测试通过！")

# 复杂度分析:
# - 时间复杂度: birth() O(1), death() O(1), getInheritanceOrder() O(N)，其中 N 为家族成员总数
# - 空间复杂度: O(N)，存储家族树结构
