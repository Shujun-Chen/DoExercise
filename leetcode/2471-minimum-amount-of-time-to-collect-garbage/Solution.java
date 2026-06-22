class Solution {
    /**
     * 计算收集所有垃圾的最短时间。
     *
     * 思路：
     * 三种垃圾（M/P/G）由三辆独立卡车收集，每辆从 house 0 出发按顺序前进。
     * 因为同一时刻只能有一辆车操作，总时间 = 三辆车各自时间之和。
     * 对每种类型：总时间 = 收集时间 + 行驶到最远有该垃圾的房子的路程。
     */
    public int garbageCollection(String[] garbage, int[] travel) {
        int n = garbage.length;

        // 每种垃圾的最后出现位置
        int lastM = 0, lastP = 0, lastG = 0;
        // 每种垃圾的总数量
        int countM = 0, countP = 0, countG = 0;

        for (int i = 0; i < n; i++) {
            String g = garbage[i];
            for (int j = 0; j < g.length(); j++) {
                char c = g.charAt(j);
                if (c == 'M') {
                    countM++;
                    lastM = i;
                } else if (c == 'P') {
                    countP++;
                    lastP = i;
                } else { // 'G'
                    countG++;
                    lastG = i;
                }
            }
        }

        // 计算前缀行驶时间
        // travel[i] = 从 house i 到 house i+1 的时间
        // 到 house k 需要 sum(travel[0..k-1])
        int[] prefixTravel = new int[n];
        for (int i = 1; i < n; i++) {
            prefixTravel[i] = prefixTravel[i - 1] + travel[i - 1];
        }

        int total = 0;
        // 金属 M
        total += countM + prefixTravel[lastM];
        // 纸张 P
        total += countP + prefixTravel[lastP];
        // 玻璃 G
        total += countG + prefixTravel[lastG];

        return total;
    }

    // 时间复杂度: O(N * K)，N = garbage.length, K = 字符串平均长度(≤10)
    // 空间复杂度: O(N) 用于前缀和数组，可优化为 O(1)

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1
        String[] garbage1 = {"G", "P", "GP", "GG"};
        int[] travel1 = {2, 4, 3};
        System.out.println(sol.garbageCollection(garbage1, travel1)); // 预期: 21

        // 示例 2
        String[] garbage2 = {"MMM", "PGM", "GP"};
        int[] travel2 = {3, 10};
        System.out.println(sol.garbageCollection(garbage2, travel2)); // 预期: 37
    }
}
