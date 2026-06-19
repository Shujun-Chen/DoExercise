import java.util.*;

/**
 * 3924. Minimum Threshold Path With Limited Heavy Edges
 *
 * 给定无向带权图，求最小的 threshold，使得存在一条从 source 到 target 的路径，
 * 其中 heavy 边（weight > threshold）的数量不超过 k。
 *
 * 解法：二分答案 + 0-1 BFS
 */
class Solution {
    private int n;
    private List<int[]>[] adj;
    private int source, target, k;

    public int minThreshold(int n, int[][] edges, int source, int target, int k) {
        // 起点即终点
        if (source == target) {
            return 0;
        }

        this.n = n;
        this.source = source;
        this.target = target;
        this.k = k;

        // 建图
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        int maxWeight = 0;
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
            maxWeight = Math.max(maxWeight, w);
        }

        // 检查是否连通
        if (!reachable(maxWeight)) {
            return -1;
        }

        // 二分查找最小 threshold
        int lo = 0, hi = maxWeight;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (reachable(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    /**
     * 0-1 BFS：检查给定 threshold 下，是否存在 ≤ k 条 heavy 边的 source→target 路径
     */
    private boolean reachable(int threshold) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        Deque<Integer> dq = new ArrayDeque<>();
        dq.addFirst(source);

        while (!dq.isEmpty()) {
            int u = dq.pollFirst();
            int d = dist[u];
            if (d > k) {
                continue; // 超过 heavy 限制，无需继续
            }
            if (u == target) {
                return true;
            }

            for (int[] edge : adj[u]) {
                int v = edge[0], w = edge[1];
                int heavy = (w > threshold) ? 1 : 0; // heavy 边代价 1
                int nd = d + heavy;
                if (nd < dist[v] && nd <= k) {
                    dist[v] = nd;
                    if (heavy == 0) {
                        dq.addFirst(v); // light 边优先出队
                    } else {
                        dq.addLast(v);  // heavy 边后出队
                    }
                }
            }
        }

        return dist[target] <= k;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        int n1 = 6;
        int[][] edges1 = {{0,1,5},{1,2,3},{3,4,4},{4,5,1},{1,4,2}};
        System.out.println(sol.minThreshold(n1, edges1, 0, 3, 1)); // 期望: 4

        // Example 2
        int n2 = 6;
        int[][] edges2 = {{0,1,3},{1,2,4},{3,4,5},{4,5,6}};
        System.out.println(sol.minThreshold(n2, edges2, 0, 4, 1)); // 期望: -1

        // Example 3: source == target
        int n3 = 4;
        int[][] edges3 = {{0,1,2},{1,2,2},{2,3,2},{3,0,2}};
        System.out.println(sol.minThreshold(n3, edges3, 0, 0, 0)); // 期望: 0

        // 自定义测试
        int n4 = 3;
        int[][] edges4 = {{0,1,10},{1,2,20}};
        System.out.println(sol.minThreshold(n4, edges4, 0, 2, 2)); // 期望: 0
    }
}
