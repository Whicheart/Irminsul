# Git 钩子

## 启用（每次全新克隆后执行一次）

```bash
git config core.hooksPath .githooks
```

`core.hooksPath` 属于本地配置，不会随仓库分发，因此每个克隆都需要执行一次。

## pre-commit

提交前扫描暂存区**新增/修改的行**，命中疑似密钥时阻止提交：

- 系统已安装 [gitleaks](https://github.com/gitleaks/gitleaks) 时，优先用 `gitleaks protect --staged` 做完整扫描（熵检测 + 规则库）；
- 未安装时使用脚本内置规则：私钥块、云厂商/平台访问令牌、带账号口令的连接串、口令或密钥字面量赋值等。环境变量占位（`${...}`）与明显的示例/占位值会被放行。

确认是误报时可临时跳过：

```bash
git commit --no-verify
```

请勿把它当成常规做法；真正敏感的值应放到环境变量或本地未跟踪的配置文件里（参见仓库 `.gitignore` 中的「本地环境配置与密钥」段落）。
