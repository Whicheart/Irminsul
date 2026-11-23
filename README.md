# irminsul

游戏百科资料 API。

> 本项目仅用于学习与研究。

## 目录

- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [配置](#配置)
- [开发](#开发)
- [接口](#接口)

## 技术栈

| 类别      | 选型                                                           |
| --------- | -------------------------------------------------------------- |
| 运行时    | Java 17                                                        |
| 框架      | Spring Boot 4.0.1                                              |
| 持久化    | Spring Data MongoDB                                            |
| 接口文档  | springdoc-openapi 3.0.0（Swagger UI）                          |
| 序列化    | Jackson        |
| 辅助      | Lombok；Caffeine                           |
| 构建/格式 | Maven Wrapper（Maven 3.9.12）、Spotless + palantir-java-format |

## 快速开始

前置条件：

- JDK 17 及以上
- 可访问的 MongoDB，且已完成数据导入
- 如未完成导入，可通过[脚本](https://github.com/Whicheart/DataImporter)进行导入

```bash
git clone <repo-url> irminsul
cd irminsul

# 可选：默认就是本地 27017 的 GI 库
export MONGODB_URI='mongodb://localhost:27017/GI'

# 启动（Windows 用 mvnw.cmd spring-boot:run）
./mvnw spring-boot:run
```

启动后：

- 服务地址 `http://localhost:8080`
- Swagger UI `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON `http://localhost:8080/v3/api-docs`

打包与运行：

```bash
./mvnw clean package
java -jar target/irminsul-0.0.1-SNAPSHOT.jar
```

## 配置

| 配置项        | 默认值                         | 说明                               |
| ------------- | ------------------------------ | ---------------------------------- |
| `MONGODB_URI` | `mongodb://localhost:27017/GI` | 连接串；带账号口令时用环境变量注入 |
| `server.port` | `8080`                         | 可用 `--server.port=9090` 覆盖     |

仓库自带 `.gitignore` 规则与提交前密钥检查，启用方式见 [.githooks/README.md](.githooks/README.md)：

```bash
git config core.hooksPath .githooks
```

## 开发

```bash
./mvnw test              # 目前仅 contextLoads，连接信息取自同一份配置
./mvnw spotless:apply    # 格式化
./mvnw verify            # 会执行 spotless:check，格式不合规即失败
```

代码风格由 `.editorconfig` 约定：UTF-8、LF、4 空格缩进（`yml`/`xml`/`json` 为 2 空格），`.cmd` 使用 CRLF。

`.gitattributes` 负责把文本文件在仓库内统一为 LF，并保留 `mvnw`（LF）与 `*.cmd`（CRLF）的差异，避免跨平台提交产生整文件换行改动。

提交前会扫描暂存区的密钥特征，启用与绕过方式见 [.githooks/README.md](.githooks/README.md)。

## 接口

所有接口统一返回 `{ "code": 200, "msg": "ok", "data": ... }`，`code` 非 200 表示业务或系统错误。

以下接口参数中，name 参数采用模糊包含，剩余参数可重复传参或逗号分隔。

### 角色

| 方法与路径                            | 说明                                                       |
| ------------------------------------- |----------------------------------------------------------|
| `GET /api/avatar/list`                | 角色列表。可选参数：`name`、`elements`、`weaponTypes`、`qualityTypes` |
| `GET /api/avatar/{id}/base-info`      | 角色基础信息                                                   |
| `GET /api/avatar/{id}/outfits`        | 角色时装                                                     |
| `GET /api/avatar/{id}/stories`        | 角色故事                                                     |
| `GET /api/avatar/{id}/voices`         | 角色语音                                                     |
| `GET /api/avatar/{id}/constellations` | 角色命座                                                     |
| `GET /api/avatar/{id}/skills`         | 角色天赋                                                     |

### 武器

| 方法与路径                       | 说明                                               |
| -------------------------------- | -------------------------------------------------- |
| `GET /api/weapon/list`           | 武器列表。可选参数：`name`、`weaponTypes`、`ranks` |
| `GET /api/weapon/{id}/base-info` | 武器基础信息                                       |

### 圣遗物

| 方法与路径                          | 说明                       |
| ----------------------------------- |--------------------------|
| `GET /api/reliquary/list`           | 圣遗物套装列表。可选参数：`name`、`ranks` |
| `GET /api/reliquary/prop-rank`      | 主副词条的数值表                 |
| `GET /api/reliquary/{id}/base-info` | 圣遗物基础信息                  |

### 敌对生物

| 方法与路径                        | 说明                                                                                    |
| --------------------------------- | --------------------------------------------------------------------------------------- |
| `GET /api/monster/list`           | 敌对生物列表。可选参数：`name`、`types` |
| `GET /api/monster/{id}/base-info` | 敌对生物基础信息                                        |
