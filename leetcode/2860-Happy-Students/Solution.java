import java.util.Arrays;
import java.util.List;

class Solution {
    /**
     * 统计让所有学生都开心的选组方式数量。
     * 
     * 思路：
     * 对 nums 排序后，设选中学生数为 k。
     * 选中学生 i：需满足 nums[i] < k
     * 未选中学生 j：需满足 nums[j] > k
     * 
     * 枚举 k (0..n)，检查前 k 个和剩余 n-k 个是否满足条件。
     */
    public int countWays(List<Integer> nums) {
        int n = nums.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nums.get(i);
        }
        Arrays.sort(arr);
        
        int ans = 0;
        for (int k = 0; k <= n; k++) {
            // 选中的学生（前 k 个）：需要 nums[i] < k
            if (k > 0 && arr[k - 1] >= k) {
                continue;
            }
            // 未选中的学生（后 n-k 个）：需要 nums[j] > k
            if (k < n && arr[k] <= k) {
                continue;
            }
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 示例 1
        assert sol.countWays(Arrays.asList(1, 1)) == 2 : "示例1失败";
        
        // 示例 2
        assert sol.countWays(Arrays.asList(6, 0, 3, 3, 6, 7, 2, 7)) == 3 : "示例2失败";
        
        // 边界测试
        assert sol.countWays(Arrays.asList(0)) == 1 : "边界1失败";
        assert sol.countWays(Arrays.asList(5)) == 1 : "边界2失败";
        assert sol.countWays(Arrays.asList(0, 1)) == 1 : "边界3失败";
        
        System.out.println("所有测试用例通过！");
    }
}
