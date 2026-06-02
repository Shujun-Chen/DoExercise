import java.util.*;

class Solution {
    private List<Integer> pathVals = new ArrayList<>();
    private List<Long> pathDist = new ArrayList<>();
    private Map<Integer, Integer> count = new HashMap<>();
    private int bestLen = -1;
    private int bestNodes;
    private int excess = 0;
    private int start = 0;
    private int n;
    private List<int[]>[] adj;
    private int[] nums;

    /**
     * 最长特殊路径 II
     * 特殊路径：从祖先到后代的向下路径，所有值互不相同，但允许恰好一个值出现两次。
     * 返回 [最长特殊路径长度, 所有最长路径中节点数的最小值]。
     *
     * 时间复杂度: O(n)，每个节点最多被加入和移除路径一次
     * 空间复杂度: O(n)，用于存储路径、计数和邻接表
     */
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        this.n = nums.length;
        this.nums = nums;
        this.bestNodes = n + 1;

        // 构建邻接表
        adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
            adj[e[1]].add(new int[]{e[0], e[2]});
        }

        dfs(0, -1, 0L);
        return new int[]{bestLen, bestNodes};
    }

    private void dfs(int u, int parent, long cumDist) {
        // 保存当前状态以便回溯恢复
        List<int[]> removed = new ArrayList<>();

        // 添加当前节点
        int val = nums[u];
        pathVals.add(val);
        pathDist.add(cumDist);
        int idx = pathVals.size() - 1;

        int oldCount = count.getOrDefault(val, 0);
        count.put(val, oldCount + 1);
        if (oldCount >= 1) {
            excess++;
        }

        // 收缩窗口：当 excess > 1 时，从窗口前端移除节点
        while (excess > 1) {
            int frontVal = pathVals.get(start);
            removed.add(new int[]{frontVal, count.get(frontVal)});
            count.put(frontVal, count.get(frontVal) - 1);
            if (count.get(frontVal) >= 1) {
                excess--;
            }
            start++;
        }

        // 计算当前有效路径的长度和节点数
        long currLen = pathDist.get(idx) - pathDist.get(start);
        int currNodes = idx - start + 1;

        // 更新全局最优
        if (currLen > bestLen) {
            bestLen = (int) currLen;
            bestNodes = currNodes;
        } else if (currLen == bestLen) {
            bestNodes = Math.min(bestNodes, currNodes);
        }

        // 遍历子节点
        for (int[] edge : adj[u]) {
            int v = edge[0], w = edge[1];
            if (v != parent) {
                dfs(v, u, cumDist + w);
            }
        }

        // 回溯：恢复收缩前的状态
        for (int i = removed.size() - 1; i >= 0; i--) {
            int[] r = removed.get(i);
            start--;
            count.put(r[0], r[1]);
        }

        // 移除当前节点
        pathVals.remove(idx);
        pathDist.remove(idx);
        count.put(val, count.get(val) - 1);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        int[][] edges1 = {{0, 1, 1}, {1, 2, 3}, {1, 3, 1}, {2, 4, 6},
                          {4, 7, 2}, {3, 5, 2}, {3, 6, 5}, {6, 8, 3}};
        int[] nums1 = {1, 1, 0, 3, 1, 2, 1, 1, 0};
        System.out.println("示例 1: " + Arrays.toString(sol.longestSpecialPath(edges1, nums1)));
        // 期望: [9, 3]

        // 示例 2
        int[][] edges2 = {{1, 0, 3}, {0, 2, 4}, {0, 3, 5}};
        int[] nums2 = {1, 1, 0, 2};
        System.out.println("示例 2: " + Arrays.toString(sol.longestSpecialPath(edges2, nums2)));
        // 期望: [5, 2]
    }
}
