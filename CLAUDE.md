# DoExercise — Daily LeetCode Practice

## 仓库结构
```
leetcode/
└── {编号}-{题目名}/
    ├── solution.py       # Python 解法
    ├── Solution.java     # Java 解法
    └── README.md         # 题解说明
```

## 每日任务
每天晚上 8 点，从 LeetCode 国际站（leetcode.com）随机选一道题，完成：
1. 用 Python 写出解法（含注释）
2. 用 Java 写出解法（含注释）
3. 写题解说明（README.md）
4. git commit & push

## 题解规范

### solution.py
- 类名/函数名与原题一致
- 注释写在关键逻辑处，用中文
- 包含时间和空间复杂度注释
- 包含测试用例（`if __name__ == "__main__":`）

### Solution.java
- 类名 `Solution`，方法签名与原题一致
- 代码注释用中文
- 包含复杂度注释
- 必须有 `main` 方法测试

### README.md 格式
```markdown
# {题号}. {题目名称}

**难度：** {Easy/Medium/Hard}

## 题目描述
（简要描述）

## 解题思路
（中文思路分析，分步骤说明）

## 复杂度分析
- **时间复杂度：** O(?)
- **空间复杂度：** O(?)

## Python 解法要点
（关键点说明）

## Java 解法要点
（关键点说明）
```

## Git 规范
- commit message 格式：`feat: Add solution for {题号}. {题目名}`
- 每个题解一个独立 commit
