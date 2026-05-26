import java.util.*;

/**
 * 1600. 王位继承顺序
 * Throne Inheritance
 */
class ThroneInheritance {

    /** 国王的名字 */
    private String king;
    /** 存储每个父节点的子节点列表（按出生顺序） */
    private Map<String, List<String>> children;
    /** 记录已故人员 */
    private Set<String> dead;

    /**
     * 初始化
     * @param kingName 国王的名字
     */
    public ThroneInheritance(String kingName) {
        king = kingName;
        children = new HashMap<>();
        children.put(kingName, new ArrayList<>());
        dead = new HashSet<>();
    }

    /**
     * parentName 的孩子 childName 出生
     * @param parentName 父节点名字
     * @param childName  孩子名字
     */
    public void birth(String parentName, String childName) {
        // 确保父节点的列表存在
        children.putIfAbsent(parentName, new ArrayList<>());
        children.get(parentName).add(childName);
        // 为孩子初始化子节点列表
        children.putIfAbsent(childName, new ArrayList<>());
    }

    /**
     * 标记 name 已故
     * @param name 人员名字
     */
    public void death(String name) {
        dead.add(name);
    }

    /**
     * 返回当前继承顺序（排除已故人员）
     * 继承顺序 = 前序遍历（DFS），先父节点再子节点
     * @return 继承顺序列表
     */
    public List<String> getInheritanceOrder() {
        List<String> result = new ArrayList<>();
        dfs(king, result);
        return result;
    }

    /** 前序遍历家族树 */
    private void dfs(String name, List<String> result) {
        if (!dead.contains(name)) {
            result.add(name);
        }
        for (String child : children.getOrDefault(name, new ArrayList<>())) {
            dfs(child, result);
        }
    }

    /**
     * 测试主方法
     */
    public static void main(String[] args) {
        ThroneInheritance t = new ThroneInheritance("king");

        // king > andy
        t.birth("king", "andy");
        // king > andy > bob
        t.birth("king", "bob");
        // king > andy > bob > catherine
        t.birth("king", "catherine");
        // king > andy > matthew > bob > catherine
        t.birth("andy", "matthew");
        // king > andy > matthew > bob > alex > catherine
        t.birth("bob", "alex");
        // king > andy > matthew > bob > alex > asha > catherine
        t.birth("bob", "asha");

        List<String> order1 = t.getInheritanceOrder();
        System.out.println("继承顺序: " + order1);
        assert order1.equals(Arrays.asList("king", "andy", "matthew", "bob", "alex", "asha", "catherine"));

        // bob 去世
        t.death("bob");
        List<String> order2 = t.getInheritanceOrder();
        System.out.println("Bob 去世后: " + order2);
        assert order2.equals(Arrays.asList("king", "andy", "matthew", "alex", "asha", "catherine"));

        System.out.println("所有测试通过！");
    }
}

// 复杂度分析:
// - 时间复杂度: birth() O(1), death() O(1), getInheritanceOrder() O(N)，其中 N 为家族成员总数
// - 空间复杂度: O(N)，存储家族树结构
